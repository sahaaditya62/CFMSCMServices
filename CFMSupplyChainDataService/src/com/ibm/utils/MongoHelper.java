package com.ibm.utils;

import java.net.InetSocketAddress;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Helper class for mongo db
 * 
 * @author SUDDUTT1
 *
 */
public class MongoHelper {

	public static final String MONGO_PROPERTY_BUNCH = "MONGO_PROP";
	public static final String MONGO_CONNECT_URI = "mongo.db.uri";
	public static final String MONGO_CONNECT_HOST = "mongo.db.hostname";
	public static final String MONGO_CONNECT_PORT = "mongo.db.hostport";
	public static final String MONGO_DB_USERID = "mongo.db.uid";
	public static final String MONGO_DB_PASSWORD = "mongo.db.pwd";
	public static final String MONGO_DB_NAME = "mongo.db.name";
	private static String _dbhost;
	private static String _dbname;
	private static int _port;
	private static String _userid;
	private static String _password;
	private static String _dburi;

	private static MongoClient _mongoClient = null;
	private static MongoDatabase _database = null;
	private static boolean _isInitalized = false;
	private static TrustManager[] _trustAllCerts = null;

	static {
		
		_trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[0];
			}

			public void checkClientTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(
					java.security.cert.X509Certificate[] certs, String authType) {
			}
			
		} };
	}

	public static boolean init(Properties connectionProps) {
		try {
			_dburi = connectionProps.getProperty(MONGO_CONNECT_URI);
			if (_dburi == null) {
				SSLContext sc = SSLContext.getInstance("TLS");
				sc.init(null, _trustAllCerts, new java.security.SecureRandom());
				_dbhost = connectionProps.getProperty(MONGO_CONNECT_HOST);
				_dbname = connectionProps.getProperty(MONGO_DB_NAME);
				_port = Integer.parseInt(connectionProps
						.getProperty(MONGO_CONNECT_PORT));
				_userid = connectionProps.getProperty(MONGO_DB_USERID);
				_password = connectionProps.getProperty(MONGO_DB_PASSWORD);
				MongoClientOptions opts = MongoClientOptions.builder()
						.socketFactory(sc.getSocketFactory()).build();
				ServerAddress address = new ServerAddress(
						new InetSocketAddress(_dbhost, _port));
				MongoCredential cred = MongoCredential.createCredential(
						_userid, _dbname, _password.toCharArray());
				List<MongoCredential> credList = new ArrayList<>();
				credList.add(cred);
				_mongoClient = new MongoClient(address, credList, opts);
			}
			else
			{
				MongoClientURI connectionString = new MongoClientURI(_dburi);
				_mongoClient = new MongoClient(connectionString);
				_dbname = connectionProps.getProperty(MONGO_DB_NAME);
			}
			_database = _mongoClient.getDatabase(_dbname);
			_isInitalized = true;
		} catch (Throwable th) {
			th.printStackTrace();
			_isInitalized = false;
		}
		return _isInitalized;
	}

	public static boolean isInitialized() {
		return _isInitalized;
	}

	public static MongoCollection<Document> getCollection(String name) {
		if (isInitialized()) {
			MongoCollection<Document> collection = _database
					.getCollection(name);
			return collection;
		}
		return null;
	}
}
