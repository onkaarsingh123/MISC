package com.urbanlife.services.business;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.dao.DataDaoImpl;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.City;
import com.urbanlife.services.DataServices;

public class CityService {

	DataServices dataServices = (DataServices) SpringApplicationContext
			.getBean("dataServices");

	public List<City> getRow(Class class1, String country) throws UrbanLifeException {
		// TODO Auto-generated method stub
		List<City> city;
		Criterion criterion = Restrictions.eq("countryCode", country);
		try{
		city = (List<City>) dataServices.getCity(class1, criterion);
		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		}
		return city;
	}

	public List<City> getRow(Class class1, String country, String state) throws UrbanLifeException {
		// TODO Auto-generated method stub
		List<City> info ;
		Criterion criterion=Restrictions.eq("state", state);
		try{
			info = (List<City>) dataServices.getInfo(class1, criterion);
		} catch (ObjectNotFoundException e) {
			throw new UrbanLifeException(
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB,
					UrbanLifeConstants.ID_NOT_PRESENT_IN_DB_CODE);
		}
		return info;
	}

}
