package com.ibm.services.asset.resource;

import org.bson.Document;

import com.ibm.utils.MongoSerializable;

public class TrackingRecord extends MongoSerializable {
	
	/**
	 * Keeps the complier happy
	 */

	private static final long serialVersionUID = -5099628828026509439L;
	@Override
	public void buildInstance(Document doc) {
		// TODO Auto-generated method stub
		setInternalFields(doc);
	}

}
