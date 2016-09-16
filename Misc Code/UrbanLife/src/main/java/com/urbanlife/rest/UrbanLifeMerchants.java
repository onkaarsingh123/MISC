package com.urbanlife.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
/*import org.jboss.resteasy.logging.Logger;*/
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
//import org.jboss.resteasy.spi.validation.GeneralValidator;//spi.validation.ValidateRequest;
import com.google.gson.Gson;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.Account;
import com.urbanlife.model.Merchant;
import com.urbanlife.model.User;
import com.urbanlife.services.DataServices;
import com.urbanlife.services.PaymentService;
import com.urbanlife.services.login.LoginService;
import com.urbanlife.util.EncryptDecrypt;
import com.urbanlife.util.Mailer;
import com.urbanlife.util.MerchantValidation;
import com.urbanlife.util.UrbanLifeUtils;

@Path("api/v1/merchants")
public class UrbanLifeMerchants {

	DataServices dataServices;
	@Context
	HttpServletRequest request;
	static final Logger logger = Logger.getLogger(UrbanLifeMerchants.class);

	/**
	 * 
	 * @param merchant
	 * @return
	 * @throws UrbanLifeException
	 * @throws AddressException
	 */
	@Consumes("multipart/form-data")
	@Produces("application/json")
	@POST
	@Path(value = "/add")
	public Response addMerchant(MultipartFormDataInput formDataInput)
			throws UrbanLifeException, AddressException {
		boolean isValidated = false;
		Account returnedAccount = null;
		Account account = new Account();
		Customer stripeId = null;
		int exp_month = 0;
		int exp_year = 0;
		int ccv_no = 0;
		String cardNumber = null;
		Merchant returnedMerchant = null;
		Merchant merchant = new Merchant();
		User user = null;
		Gson gson = new Gson();
		String json = null;
		Map<String, List<InputPart>> registrationForm = formDataInput
				.getFormDataMap();
		try {
			List<InputPart> inputExp_month = registrationForm.get("exp_month");
			for (InputPart inputPart0 : inputExp_month) {
				exp_month = Integer.parseInt(inputPart0.getBody(String.class,
						null));
			}
			List<InputPart> inputExp_year = registrationForm.get("exp_year");
			for (InputPart inputPart1 : inputExp_year) {
				exp_year = Integer.parseInt(inputPart1.getBody(String.class,
						null));
			}
			List<InputPart> inputCvv_no = registrationForm.get("ccv_no");
			for (InputPart inputPart2 : inputCvv_no) {
				ccv_no = Integer.parseInt(inputPart2
						.getBody(String.class, null));
			}
			List<InputPart> inputCardNumber = registrationForm
					.get("cardNumber");
			for (InputPart inputPart3 : inputCardNumber) {
				cardNumber = inputPart3.getBody(String.class, null);
			}

			List<InputPart> photoInputPart = registrationForm.get("photo");
			List<InputPart> merchantInputPart = registrationForm
					.get("merchant");
			// store photo on disk and save the path to merchant object
			String photoUrl = UrbanLifeUtils.savePhoto(photoInputPart,
					"PHOTO_PATH");

			logger.info("creating merchant object");
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			for (InputPart inputPart : merchantInputPart) {
				String merchantJsonString = inputPart.getBody(String.class,
						null);
				Gson mapper = new Gson();

				user = mapper.fromJson(merchantJsonString, User.class);

				user.setPhotoUrl(photoUrl);
				user.setCreatedDate(new Date());
				user.setCreatedBy(user.getFirstName());
			}
			MerchantValidation merchantValidate = new MerchantValidation();
			isValidated = merchantValidate.validate(user);
			// validating user details
			if (isValidated) {
				merchant.setEmailId(user.getEmailId());
				merchant.setIsActive(false);
				merchant.setCreatedDate(new Date());
				// Payment Gateway code integration
				PaymentService paymentService = new PaymentService();
				stripeId = (Customer) paymentService.getStripeID(cardNumber,
						exp_month, exp_year, ccv_no, null,
						user.getAddressLine1(), user.getAddressLine2(),
						user.getCity(), user.getState(), user.getZipCode(),
						user.getCountry());
				if (stripeId != null) {
					// adding merchant object in DB
					returnedMerchant = (Merchant) dataServices.addEntity(
							merchant, Merchant.class);
					user.setUserId(returnedMerchant.getId());
					// adding user in DB
					dataServices.addEntity(user, User.class);

					account.setStripeId(stripeId.getId());
					if (stripeId.getDefaultSource() != null) {
						account.setCardId(stripeId.getDefaultSource());
					}
					if (stripeId.getDefaultSource() == null) {
						account.setCardId(stripeId.getDefaultCard());
					}
					account.setAccountState("cc_validation_requested");
					account.setBusinessOwnerId(returnedMerchant.getId());
					account.setCreatedDate(new Date());
					returnedAccount = (Account) dataServices.addEntity(account,
							Account.class);

					if (returnedMerchant != null) {
						Mailer mail = new Mailer();
						try {
							String emailName = user.getFirstName() + " "
									+ user.getLastName();
							mail.sendMail(returnedMerchant.getEmailId(),
									emailName);
						} catch (Exception e) {
							throw new UrbanLifeException(
									UrbanLifeConstants.MAIL_SENT_UNSECESSFULL,
									UrbanLifeConstants.MAIL_SENT_UNSECESSFULL_CODE);
						}
					}
				}

				if (stripeId == null) {
					throw new UrbanLifeException(
							UrbanLifeConstants.SERVER_DOWN_ERROR,
							UrbanLifeConstants.SERVER_DOWN_ERROR_CODE);
				}

			}
		} catch (IOException e) {
			throw new UrbanLifeException(UrbanLifeConstants.SERVER_DOWN_ERROR,
					UrbanLifeConstants.SERVER_DOWN_ERROR_CODE);
		}

		json = gson.toJson(returnedMerchant);
		return Response.ok(json, MediaType.APPLICATION_JSON)
				.header("page_size", 1).header("offset", 0)
				.header("total_count", 1).build();

	}

