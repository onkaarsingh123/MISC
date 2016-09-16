package com.urbanlife.rest;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import com.urbanlife.constants.UrbanLifeConstants;
import com.urbanlife.exception.UrbanLifeException;

@Path("/api/v1/tripadvisor")
public class TripAdvisorVerification {

//	public static void main(String[] args) {
//		// pass locationId and urbanlife's Key
//		int locationId = 943301;
//		String key = "8363abadfb334751845c10c8ce4714a2";
//
//		// URl for Merchant's Id verification
//		String urlStr = "http://api.tripadvisor.com/api/partner/2.0/location/"
//				+ locationId + "/hotels?key=" + key;
//
//		// if return True verification done
//		boolean isTripAdvisorVerified = onTripAdvisorVerification(urlStr);
//
//		System.out
//				.println("TripAdvisor Verification: " + isTripAdvisorVerified);
//
//	}

	// on TripAdvisor Verification Based on Location Id or ID
	@Consumes("*/*")
	@GET
	@Produces("application/json")
	@Path(value = "/verify/{id}")
	public Response onTripAdvisorVerification(String id)
			throws UrbanLifeException {
		int locationId = 94330;
		String key = "8363abadfb334751845c10c8ce4714a2";
		String urlStr = "http://api.tripadvisor.com/api/partner/2.0/location/"
				+ id + "/hotels?key=" + key;
		Gson gson = new Gson();
		String gSonString = null;
		boolean isVerified = false;
		URL url;
		HttpURLConnection connection = null;
		try {
			// Create connection
			url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("HEAD");
			connection.setConnectTimeout(1000);
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				isVerified = true;
				gSonString = UrbanLifeConstants.TRUE;
			}

		} catch (Exception e) {

			e.printStackTrace();
			isVerified = false;
			gSonString = UrbanLifeConstants.FALSE;
			throw new UrbanLifeException(
					UrbanLifeConstants.TRIPADVISOR_ID_INCORRECT,
					UrbanLifeConstants.TRIPADVISOR_ID_INCORRECT_CODE);
			
		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}

		
		String result = gson.toJson(gSonString);
		return Response.ok(result, MediaType.APPLICATION_JSON).build();
	}

}
