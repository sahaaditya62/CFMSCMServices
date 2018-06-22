package com.ibm.services.asset.hlfdao.test;

import com.ibm.hlfabric.client.HLFabricResponse;
import com.ibm.services.asset.ApplicationConstants;
import com.ibm.services.asset.hlfdao.SupplyChainMgmtHLFDAO;
import com.ibm.services.asset.resource.TrackingRecord;
import com.ibm.utils.CommonUtil;
import com.ibm.utils.PropertyManager;
import com.ibm.utils.RandomDataGenUtil;

public class SupplyChainManagementHLDAOTest {
	/*private static final int TIME_OUT = 10000;
	
	public static void main(String[] args) throws Exception {
		PropertyManager.initProperty(ApplicationConstants.APP_PROPS_BUNDLE, "application.properties", true);
		String shipmentNumber = RandomDataGenUtil.generateRandomUUID(null, new int[] { 4, 3, 4 });
		TrackingRecord trackingRecord = createShipment(shipmentNumber);
		HLFabricResponse resp = SupplyChainMgmtHLFDAO.createNewShipment(shipmentNumber, trackingRecord,
				ApplicationConstants.BE_MINE);
		if (resp.isOk()) {
			System.out.println("Created the shipment :" + shipmentNumber);

		}
		Thread.sleep(TIME_OUT);
		HLFabricResponse rtrvResp = SupplyChainMgmtHLFDAO.getShipmentsByStatus(
				ApplicationConstants.STATUS_INIT_SHIPMENT_FROM_MINE, ApplicationConstants.BE_LOGISTICS);
		if(rtrvResp.isOk()){
			System.out.println("Output :" + rtrvResp.getMessage());
			TrackingRecord[] recs = CommonUtil.fromJson(rtrvResp.getMessage(), TrackingRecord[].class);
			System.out.println(recs);
		}
		Thread.sleep(TIME_OUT);
		TrackingRecord updatedRecord = updateStatus(shipmentNumber, ApplicationConstants.STATUS_SHIPMENT_ACK);
		HLFabricResponse respUpd = SupplyChainMgmtHLFDAO.updateShipment(shipmentNumber, updatedRecord,
				ApplicationConstants.BE_MINE);
		if (respUpd.isOk()) {
			System.out.println("Updated the shipment :" + shipmentNumber);

		}
		Thread.sleep(TIME_OUT);
		rtrvResp = SupplyChainMgmtHLFDAO.getShipmentsByStatus(
				ApplicationConstants.STATUS_SHIPMENT_ACK, ApplicationConstants.BE_LOGISTICS);
		if(rtrvResp.isOk()){
			System.out.println("Output :" + rtrvResp.getMessage());
			TrackingRecord[] recs = CommonUtil.fromJson(rtrvResp.getMessage(), TrackingRecord[].class);
			System.out.println(recs);
		}
	}

	private static TrackingRecord createShipment(String shipmentNumber) {

		TrackingRecord record = new TrackingRecord();
		record.put("shipmentNumber", shipmentNumber);
		record.put("date", CommonUtil.getTimeStamp());
		record.put("shipmentWt", RandomDataGenUtil.pickupFromList(new String[] { "20000", "40000", "12000", "30000" }));
		record.put("shipType", RandomDataGenUtil.pickupFromList(new String[] { "Rail", "Truck", "Sea", "Air" }));
		record.put("shipSrc", RandomDataGenUtil
				.pickupFromList(new String[] { "MINE COMP 1", "MINE COMP 1", "MINE COMP 1", "MINE COMP 1" }));
		record.put("shipDest",
				RandomDataGenUtil.pickupFromList(new String[] { "SMELTER 1", "SMELTER 2", "SMELTER 3", "SMELTER 4" }));
		record.put("shipingComp",
				RandomDataGenUtil.pickupFromList(new String[] { "Comp A", "Comp B", "Comp C", " Comp D" }));
		record.put("sealNumber", RandomDataGenUtil.generateRandomUUID(null, new int[] { 8, 2 }));
		record.put("contractNumber", RandomDataGenUtil.generateRandomUUID(null, new int[] { 3, 4, 3 }));
		record.put("expYield", RandomDataGenUtil.pickupFromList(new String[] { "0.5", "1.4", "1.9", "2.2" }));
		record.put("type", RandomDataGenUtil.pickupFromList(new String[] { "Ore" }));
		record.put("oreType", RandomDataGenUtil.pickupFromList(new String[] { "Ignecious Rock" }));
		record.put("srcMine", RandomDataGenUtil.pickupFromList(new String[] { "MINE1", "MINE2", "MINE3", "MINE4" }));
		record.put("catetogy", "MINE_TO_SMELTER");
		record.put("status", ApplicationConstants.STATUS_INIT_SHIPMENT_FROM_MINE);
		record.remove("objType");

		return record;

	}
	
	private static TrackingRecord updateStatus(String shipmentNumber,String status) {

		TrackingRecord record = new TrackingRecord();
		record.put("shipmentNumber", shipmentNumber);
		record.put("status", status);
		record.put("vehicleId",RandomDataGenUtil.generateRandomUUID(null, new int[]{2,3,3}));
		record.put("shipperRecvdWt", "20200");
		record.remove("objType");
		return record;

	}*/
}