	/**
	 * 
	 * @param merchant
	 * @return
	 * @throws UrbanLifeException
	 * @throws AddressException
	 */
	@Consumes("multipart/form-data")
	@Produces("application/json")
	@POST
	@Path(value = "/{id}/update")
	public Response updateMerchant(MultipartFormDataInput formDataInput,
			@PathParam("id") int id) throws UrbanLifeException,
			AddressException {
		boolean isValidated = false;
		Account returnedAccount = null;
		Account account = new Account();
		Customer stripeId = null;
		int exp_month = 0;
		int exp_year = 0;
		int ccv_no = 0;
		Merchant returnedMerchant = null;
		Merchant merchant = new Merchant();
		boolean updateCardDetails = false;
		User user = null;
		Gson gson = new Gson();
		String json = null;
		Map<String, List<InputPart>> registrationForm = formDataInput
				.getFormDataMap();
		try {
			HttpSession session = request.getSession();
			List<InputPart> inputExp_month = registrationForm.get("exp_month");
			for (InputPart inputPart0 : inputExp_month) {
				exp_month = Integer.parseInt(inputPart0.getBody(String.class,
						null));
			}
			List<InputPart> inputExp_year = registrationForm.get("exp_year");
			for (InputPart inputPart1 : inputExp_year) {
				exp_year = Integer.parseInt(inputPart1.getBody(String.class,
						null));
			}
			List<InputPart> inputCvv_no = registrationForm.get("ccv_no");
			for (InputPart inputPart2 : inputCvv_no) {
				ccv_no = Integer.parseInt(inputPart2
						.getBody(String.class, null));
			}

			List<InputPart> photoInputPart = registrationForm.get("photo");
			List<InputPart> userInputPart = registrationForm.get("merchant");
			// store photo on disk and save the path to merchant object
			String photoUrl = UrbanLifeUtils.savePhoto(photoInputPart,
					"PHOTO_PATH");

			logger.info("updating merchant object");
			User oldUser = (User) session.getAttribute("returnedUser");
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			for (InputPart inputPart : userInputPart) {
				String userJsonString = inputPart.getBody(String.class, null);
				Gson mapper = new Gson();
				user = mapper.fromJson(userJsonString, User.class);
				user.setPhotoUrl(photoUrl);
				user.setModifiedDate(new Date());
				user.setModifiedBy(user.getFirstName());
				user.setId(oldUser.getId());
			}

			MerchantValidation merchantValidate = new MerchantValidation();
			isValidated = merchantValidate.validate(user);
			// validating user details
			if (isValidated) {
				Account oldAccount = (Account) session
						.getAttribute("returnedAccount");

				if (oldAccount != null) {
					PaymentService paymentService = new PaymentService();
					updateCardDetails = paymentService.updateDetails(
							oldAccount.getStripeId(), oldAccount.getCardId(),
							null, exp_month, exp_year, ccv_no, null,
							user.getAddressLine1(), user.getAddressLine2(),
							user.getCity(), user.getState(), user.getZipCode(),
							user.getCountry());

				}
				if (updateCardDetails) {

					// adding user in DB
					dataServices.updateEntity(user, User.class);
					account.setAccountState("cc_validation_requested");
					account.setModifiedDate(new Date());
					account.setModifiedBy(user.getFirstName());
					account.setId(oldAccount.getId());
					returnedAccount = (Account) dataServices.updateEntity(
							account, Account.class);
				}
			}

		} catch (IOException e) {
			updateCardDetails = false;
			throw new UrbanLifeException(UrbanLifeConstants.SERVER_DOWN_ERROR,
					UrbanLifeConstants.SERVER_DOWN_ERROR_CODE);
		}
		if (!updateCardDetails) {
			throw new UrbanLifeException(UrbanLifeConstants.SERVER_DOWN_ERROR,
					UrbanLifeConstants.SERVER_DOWN_ERROR_CODE);
		}
		json = gson.toJson(returnedMerchant);
		return Response.ok(json, MediaType.APPLICATION_JSON)
				.header("page_size", 1).header("offset", 0)
				.header("total_count", 1).build();

	}

