package com.urbanlife.rest;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.User;
import com.urbanlife.services.DataServices;
import com.urbanlife.services.login.LoginService;
import com.urbanlife.util.EncryptDecrypt;
import com.urbanlife.util.Mailer;

/**
 * 
 * @author jeevak_gajbhiye
 *
 */
@Path("/api/v1/login")
public class UrbanLifeLogin {
	DataServices dataServices;
	@Context HttpServletRequest request;
	static final Logger logger = Logger.getLogger(UrbanLifeLogin.class);
	
	/**
	 * Service api to check if email id exist
	 */
	@Consumes("*/*")
	@GET
	@Produces("application/json")
	@Path(value = "/emailValidation/{emailid}")
	public Response emailValidate(@PathParam("emailid") String emailId)
			throws UrbanLifeException {
		logger.info("validating email for" + emailId);
		LoginService loginService = new LoginService();
		boolean status = false;

		try {
			status = loginService.emailValidate(emailId, User.class);
		} catch (Exception e) {

			throw new UrbanLifeException(UrbanLifeConstants.ID_NOT_PRESENT,
					UrbanLifeConstants.ID_NOT_PRESENT_CODE);
		}
		if (status) {
			Gson gson = new Gson();
			String emailFound = gson.toJson(UrbanLifeConstants.EMAIL_ID_FOUND);
			return Response.ok(emailFound, MediaType.APPLICATION_JSON).build();
		}
		Gson gson = new Gson();
		String emailnotfound = gson
				.toJson(UrbanLifeConstants.EMAIL_ID_NOT_FOUND);
		return Response.status(Response.Status.NOT_FOUND).entity(emailnotfound)
				.build();
	}

	
	/**
	 * login Merchant service api
	 * 
	 * @param username   and password
	 * @return Response
	 * @throws UrbanLifeException
	 */
	@Consumes("application/json")
	@POST
	@Produces("application/json")
	@Path(value = "/auth")
	public Response loginMerchant(InputStream inputStream)
			throws UrbanLifeException {
	
		HttpSession session = request.getSession();
		logger.info("login started");
		LoginService loginService = new LoginService();
		String response = null;
		Map<String, String> returnedMap = new HashMap<String, String>();
		
		try {
			
			
			Map<String, String> userDetailsMap = new HashMap<String, String>();
			Map<String, String> oldUserDetailsMap = null;
			ObjectMapper mapper = new ObjectMapper();
			// convert JSON string to Map
			userDetailsMap = mapper.readValue(inputStream,
					new TypeReference<HashMap<String, String>>() {
					});
			

			if (!userDetailsMap.get(UrbanLifeConstants.USERNAME).equals(UrbanLifeConstants.NULL)
					&& !userDetailsMap.get(UrbanLifeConstants.USERNAME).equals("")
					&& !userDetailsMap.get(UrbanLifeConstants.PASSWORD).equals(UrbanLifeConstants.NULL)
					&& !userDetailsMap.get(UrbanLifeConstants.PASSWORD).equals("")) {
				session.setAttribute("oldUserDetailsMap", userDetailsMap);
			}
			oldUserDetailsMap = (Map<String, String>) session
					.getAttribute("oldUserDetailsMap");
			String username = oldUserDetailsMap != null ? oldUserDetailsMap
					.get(UrbanLifeConstants.USERNAME) : userDetailsMap.get(UrbanLifeConstants.USERNAME);
			String password = oldUserDetailsMap != null ? oldUserDetailsMap
					.get(UrbanLifeConstants.PASSWORD) : userDetailsMap.get(UrbanLifeConstants.PASSWORD);
			String prefix = oldUserDetailsMap != null ? oldUserDetailsMap
					.get(UrbanLifeConstants.PREFIX) : userDetailsMap.get(UrbanLifeConstants.PREFIX);
			response = loginService.loginEntity(username, password, prefix,
					userDetailsMap.get(UrbanLifeConstants.USERTOKEN),
					userDetailsMap.get(UrbanLifeConstants.FLAG),
					userDetailsMap.get(UrbanLifeConstants.REMEMBERME));
			
			if(username!=null)
				session.setAttribute(UrbanLifeConstants.USERNAME,username);
		} catch (Exception e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.CREDENTIAL_INCORRECT,
					UrbanLifeConstants.CREDENTIAL_INCORRECT_CODE);
		}
		if (response == null) {
			throw new UrbanLifeException(
					UrbanLifeConstants.CREDENTIAL_INCORRECT,
					UrbanLifeConstants.CREDENTIAL_INCORRECT_CODE);
		}
		String username=(String) session.getAttribute(UrbanLifeConstants.USERNAME);
		String[] parts =username.split("@");
		String merchantName = parts[0];
		returnedMap.put("username",merchantName);
		returnedMap.put("token",response);
		Gson gson = new Gson();
		String returnedtoken = gson.toJson(returnedMap);
		return Response.ok(returnedtoken, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Forget password service api for merchant
	 * 
	 * @param inputStream
	 * @return
	 * @throws UrbanLifeException
	 */
	@Consumes("application/json")
	@POST
	@Produces("application/json")
	@Path(value = "/forgotpassword")
	public Response forgetPassword(InputStream inputStream)
			throws UrbanLifeException {
		logger.info("user requested for forgot password");
		boolean response = false;
		boolean status = false;
		User returnedUser = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			// convert JSON string to Map
			map = mapper.readValue(inputStream,
					new TypeReference<HashMap<String, String>>() {
					});
			dataServices = (DataServices) SpringApplicationContext
					.getBean(UrbanLifeConstants.DATASERVICE);
			Criterion criterion = Restrictions
					.eq(UrbanLifeConstants.EMAILID, map.get(UrbanLifeConstants.EMAILID));
			List<Object> userList = (List<Object>) dataServices.getInfo(
					User.class, criterion);
			returnedUser = (User) userList.get(0);

		} catch (Exception e) {
			throw new UrbanLifeException(UrbanLifeConstants.MAIL_ID_NOT_FOUND,
					UrbanLifeConstants.MAIL_ID_NOT_FOUND_CODE);
		}
		if (returnedUser.getEmailId().equalsIgnoreCase(map.get(UrbanLifeConstants.EMAILID))) {
			Mailer mail = new Mailer();
			try {
				String emailName = returnedUser.getFirstName() + " "
						+ returnedUser.getLastName();
				mail.sendForgetPassMail(map.get(UrbanLifeConstants.EMAILID), emailName);
				Gson gson = new Gson();
				String mailSent = gson.toJson(UrbanLifeConstants.MAIL_SENT);
				return Response.ok(mailSent, MediaType.APPLICATION_JSON)
						.build();
			} catch (Exception e) {
				throw new UrbanLifeException(
						UrbanLifeConstants.MAIL_SENT_UNSECESSFULL,
						UrbanLifeConstants.MAIL_SENT_UNSECESSFULL_CODE);
			}

		}
		Gson gson = new Gson();
		String mailNotFound = gson.toJson(UrbanLifeConstants.MAIL_ID_NOT_FOUND);
		return Response.ok(mailNotFound, MediaType.APPLICATION_JSON).build();
	}

