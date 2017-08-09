package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class TCPServer {
	private static final int SERVER_PORT = 5000;
	
	public static void main(String[] args) {
		
		
		// 1. 서버 소켓 생성
		ServerSocket serverSocket = null;
		InetAddress inetAddress = null;

		try {
				serverSocket = new ServerSocket();
				
				// 2. 바인딩
				inetAddress = InetAddress.getLocalHost();
				String localhostAddress = inetAddress.getHostAddress();
	
				serverSocket.bind(new InetSocketAddress(localhostAddress, SERVER_PORT));
				
				System.out.println("[server] binding " + localhostAddress);
				
				// 3. 연결 요청 기다림(accept)
				// 데이터 통신은 serverSocket.accept()가 반환해주는 socket으로 해야한다.
				Socket socket = serverSocket.accept();
				
				// 4. 연결 성공
				InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
				
				int remoteHostPort = remoteSocketAddress.getPort();
				
				String remoteHostAddress = remoteSocketAddress.getAddress().getHostAddress();
				System.out.println("[server] connected from " + remoteHostAddress + " : " + remoteHostPort);
				
				try {
				// 5. I/O 스트림 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();
				
				while(true)
				{
					// 6. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer); //block
					
					// 정상종료 했을 경우 나오는 것
					if(readByteCount <= -1)
					{
						System.out.println("[server] disconnection by client");
						break;
					}
					
					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[server] received : " + data);
					
					// 7. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));
				}
			}catch(SocketException e ) {
				//socket이 닫기지 않고 연결이 종료된 경우 --> 즉 강제종료 된 경우
				//Xshell은 자동적으로 socket.close()를 해주기 때문에 이곳으로 잘 안들어오게 된다.
				//하지만 충분히 발생할 수 있는 문제이기 때문에 catch문을 사용해주는 것이 좋다.
				System.out.println("Socket is not closed : " + e);
				}catch(IOException e){
					
				System.out.println("I/O Exception : " + e);
			}finally {
				try {
				if(socket!=null && socket.isClosed() == false)
					serverSocket.close();
				}catch(IOException e) {
					System.out.println("Socket Close Exception : " +e);
				}
			}
			
			
			
		} catch (IOException e) {
			System.out.println("I/O Error : " + e);
		} finally {
			try {
				// 이미 닫혀버린 경우도 있을 수 있다. 그렇기 때문에 비어있거나 이미 닫긴 상태일 때에는 close()를 해주면 안된다.
				if (serverSocket != null && serverSocket.isClosed()==false)
					serverSocket.close();
			} catch (IOException e) {
				System.out.println("Server Socket Close Error : " + e);
			}
		}

	}

}
