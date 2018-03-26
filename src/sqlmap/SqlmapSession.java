package sqlmap;

import java.io.*;
import java.net.*;


/**
 * SqlmapSession class
 * 
 * Contains the session information:
 *     _host [no default]
 *     _port [8775]
 * Processes the api request:
 *     get()
 *     post()
 */
public class SqlmapSession {
	private String _host = null;
	private int _port = 8775; // default
	
	/**
	 * Create the SqlmapSession object
	 * 
	 * @param  host
	 *         string of the hostname
	 * @param  port
	 *         int of port number
	 */
	public SqlmapSession(String host, int port) {
		_host = host;
		_port = port;
	}
	public SqlmapSession(String host) {
		_host = host;
		_port = 8775;
	}
	
	/**
	 * Execute an HTTP Get request and return a string result
	 * 
	 * @param  page
	 *         string of the task page
	 * @return a string containing the request result
	 */
	public String executeGet(String page) {
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL("http://" + _host + ":" + _port + page);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();
			return result.toString();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Execute an HTTP Post request and return a string result
	 * 
	 * @param  page
	 *         string of the task page
	 * @param  data
	 *         string of post parameters data String
	 * @return a string containing the request result
	 */
	public String executePost(String page, String data) {
		try {
			StringBuilder result = new StringBuilder();
			URL url = new URL("http://" + _host + ":" + _port + page);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			conn.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				result.append(line);
			}
			br.close();
			return result.toString();
		} catch(Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
