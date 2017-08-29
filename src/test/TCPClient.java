package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TCPClient {
	private static final String SERVER_IP = "192.168.1.11";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {

		Socket socket = null;
		try {
			// 1. Create Socket
			socket = new Socket();

			// 1-1 Check Socket Buffer Size
			int receiveBufferSize = socket.getReceiveBufferSize();
			int sendBufferSize = socket.getSendBufferSize();

			System.out.println("Before");
			System.out.println("receiveBufferSize : " + receiveBufferSize);
			System.out.println("sendBufferSize : " + sendBufferSize);

			// 1-2 Change Socket Buffer Size
			socket.setReceiveBufferSize(10 * 1024);
			socket.setSendBufferSize(10 * 1024);

			System.out.println("After");
			System.out.println("receiveBufferSize : " + socket.getReceiveBufferSize());
			System.out.println("sendBufferSize : " + socket.getSendBufferSize());

			// 1-3 SO_NODELAY (Nagle Algorithm Off)
			socket.setTcpNoDelay(true);

			// 1-4 SO_TIMEOUT
			//socket.setSoTimeout( 3000 );


			// 2. Connect Server
			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			// 3. receive I/O
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			// 4. read / write
			String data = "hello";
			os.write(data.getBytes("UTF-8"));

			byte[] buffer = new byte[256];
			int readByteCount = is.read(buffer);
			if (readByteCount <= -1) {
				System.out.println("[client] Disconnection by Server");
				return;
			}
			// = new String(buffer, 0, readByteCount, "UTF-8");
			data = new String(buffer, 0, readByteCount, "UTF-8");
			System.out.println("[client] Received : " + data);

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		}catch (IOException e) {
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
