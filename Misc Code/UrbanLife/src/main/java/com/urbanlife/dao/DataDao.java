package com.urbanlife.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;

import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.Account;

public interface DataDao {

	public Object getEntityById(Integer id, Class className)
			throws UrbanLifeException;

	public Object addEntity(Object entity, Class className)
			throws UrbanLifeException;

	public Object updateEntity(Object entity, Class className)
			throws UrbanLifeException;

	public List<Object> getEntityList(Class className)
			throws UrbanLifeException;

	public Object getInfo(Class className, Criterion criterion)
			throws UrbanLifeException;

	public Object getInfo(Class className, Criteria criteria)
			throws UrbanLifeException;

	public Object getDistinctInfo(Class className, Criterion criterion);
	
	public Object getUserId(Class className, String emailId)
			throws UrbanLifeException;

}
