package echo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

//Thread처리도 안했는데 어떻게 동시에 여러명이 접속이 가능해??
//한곳이랑 연결을 계속 유지하는 것이 아니라 데이터를 송수신만 하는 것이기때문에
//Thread처리를 하지 않아도 된다.
//But!! 속도가 조금 느려질수도 있다. A가 보낸 정보를 처리하는 동안 B가 정보를 또 보내면
//Delay가 생기기 마련!!!

public class UDPEchoServer {

	private static final int PORT = 6000;
	// UDP의 단점
	// bufferSize를 미리 클라이언트와 약속해야한다.
	private static final int BUFFER_SIZE = 1024;

	public static void main(String[] args) {

		DatagramSocket socket = null;
		try {
			while(true) {
			// 1. Create Socket
			socket = new DatagramSocket(PORT);

			// 2. Get Data
			// 니가 앞에꺼만큼 생성해놔도 난 뒤에 숫자만큼 받아오겠다.
			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);

			// 3. 데이터 수신 대기
			socket.receive(receivePacket); // block

			// 4. 수신
			String message = new String(receivePacket.getData(), 0, receivePacket.getLength(), "UTF-8");
			System.out.println("[UDP Echo Server] Recevie : " + message);

			// 5. 데이터 송신
			byte[] sendData = message.getBytes("UTF-8");
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, 
					receivePacket.getAddress(),receivePacket.getPort());
			
			socket.send(sendPacket);
			}
		} catch (SocketException e) {

			System.out.println("SocketException : " + e);
		} catch (IOException e) {
			System.out.println("IOException : " + e);
		}

	}

}
