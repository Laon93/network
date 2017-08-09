package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "192.168.1.11";
	private static final int SERVER_PORT = 6000;
	
	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = new Scanner(System.in);
		
		try {
			// 1. Create Socket
			socket = new Socket();

			// 2. Connect Server
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 3. receive I/O
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			//utf-8로 연결되어있지 않은 경우를 위해 new OutputStreamWriter을 사용한다.
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			
			
			// 4. read / write
			while(true) {
				//block 
				System.out.print(">>");
				String message = scanner.nextLine();
				if("exit".equals(message))
					break;
				
				pw.println(message);
				
				String echoMessage = br.readLine();
				if(echoMessage == null)
				{
					System.out.println("[Client] Disconnection by Server");
					break;
				}
				//출력
				System.out.println("<<" + message);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
