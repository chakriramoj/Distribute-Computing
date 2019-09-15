import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;



class WorkerThread implements Callable{
    Integer uid;
    Integer message;
    Integer send;
    Double waitedRounds;
    Map<Integer,Integer> map;
    String status;
    
    WorkerThread(Integer uid,Integer send,Double waitedRounds,Map<Integer,Integer> map,Integer message,String status){
    	this.uid=uid;
    	this.send=send;
    	this.waitedRounds=waitedRounds;
    	this.map=map;
    	this.message=message;
    	this.status=status;
    }

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		System.out.println(uid);
		setMessage();
		Thread.sleep(3000);
		changeState();
		return this;
	}
	
	public void setMessage() {
		System.out.println(status+" "+uid +" "+message);
		if(waitedRounds==Math.pow(2, send)) {map.put(uid,send);}
		else {waitedRounds++;map.put(uid,null);}
	}
	
	public void changeState() {
		
		if(map.get(message)!=null) {
			Integer minId=map.get(message);
			if(minId==uid) {status=String.valueOf(uid);}
			else if(minId<send) {send=minId;waitedRounds=0.0;}
			System.out.println(status+" "+uid +" "+ message+" "+status);
		}
		
	}
	
	
	
	
}

public class VariableSpeedsAlgorithm {
	
	public static void main(String[] args) {
		try {
			BufferedReader br=new BufferedReader(new FileReader("input.dat"));
		
			Integer noOfThreads=Integer.parseInt(br.readLine());
			String[] ids=br.readLine().split("\\s+");
			Thread[] a=new Thread[noOfThreads];
			Integer[] uids=new Integer[noOfThreads];
			for(Integer i=0;i<noOfThreads;i++) {
				uids[i]=Integer.parseInt(ids[i]);
			}
			System.out.println(Arrays.toString(uids));
			String leader="unknown";
			HashMap<Integer,Integer> map=new HashMap();
			for(Integer r:uids) {map.put(r, r);}
			FutureTask<WorkerThread>[] future=new FutureTask[noOfThreads];
			for(Integer i=0;i<noOfThreads;i++) {
				WorkerThread thread=new WorkerThread(uids[i],uids[i],Math.pow(2,uids[i]),map,(i-1>=0)?uids[i-1]:uids[noOfThreads-1],"unknown");
				future[i]=new FutureTask<WorkerThread>(thread);
			}
			while(leader.equals("unknown")) {
				
				for(Integer i=0;i<noOfThreads;i++) {
					new Thread(future[i]).start();
				}
				for(Integer i=0;i<noOfThreads;i++) {
					WorkerThread thread=future[i].get();
					if(!thread.status.equals("unknown")) {leader=thread.status;}
				}
				for(Integer i=0;i<noOfThreads;i++) {
					WorkerThread thread=future[i].get();
					future[i]=new FutureTask<WorkerThread>(thread);
				}
				
			}
			System.out.println("UID with "+leader+" is the leader");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
