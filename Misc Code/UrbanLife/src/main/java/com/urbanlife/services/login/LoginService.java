package com.urbanlife.services.login;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.Account;
import com.urbanlife.model.Merchant;
import com.urbanlife.model.User;
import com.urbanlife.services.DataServices;
import com.urbanlife.util.EncryptDecrypt;
import com.urbanlife.util.TokenGenerator;

public class LoginService {
	DataServices dataServices = (DataServices) SpringApplicationContext
			.getBean("dataServices");

	public String loginEntity(String usrname, String password,
			String prefixtxt, String userToken, String flag, String rememberme)
			throws UrbanLifeException {

		EncryptDecrypt encryptDecrypt = new EncryptDecrypt();
		boolean authenticated = false;
		Class queryClass = null;
		User user=null;
		String responseToken = null;
		TokenGenerator tokenGenerator = new TokenGenerator();
		String prefix = tokenGenerator.getPrefix(prefixtxt);
		/*
		 * if (prefix.equalsIgnoreCase("C")) { queryClass = Consumer.class; }
		 */
		if (prefix.equalsIgnoreCase(UrbanLifeConstants.CUSTOM_PREFIX_BO)) {
			queryClass = User.class;
		}
		// if (prefix.equalsIgnoreCase("BM")) {
		// queryClass = "Businessmanager";
		// }
		// if (prefix.equalsIgnoreCase("A")) {
		// queryClass = "Admin.class";
		// }
		try{
		Criterion criterion = Restrictions.eq("emailId", usrname);
		List<Object> entityList = (List<Object>) dataServices.getInfo(
				queryClass, criterion);
		user = (User) entityList.get(0);
		}catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
			
		}
		// user authentication
		if (user != null) {
			try {
				if (user.getPassword().equals(encryptDecrypt.encrypt(password))) {
					authenticated = true;
				}
			} catch (Exception e) {
				authenticated = false;
				throw new UrbanLifeException(
						UrbanLifeConstants.CREDENTIAL_INCORRECT,
						UrbanLifeConstants.CREDENTIAL_INCORRECT_CODE);
			}
			if (authenticated && flag.equalsIgnoreCase("validateToken")
					&& rememberme.equalsIgnoreCase("yes")) {
				if (validateToken(userToken, Merchant.class))
					return userToken;
				else
					responseToken = generateToken(usrname, Merchant.class);
			}
			if (authenticated && flag.equalsIgnoreCase("validateToken")
					&& rememberme.equalsIgnoreCase("no")) {
				if (validateToken(userToken, Merchant.class))
					return userToken;
				else
					return null;
			}

		}
		if (authenticated && flag.equalsIgnoreCase("generateToken")) {
			responseToken = generateToken(usrname, Merchant.class);
		}
		return responseToken;

	}

	/**
	 * validating token
	 */

	private boolean validateToken(String token, Class clazz)
			throws UrbanLifeException {
		boolean status = false;
		List<Object> entityList = null;
		TokenGenerator tokenGanarator = new TokenGenerator();
		List<Merchant> merchantList = new ArrayList<Merchant>();
		Merchant merchant = null;
		String[] parts = token.split(":");
		String tokenId = parts[0];

		try {
			Criterion criterion = Restrictions.eq("authToken", tokenId);
			entityList = (List<Object>) dataServices.getInfo(clazz, criterion);
			merchant = (Merchant) entityList.get(0);

		}catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
			
		}
		if (entityList.isEmpty()) {
			status = false;
		}
		if (!entityList.isEmpty()) {
			if (merchant.getAuthTokenExpires() > tokenGanarator
					.getCurrentTime()) {
				status = true;
			}
		}

		return status;
	}

	public String generateToken(String usrname, Class clazz)
			throws UrbanLifeException {
		String responseToken = null;
		List<Object> entityList = null;
		TokenGenerator tokenGenerator = new TokenGenerator();
		List merchantList = new ArrayList<Merchant>();
		Merchant merchant = null;
		String email = null;
		User user = null;
		boolean save = false;

		try {
			Criterion criterion = Restrictions.eq("emailId", usrname);
			entityList = (List<Object>) dataServices.getInfo(clazz, criterion);
			merchant = (Merchant) entityList.get(0);
			merchant.setAuthToken(tokenGenerator.generateToken());
			merchant.setAuthTokenExpires(tokenGenerator.generateExpTime());
			dataServices.updateEntity(merchant, clazz);
			save = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (save) {
			email = usrname;
			Merchant returnedMerchant = null;
			try {
				returnedMerchant = (Merchant) dataServices.getEntityById(
						merchant.getId(), clazz);
			} catch (Exception e) {
			}
			responseToken = tokenGenerator.responseToken(
					returnedMerchant.getAuthToken(),
					returnedMerchant.getAuthTokenExpires());
		}
		return responseToken;

	}

	/**
	 * validating if email id already exist
	 */

	public boolean emailValidate(String email, Class clazz)
			throws UrbanLifeException {
		boolean status = false;
		List<Object> entityList = new ArrayList<Object>();
		try{
		Criterion criterion = Restrictions.eq("emailId", email);
		entityList = (List<Object>) dataServices.getInfo(clazz, criterion);
			} catch (Exception e) {
				status = false;
				throw new UrbanLifeException(UrbanLifeConstants.MAIL_ID_NOT_FOUND,
						UrbanLifeConstants.MAIL_ID_NOT_FOUND_CODE);
			}

		if (entityList.isEmpty()) {
			status = false;
		}
		if (!entityList.isEmpty()) {
			status = true;
		}

		return status;
	}
	

}
