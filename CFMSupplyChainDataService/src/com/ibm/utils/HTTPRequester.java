package com.ibm.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Custom lightweight HTTP requester without any certificate check.
 * 
 * @author SUDDUTT1
 * 
 */
public class HTTPRequester {
	private static final Logger _LOGGER = Logger.getLogger(HTTPRequester.class
			.getName());
	private static final String USER_AGENT = "Mozilla/5.0";
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
		try {
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, _trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * Send a POST request to input url with postbody . No URLEncoding
	 * 
	 * @param url
	 *            String Url to invoke
	 * @param postBody
	 *            Post body ( No URL Encoding)
	 * @param header
	 *            Set of header values
	 */
	public static HTTPResponse sendPostRequest(String url, String postBody,
			Map<String, String> header) {
		HTTPResponse response = null;
		StringBuffer responseContent = new StringBuffer();
		HttpURLConnection con =null;
		try {
			if(url.startsWith("https:"))
			{
				con = (HttpsURLConnection) (new URL(url))
					.openConnection();
			}
			else{
				con = (HttpURLConnection) (new URL(url))
						.openConnection();
			}
			// TODO:Add request header
			
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			//con.setRequestMethod("POST");
			//con.setRequestProperty("User-Agent", USER_AGENT);
			//con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postBody);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			_LOGGER.fine("\nSending 'POST' request to URL : " + url);
			_LOGGER.fine("Post parameters : " + postBody);
			_LOGGER.fine("Response Code : " + responseCode);
			if (responseCode == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;

				while ((inputLine = in.readLine()) != null) {
					responseContent.append(inputLine);
				}
				in.close();
				_LOGGER.fine(responseContent.toString());
			}
			response = new HTTPResponse(responseCode,
					responseContent.toString());

		} catch (Throwable th) {
			_LOGGER.log(Level.WARNING, "Error thrown: ", th);
			response = new HTTPResponse(HTTPResponse.CLIENT_SIDE_ERROR,
					th.getMessage());
		}
		return response;
	}

}
