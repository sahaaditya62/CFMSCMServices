package com.ibm.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint("/asyncmessage")
public class AsyncMessageHanlder {

	private static Map<String, Session> _queue = new ConcurrentHashMap<>();
	private static final Logger _LOGGER = Logger
			.getLogger(AsyncMessageHanlder.class.getName());

	@OnOpen
	public void open(Session session) {
		// Here is a new Device opened
		_queue.put(session.getId(), session);
		_LOGGER.info("Device added : " + session.getId());

	}

	@OnClose
	public void close(Session session) {
		_queue.remove(session.getId());
		_LOGGER.info("Device removed : " + session.getId());
	}

	@OnError
	public void onError(Throwable error) {
	}

	@OnMessage
	public void handleMessage(String message, Session session) {

	}

	public static boolean sendMessage(String payload) {
		boolean isSuccess = false;
		try{
		for (Session session : _queue.values()) {
			// Send the message to all
			// TODO: Need to change this .. Insted of sending all send to
			// specific member.
			if(session.isOpen())
			{
				session.getBasicRemote().sendText(payload);
			}
			else
			{
				_queue.remove(session.getId());
			}
			isSuccess = true;	
				
		}
		}catch(Throwable th)
		{
			_LOGGER.log(Level.WARNING,"Error is sending message "+ payload ,th);
			isSuccess = false;
		}
		return isSuccess;
	}
}
