package com.ibm.utils;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;


import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * This class is the base class of a java bean that need to be serialized in a
 * mongo nosql db.
 * 
 * @author SUDDUTT1
 *
 */
@SuppressWarnings("serial")
public abstract class MongoSerializable extends Document {
	private String objType;
	
	public MongoSerializable() {
		super();
		this.objType = this.getClass().getName();
		put("objType",this.objType);
	}

	public abstract void buildInstance(Document doc);

	public void setInternalFields(Document doc) {
		for (String key : doc.keySet()) {
			this.put(key, doc.get(key));
		}
	}

	public Bson buildFilter() {
		List<Bson> filters = new ArrayList<>();
		for (String key : this.keySet()) {
			filters.add(eq(key, this.get(key)));
		}
		return and(filters);
	}

	/**
	 * @return the objType
	 */
	public final String getObjType() {
		return (String)get("objType");
	}

	/**
	 * @param objType the objType to set
	 */
	public final void setObjType(String objType) {
		this.objType = objType;
		put("objType",objType);
	}

	
}
