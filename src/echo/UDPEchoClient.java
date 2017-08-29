package echo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.Scanner;

public class UDPEchoClient {
	private static final String SERVER_IP = "192.168.1.11";
	private static final int SERVER_PORT = 6000;
	private static final int BUFFER_SIZE = 1024;


	public static void main(String[] args) {
		DatagramSocket socket = null;
		try {
			// Connect Keyboard
			Scanner sc = new Scanner(System.in);

			socket = new DatagramSocket();
			while (true) {
				//사용자 입력
				System.out.print(">>");
				String message = sc.nextLine();
				
				//아무것도 없는데 전송할 필요는 없음
				if("".equals(message))
					continue;
				if("quit".equals(message))
					break;
				
				byte[] sendData = message.getBytes("UTF-8");
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(SERVER_IP, SERVER_PORT));
				//보내고 나서 밑에서 close를 하기때문에 제대로 동작하지 않을 수가 있다.
				socket.send(sendPacket);
				
				//Message Receive 
				DatagramPacket receivePacket= new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
				socket.receive(receivePacket);
				
				message = new String(receivePacket.getData(),0,receivePacket.getLength());
				
				System.out.println("<<" + message);
				
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
		}

	}

}
