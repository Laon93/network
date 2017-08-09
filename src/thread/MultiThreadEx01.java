package thread;

public class MultiThreadEx01 {

	public static void main(String[] args) {
		
		Thread thread1 = new AlphabetThread();
		//Thread thread2 = new DigitThread(); --> 불가능
		//why?Thread를 상속받지 않았기 때문
		Thread thread2 = new Thread(new DigitThread());
		
		//익명 클래스로 이런 식으로 해도 된다.
		new Thread(new Runnable() {
			@Override
			public void run() {
				for(char c= 'A'; c<= 'Z'; c++)
				{
					
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.print(c);
				}
			}
		}).start();
		
		thread1.start();
		thread2.start();
	
		//아래 숫자출력  for문과 알파벳 출력 for문 두개를 동시에 실행되도록 Thread를 사용하기
		/*for(int i=0; i<10; i++)
		{
			System.out.print(i);
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
		
		/*for(char c = 'a'; c<= 'z'; c++)
			System.out.print(c);*/

	}

}
