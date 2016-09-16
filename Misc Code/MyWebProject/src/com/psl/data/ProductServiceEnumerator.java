package com.psl.data;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.psl.service.ProductService;

public class ProductServiceEnumerator extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		
		Set<Class<?>> classes=new HashSet<Class<?>>();
		classes.add(ProductService.class);
		return classes;
	
		
	}

	
	
}
