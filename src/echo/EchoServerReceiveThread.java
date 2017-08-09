package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServerReceiveThread extends Thread {

	private Socket socket;
	
	public EchoServerReceiveThread(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		
		InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

		int remoteHostPort = remoteSocketAddress.getPort();

		String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();
		consoleLog(" from " + remoteHostAddress + " : " + remoteHostPort);

		try {
			// 5. I/O 스트림 받아오기
			//utf-8로 연결되어있지 않은 경우를 위해 new OutputStreamWriter을 사용한다.
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));

			while (true) {
				// 6. 데이터 읽기
				String message = br.readLine();

				// 정상종료 했을 경우 나오는 것
				if (message == null) {
					consoleLog(" Disconnection by Client");
					break;
				}

				consoleLog(" Received : " + message);

				// 7. 데이터 쓰기
				pw.println(message);
			}
		} catch (SocketException e) {
			// socket이 닫기지 않고 연결이 종료된 경우 --> 즉 강제종료 된 경우
			// Xshell은 자동적으로 socket.close()를 해주기 때문에 이곳으로 잘 안들어오게 된다.
			// 하지만 충분히 발생할 수 있는 문제이기 때문에 catch문을 사용해주는 것이 좋다.
			System.out.println("Socket is not closed : " + e);
		} catch (IOException e) {
			System.out.println("I/O Exception : " + e);
		} finally {
			try {
				if (socket != null && socket.isClosed() == false)
					socket.close();
			} catch (IOException e) {
				System.out.println("Socket Close Exception : " + e);
			}
		}
		
	}
	private void consoleLog(String log) {
		System.out.println("[server : " + getId() + "]" + log);
	}

}
