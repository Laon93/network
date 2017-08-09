package thread;

public class DigitThread implements Runnable {

	@Override
	public void run() {
		for(int i=0; i<10; i++)
		{
		
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.print(i);
		}
	}

}
