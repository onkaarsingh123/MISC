package com.urbanlife.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

@Provider
public class UrbanLifeExceptionMapper implements
		ExceptionMapper<UrbanLifeException> {
	@Override
	public Response toResponse(UrbanLifeException exception) {
		UrbanLifeError error = new UrbanLifeError();
		Gson gson = new Gson();
		error.setErrorCode(exception.getCode());
		error.setErrorMessage(exception.getMessage());
		if (exception.getSuppressed() != null) {
			error.setSuppressedMessage(exception.getSuppressed());
		}
		if (exception.getCause() != null) {
			error.setCauseMessage(exception.getCause().toString());
		}

		String json = gson.toJson(error);
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(json)
				.build();
	}
}
