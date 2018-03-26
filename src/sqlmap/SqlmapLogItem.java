package sqlmap;

public class SqlmapLogItem {
	private String _message;
	private String _level;
	private String _time;
	
	
	/**
	 * Return the saved message result for api scan
	 * 
	 * @return a string containing the message
	 */
	public String getMessage() {
		return _message;
	}
	
	/**
	 * Set the message into SqlmapLogItem object
	 * 
	 * @param message
	 *        the result api message
	 */
	public void setMessage(String message) {
		_message = message;
	}
	
	/**
	 * Return the level of tests to perform
	 * 
	 * @return a string containing the level
	 */
	public String getLevel() {
		return _level;
	}
	
	/**
	 * Set the level of complexity of tests to perform
	 * 
	 * @param level
	 *        the level of tests
	 */
	public void setLevel(String level) {
		_level = level;
	}
	
	/**
	 * Return the scan time
	 * 
	 * @return a string containing the time
	 */
	public String getTime() {
		return _time;
	}
	
	/**
	 * Set the scan time
	 * 
	 * @param a string containing the time of the scan
	 */
	public void setTime(String time) {
		_time = time;
	}
}
