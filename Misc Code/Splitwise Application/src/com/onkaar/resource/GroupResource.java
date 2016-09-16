package com.onkaar.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.onkaar.bean.Group;
import com.onkaar.service.UserService;

@Path("groups")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class GroupResource {

	UserService service = new UserService();
	
	@GET
	public Response getAllGroups(){
		List<Group> list = service.getAllGroups();
		GenericEntity<List<Group>> entity = new GenericEntity<List<Group>>(list) {};
		Response response = Response.ok(entity).build();
		
		return response;
	}
	
	@GET
	@Path("{groupId}")
	public Response getAGroup(@PathParam("groupId") String gId){
		
		Group group = service.getAGroup(gId);
		return Response.ok(group).build();
	}
	
	
}
