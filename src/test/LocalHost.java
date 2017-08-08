package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getLocalHost();
			String hostName = inetAddress.getHostName();
			System.out.println(inetAddress.getHostName());
			String hostAddress = inetAddress.getHostAddress();
			System.out.println(inetAddress.getHostAddress());
			
			byte[] addresses = inetAddress.getAddress();
			for(int i =0; i<addresses.length; i++)
			{
				//왜 -가 찍혀?!?! --> 2의 보수를 하는 과정에서 부호비트에 1이 들어갔기 때문에
				//양수인 애들이 음수로 바뀌게 된것!! 
				//그래서 제대로 출력하기 위해서는 비트연산을 해야한다. 
				System.out.print(addresses[i]&0x000000ff);
				if(i<3)
					System.out.print(".");
			}
			
		} catch (UnknownHostException e) {
			System.out.println("Unkown Host Address : " + e);
		}
		

	}

}