	/**
	 * 
	 * @param id
	 * @return
	 * @throws UrbanLifeException
	 */
	@GET
	@Produces("application/json")
	@Path(value = "/{id}")
	public Response getMerchant(@PathParam("id") String id)
			throws UrbanLifeException {

		Card cardDetails = null;
		Gson gson = new Gson();
		String json = null;
		if (id == null || id.trim().length() == 0) {
			throw new UrbanLifeException(UrbanLifeConstants.ID_NOT_PRESENT,
					UrbanLifeConstants.ID_NOT_PRESENT_CODE);
		}
		try {
			Integer.parseInt(id);
		} catch (NumberFormatException e) {
			throw new UrbanLifeException(UrbanLifeConstants.ID_NAN,
					UrbanLifeConstants.ID_NAN_CODE);
		}
		User returnedUser = null;
		HttpSession session = request.getSession();
		Merchant returnedMerchant = null;
		Account returnedAccount = null;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			returnedUser = (User) dataServices.getEntityById(
					Integer.parseInt(id), User.class);

			if (returnedUser != null) {

				session.setAttribute("returnedUser", returnedUser);
				String emailID = returnedUser.getEmailId();
				Criterion criterion = Restrictions.eq("emailId", emailID);
				List<Object> userList = (List<Object>) dataServices.getInfo(
						Merchant.class, criterion);
				returnedMerchant = (Merchant) userList.get(0);
			}
			if (returnedMerchant != null) {
				// session.setAttribute("returnedMerchant", returnedMerchant);
				int merchantId = returnedMerchant.getId();
				Criterion criterion = Restrictions.eq("businessOwnerId",
						merchantId);
				List<Object> userList = (List<Object>) dataServices.getInfo(
						Account.class, criterion);
				returnedAccount = (Account) userList.get(0);
			}
			if (returnedAccount != null) {
				// session.setAttribute("returnedAccount", returnedAccount);
				PaymentService paymentService = new PaymentService();
				String stripeId = returnedAccount.getStripeId();
				String cardId = returnedAccount.getCardId();
				cardDetails = paymentService.getCardDetail(stripeId, cardId);
				map.put("returnedUser", returnedUser);
				map.put("exp_month", cardDetails.getExpMonth());
				map.put("exp_year", cardDetails.getExpYear());
				map.put("cardNumber", cardDetails.getLast4());
			}

			json = gson.toJson(map);
			System.out.println("userList-->" + returnedUser);
		}
		// catch (NotFoundException e) {
		// // e.printStackTrace();
		// throw new UrbanLifeException(UrbanLifeConstants.PATH_NOT_FOUND,
		// UrbanLifeConstants.PATH_NOT_FOUND_CODE);
		// }
		catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity(e.getMessage()).build();
		}

		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	// @Consumes("application/json")
	@DELETE
	@Produces("application/json")
	@Path(value = "/{id}")
	public void deleteMerchant(@PathParam("id") Integer id) {
		Merchant returnedMerchant = null;
		try {
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			returnedMerchant = (Merchant) dataServices.getEntityById(id,
					Merchant.class);
			returnedMerchant.setIsActive(false);
			returnedMerchant = (Merchant) dataServices.updateEntity(id,
					Merchant.class);
		} catch (Exception e) {

		}

	}

	@GET
	@Produces("application/json")
	@Path(value = "")
	public Response getAllMerchants() throws UrbanLifeException {

		List<Object> merchantList = null;
		Gson gson = new Gson();
		String json = null;
		try {
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			Criterion criterion = Restrictions.eq("isActive", true);

			merchantList = (List<Object>) dataServices.getInfo(Merchant.class,
					criterion);

			json = gson.toJson(merchantList);

		} catch (Exception e) {
			e.printStackTrace();
			// throw new UrbanLifeException(111, e.getMessage());
		}
		return Response.ok(json, MediaType.APPLICATION_JSON)
				.header("page_size", 1).header("offset", 0)
				.header("total_count", 1).build();
	}

	/**
	 * Service api to check if email id exist
	 */
	@Consumes("*/*")
	@GET
	@Produces("application/json")
	@Path(value = "/emailValidation/{emailId}")
	public Response emailValidate(@PathParam("emailId") String emailId)
			throws UrbanLifeException {
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
	 * @param username
	 *            and password
	 * @return
	 * @throws UrbanLifeException
	 */
	@Consumes("application/json")
	@POST
	@Produces("application/json")
	@Path(value = "/login")
	public Response loginMerchant(InputStream inputStream)
			throws UrbanLifeException {
		LoginService loginService = new LoginService();
		String response = null;
		HttpSession session = request.getSession();
		try {

			Map<String, String> userDetailsMap = new HashMap<String, String>();
			Map<String, String> oldUserDetailsMap = null;
			ObjectMapper mapper = new ObjectMapper();
			// convert JSON string to Map
			userDetailsMap = mapper.readValue(inputStream,
					new TypeReference<HashMap<String, String>>() {
					});

			if (userDetailsMap.get("username") != null
					&& userDetailsMap.get("password") != null) {
				session.setAttribute("oldUserDetailsMap", userDetailsMap);
				System.out.println(userDetailsMap.get("username"));
			}
			oldUserDetailsMap = (Map<String, String>) session
					.getAttribute("oldUserDetailsMap");
			String username = oldUserDetailsMap != null ? oldUserDetailsMap
					.get("username") : userDetailsMap.get("username");
			String password = oldUserDetailsMap != null ? oldUserDetailsMap
					.get("password") : userDetailsMap.get("password");
			String prefix = oldUserDetailsMap != null ? oldUserDetailsMap
					.get("prefix") : userDetailsMap.get("prefix");
			response = loginService.loginEntity(username, password, prefix,
					userDetailsMap.get("usertoken"),
					userDetailsMap.get("flag"),
					userDetailsMap.get("rememberme"));
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
		Gson gson = new Gson();
		String token = gson.toJson(response);
		return Response.ok(token, MediaType.APPLICATION_JSON).build();
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
	@Path(value = "/forgetPassword")
	public Response forgetPassword(InputStream inputStream)
			throws UrbanLifeException {
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
					.getBean("dataServices");
			Criterion criterion = Restrictions
					.eq("emailId", map.get("emailId"));
			returnedUser = (User) dataServices.getInfo(User.class, criterion);

		} catch (Exception e) {
			throw new UrbanLifeException(UrbanLifeConstants.MAIL_ID_NOT_FOUND,
					UrbanLifeConstants.MAIL_ID_NOT_FOUND_CODE);
		}
		if (returnedUser.getEmailId().equalsIgnoreCase(map.get("emailId"))) {
			Mailer mail = new Mailer();
			try {
				String emailName = returnedUser.getFirstName() + " "
						+ returnedUser.getLastName();
				mail.sendForgetPassMail(map.get("emailId"), emailName);
				System.out.println("success" + map.get("emailId"));
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
	@Path(value = "/changePassword")
	public Response changePassword(InputStream inputStream)
			throws UrbanLifeException {
		EncryptDecrypt encrypt = new EncryptDecrypt();
		Map<String, String> map = new HashMap<String, String>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			// convert JSON string to Map
			map = mapper.readValue(inputStream,
					new TypeReference<HashMap<String, String>>() {
					});
			dataServices = (DataServices) SpringApplicationContext
					.getBean("dataServices");
			Criterion criterion = Restrictions
					.eq("emailId", map.get("emailId"));
			List<Object> userList = (List<Object>) dataServices.getInfo(
					User.class, criterion);
			User returnedUser = (User) userList.get(0);
			returnedUser.setPassword(encrypt.encrypt(map.get("password")));

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
		String[] url = photoUrl.split("urbanlifetemp");
		System.out.println("path ***************************="+request.getServletContext().getContextPath() +  File.separator +"urbanlifetemp" + url[1]);
		return request.getServletContext().getContextPath() +  File.separator +"urbanlifetemp" + url[1];
	}

	/*
	 * private String getPath() {
	 * 
	 * Properties props = new Properties(); InputStream input = null;
	 * 
	 * ClassLoader classLoader = getClass().getClassLoader(); InputStream file =
	 * classLoader.getResourceAsStream("config.properties");
	 * 
	 * try { // input = new FileInputStream(file); props.load(file); } catch
	 * (IOException ex) { logger.error("error While getting property");
	 * ex.printStackTrace(); } finally { if (input != null) { try {
	 * input.close(); } catch (IOException e) {
	 * logger.error("error While closing input stream"); e.printStackTrace(); }
	 * } }
	 * 
	 * return props.getProperty("PHOTO_PATH"); }
	 * 
	 * private String getFileName(MultivaluedMap<String, String> header) {
	 * 
	 * String[] contentDisposition = header.getFirst("Content-Disposition")
	 * .split(";");
	 * 
	 * for (String filename : contentDisposition) { if
	 * ((filename.trim().startsWith("filename"))) {
	 * 
	 * String[] name = filename.split("=");
	 * 
	 * String finalFileName = name[1].trim().replaceAll("\"", ""); return
	 * finalFileName; } } return "unknown"; }
	 * 
	 * // save to somewhere private void writeFile(byte[] content, String
	 * filename) throws IOException {
	 * 
	 * File file = new File(filename);
	 * 
	 * if (!file.exists()) { file.createNewFile(); }
	 * 
	 * FileOutputStream fop = new FileOutputStream(file);
	 * 
	 * fop.write(content); fop.flush(); fop.close();
	 * 
	 * }
	 */

}