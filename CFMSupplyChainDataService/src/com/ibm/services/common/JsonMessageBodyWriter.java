package com.ibm.services.common;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Produces(MediaType.APPLICATION_JSON)
public class JsonMessageBodyWriter implements MessageBodyWriter<JsonSerializable> {

	private static final Gson _GSON_SERIALIZER = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
	@Override
	public long getSize(JsonSerializable arg0, Class<?> arg1, Type arg2, Annotation[] arg3, MediaType arg4) {
		// Deprecated
		
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		// TODO Auto-generated method stub
		return (type.isInstance(JsonSerializable.class));
	}

	@Override
	public void writeTo(JsonSerializable objectToSerialize, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
			throws IOException, WebApplicationException {
		String serializedContent = _GSON_SERIALIZER.toJson(objectToSerialize);
		entityStream.write(serializedContent.getBytes());
		entityStream.flush();

	}

}
