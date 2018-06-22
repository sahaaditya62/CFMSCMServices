package com.ibm.services.asset.resource;

import org.bson.Document;

import com.ibm.utils.MongoSerializable;

public class ManufReturnRequest extends MongoSerializable {
	
	/**
	 * Keep the compiler happy
	 */
	private static final long serialVersionUID = 3690957514929458317L;

	@Override
	public void buildInstance(Document doc) {
		
		setInternalFields(doc);
	}

}
