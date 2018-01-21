/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tenpearls.olaround.helpers;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import net.sf.jazzlib.GZIPInputStream;
import com.sun.lwuit.Display;
import com.sun.lwuit.Image;
import com.tenpearls.olaround.constants.ApplicationConstants;
import com.tenpearls.olaround.constants.HTTPConstatns;
import com.tenpearls.olaround.ui.components.CustomDialogBox;


public class WebServiceHelper {
	static boolean connectionInProgress = false;
	static HttpConnection httpConn;
	static InputStream inputStream;
	static boolean isNoDataAvailable = false;
	public static int responseCode = 0;
	
	public static boolean isInputStreamOpen() 
	{
		if (inputStream != null)
		{
			return true;
		}

		return false;
	}

	public static void closeInputStream() 
	{
		if (inputStream != null)
		{
			try {
				inputStream.close();
			} 
			catch (IOException e) {

			}
			finally {
				inputStream = null;
			}
		}
	}
	
	public static void closeHttpConn() 
	{
		if (httpConn != null)
		{
			try
			{
				httpConn.close();
			}
			catch(Exception e)
			{
				
			}
			finally
			{
				httpConn = null;
			}
		}
	}

	public static boolean isHttpConnOpen() 
	{
		if (httpConn != null)
		{
			return true;
		}

		return false;
	}
	
	public static void setConnectionInProgress(boolean b) {
		connectionInProgress = b;
	}

	public static boolean isNoDataAvailable() {
		return isNoDataAvailable;
	}

	public static void setNoDataAvailable(boolean isNoDataAvailable) {
		WebServiceHelper.isNoDataAvailable = isNoDataAvailable;
	}

	public static boolean isConnectionInProgress() {
		return connectionInProgress;
	}
	
	public static int startHTTPConnector(String url) throws Exception {
		System.out.println("->starting thread");
		Thread thread = new Thread(new HttpConnector(url));
		Display.getInstance().callSeriallyAndWait(thread);
		return responseCode;
	}
	
	public static int startHTTPConnector(String url, String requestType, String requestHeader, String requestBody) throws Exception {
		System.out.println("->starting thread");
		Thread thread = new Thread(new HttpConnector(url, requestType, requestHeader, requestBody));
		Display.getInstance().callSeriallyAndWait(thread);
		return responseCode;
	}
	
	static class HttpConnector implements Runnable {
		
		String url;
		String requestType = "";
		String requestBody = ""; 
		String requestHeader = "";
		
		public HttpConnector(String url, String requestType, String requestHeader, String requestBody) {
		
			this.url = url;
			this.requestType = requestType;
			this.requestBody = requestBody;
			this.requestHeader = requestHeader;
			setConnectionInProgress(true);
			setNoDataAvailable(false);
		}
		
		public HttpConnector(String url) {
			this.url = url;
			this.requestType = HTTPConstatns.REQUEST_TYPE_GET;
			setConnectionInProgress(true);
			setNoDataAvailable(false);
		}
		
		public synchronized void run() {
			try {
				System.out.println("**HttpConnector THREAD");
				setNoDataAvailable(false);
				httpConn = (HttpConnection) Connector.open(url, Connector.READ_WRITE);
				setRequestHeaders(url, requestHeader);
				if(requestType == HTTPConstatns.REQUEST_TYPE_GET) {
					handlerForGETRequest(url, requestHeader);
				} else if(requestType == HTTPConstatns.REQUEST_TYPE_POST) {
					handlerForPOSTRequest(url, requestHeader, requestBody);
				}
				WebServiceHelper.responseCode = httpConn.getResponseCode();
				responseCodeHandler();
				
			} catch (Exception ex) {
				setNoDataAvailable(true);
				try {
					httpConn.close();		
				}
				catch(Exception e) {
					e.printStackTrace();
				}	
				System.out.println("**Exception raised in connection.");
				CustomDialogBox.showDialog(ApplicationConstants.DIALOG_TYPE_ERROR, ApplicationConstants.MSG_NO_INTERNET_CONNECTION, ApplicationConstants.DIALOG_BUTTON_OK);
			
			} finally {
				httpConn = null;
			}
		}
	}
	
