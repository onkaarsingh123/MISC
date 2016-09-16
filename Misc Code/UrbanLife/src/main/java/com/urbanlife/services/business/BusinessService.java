package com.urbanlife.services.business;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;
import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.Account;
import com.urbanlife.model.Business;
import com.urbanlife.model.BusinessAttributes;
import com.urbanlife.model.BusinessManager;
import com.urbanlife.model.BusinessType;
import com.urbanlife.model.User;
import com.urbanlife.services.DataServices;
import com.urbanlife.util.Mailer;
import com.urbanlife.util.UrbanLifeUtils;

public class BusinessService {
	DataServices dataServices = (DataServices) SpringApplicationContext
			.getBean("dataServices");
	
	public Business addBusiness(String businessJsonString, 
			String photoUrl,String otherJsonString ,String businessMannagerStr,
			HttpServletRequest request) throws UrbanLifeException {

		Gson mapper = new Gson();
		BusinessAttributes returnedBusinessAttributes = null;
		BusinessType returnedBusinessType = null;
		Business returnedBusiness = null;
		BusinessManager returnedBusinessManager= null;

		Business business = mapper.fromJson(businessJsonString, Business.class);

		Account account = getBusinessAccount(request);

		returnedBusinessAttributes = addBusinessAttributes(otherJsonString,request);

		returnedBusinessType = addBusinessType(returnedBusinessAttributes,request);
		

		business.setAccount(account);
		business.setBusinessTypeId(returnedBusinessType);
		business.setPhotoUrl(photoUrl);
		business.setCreatedBy(UrbanLifeUtils.getUserName(request));
		business.setCreatedDate(UrbanLifeUtils.getCurrentdate());

		returnedBusiness = (Business) dataServices.addEntity(business,
				Business.class);
		if(null!=businessMannagerStr && !businessMannagerStr.equalsIgnoreCase("{}"))
		returnedBusinessManager = addBusinessManager(businessMannagerStr,returnedBusiness,request);
		return returnedBusiness;
		}
	
	private BusinessManager addBusinessManager(String businessMannagerStr,Business business,HttpServletRequest request)
			throws UrbanLifeException {
		Gson mapper = new Gson();
		BusinessManager businessmanager = new BusinessManager();
		User user  = mapper.fromJson(businessMannagerStr,User.class);
		user.setCreatedDate(UrbanLifeUtils.getCurrentdate());
		businessmanager.setBusiness(business);
		businessmanager.setEmail(user.getEmailId());
		businessmanager.setCreatedBy(UrbanLifeUtils.getUserName(request));
		businessmanager.setCreatedDate(UrbanLifeUtils.getCurrentdate());
		BusinessManager returnedBusinessManager = (BusinessManager) dataServices
				.addEntity(businessmanager, BusinessManager.class);
		user.setUserId(returnedBusinessManager.getId());
		User returnedUser = (User) dataServices
				.addEntity(user, User.class);
		if(returnedUser!=null && returnedBusinessManager!=null ){
			try {
				Mailer mail = new Mailer();
				String emailName = returnedUser.getFirstName();
				mail.sendBusinessManagerMail(returnedBusinessManager.getEmail(),
						emailName,business.getName());
			} catch (Exception e) {
				throw new UrbanLifeException(
						UrbanLifeConstants.MAIL_SENT_UNSECESSFULL,
						UrbanLifeConstants.MAIL_SENT_UNSECESSFULL_CODE);
			}
		}
		
		return returnedBusinessManager;
	}


	private BusinessAttributes addBusinessAttributes(String otherJsonString,HttpServletRequest request)
			throws UrbanLifeException {
		Gson mapper = new Gson();
		//Map other = mapper.fromJson(otherJsonString, Map.class);
		String cuisine  = getcuisine(otherJsonString);
		
		
		BusinessAttributes businessAttributes = new BusinessAttributes();
		BusinessAttributes returnedBusinessAttributes = null;
		businessAttributes.setCreatedBy(UrbanLifeUtils.getUserName(request));
		businessAttributes.setAttributeName("cuisines");
		businessAttributes.setAttributeType("array");
		businessAttributes.setAttributeValue(cuisine);
		businessAttributes.setCreatedDate(UrbanLifeUtils.getCurrentdate());

		returnedBusinessAttributes = (BusinessAttributes) dataServices
				.addEntity(businessAttributes, BusinessAttributes.class);

		return returnedBusinessAttributes;
	}

	private BusinessType addBusinessType(
			BusinessAttributes returnedBusinessAttributes,HttpServletRequest request)
			throws UrbanLifeException {
		BusinessType businessType = new BusinessType();
		BusinessType returnedBusinessType = null;

		businessType.setType("Restaurant");
		businessType.setAttr1(returnedBusinessAttributes);
		businessType.setCreatedBy(UrbanLifeUtils.getUserName(request));
		businessType.setCreatedDate(UrbanLifeUtils.getCurrentdate());

		returnedBusinessType = (BusinessType) dataServices.addEntity(
				businessType, BusinessType.class);
		return returnedBusinessType;
	}

