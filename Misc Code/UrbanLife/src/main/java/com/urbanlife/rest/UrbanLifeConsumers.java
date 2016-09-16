package com.urbanlife.rest;

import java.util.Collection;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.Path;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;



import com.urbanlife.deal.Deal;
import com.urbanlife.consumer.Consumer;
//import com.urbanlife.model.Status;
//import com.urbanlife.services.DataServices;
import com.urbanlife.services.DataServices;

//@Controller
@Path("/api/v1/consumers")
public class UrbanLifeConsumers {
//
//	@Autowired
//	DataServices dataServices;
//	static final Logger logger = Logger.getLogger(UrbanLifeConsumers.class);
//
//	/**
//	 * Add Consumer
//	 * 
//	 * @param Consumer
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "" + "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Merchant addConsumer(@RequestBody Merchant Consumer)
//			throws UrbanLifeException {
//		Merchant returnedConsumer = null;
//		try {
//			returnedConsumer = (Merchant) dataServices.addEntity(Consumer);
//		} catch (Exception e) {  
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return returnedConsumer;
//	}
//
//	/**
//	 * Update Consumer
//	 * 
//	 * @param Consumer
//	 * @return updated Consumer
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}" + "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Merchant updateConsumer(@RequestBody Merchant Consumer)
//			throws UrbanLifeException {
//		Merchant updatedConsumer = null;
//		try {
//			updatedConsumer = (Merchant) dataServices.updateEntity(Consumer);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(113, e.getMessage());
//
//		}
//		return updatedConsumer;
//	}
//
////	/**
////	 * Update Consumer with image photo
////	 * 
////	 * @param Consumer
////	 * @return updated Consumer
////	 * @throws UrbanLifeException
////	 */
////	@Path(value = "/{id}/profile/image" + "", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////	public @ResponseBody
////	Consumer uploadImage(@PathVariable("id") long id,
////			@PathVariable("image") String imagePath) throws UrbanLifeException {
////		Consumer updatedConsumer = null;
////		try {
////			updatedConsumer = (Consumer) dataServices
////					.uploadImage(id, imagePath);
////		} catch (Exception e) {
////			e.printStackTrace();
////			throw new UrbanLifeException(113, e.getMessage());
////
////		}
////		return updatedConsumer;
////	}
//
//	/**
//	 * Get Consumer
//	 * 
//	 * @param id
//	 * @return Consumer
//	 * @throws UrbanLifeException
//	 */
////	@Path(value = "/{id}" + "", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
////	public @ResponseBody
////	Consumer getConsumer(@PathVariable("id") long id) throws UrbanLifeException {
////		Consumer returnedConsumer = null;
////		try {
////			returnedConsumer = (Consumer) dataServices.getEntityById(id);
////		} catch (Exception e) {
////			e.printStackTrace();
////			throw new UrbanLifeException(112, e.getMessage());
////		}
////		return returnedConsumer;
////	}
//
//	/**
//	 * Delete Consumer
//	 * 
//	 * @param id
//	 * @return success/failure
//	 */
//	@Path(value = "/{id}", method = RequestMethod.DELETE)
//	public @ResponseBody
//	Status deleteConsumer(@PathVariable("id") long id) {
//
//		try {
//			dataServices.deleteEntity(id);
//		} catch (Exception e) {
//			return new Status(114, e.getMessage());
//		}
//		return new Status(204, "Consumer deleted Successfully !");
//	}
//
//	/**
//	 * login Consumer
//	 * 
//	 * @param username
//	 *            and password
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/login" + "", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Status loginConsumer(@PathVariable("username") String username,
//			@PathVariable("password") String password)
//			throws UrbanLifeException {
//		try {
//			dataServices.loginEntity(username, password);
//		} catch (Exception e) {
//			return new Status(114, e.getMessage());
//		}
//		return new Status(204, "Consumer logged in Successfully !");
//	}
//
//	/**
//	 * 
//	 * @param username
//	 * @param oldPassword
//	 * @param newPassword
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}/changepassword" + "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Status changePassword(@PathVariable("username") String username,
//			@PathVariable("oldPassword") String oldPassword, @PathVariable("newPassword") String newPassword)
//			throws UrbanLifeException {
//		try {
//			dataServices.changePassword(username, oldPassword, newPassword);
//		} catch (Exception e) {
//			return new Status(114, e.getMessage());
//		}
//		return new Status(204, "Consumer password changed Successfully !");
//	}
//
//   /**
//    * 
//    * @param emailId
//    * @return
//    * @throws UrbanLifeException
//    */
//	
//	@Path(value = "/{id}/resetpassword" + "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Status resetPassword(@PathVariable("emailId") String emailId)
//			throws UrbanLifeException {
//		try {
//			dataServices.resetPassword(emailId);
//		} catch (Exception e) {
//			return new Status(114, e.getMessage());
//		}
//		return new Status(204, "Consumer password reset Successfully !");
//	}
//
//	/**
//	 * 
//	 * @param deal
//	 * @param ConsumerId
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}/deals" + "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Deal createDeal(@RequestBody Deal deal, @PathVariable("id") long ConsumerId)
//			throws UrbanLifeException {
//		Deal createdDeal = null;
//		try {
//			createdDeal = (Deal) dataServices.addEntity(deal, ConsumerId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return createdDeal;
//	}
//
//	/**
//	 * 
//	 * @param id
//	 * @param dealId
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}/deals/{dealId}" + "", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Deal getDeal(@PathVariable("id") long id,
//			@PathVariable("dealId") long dealId) throws UrbanLifeException {
//		Deal returnedDeal = null;
//		try {
//			returnedDeal = (Deal) dataServices.getEntityById(id, dealId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(112, e.getMessage());
//		}
//		return returnedDeal;
//	}
//
//	/**
//	 * Update Deal
//	 * 
//	 * @param deal
//	 * @return updated deal
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}/deals/{dealId}" + "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Deal updateDeal(@RequestBody Deal deal, @PathVariable("id") long ConsumerId)
//			throws UrbanLifeException {
//		Deal updatedDeal = null;
//		try {
//			updatedDeal = (Deal) dataServices.updateEntity(deal, ConsumerId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return updatedDeal;
//	}
//
//	/**
//	 * Delete Deal
//	 * 
//	 * @param id
//	 * @return success/failure
//	 */
//	@Path(value = "/{id}/deals/{dealId}", method = RequestMethod.DELETE)
//	public @ResponseBody
//	Status deleteDeal(@PathVariable("id") long id, @PathVariable("dealId") long dealId) {
//
//		try {
//			dataServices.deleteEntity(id, dealId);
//		} catch (Exception e) {
//			return new Status(114, e.getMessage());
//		}
//		return new Status(204, "Deal deleted Successfully !");
//	}
//	
//	/**
//	 * 
//	 * @param ConsumerId
//	 * @param type - can be active, accepted or rejected
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Produces("application/json")
//	@Path(value = "/{id}/deals/{type}")//, method = RequestMethod.GET)
//	public @ResponseBody
//	Collection <Deal> getAllDeals(@PathVariable("id") long ConsumerId, String type) throws UrbanLifeException {
//
//		Collection<Deal> allDeals = null;
//		try {
//			allDeals = (Collection<Deal>) dataServices.getAllDeals(ConsumerId, type);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return allDeals;
//	}
//	
//	@Path(value = "/{id}/deals/{type}", method = RequestMethod.GET)
//	public @ResponseBody
//	int getDealsCount(@PathVariable("id") long ConsumerId, String type) throws UrbanLifeException {
//
//		int dealsCount = -1;
//		try {
//			dealsCount =  dataServices.getDealsCount(ConsumerId, type);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return dealsCount;
//	}
//	
//	/**
//	 * 
//	 * @param ConsumerId
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}/deals/billings", method = RequestMethod.GET)
//	public @ResponseBody
//	Collection <Object> getBillingHistory(@PathVariable("id") long ConsumerId) throws UrbanLifeException {
//
//		Collection<Object> billingHistory = null;
//		try {
//			billingHistory = (Collection<Object>) dataServices.getBillingHistory(ConsumerId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return billingHistory;
//	}
//	
//	/**
//	 * 
//	 * @param deal
//	 * @param ConsumerId
//	 * @param dealId
//	 * @param status
//	 * @return
//	 * @throws UrbanLifeException
//	 */
//	@Path(value = "/{id}/deals/{dealId}" + "", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody
//	Deal setDealStatus(@RequestBody Deal deal, @PathVariable("id") long ConsumerId, long dealId, boolean status)
//			throws UrbanLifeException {
//		Deal updatedDeal = null;
//		try {
//			updatedDeal = (Deal) dataServices.setDealStatus(ConsumerId, dealId, status);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new UrbanLifeException(111, e.getMessage());
//		}
//		return updatedDeal;
//	}
}
