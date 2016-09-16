package com.urbanlife.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
//import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;

import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.City;
import com.urbanlife.util.HibernateUtil;

public class DataDaoImpl implements DataDao {

	private SessionFactory sessionFactory = HibernateUtil.getInstance()
			.getSessionFactory();
	private Session session = sessionFactory.openSession();
	Transaction tx = null;

	@Override
	public Object getEntityById(Integer id, Class className)
			throws UrbanLifeException {

		tx = session.beginTransaction();

		Object entity = null;
		try {
			entity = session.get(className, id);

			tx.commit();
			tx = null;
		} catch (Exception ex) {
			// Log the exception here
			tx.rollback();
			tx.commit();
			throw new UrbanLifeException();

		}

		return entity;
	}

	@Override
	public Object addEntity(Object entity, Class className)
			throws UrbanLifeException {

		// tx = session.beginTransaction();
		int entityId = (Integer) session.save(entity);
		Object returnedMerchant = session.load(className, entityId);

		// tx.commit();
		return returnedMerchant;
	}

	@Override
	public Object updateEntity(Object entity, Class className)
			throws UrbanLifeException {
		Object updatedEntity = null;
		try {
			tx = session.beginTransaction();

			session.update(entity);
			tx.commit();
			tx = null;
		} catch (Exception ex) {
			// Log the exception here
			tx.rollback();
			tx.commit();
			throw new UrbanLifeException();

		}
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getEntityList(Class className)
			throws UrbanLifeException {
		List<Object> entityList = null;
		try {
			tx = session.beginTransaction();
			entityList = (List<Object>) session.createCriteria(className)
					.list();
			tx.commit();
		} catch (Exception ex) {
			// Log the exception here
			ex.printStackTrace();
			tx.rollback();
			tx.commit();
			throw new UrbanLifeException();

		}
		return entityList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Object getUserId(Class className, String emailId)
			throws UrbanLifeException {
		Object entity = null;
		Integer id =null;
		try {
			tx = session.beginTransaction();
			List<Object> entityList = (List<Object>) session.createCriteria(className).add(Restrictions.eq("emailId", emailId))
					.list();
			if(entityList!=null && entityList.size()!= 0){
			entity = entityList.get(0);
			}
			tx.commit();
		} catch (Exception ex) {
			// Log the exception here
			ex.printStackTrace();
			tx.rollback();
			tx.commit();
			throw new UrbanLifeException();

		}
		return entity;
	}

	@Override
	public Object getInfo(Class className, Criterion criterion)
			throws UrbanLifeException {

		// Object entity = session.get(className, id);
		List<Object> entityList = null;
		try {
			tx = session.beginTransaction();

			entityList = (List<Object>) session.createCriteria(className)
					.add(criterion).list();

			tx.commit();
		} catch (Exception ex) {
			// Log the exception here
			ex.printStackTrace();
			tx.rollback();
			tx.commit();
			throw new UrbanLifeException();

		}
		return entityList;
	}

	@Override
	public Object getInfo(Class className, Criteria criteria)
			throws UrbanLifeException {
		// TODO Auto-generated method stub
		tx=session.beginTransaction();
		
		List<Object> entityList=criteria.list();
//		System.out.println("EntityList in DataDao ::::"+entityList);
		tx.commit();
		return entityList;
	}
	
	@Override
	public Object getDistinctInfo(Class className, Criterion criterion) {
		// TODO Auto-generated method stub
		tx=session.beginTransaction();
		
		List<City> entityList = (List<City>)session.createCriteria(className)
											.setProjection(Projections.projectionList().
											add(Projections.distinct(Projections.property("state")))).addOrder(Order.asc("state"))
											.add(criterion).list();
		
		tx.commit();
		return entityList;
	}

}
