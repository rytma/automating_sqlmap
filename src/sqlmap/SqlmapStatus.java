package sqlmap;

public class SqlmapStatus {
	private String _status;
	private int _returnCode;
	
	/**
	 * Return the status of the current scan
	 * 
	 * @return a string containing the status scan
	 */
	public String getStatus() {
		return _status;
	}
	
	/**
	 * Set the current status of the scan
	 * 
	 * @param status
	 *        a string containing the status of the scan
	 */
	public void setStatus(String status) {
		_status = status;
	}
	
	/**
	 * Return the status code of the result
	 * 
	 * @return an integer representing the status code number
	 */
	public int getReturnCode() {
		return _returnCode;
	}
	
	/**
	 * Set the return status code of the result
	 * 
	 * @param returnCode
	 *        the integer of the return scan result
	 */
	public void setReturnCode(int returnCode) {
		_returnCode = returnCode;
	}
}
