package com.onkaar.utils;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.onkaar.resource.GroupResource;

@ApplicationPath("/")
public class SplitwiseApplication extends Application{

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(GroupResource.class);
		
		return classes;
		
	}
}
