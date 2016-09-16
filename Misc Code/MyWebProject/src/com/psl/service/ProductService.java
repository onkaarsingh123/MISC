package com.psl.service;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.psl.bean.Product;
import com.psl.data.ProductDataStore;
import com.sun.jersey.api.core.InjectParam;


@Path("/")
public class ProductService {

	private static ProductDataStore productDataStore=new ProductDataStore();
	
	@POST
	@Path("/new")
	@Produces(MediaType.TEXT_PLAIN)
	public Response addNewProduct(@InjectParam Product product){
		productDataStore.addProduct(product);
		return Response.ok("New Contact Added",MediaType.TEXT_PLAIN).build();
	}
	
	@GET
	@Path("/xml/all")
	public  Response getAllProducts(){
		return Response.ok(productDataStore,MediaType.APPLICATION_XML).build();
		
	}
	
	
}
