package com.urbanlife.rest;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//import scala.collection.generic.GenTraversableFactory.ReusableCBF;

import com.google.gson.Gson;
import com.urbanlife.context.SpringApplicationContext;
import com.urbanlife.exception.UrbanLifeException;
import com.urbanlife.model.City;
//import com.urbanlife.model.Deal;
import com.urbanlife.services.DataServices;
import com.urbanlife.services.business.CityService;

@Path("/api/v1/city")

public class UrbanLifeCities {

	DataServices dataServices;

	@GET
	@Produces("application/json")
	@Path(value = "/{country}")
	public Response getState(@PathParam("country") String country) throws UrbanLifeException {
		List<City> returnedRow = null;
		Gson gson = new Gson();
		String json = null;
		CityService cityService=new CityService();
		dataServices = (DataServices) SpringApplicationContext.getBean("dataServices");
		returnedRow = cityService.getRow(City.class,country);
		json=gson.toJson(returnedRow);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
	
	@GET
	@Produces("application/json")
	@Path(value="/{country}/{state}")
	public Response getCity(@PathParam("country") String country,@PathParam("state") String state)throws UrbanLifeException
	{
		List<City> returnedRow = null;
		Gson gson = new Gson();
		String json = null;
		CityService cityService=new CityService();
		dataServices = (DataServices) SpringApplicationContext.getBean("dataServices");
		returnedRow = cityService.getRow(City.class,country,state);
		json=gson.toJson(returnedRow);
		return Response.ok(json, MediaType.APPLICATION_JSON).build();
	}
}
