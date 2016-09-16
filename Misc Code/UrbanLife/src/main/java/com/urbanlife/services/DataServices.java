package com.urbanlife.services;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import com.stripe.model.Customer;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.consumer.Consumer;
import com.urbanlife.deal.Deal;
import com.urbanlife.model.Account;
import com.urbanlife.model.Merchant;

public interface DataServices {
	
	public Object getEntityById(Integer id,Class className) throws UrbanLifeException;
	public Object addEntity(Object entity,Class className) throws UrbanLifeException;
	public Object updateEntity(Object entity ,Class className)throws UrbanLifeException;
	public List<Object> getEntityList(Class className) throws UrbanLifeException;
	public Object getInfo( Class className, Criterion criterion)
			throws UrbanLifeException; 
	
	public Object getInfo(Class className,Criteria criteria) throws UrbanLifeException;
	public Object getCity(Class className,Criterion criterion);
	public Object getUserId(Class className, String emailId)
			throws UrbanLifeException;
}
