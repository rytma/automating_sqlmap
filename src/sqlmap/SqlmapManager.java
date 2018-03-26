package sqlmap;

import java.util.*;
import org.json.*;

/**
 * SqlmapManager class used to manage the sqlmap api
 * 
 * @see <a href="https://github.com/735tesla/Unofficial-SQLMap-API-Doc/wiki">SQLMap Api</a>
 */
public class SqlmapManager {
	private SqlmapSession _session = null;
	
	/**
	 * SqlmapManager constructor used to create and manage the Sqlmap-api
	 * 
	 * @param session
	 *        an SqlmapSession object containing the fundamental information
	 * @see <a href="https://github.com/735tesla/Unofficial-SQLMap-API-Doc/wiki">SQLMap Api</a>
	 */
	public SqlmapManager(SqlmapSession session) {
		if (session == null)
			throw new NullPointerException("session");
		_session = session;
	}
	
	/**
	 * Create a new sqlmap task
	 * 
	 * @return the String of the task id session
	 */
	public String newTask() {
		JSONObject json = new JSONObject(_session.executeGet("/task/new"));
		return json.getString("taskid");
	}
	
	/**
	 * Delete a created task
	 * 
	 * @param taskid
	 *        a string representing the sqlmap session id
	 * @return a boolean from result json key success
	 */
	public boolean deleteTask(String taskid) {
		JSONObject json = new JSONObject(_session.executeGet("/task/" + taskid + "/delete"));
		return json.getBoolean("success");
	}
	
	/**
	 * Return the options list of sqlmap api
	 * 
	 * @param taskid
	 *        a string representing the task id session
	 * @return a map of string to object of the options list
	 */
	public Map<String, Object> getOptions(String taskid) {
		JSONObject json = new JSONObject(_session.executeGet("/option/" + taskid + "/list"));
		Map<String, Object> options = new HashMap<>();
		json = json.getJSONObject("options");
		for (Object key: json.keySet()) {
			String k = (String)key;
			options.put(k, json.get(k));
		}
		return options;
	}
	
	/**
	 * Start a task by setting the related options
	 * 
	 * @param taskid
	 *        a string representing the task id session
	 * @param options
	 *        a map of string to object containing the options
	 * @return a boolean from the result json key success
	 */
	public boolean startTask(String taskid, Map<String, Object> options) {
		String js = new JSONObject(options).toString();
		JSONObject json = new JSONObject(_session.executePost("/scan/" + taskid + "/start", js));
		return json.getBoolean("success");
	}
	
	/**
	 * Return the current status scan by taskid
	 *
	 * @param taskid
	 *        a string representing the task id session
	 * @return an SqlmapStatus object of the status
	 */
	public SqlmapStatus getScanStatus(String taskid) {
		JSONObject json = new JSONObject(_session.executeGet("/scan/" + taskid + "/status"));
		SqlmapStatus stat = new SqlmapStatus();
		stat.setStatus(json.getString("status"));
		if (!json.isNull("returncode"))
			stat.setReturnCode(json.getInt("returncode"));
		return stat;
	}
	
	/**
	 * Return the log items list of the result scan
	 * 
	 * @param taskid
	 *        a string representing the task id session
	 * @return a list of SqlmalLogItem
	 */
	public List<SqlmapLogItem> getLog(String taskid) {
		JSONObject json = new JSONObject(_session.executeGet("/scan/" + taskid + "/log"));
		JSONArray jarray = json.getJSONArray("log");
		List<SqlmapLogItem> logItems = new ArrayList<>();
		jarray.forEach(x -> {
			SqlmapLogItem i = new SqlmapLogItem();
			JSONObject item = (JSONObject)x;
			i.setMessage(item.getString("message"));
			i.setLevel(json.getString("level"));
			i.setTime(item.getString("time"));
			logItems.add(i);
		});
		return logItems;
	}
}
