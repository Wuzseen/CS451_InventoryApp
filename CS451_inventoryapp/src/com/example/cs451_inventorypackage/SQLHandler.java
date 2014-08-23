package com.example.cs451_inventorypackage;

// Some HTTP code gathered from:
// http://webdesignergeeks.com/mobile/android/android-login-authentication-with-remote-db/

import java.io.*;
import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;  
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.conn.params.ConnManagerParams;  
import org.apache.http.impl.client.DefaultHttpClient;  
import org.apache.http.params.HttpConnectionParams;  
import org.apache.http.params.HttpParams;  

public class SQLHandler {
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds 
	private HttpClient httpClient;
	public SQLHandler() {
		httpClient = new DefaultHttpClient();
		HttpParams p = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(p, HTTP_TIMEOUT);
		ConnManagerParams.setTimeout(p, HTTP_TIMEOUT);
	}
	
	public String makePost(String url, ArrayList<NameValuePair> params) {
		HttpPost request = new HttpPost(url);
		BufferedReader in = null;
		try {
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params);
			request.setEntity(formEntity);
			HttpResponse resp = this.httpClient.execute(request);
			in = new BufferedReader(new InputStreamReader(resp.getEntity().getContent()));
			StringBuffer sb = new StringBuffer("");
			String line = "";
			String NL = System.getProperty("line.separator");
			while((line = in.readLine()) != null) {
				sb.append(line + NL);
			} 
			in.close();
			String result = sb.toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
