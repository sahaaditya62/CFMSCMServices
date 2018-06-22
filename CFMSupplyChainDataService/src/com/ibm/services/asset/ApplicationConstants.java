package com.ibm.services.asset;

public interface ApplicationConstants {

	
	int ACTION_SUCESS = 0;
	int ACTION_ERROR = 1;
	int ACTION_EXCEPTION = 2;
	int ACTION_INVALID_INPUT =3;
	
	
	String APP_PROPS_BUNDLE = "APP_PROPS";
	String APP_USER = "app.user";
	String APP_USER_ROLE = "app.user.role";
	String APP_LOGIN_AUTH_TOKEN = "auth_token";
	String APP_ACTION_RESPONSE="actionResponse";
	
	String HL_URL = "hyperledger.server.url";
	String HL_CHAIN_CODE_ID = "hyperledger.chaincode.id";
	String HL_USER_CONTEXT ="hyperledger.user.context";
	String HL_USER_ID ="hyperledger.user.id";
	String HL_USER_SECRET ="hyperledger.user.secret";
	
	String BE_MINE ="MINE";
	String BE_LOGISTICS ="LOGISTICS";
	String BE_SMELTER ="SMELTER";
	String BE_REFINER ="REFINER";
	
	String STATUS_INIT_SHIPMENT_FROM_MINE="SHIPMENT_INITIATED_FROM_MINE";
	String STATUS_SHIPMENT_ACK="SHIPMENT_ACKNOWLEDGED";
	String STATUS_SHIPMENT_DELIVERED="SHIPMENT_DELIVERED_TO_SMELTER";
	String STATUS_SHIPMENT_RECEIVED_BY_SMELTER="SHIPMENT_RECEIVED_BY_SMELTER";
	
	String STATUS_SMELTING_WIP="SMELTING_IN_PROGRESS";
	
	
	
	
}
