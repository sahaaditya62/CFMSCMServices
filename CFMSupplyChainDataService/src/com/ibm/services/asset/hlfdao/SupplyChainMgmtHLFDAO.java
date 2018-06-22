package com.ibm.services.asset.hlfdao;

import static com.ibm.services.asset.ApplicationConstants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ibm.hlfabric.client.HLFabricClient;
import com.ibm.hlfabric.client.HLFabricRequest;
import com.ibm.hlfabric.client.HLFabricResponse;
import com.ibm.services.asset.resource.ASNDetails;
import com.ibm.services.asset.resource.LineItem;
import com.ibm.services.asset.resource.ManufReturnRequest;
import com.ibm.services.asset.resource.TrackingRecord;
import com.ibm.utils.CommonUtil;
import com.ibm.utils.PropertyManager;

public class SupplyChainMgmtHLFDAO {

	private static final Logger _LOGGER = Logger.getLogger(SupplyChainMgmtHLFDAO.class.getName());

	private static Map<String, Map<String, String>> _credentialMap = null;
	private static String _hyperLedgerUrl_MINE = null;
	private static String _hyperLedgerUrl_LOGISTICS = null;
	private static String _hyperLedgerUrl_SMELTER = null;
	private static String _hyperLedgerUrl_REFINER = null;

