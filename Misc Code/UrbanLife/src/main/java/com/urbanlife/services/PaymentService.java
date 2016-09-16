package com.urbanlife.services;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.payment.PaymentGatewayImpl;

public class PaymentService {
	
	PaymentGatewayImpl paymentgatewayservice=new PaymentGatewayImpl();
	
	/**
	 * careating card object from user input and returning the card 
	 * @param cardNumber
	 * @param exp_month
	 * @param exp_year
	 * @param cvv_no
	 * @param name
	 * @param address_line1
	 * @param address_line2
	 * @param address_city
	 * @param address_state
	 * @param address_zip
	 * @param address_country
	 * @return
	 * @throws UrbanLifeException
	 */
	public Customer getStripeID(String cardNumber, int exp_month,
			int exp_year, int cvv_no, String name, String address_line1,
			String address_line2, String address_city, String address_state,
			String address_zip, String address_country) throws UrbanLifeException  {
		Customer stripeId =null;
		
		 try {
			stripeId =paymentgatewayservice.createCustomer(cardNumber,
					exp_month, exp_year, cvv_no, name, address_line1,
					address_line2, address_city, address_state, address_zip,
					address_country);
		 } catch (AuthenticationException e1) {
				throw new UrbanLifeException(e1.getMessage(),
						UrbanLifeConstants.CARD_AUTH_FAIlED_CODE);		
				} catch (InvalidRequestException e1) {
				throw new UrbanLifeException(e1.getMessage(),
						UrbanLifeConstants.CARD_INVALID_REQUEST_CODE);
			} catch (APIConnectionException e1) {
				e1.printStackTrace();
				throw new UrbanLifeException(e1.getMessage(),
						UrbanLifeConstants.CARD_APICONNECTION_CODE);
			} catch (CardException e1) {
				String msgCode = e1.getCode();				
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INCORRECT_NUMBER))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INCORRECT_NUMBER_STRIPE,
							UrbanLifeConstants.CARD_INCORRECT_NUMBER_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INVALID_NUMBER))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INVALID_NUMBER_STRIPE,
							UrbanLifeConstants.CARD_INVALID_NUMBER_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INVALID_EXPIRYMONTH))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INVALID_EXPIRYMONTH_STRIPE,
							UrbanLifeConstants.CARD_INVALID_EXPIRYMONTH_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INVALID_EXPIRYYEAR))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INVALID_EXPIRYYEAR_STRIPE,
							UrbanLifeConstants.CARD_INVALID_EXPIRYYEAR_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INVALID_CVC))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INVALID_CVC_STRIPE,
							UrbanLifeConstants.CARD_INVALID_CVC_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_EXPIRED))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_EXPIRED_STRIPE,
							UrbanLifeConstants.CARD_EXPIRED_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INCORRECT_CVC))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INCORRECT_CVC_STRIPE,
							UrbanLifeConstants.CARD_INCORRECT_CVC_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_DECLINED))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_DECLINED_STRIPE,
							UrbanLifeConstants.CARD_DECLINED_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_PROCESSING_ERROR))
								throw new UrbanLifeException(UrbanLifeConstants.CARD_PROCESSING_ERROR_STRIPE,
										UrbanLifeConstants.CARD_PROCESSING_ERROR_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_MISSING))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_MISSING_STRIPE,
							UrbanLifeConstants.CARD_MISSING_CODE);
				if(msgCode.equalsIgnoreCase(UrbanLifeConstants.CARD_INCORRECTZIP))
					throw new UrbanLifeException(UrbanLifeConstants.CARD_INCORRECTZIP_STRIPE,
							UrbanLifeConstants.CARD_INCORRECTZIP_CODE);
				} catch (APIException e1) {
				throw new UrbanLifeException(e1.getMessage(),
						UrbanLifeConstants.CARD_API_CODE);
			}
		
		return stripeId;
	}
	/**
	 * retriving the card details of already existing card
	 * @param stripeId
	 * @param cardId
	 * @return
	 * @throws UrbanLifeException
	 */
	public Card getCardDetail(String stripeId,String cardId)throws UrbanLifeException{
		Card cardDetails=null;
		try {
			cardDetails=paymentgatewayservice.retriveCardDetsils(stripeId, cardId);
		} catch (AuthenticationException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_AUTH_FAIlED_CODE);	
		} catch (InvalidRequestException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_INVALID_REQUEST_CODE);
		} catch (APIConnectionException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_APICONNECTION_CODE);
			} catch (CardException e) {
				throw new UrbanLifeException(e.getMessage(),
						UrbanLifeConstants.CARD_EXCEPTION_CODE);
		} catch (APIException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_API_CODE);
		}
		
	return cardDetails;
}
	/**
	 * updating card details of existing card
	 * @param customerId
	 * @param cardId
	 * @param cardNumber
	 * @param exp_month
	 * @param exp_year
	 * @param cvv_no
	 * @param name
	 * @param address_line1
	 * @param address_line2
	 * @param address_city
	 * @param address_state
	 * @param address_zip
	 * @param address_country
	 * @return
	 * @throws UrbanLifeException
	 */

	public boolean updateDetails(String customerId,String cardId,String cardNumber, int exp_month,
			int exp_year, int cvv_no, String name, String address_line1,
			String address_line2, String address_city, String address_state,
			String address_zip, String address_country)throws UrbanLifeException{
		boolean status=false;
		try {
			status=paymentgatewayservice.updateCardInfo(customerId, cardId, exp_month, exp_year, name, address_line1, address_line2, address_city, address_state, address_zip, address_country);
		} catch (AuthenticationException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_AUTH_FAIlED_CODE);	
		} catch (InvalidRequestException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_INVALID_REQUEST_CODE);
		} catch (APIConnectionException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_APICONNECTION_CODE);
			} catch (CardException e) {
				throw new UrbanLifeException(e.getMessage(),
						UrbanLifeConstants.CARD_EXCEPTION_CODE);
		} catch (APIException e) {
			throw new UrbanLifeException(e.getMessage(),
					UrbanLifeConstants.CARD_API_CODE);
		}
		
		return status;
	}
}
