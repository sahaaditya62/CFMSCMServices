package com.ibm.services.asset;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.hlfabric.client.HLFabricResponse;
import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.ibm.services.asset.hlfdao.SupplyChainMgmtHLFDAO;
import com.ibm.services.asset.resource.TrackingRecord;
import com.ibm.services.common.ServiceResponse;
import com.ibm.services.common.Status;
import com.ibm.utils.CommonUtil;
import com.ibm.utils.HTTPRequester;
import com.ibm.utils.HTTPResponse;

@Path("scmdata")
public class SupplyChainService {

	private static Logger _LOGGER = Logger.getLogger(SupplyChainService.class.getName());
	private static final boolean _saveInDB = false;
	private static Map<String, String> _STATUS_MAP = new HashMap<>();
	private static Map<String, String> _DEFAULT_STAT = new HashMap<>();
	String invokeUrl = "http://35.237.159.236:4200/api/invoke";
	String queryUrl = "http://35.237.159.236:4200/api/query";

	static {

	}

	/**
	 * Probes the service for configuration check up.
	 * 
	 * @return Response
	 */
	@GET
	@Path("probe")
	@Produces(MediaType.APPLICATION_JSON)
	public Response probe() {
		ServiceResponse resp = null;
		HLFabricResponse hlfResponse = null;
		try {
			/*hlfResponse = SupplyChainMgmtHLFDAO.getShipmentsByStatus(
					ApplicationConstants.STATUS_INIT_SHIPMENT_FROM_MINE, ApplicationConstants.BE_MINE);
			*/if (hlfResponse.isOk()) {
				resp = new ServiceResponse(Status.SUCCESS, "Probed succesfully ", hlfResponse.getMessage());
			} else {

				resp = new ServiceResponse(Status.SUCCESS, "Block chain not responding ", hlfResponse.getMessage());
			}

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "Exception thrown in probe", ex);
			resp = new ServiceResponse(Status.EXCEPTION, "Exception thrown at server side probe",
					CommonUtil.serializeThowable(ex));
		}
		return Response.ok().entity(resp).build();
	}

	/**
	 * Creates a new Shipment
	 * 
	 * @param postBody
	 *            Post body in json format
	 * @return Response
	 */
	@POST
	@Path("createNewShipment")
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewShipment(String postBody) {
		ServiceResponse resp;
		try {
			
			String requestPost1 = postBody.replace('}', ',');
			String requestPostBody = requestPost1.concat("\"status\" : \"SHIPMENT_INITIATED_FROM_MINE\"}");
			
			TrackingRecord requestObject = CommonUtil.fromJson(postBody, TrackingRecord.class);

			if (requestObject != null) {
				String args = "  \"args\": [\"shipmentNumber\"," + CommonUtil.toJson(requestPostBody)+ "],\r\n";
				String payload="{\r\n" + 
						"  \"channel\": \"confmintrackingchannel\",\r\n" + 
						"  \"org\": \"mine\",\r\n" + 
						"  \"ccid\": \"tcm\",\r\n" + 
						"  \"fn\": \"createShipment\",\r\n" + 
						   args +
						"  \"invokerRole\": \"USER1\"\r\n" + 
						"}";
				
				int responseCode = sendPostRequest(invokeUrl,payload);
				requestObject.put("status", ApplicationConstants.STATUS_INIT_SHIPMENT_FROM_MINE);
				requestObject.remove("objType");
				String shipmentNumber = requestObject.getString("shipmentNumber");
				// Now saving in Block chain
				if (responseCode==200) {
					resp = new ServiceResponse(Status.SUCCESS, "Shipment created successfully", "");
				} else {
					resp = new ServiceResponse(Status.FAILED_HLF, "Failed to store Shipment is Hyperledger Fabric", "");
				}

			} else {
				resp = new ServiceResponse(Status.INVALID_INPUT, "Invalid inputs provided", "");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "Exception thrown in createNewShipment", ex);
			resp = new ServiceResponse(Status.EXCEPTION, "Exception thrown at server side createNewShipment",
					ex.getMessage());
		}
		return Response.ok().entity(resp).build();
	}

	/**
	 * Updates an existing Shipment
	 * 
	 * @param postBody
	 *            Post body in json format
	 * @return Response
	 */
	@POST
	@Path("updateShipmentRecord")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateShipmentRecord(String postBody) {
		ServiceResponse resp;
		try {
			TrackingRecord requestObject = CommonUtil.fromJson(postBody, TrackingRecord.class);

			if (requestObject != null) {
				String args = "  \"args\": [\"shipmentNumber\"," + CommonUtil.toJson(postBody)+"],\r\n";
				String payload="{\r\n" + 
						"  \"channel\": \"confmintrackingchannel\",\r\n" + 
						"  \"org\": \"transporter\",\r\n" + 
						"  \"ccid\": \"tcm\",\r\n" + 
						"  \"fn\": \"updateShipment\",\r\n" + 
						   args +
						"  \"invokerRole\": \"ADMIN\"\r\n" + 
						"}";
				
				int responseCode = sendPostRequest(invokeUrl,payload);
				// Now saving in Block chain
				if (responseCode==200) {
					resp = new ServiceResponse(Status.SUCCESS, "Shipment updated successfully", "");
				} else {
					resp = new ServiceResponse(Status.FAILED_HLF, "Failed to update Shipment is Hyperledger Fabric",
							"");
				}

			} else {
				resp = new ServiceResponse(Status.INVALID_INPUT, "Invalid inputs provided", "");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "Exception thrown in updateShipmentRecord", ex);
			resp = new ServiceResponse(Status.EXCEPTION, "Exception thrown at server side updateShipmentRecord",
					ex.getMessage());
		}
		return Response.ok().entity(resp).build();
	}

	/**
	 * Returns shipment by status
	 * 
	 * @param status
	 *            String
	 * @return Response
	 */
	@GET
	@Path("shipmentListByStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shipmentListByStatus(@QueryParam("status") String status) {
		ServiceResponse resp;
		try {
			
			
			String s = "\\\"status\\\" :" + "\\\"" + status+"\\\"";
			String args = "  \"args\": [\"{" + s + "}\"],\r\n";
			//String args = "  \"args\": [{\"status\":" + CommonUtil.toJson(status)+"}],\r\n";
			String payload="{\r\n" + 
					"  \"channel\": \"confmintrackingchannel\",\r\n" + 
					"  \"org\": \"mine\",\r\n" + 
					"  \"ccid\": \"tcm\",\r\n" + 
					"  \"fn\": \"getRecordsByQuery\",\r\n" + 
					   args  +
					"  \"invokerRole\": \"USER1\"\r\n" + 
					"}";
		
			String payload1="{\r\n" + 
					"  \"channel\": \"confmintrackingchannel\",\r\n" + 
					"  \"org\": \"mine\",\r\n" + 
					"  \"ccid\": \"tcm\",\r\n" + 
					"  \"fn\": \"getRecordsByQuery\",\r\n" + 
					"  \"args\": [\"{\\\"status\\\":\\\"SHIPMENT_INITIATED_FROM_MINE\\\"}\"],\r\n" + 
					"  \"invokerRole\": \"USER1\"\r\n" + 
					"}";
			
			
			
			
			
			String responseString = sendPostRequestForQuery(queryUrl,payload);
			/*HLFabricResponse hlResp = SupplyChainMgmtHLFDAO.getShipmentsByStatus(status,
					ApplicationConstants.BE_LOGISTICS,payload);*/
			
			JSONObject p = (JSONObject) JSON.parse(responseString);
			JSONArray uris = (JSONArray) p.get("Payload");
			// Take the first uri
			/*if (uris != null) {
				System.out.print((String) uris.iterator().next());
			}*/
			
				resp = new ServiceResponse(Status.SUCCESS, "Shipments retived successfully", p);
			

		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "Exception thrown in shipmentListByStatus", ex);
			resp = new ServiceResponse(Status.EXCEPTION, "Exception thrown at server side shipmentListByStatus",
					ex.getMessage());
		}
		return Response.ok().entity(resp).build();
	}
	
	public static int sendPostRequest(String requestUrl, String payload) {
		
		StringBuffer jsonString = new StringBuffer();
		int responseCode;
	    try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        responseCode = connection.getResponseCode();
	        connection.disconnect();
	        
	    } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	    }
	    return responseCode;
	}
	
	public static String sendPostRequestForQuery(String requestUrl, String payload) {
		
		StringBuffer jsonString = new StringBuffer();
		try {
	        URL url = new URL(requestUrl);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Accept", "application/json");
	        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	        writer.write(payload);
	        writer.close();
	        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	        String line;
	        while ((line = br.readLine()) != null) {
	                jsonString.append(line);
	        }
	        br.close();
	        connection.disconnect();
	        
	    } catch (Exception e) {
	            throw new RuntimeException(e.getMessage());
	    }
	    return jsonString.toString();
	}
}