	private static String _chainCode = null;

	
	private static void waitForSync(long timeinMillis) {
		try {
			Thread.sleep(timeinMillis);
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "Exception while waiting ", ex);
		}
	}

	/**
	 * Retrieves all line items for input lineItem id
	 * 
	 * @param lineItemId
	 *            String
	 * @param businessEntity
	 *            String
	 * @return HLFabricResponse
	 */
	public static HLFabricResponse getShipmentsByStatus(String status, String businessEntity, String payload) {
		HLFabricResponse response = null;
		try {
			HLFabricClient client = getClient(businessEntity);
			//if (client.register()) {
				HLFabricRequest getASNDetailsRequest = buildShipmentsByStatus(status,
						getUserId(businessEntity));
				response = client.invokeMethod(getASNDetailsRequest,payload);
			//} else {
				//response = new HLFabricResponse(false);
				//response.setMessage("User registration failed");
			//}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "HLF getShipmentsByStatus failed ", ex);
			response = new HLFabricResponse(false);
			response.setMessage("Exception in getShipmentsByStatus:" + ex.getMessage());
		}
		return response;
	}

	/**
	 * Creates a new shipment
	 * @param shipmentNumber String
	 * @param trackingRecord TrackingRecord
	 * @param businessEntity String
	 * @return HLFabricResponse
	 */
	/*public static HLFabricResponse createNewShipment(String shipmentNumber, TrackingRecord trackingRecord,
			String businessEntity) {
		HLFabricResponse response = null;
		try {
			HLFabricClient client = getClient(businessEntity);
			if (client.register()) {
				HLFabricRequest newASNRequest = buildCreateNewShipmentRequest(shipmentNumber, trackingRecord, getUserId(businessEntity));
				response = client.invokeMethod(newASNRequest);
			} else {
				response = new HLFabricResponse(false);
				response.setMessage("User registration failed");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "HLF createNewShipment failed ", ex);
			response = new HLFabricResponse(false);
			response.setMessage("Exception in createNewShipment:" + ex.getMessage());
		}
		return response;
	}*/
	/**
	 * Updates existing tracking details
	 * @param shipmentNumber String
	 * @param trackingRecord TrackingRecord
	 * @param businessEntity String
	 * @return HLFabricResponse
	 */
	/*public static HLFabricResponse updateShipment(String shipmentNumber, TrackingRecord trackingRecord,
			String businessEntity) {
		HLFabricResponse response = null;
		try {
			HLFabricClient client = getClient(businessEntity);
			if (client.register()) {
				HLFabricRequest updateShipmentReq = buildUpdateShipmentRequest(shipmentNumber, trackingRecord, getUserId(businessEntity));
				response = client.invokeMethod(updateShipmentReq);
			} else {
				response = new HLFabricResponse(false);
				response.setMessage("User registration failed");
			}
		} catch (Exception ex) {
			_LOGGER.log(Level.WARNING, "HLF updateShipment failed ", ex);
			response = new HLFabricResponse(false);
			response.setMessage("Exception in updateShipment:" + ex.getMessage());
		}
		return response;
	}*/

	
	/** 
	 * Builds a shipment request
	 * @param shipmentNumber String
	 * @param payload  TrackingRecord
	 * @param userId String
	 * @return HLFabricRequest
	 */
	private static HLFabricRequest buildCreateNewShipmentRequest(String shipmentNumber, TrackingRecord payload, String userId) {
		HLFabricRequest request = new HLFabricRequest();
		request.setMethod("invoke");
		request.setChainCodeName(_chainCode);
		request.setCallFunction("createShipment");
		request.setFunctionArgs(
				new String[] { shipmentNumber, CommonUtil.toJsonNoPP(payload), userId });

		request.setSecureContext(userId);
		return request;
	}
	/** 
	 * Builds a update record request
	 * @param shipmentNumber String
	 * @param payload  TrackingRecord
	 * @param userId String
	 * @return HLFabricRequest
	 */
	private static HLFabricRequest buildUpdateShipmentRequest(String shipmentNumber, TrackingRecord payload, String userId) {
		HLFabricRequest request = new HLFabricRequest();
		request.setMethod("invoke");
		request.setChainCodeName(_chainCode);
		request.setCallFunction("updateShipment");
		request.setFunctionArgs(
				new String[] { shipmentNumber, CommonUtil.toJsonNoPP(payload), userId });

		request.setSecureContext(userId);
		return request;
	}

	/**
	 * Builds a HLF request object for retrieving line items for the input
	 * lineItemId
	 * 
	 * @param lineItemId
	 *            String
	 * @param userId
	 *            String
	 * @return HLFabricRequest
	 */
	private static HLFabricRequest buildShipmentsByStatus(String status, String userId) {
		HLFabricRequest request = new HLFabricRequest();
		request.setMethod("query");
		request.setChainCodeName(_chainCode);
		request.setCallFunction("getAllRecordsByStatus");

		request.setFunctionArgs(new String[] { status, userId });

		request.setSecureContext(userId);
		return request;
	}

	/**
	 * Returns the HLFClient instance based on the input business entity
	 * 
	 * @param context
	 * @return HLFabricClient
	 */
	private static HLFabricClient getClient(String context) {
		HLFabricClient client = null;
		init();
		if (context.equals(BE_MINE)) {
			client = new HLFabricClient(_hyperLedgerUrl_MINE, getUserId(context),
					_credentialMap.get(context).get("secret"));
		} else if (context.equals(BE_LOGISTICS)) {
			client = new HLFabricClient(_hyperLedgerUrl_LOGISTICS, getUserId(context),
					_credentialMap.get(context).get("secret"));
		} else if (context.equals(BE_SMELTER)) {
			client = new HLFabricClient(_hyperLedgerUrl_SMELTER, getUserId(context),
					_credentialMap.get(context).get("secret"));
		} else {
			client = new HLFabricClient(_hyperLedgerUrl_REFINER, getUserId(context),
					_credentialMap.get(context).get("secret"));

		}
		return client;
	}

	/**
	 * Returns the context and user ids
	 * 
	 * @param context
	 * @return users actual user id
	 */
	private static String getUserId(String context) {
		if (_credentialMap == null) {
			init();
		}
		return _credentialMap.get(context).get("uid");
	}

	/**
	 * Initialize properties and urls
	 */
	private static void init() {
		if (_hyperLedgerUrl_MINE == null) {
			_hyperLedgerUrl_MINE = PropertyManager.getStringProperty(APP_PROPS_BUNDLE,
					HL_URL + "." + BE_MINE);
			_hyperLedgerUrl_LOGISTICS = PropertyManager.getStringProperty(APP_PROPS_BUNDLE, HL_URL + "." + BE_LOGISTICS);
			_hyperLedgerUrl_SMELTER = PropertyManager.getStringProperty(APP_PROPS_BUNDLE,
					HL_URL + "." + BE_SMELTER);
			_hyperLedgerUrl_REFINER = PropertyManager.getStringProperty(APP_PROPS_BUNDLE,
					HL_URL + "." + BE_REFINER);
		}
		if (_chainCode == null) {
			_chainCode = PropertyManager.getStringProperty(APP_PROPS_BUNDLE, HL_CHAIN_CODE_ID);
		}
		if (_credentialMap == null) {
			_credentialMap = new HashMap<String, Map<String, String>>();
			String contexts = PropertyManager.getStringProperty(APP_PROPS_BUNDLE, HL_USER_CONTEXT);
			String userIds = PropertyManager.getStringProperty(APP_PROPS_BUNDLE, HL_USER_ID);
			String secrets = PropertyManager.getStringProperty(APP_PROPS_BUNDLE, HL_USER_SECRET);
			String[] secretList = secrets.split(",");
			String[] userList = userIds.split(",");
			String[] contextList = contexts.split(",");
			for (int index = 0; index < contextList.length; index++) {
				Map<String, String> contextMap = new HashMap<>();
				contextMap.put("uid", userList[index]);
				contextMap.put("secret", secretList[index]);
				_credentialMap.put(contextList[index], contextMap);
			}

		}
	}
}