	@Consumes("application/json")
	@POST
	@Produces("application/json")
	@Path(value = "/changepassword")
	public Response changePassword(InputStream inputStream)
			throws UrbanLifeException {
		logger.info("User changing password");
		EncryptDecrypt encrypt = new EncryptDecrypt();
		Map<String, String> map = new HashMap<String, String>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			// convert JSON string to Map
			map = mapper.readValue(inputStream,
					new TypeReference<HashMap<String, String>>() {
					});
			dataServices = (DataServices) SpringApplicationContext
					.getBean(UrbanLifeConstants.DATASERVICE);

			Criterion criterion = Restrictions
					.eq(UrbanLifeConstants.EMAILID, map.get(UrbanLifeConstants.EMAILID));
			List<Object> userList = (List<Object>) dataServices.getInfo(
					User.class, criterion);
			User returnedUser = (User) userList.get(0);
			returnedUser.setPassword(encrypt.encrypt(map.get(UrbanLifeConstants.PASSWORD)));

			dataServices.updateEntity(returnedUser, User.class);
		} catch (Exception e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.FAIL_TO_SET_PASSORD,
					UrbanLifeConstants.FAIL_TO_SET_PASSORD_CODE);
		}
		Gson gson = new Gson();
		String mailNotFound = gson
				.toJson(UrbanLifeConstants.PASSWORD_SET_SUCESSFULLY);
		return Response.ok(mailNotFound, MediaType.APPLICATION_JSON).build();

	}
	@Consumes("*/*")
    @GET
    @Produces("application/json")
    @Path(value = "/getsessiondata")
    public Response emailValidate()
                  throws UrbanLifeException {
           String username=null;
           HttpSession session=request.getSession();
           try{
           if(session!=null){
           username=(String) session.getAttribute(UrbanLifeConstants.USERNAME);
           }
           
           System.out.println("username #############"+username);
           }catch(Exception e){
                  throw new UrbanLifeException(
                               UrbanLifeConstants.SESSIONNULL,
                               UrbanLifeConstants.SESSIONNULL_CODE);
           }
           String[] parts =username.split("@");
           String sessionData = parts[0];
           Gson gson = new Gson();
           String returneddata = gson.toJson(sessionData);
           return Response.ok(returneddata, MediaType.APPLICATION_JSON).build();
	}
	

}
