import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureCheck implements Callable {
	Integer a,b,c;
      FutureCheck(Integer... a){
    	  this.a=a[0];
    	  b=a[1];
    	  c=a[2];
      }
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureCheck f=new FutureCheck(1,2,3);
		FutureTask<Integer> future=new FutureTask<Integer>(f);
		new Thread(future).start();
		System.out.println(future.get()+" "+"Chakri");
		new Thread(future).start();
		System.out.println(future.get()+" "+"Ramoj");
	}

	@Override
	public Object call() throws Exception {
		// TODO Auto-generated method stub
		c+=5;
		System.out.println(c);
		return a+b+c;
	}
}