	private static void setRequestHeaders(String url, String requestHeader) throws IOException {
		httpConn.setRequestProperty(HTTPConstatns.HEADER_CONTENT_TYPE, HTTPConstatns.HEADER_CONTENT_TYPE_VALUE);
		httpConn.setRequestProperty("Authorization", requestHeader);
		httpConn.setRequestProperty("User-Agent", "Profile/MIDP-2.1 Configuration/CLDC-1.1");
		httpConn.setRequestProperty("Connection", "close");
	}
	
	private static void responseCodeHandler() throws IOException, Exception {
		System.out.println("Response code: " + responseCode);
		if(isConnectionInProgress()) {
			if (httpConn.getResponseCode() == HttpConnection.HTTP_OK) {
				inputStream = httpConn.openInputStream();	
				closeHttpConn();
			} else if(httpConn.getResponseCode() ==  HTTPConstatns.STATUS_CODE_400
					|| httpConn.getResponseCode() ==  HTTPConstatns.STATUS_CODE_404
					|| httpConn.getResponseCode() ==  HTTPConstatns.STATUS_CODE_500
					|| httpConn.getResponseCode() ==  HTTPConstatns.STATUS_CODE_430 ) {
					
				inputStream = httpConn.openInputStream();	
				closeHttpConn();		
			} else if(httpConn.getResponseCode() == HTTPConstatns.STATUS_CODE_302) {
				String redirectUrl = httpConn.getHeaderField("Location");
				System.out.println(redirectUrl);
				closeHttpConn();	
				startHTTPConnector(redirectUrl);
			} else {
				System.out.println("ERROR");
				httpConn.close();
				throw new Exception();	
			}
		}
	}
	
	private static void handlerForGETRequest(String url, String requestHeader) throws IOException {
		httpConn.setRequestMethod(HttpConnection.GET);
		if(StringUtils.isContains(url, URLHelper.SEARCH_KEYWORD)) {
			httpConn.setRequestProperty("Accept-Encoding", "gzip");
		}
	}
	
	private static void handlerForPOSTRequest(String url, String requestHeader, String requestBody) throws IOException {
		httpConn.setRequestMethod(HttpConnection.POST);
		if( requestBody != null && !(requestBody.equals("")) ) {
			createRequestBody(requestBody);
		}
	}
	
	private static void createRequestBody(String requestString) throws IOException {
		DataOutputStream outStream = null;
		if(httpConn != null) {
			// obtaining output stream for sending query string
			outStream = httpConn.openDataOutputStream();
			byte[] request_body = requestString.getBytes();
			
			// sending query string to web server
			for (int i = 0; i < request_body.length; i++)
			{
				outStream.writeByte(request_body[i]);
			}
			outStream.flush();
			outStream.close();
			outStream = null;
		}
	}
	
	public static Image fetchImage(String url) {
		Image img = null;
		try {
			startHTTPConnector(url);
			
			if(inputStream != null) {
				img = Image.createImage(inputStream);
				inputStream.close();
			}  	
		}
		catch(Exception e) {
			System.out.println("**Exception raised in connection.");
			e.printStackTrace();
			img = null;
		}
		return img;
	}
	
	public static String getStringFromInputStream() {
		System.out.println("in getStringFromInputStream() class Webservicehelper");
		StringBuffer sb = new StringBuffer();
		try {
			if (inputStream != null) {
				
				int chr;
				while ((chr = inputStream.read()) != -1 && inputStream != null  ) {
					sb.append((char) chr);
				}
			}
		} catch (Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
			
		} finally {
			if(inputStream == null) {
				System.out.println("Empty no data loaded");
				sb =new StringBuffer("");
			} else {
				closeInputStream(); 
			}
		}
		return sb.toString();
		
	}
	
	 public static String getdeCompressSearchData()  {
        StringBuffer sb = new StringBuffer();
        try {
        	if (inputStream != null) {
		        GZIPInputStream gz = new GZIPInputStream(inputStream);
		        int c = 0;
		        while ((c = gz.read()) != -1 && connectionInProgress == true) {
		            sb.append((char) c);
		        }
	
		        gz.close();
        	}
        } catch (Exception e) {
        	System.out.println("Exception occured in decompression");
			//e.printStackTrace();
        } finally {
        	if(inputStream == null) {
				System.out.println("Empty no data loaded");
				sb =new StringBuffer("");
			} else {
				closeInputStream(); 
			}
        }
        return sb.toString();
	}

}
