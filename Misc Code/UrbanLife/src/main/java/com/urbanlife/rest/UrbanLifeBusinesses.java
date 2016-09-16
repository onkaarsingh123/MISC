package com.urbanlife.rest;

import java.io.IOException;
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
import javax.ws.rs.core.Response.Status;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.google.gson.Gson;
import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.Account;
import com.urbanlife.model.Business;
import com.urbanlife.model.CuisineType;
import com.urbanlife.model.User;
import com.urbanlife.services.DataServices;
import com.urbanlife.services.business.BusinessService;
import com.urbanlife.util.UrbanLifeUtils;

//@Path("/api/v1/merchants/{id}/businesses")
@Path("/api/v1/business")
public class UrbanLifeBusinesses {
	DataServices dataServices;
	@Context
	HttpServletRequest request;
	
	@GET
	@Path("/{businessId}")
	@Produces("application/json")
	public Response getBusiness(
			@PathParam("businessId") Integer businessId)
			throws UrbanLifeException {
		Gson gson = new Gson();
		String json = null;
		if (businessId == null) {
			throw new UrbanLifeException(UrbanLifeConstants.ID_NOT_PRESENT,
					UrbanLifeConstants.ID_NOT_PRESENT_CODE);
		}

		Business returnedBusiness = null;
		try {
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			returnedBusiness = (Business) dataServices.getEntityById(
					businessId, Business.class);
			json = gson.toJson(returnedBusiness);

		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}

		return Response.ok(json, MediaType.APPLICATION_JSON).build();

	}
	
	@GET
	@Produces("application/json")
	@Path("/cuisine")
	public Response getCuisineType() throws UrbanLifeException {
		List<Object> cuisineTypeList = null;
		Gson gson = new Gson();
		String json = null;
		try {
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			cuisineTypeList = dataServices.getEntityList(CuisineType.class);
			json = gson.toJson(cuisineTypeList);
		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON).build();

	}
	
	@GET
	@Produces("application/json")
	@Path("/")
	public Response getAllBusiness() throws UrbanLifeException {
		List<Object> businessList = null;
		Gson gson = new Gson();
		String json = null;
		try {
			HttpSession session = request.getSession();
			String emailId = (String) session.getAttribute(UrbanLifeConstants.USERNAME);
			User user = (User) dataServices.getUserId(User.class, emailId);
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			Criterion criterion = Restrictions.eq("businessOwnerId",user.getUserId());
			List<Account> accountL = (List<Account>) dataServices.getInfo(Account.class,criterion);
			
			Criterion criterion1 = Restrictions.eq("account.id", accountL.get(0).getId());
			businessList = (List<Object>) dataServices.getInfo(Business.class,criterion1);
			json = gson.toJson(businessList);
		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON).build();

	}

	@Consumes("multipart/form-data")
	@Produces("application/json")
	@POST
	@Path(value = "/add")
	public Response addBusiness(MultipartFormDataInput formDataInput
			) throws UrbanLifeException, IOException {
		dataServices = (DataServices) SpringApplicationContext
				.getBean("dataServices");
		BusinessService businessService = new BusinessService();
		Business returnedBusiness = null;
		Gson gson = new Gson();
		String json = null;
		String otherJsonString = null;
		String businessMannagerStr = null;


		Map<String, List<InputPart>> registrationForm = formDataInput
				.getFormDataMap();

		List<InputPart> photoInputPart = registrationForm.get("photo");
		List<InputPart> merchantInputPart = registrationForm.get("business");
		List<InputPart> otherInputPart = registrationForm.get("other");
		List<InputPart> businessMannagerInputPart = registrationForm.get("user");
		String photoUrl = UrbanLifeUtils.savePhoto(photoInputPart,"PHOTO_PATH");
		
		for (InputPart inputPart : otherInputPart) {
			   otherJsonString = inputPart.getBody(String.class,
					null);
		}
		for (InputPart inputPart : businessMannagerInputPart) {
			businessMannagerStr = inputPart.getBody(String.class,
					null);
		}
		try {
			for (InputPart inputPart : merchantInputPart) {
				String businessJsonString = inputPart.getBody(String.class,
						null);
				returnedBusiness = businessService.addBusiness(
						businessJsonString, photoUrl,otherJsonString,businessMannagerStr,request);
				json = gson.toJson(returnedBusiness);
			}
		} 
		catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		} 
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON)
				.header("page_size", 1).header("offset", 0)
				.header("total_count", 1).build();

	}

	@Consumes("multipart/form-data")
	@Produces("application/json")
	@POST
	@Path(value = "/{businessId}/update")
	public Response updateBusiness(MultipartFormDataInput formDataInput,
			@PathParam("id") Integer id,
			@PathParam("businessId") Integer businessId)
			throws UrbanLifeException, IOException {
		dataServices = (DataServices) SpringApplicationContext
				.getBean("dataServices");
		BusinessService businessService = new BusinessService();
		Business returnedBusiness = null;
		Gson gson = new Gson();
		String json = null;
		if (id == null || businessId == null) {
			throw new UrbanLifeException(UrbanLifeConstants.ID_NOT_PRESENT,
					UrbanLifeConstants.ID_NOT_PRESENT_CODE);
		}

		Map<String, List<InputPart>> registrationForm = formDataInput
				.getFormDataMap();

		List<InputPart> photoInputPart = registrationForm.get("photo");
		List<InputPart> merchantInputPart = registrationForm.get("business");
		String photoUrl = UrbanLifeUtils.savePhoto(photoInputPart,"PHOTO_PATH");
		try {
			for (InputPart inputPart : merchantInputPart) {
				String businessJsonString = inputPart.getBody(String.class,
						null);
				returnedBusiness = businessService.updateBusiness(
						businessJsonString, id, photoUrl, businessId);
				json = gson.toJson(returnedBusiness);
			}
		} 
		catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		} 
		catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}
		return Response.ok(json, MediaType.APPLICATION_JSON)
				.header("page_size", 1).header("offset", 0)
				.header("total_count", 1).build();

	}

	@Consumes("multipart/form-data")
	@Produces("text/plain")
	@POST
	@Path(value = "/upload")
	public String uploadPhoto(MultipartFormDataInput formDataInput)
			throws UrbanLifeException {
		Map<String, List<InputPart>> registrationForm = formDataInput
				.getFormDataMap();
		UrbanLifeUtils.SERVER_PATH = request.getServletContext()
				.getRealPath("");

		List<InputPart> photoInputPart = registrationForm.get("photo");
		String photoUrl = UrbanLifeUtils.savePhoto(photoInputPart,
				"TEMP_PHOTO_PATH");
		String[] url = photoUrl.split("temp");
		return request.getServletContext().getContextPath() + "\\temp" + url[1];
	}

}
