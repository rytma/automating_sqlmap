import java.util.List;
import java.util.Map;

import sqlmap.*;

public class Main {
	public static void main(String[] args) {
		// Create the manager
		SqlmapManager mgr = new SqlmapManager(new SqlmapSession("127.0.0.1", 8775));
		
		// new task
		String taskid = mgr.newTask();
		System.out.println("Created task: " + taskid);
		
		System.out.println("Listing task");
		// get options
		Map<String, Object> options = mgr.getOptions(taskid);
		System.out.println("Listed tasks");
		options.put("url", args[0]);        // set url
		options.put("flushSession", true);  // flush the session
		
		// start task
		mgr.startTask(taskid, options);
		SqlmapStatus status = mgr.getScanStatus(taskid);
		
		// while running
		while (status.getStatus() != "terminated") {
			try {
				Thread.sleep(10000);
				status = mgr.getScanStatus(taskid);
				System.out.println(status.getStatus());
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		// get the result list
		List<SqlmapLogItem> logs = mgr.getLog(taskid);
		logs.forEach(e -> {
			System.out.println(e.getMessage());
		});
		
		// delete task
		System.out.println("Deleting task");
		boolean success = mgr.deleteTask(taskid);
		System.out.println("Deleted successful: " + success);
	}
}
