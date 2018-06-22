package com.ibm.services.asset.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bson.Document;

import com.ibm.services.asset.resource.ASNDetails;
import com.ibm.services.asset.resource.ManufReturnRequest;
import com.ibm.services.asset.resource.LineItem;
import com.ibm.utils.MongoHelper;
import com.ibm.utils.PropertyManager;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;

/**
 * Data Access Object class for the application. Talks to Mongodb
 * 
 * @author SUDDUTT1
 *
 */
public class AssetDataDAO {

	// private static final Gson _DESERIALIZER = new
	// GsonBuilder().setPrettyPrinting().create();
	private static final String _RECORD_COLLECTION_NAME = "abcv2";
	private static final Logger _LOGGER = Logger.getLogger(AssetDataDAO.class.getName());

	/**
	 * Returns all the line items for the input status
	 * @param status String 
	 * @return
	 */
	public static List<LineItem> getAllLineItemsforStatus(String status){
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			LineItem srchObject = new LineItem();
			srchObject.append("status", status);
			List<LineItem> list = new ArrayList<>();
			for (Document doc : collection.find(srchObject.buildFilter())) {
				LineItem existingRecord = new LineItem();
				existingRecord.buildInstance(doc);
				list.add(existingRecord);
			}
			return list;
		}
		return null;
	}
	/**
	 * Returns all the line items for the input line item number
	 * @param lineItemId String 
	 * @return
	 */
	public static List<LineItem> getLineItems(String lineItemId){
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			LineItem srchObject = new LineItem();
			srchObject.append("lineItemId", lineItemId);
			List<LineItem> list = new ArrayList<>();
			for (Document doc : collection.find(srchObject.buildFilter())) {
				LineItem existingRecord = new LineItem();
				existingRecord.buildInstance(doc);
				list.add(existingRecord);
			}
			return list;
		}
		return null;
	}
	
	/**
	 * Returns all the line items associated with an ASN
	 * @param asnNumber String
	 * @return
	 */
	public static List<LineItem> getAllLineItemsforASN(String asnNumber){
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			LineItem srchObject = new LineItem();
			srchObject.append("asnNumber", asnNumber);
			List<LineItem> list = new ArrayList<>();
			for (Document doc : collection.find(srchObject.buildFilter())) {
				LineItem existingRecord = new LineItem();
				existingRecord.buildInstance(doc);
				list.add(existingRecord);
			}
			return list;
		}
		return null;
	}
	/**
	 * Searches a ASN based on status
	 * 
	 * @param status
	 *            String
	 * @return List<ASNDetails> if data exists , null other wise
	 */
	public static List<ASNDetails> getALLAsnByStatus(String status) {
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			ASNDetails srchObject = new ASNDetails();
			srchObject.append("status", status);
			List<ASNDetails> list = new ArrayList<>();
			for (Document doc : collection.find(srchObject.buildFilter())) {
				ASNDetails existingRecord = new ASNDetails();
				existingRecord.buildInstance(doc);
				list.add(existingRecord);
			}
			return list;
		}
		return null;
	}

	/**
	 * Searches a ASN based on ASN Number
	 * 
	 * @param asnNumber
	 *            String
	 * @return ASNDetails if data exists , null other wise
	 */
	public static ASNDetails getASNDetails(String asnNumber) {
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			ASNDetails srchObject = new ASNDetails();
			srchObject.append("asnNumber", asnNumber);
			for (Document doc : collection.find(srchObject.buildFilter())) {
				ASNDetails existingRecord = new ASNDetails();
				existingRecord.buildInstance(doc);
				return existingRecord;
			}
		}
		return null;
	}

	/**
	 * Searches a Disposal Request Details based on request number
	 * 
	 * @param asnNumber
	 *            String
	 * @return ASNDetails if data exists , null other wise
	 */
	public static ManufReturnRequest getDiposalRequest(String requestNumber) {
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			ManufReturnRequest srchObject = new ManufReturnRequest();
			srchObject.append("requestNumber", requestNumber);
			for (Document doc : collection.find(srchObject.buildFilter())) {
				ManufReturnRequest existingRecord = new ManufReturnRequest();
				existingRecord.buildInstance(doc);
				return existingRecord;
			}
		}
		return null;
	}
	/**
	 * Returns all the line items associated with an Disposal Request
	 * @param requestNumber String
	 * @return List<LineItem>
	 */
	public static List<LineItem> getAllLineItemsforDispReq(String requestNumber){
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			LineItem srchObject = new LineItem();
			srchObject.append("ddrUniqueid", requestNumber);
			List<LineItem> list = new ArrayList<>();
			for (Document doc : collection.find(srchObject.buildFilter())) {
				LineItem existingRecord = new LineItem();
				existingRecord.buildInstance(doc);
				list.add(existingRecord);
			}
			return list;
		}
		return null;
	}
	/**
	 * Update an ASN details
	 * 
	 * @param asnDetails
	 *            ASNDetails
	 * @return true if succeeds
	 */
	public static boolean updateASN(ASNDetails asnDetails) {
		ASNDetails searchItem = new ASNDetails();
		searchItem.append("asnNumber", asnDetails.get("asnNumber"));
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			// Removing ASN Number from input
			Document docToUpdate = Document.parse(asnDetails.toJson());
			UpdateResult result = collection.updateOne(searchItem.buildFilter(), new Document("$set", docToUpdate));
			return (result.getModifiedCount() > 0 || result.getMatchedCount() > 0 ? true : false);
		}
		return false;
	}
	/**
	 * Update an Disposal Request
	 * 
	 * @param request
	 *            DisposalRequest
	 * @return true if succeeds
	 */
	public static boolean updateDisposalRequest(ManufReturnRequest request) {
		ManufReturnRequest searchItem = new ManufReturnRequest();
		searchItem.append("requestNumber", request.get("requestNumber"));
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			// Removing ASN Number from input
			Document docToUpdate = Document.parse(request.toJson());
			UpdateResult result = collection.updateOne(searchItem.buildFilter(), new Document("$set", docToUpdate));
			return (result.getModifiedCount() > 0 || result.getMatchedCount() > 0 ? true : false);
		}
		return false;
	}

	/**
	 * Update an Line item details
	 * 
	 * @param asnDetails
	 *            ASNDetails
	 * @return true if succeeds
	 */
	public static boolean updateLineItems(List<LineItem> lineItems) {
		boolean rslt = true;
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			// Removing ASN Number from input
			for (LineItem li : lineItems) {
				LineItem searchItem = new LineItem();
				searchItem.append("lineItemId", li.get("lineItemId"));
				Document docToUpdate = Document.parse(li.toJson());
				UpdateResult result = collection.updateOne(searchItem.buildFilter(), new Document("$set", docToUpdate));
				rslt = rslt && (result.getModifiedCount() > 0 || result.getMatchedCount() > 0 ? true : false);
			}
			return rslt;
		}
		return false;
	}

	/**
	 * Saves ASN details
	 * 
	 * @param ASNDetails
	 *            ASN Details
	 * @return boolean true if save succeeds
	 */
	public static boolean saveASNDetails(ASNDetails asnDetails) {
		boolean isSucess = false;
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			collection.insertOne(Document.parse(asnDetails.toJson()));
			isSucess = true;
		}
		return isSucess;
	}

	/**
	 * Saves ManufReturnRequest Request
	 * 
	 * @param ManufReturnRequest
	 *            ManufReturnRequest request details
	 * @return boolean true if save succeeds
	 */
	public static boolean saveManufReturnRequest(ManufReturnRequest dispRequest) {
		boolean isSucess = false;
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			collection.insertOne(Document.parse(dispRequest.toJson()));
			isSucess = true;
		}
		return isSucess;
	}
	/**
	 * Save line items
	 * 
	 * @param List<LineItem>
	 *            lineItemList
	 * @return boolean true if save succeeds
	 */
	public static boolean saveLineItems(List<LineItem> lineItemList) {
		boolean isSucess = false;
		MongoCollection<Document> collection = getCollection();
		if (collection != null) {
			for (LineItem li : lineItemList) {
				collection.insertOne(Document.parse(li.toJson()));
			}
			isSucess = true;
		}
		return isSucess;
	}

	/**
	 * Save data for a user . No check is performed
	 * 
	 * @param emailid
	 * @return UserDetails if user exists , null other wise
	 *//*
		 * public static UserDetails saveUserDetails(UserDetails usrDetails) {
		 * UserDetails existingRecord = null; MongoCollection<Document>
		 * collection = getCollection(); if (collection != null) { UserDetails
		 * srchObject = new UserDetails();
		 * srchObject.setEmail(usrDetails.getEmail()); for (Document doc :
		 * collection.find(srchObject.buildFilter())) { existingRecord = new
		 * UserDetails(); existingRecord.buildInstance(doc); break; }
		 * if(existingRecord==null){
		 * collection.insertOne(Document.parse(usrDetails.toJson())); return
		 * usrDetails; } } return null; }
		 */

	/**
	 * Retrieve the collection as mentioned in the _RECORD_COLLECTION_NAME name
	 * 
	 * @return MongoCollection<Document>
	 */
	private static MongoCollection<Document> getCollection() {
		boolean isInitialized = MongoHelper.isInitialized();
		if (!isInitialized) {
			isInitialized = MongoHelper.init(PropertyManager.getProperties(MongoHelper.MONGO_PROPERTY_BUNCH));
		}
		_LOGGER.log(Level.WARNING, "Mongo helper initialiation status " + isInitialized);
		if (isInitialized) {
			MongoCollection<Document> collection = MongoHelper.getCollection(_RECORD_COLLECTION_NAME);
			return collection;

		}
		return null;
	}
}