	private Account getBusinessAccount(HttpServletRequest request) throws UrbanLifeException {
		List<Account> accountL = null;
		Account returnedaccount = null;
	
		try {
			Map<String, String> oldUserDetailsMap = null;
			HttpSession session = request.getSession();
//			oldUserDetailsMap = (Map<String, String>) session
//					.getAttribute(UrbanLifeConstants.OLDUSERDETAILSMAP);
			String emailId = null;
//			if(oldUserDetailsMap!=null)
			emailId = (String) session.getAttribute(UrbanLifeConstants.USERNAME);
			
			User user = (User) dataServices.getUserId(User.class, emailId);
			Criterion criterion = Restrictions.eq("businessOwnerId", user.getUserId());

			accountL = (List<Account>) dataServices.getInfo(Account.class,
					criterion);
			if(accountL != null ){
			returnedaccount = accountL.get(0);
			}
		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		}
		return returnedaccount;
	}

	private Business getBusiness(Integer id) throws UrbanLifeException {

		Business business = null;
		if (id == null) {
			throw new UrbanLifeException(UrbanLifeConstants.ID_NOT_PRESENT,
					UrbanLifeConstants.ID_NOT_PRESENT_CODE);
		}
		try {

			business = (Business) dataServices
					.getEntityById(id, Business.class);
		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		}
		return business;
	}



	// update

	public Business updateBusiness(String businessJsonString, Integer id,
			String photoUrl,Integer businessId) throws UrbanLifeException {

		Gson mapper = new Gson();
		BusinessAttributes returnedBusinessAttributes = null;
		BusinessType returnedBusinessType = null;
		Business returnedBusiness = null;

		Business business = mapper.fromJson(businessJsonString, Business.class);

		//Account account = getBusinessAccount(id);
		Business businessObj = getBusiness(businessId);
		if(businessObj != null && businessObj.getBusinessType()!= null){
			
		//	returnedBusinessType = updateBusinessType(businessObj.getBusinessType());
		}

//		returnedBusinessAttributes = updateBusinessAttributes();

		
		businessObj.setId(businessId);
		//business.setAccount(account);
		//business.setBusinessTypeId(returnedBusinessType);
		businessObj.setCountry(business.getCountry());
		businessObj.setPhotoUrl(photoUrl);
		businessObj.setModifiedDate(UrbanLifeUtils.getCurrentdate());

		return returnedBusiness = (Business) dataServices.updateEntity(businessObj,
				Business.class);
	}
 private String getcuisine(String cuisineStr){
	//String[] strArray= cuisineStr.split("[");
	        //    cuisineStr = "{"+"\"cuisines\":\""+cuisineStr+"\"" +"}" ;
	 
	 cuisineStr= cuisineStr.replaceAll("[{}]", " ");
	 cuisineStr= cuisineStr.replaceAll("\\]}", " ");
			 cuisineStr= cuisineStr.replaceAll("\\[", " ");
			 String[] cuisineArr= cuisineStr.split(" ,");
						
		 		StringBuffer result=new StringBuffer();
		 		result.append("[");
		 
		 		for(int i=0;i<cuisineArr.length;i++)
		 		{
		 			String name=cuisineArr[i];
		 			if(name.contains("true")){
		 				name.trim();
		 				String[] onecuisineStr = name.split(",");
		 						onecuisineStr = onecuisineStr[0].split(":");
		 						System.out.println(onecuisineStr);
		 						if(i< (cuisineArr.length-1) ){
		 						result.append("\""+onecuisineStr[1]+"\",");}
		 						else{
		 							result.append("\""+onecuisineStr[1]+"\"");
		 						}
		 				
		 			}
		 			
		 			
		 		}
		 		result.append("]");

		 		return result+"";
		 	}

	
	 
 
/*	private BusinessAttributes updateBusinessAttributes()
			throws UrbanLifeException {
		BusinessAttributes businessAttributes = new BusinessAttributes();
		BusinessAttributes returnedBusinessAttributes = null;

		businessAttributes.setAttributeName("cuisineType");
		businessAttributes.setAttributeType("array");
		businessAttributes.setAttributeValue("[“Thai”,”Indian”,”Chinese”]");
		businessAttributes.setCreatedDate(UrbanLifeUtils.getCurrentdate());

		returnedBusinessAttributes = (BusinessAttributes) dataServices
				.addEntity(businessAttributes, BusinessAttributes.class);

		return returnedBusinessAttributes;
	}

	private BusinessType updateBusinessType(
			BusinessAttributes returnedBusinessAttributes)
			throws UrbanLifeException {
		BusinessType businessType = new BusinessType();
		BusinessType returnedBusinessType = null;

		businessType.setType("Restaurant");
		businessType.setAttr1(returnedBusinessAttributes.getId());
		businessType.setCreatedDate(UrbanLifeUtils.getCurrentdate());

		returnedBusinessType = (BusinessType) dataServices.addEntity(
				businessType, BusinessType.class);
		return returnedBusinessType;
	}
	*/

}
