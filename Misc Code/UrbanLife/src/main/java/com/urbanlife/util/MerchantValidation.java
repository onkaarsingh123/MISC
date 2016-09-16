package com.urbanlife.util;

import javax.mail.internet.AddressException;

import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.User;
import com.urbanlife.util.Validation;
import com.urbanlife.util.EncryptDecrypt;


public class MerchantValidation {
	public boolean validate(User user) throws UrbanLifeException, AddressException{
		EncryptDecrypt encrypt=new EncryptDecrypt();
		Validation validation=new Validation();
		
		if(!validation.validateEmail(user.getEmailId()))
		{
			throw new UrbanLifeException(UrbanLifeConstants.INVALID_EMAIL,
					UrbanLifeConstants.INVALID_EMAIL_CODE);
		}
		//if(!validation.validatePassword(user.getPassword())){
			//throw new UrbanLifeException(UrbanLifeConstants.INVALID_PASSWORD,
				//	UrbanLifeConstants.INVALID_PASSWORD_CODE);
		//}
		/*if(validation.validatePhonenumber(user.getContactNumber())){
			throw new UrbanLifeException(UrbanLifeConstants.INVALID_CONTACTNUMBER,
					UrbanLifeConstants.INVALID_CONTACTNUMBER_CODE);
		}*/
		/*if(validation.validateZip(user.getZipCode())){
			throw new UrbanLifeException(UrbanLifeConstants.INVALID_ZIP,
					UrbanLifeConstants.INVALID_ZIP_CODE);
		}*/
		if(user.getPassword()!=null)
		{
			try {
				user.setPassword(encrypt.encrypt(user.getPassword()));
			} catch (Exception e) {
				throw new UrbanLifeException(UrbanLifeConstants.PASSWORD_ENCRYPTION_FAIL,
						UrbanLifeConstants.PASSWORD_ENCRYPTION_FAIL_CODE);
			}
		}
		return true;
		
	}

}
