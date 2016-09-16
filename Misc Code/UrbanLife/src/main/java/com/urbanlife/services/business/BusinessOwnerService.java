package com.urbanlife.services.business;

import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.model.Business;
import com.urbanlife.model.BusinessAttributes;
import com.urbanlife.model.BusinessType;
import com.urbanlife.services.DataServices;
import com.urbanlife.util.UrbanLifeUtils;

public class BusinessOwnerService {
	DataServices 	dataServices = (DataServices) SpringApplicationContext
			.getBean("dataServices");
	
	
	
}
