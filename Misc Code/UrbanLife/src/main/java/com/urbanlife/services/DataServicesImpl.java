package com.urbanlife.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;

import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Customer;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.dao.DataDao;
import com.urbanlife.consumer.Consumer;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.deal.Deal;
import com.urbanlife.model.Account;
import com.urbanlife.model.Merchant;

public class DataServicesImpl implements DataServices {

//	@Autowired
	
DataDao  dataDao = (DataDao) SpringApplicationContext.getBean("dataDao");;
	

	@Override
	public Object addEntity(Object merchant,Class className) throws UrbanLifeException {
	
		return dataDao.addEntity(merchant,className);
	}

	@Override
	public Object updateEntity(Object entity,Class className) throws UrbanLifeException {
		return dataDao.updateEntity(entity, className);
	}
	
	@Override
	public Object getEntityById(Integer id,Class className) throws UrbanLifeException {
		
		return dataDao.getEntityById(id, className);
	}


	@Override
	public List<Object> getEntityList(Class className) throws UrbanLifeException {
		List merchantList = new ArrayList();
		return dataDao.getEntityList(className);
	}


	@Override
	public Object getInfo(Class className,Criterion criterion) throws UrbanLifeException {

		return dataDao.getInfo( className, criterion);
	}

	
	@Override
	public Object getUserId(Class className,String emailId) throws UrbanLifeException {

		return dataDao.getUserId(className ,emailId );
	}


	@Override
	public Object getInfo(Class className, Criteria criteria)
			throws UrbanLifeException {
		// TODO Auto-generated method stub
		return dataDao.getInfo(className, criteria);
	}

	public Object getCity(Class className,Criterion criterion)
	{
		return dataDao.getDistinctInfo(className,criterion);
	}

}
