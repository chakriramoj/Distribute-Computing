import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

class Clock {
	int messageClock;

	Clock(int messageClock) {
		this.messageClock = messageClock;
	}
}

class WorkerThread implements Callable<WorkerThread> {
	Integer uid;
	Integer message;
	Integer send;
	Double waitedRounds;
	Map<Integer, Integer> map;
	String status;
	Clock clock;

	WorkerThread(Integer uid, Integer send, Double waitedRounds, Map<Integer, Integer> map, Integer message,
			String status, Clock clock) {
		this.uid = uid;
		this.send = send;
		this.waitedRounds = waitedRounds;
		this.map = map;
		this.message = message;
		this.status = status;
		this.clock = clock;
	}

	@Override
	public WorkerThread call() throws Exception {
		// TODO Auto-generated method stub
		setMessage();
		clock.messageClock++;
		while (clock.messageClock != 10) {
			Thread.sleep(1000);
		}
		changeState();
		return this;
	}

	public void setMessage() {
		if (waitedRounds == Math.pow(2, send)) {
			map.put(uid, send);
		} else {
			waitedRounds++;
			map.put(uid, null);
		}
	}

	public void changeState() {

		if (map.get(message) != null) {
			Integer minId = map.get(message);
			if (minId == uid) {
				status = String.valueOf(uid);
			} else if (minId < send) {
				send = minId;
				waitedRounds = 0.0;
			}
		}

	}

}

public class VariableSpeedsAlgorithm {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("input.dat"));

			Integer noOfThreads = Integer.parseInt(br.readLine());
			String[] ids = br.readLine().split("\\s+");
			Integer[] uids = new Integer[noOfThreads];
			for (Integer i = 0; i < noOfThreads; i++) {
				uids[i] = Integer.parseInt(ids[i]);
			}br.close();
			System.out.println("Set of UIDS:"+Arrays.toString(uids));
			String leader = "unknown";
			HashMap<Integer, Integer> map = new HashMap<Integer,Integer>();
			for (Integer r : uids) {
				map.put(r, r);
			}
			FutureTask<WorkerThread>[] future = new FutureTask[noOfThreads];
			Clock clock = new Clock(0);
			for (Integer i = 0; i < noOfThreads; i++) {
				WorkerThread thread = new WorkerThread(uids[i], uids[i], Math.pow(2, uids[i]), map,
						(i - 1 >= 0) ? uids[i - 1] : uids[noOfThreads - 1], "unknown", clock);
				future[i] = new FutureTask<WorkerThread>(thread);
			}

			while (leader.equals("unknown")) {

				for (Integer i = 0; i < noOfThreads; i++) {
					new Thread(future[i]).start();
				}
				for (Integer i = 0; i < noOfThreads; i++) {
					WorkerThread thread = future[i].get();
					if (!thread.status.equals("unknown")) {
						leader = thread.status;
						for (Integer j = 0; j < noOfThreads; j++) 
						{WorkerThread process = future[j].get();
							System.out.println("UID:"+process.uid+" Leader:"+process.send);
						}
					}
					
					
				}
				clock.messageClock = 0;
				for (Integer i = 0; i < noOfThreads; i++) {
					WorkerThread thread = future[i].get();
					future[i] = new FutureTask<WorkerThread>(thread);
				}

			}
			System.out.println("UID with " + leader + " is the leader");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
