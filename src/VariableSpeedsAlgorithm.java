import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;


class Neighbour{
	int token;
	int neighbourId;
	int waitedRounds;
	Neighbour(int token,int neighbourId,int waitedRounds){
		this.token=token;
		this.neighbourId=neighbourId;
		this.waitedRounds=waitedRounds;
	}
}
class WorkerThread implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
	
}

public class VariableSpeedsAlgorithm {

     Map<Runnable,Neighbour> map;
 
	public static void main(String[] args) {
		try {
			BufferedReader br=new BufferedReader(new FileReader("input.dat"));
			int noOfThreads=br.read();
			WorkerThread[] a=new WorkerThread[noOfThreads];
			int[] uids=new int[noOfThreads];
			for(int i=0;i<noOfThreads;i++) {
				uids[i]=br.read();
			}
			for(int i=0;i<noOfThreads;i++) {
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	
}
