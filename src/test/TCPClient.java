package test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class TCPClient {
	private static final String SERVER_IP = "192.168.1.11";
	private static final int SERVER_PORT = 5000;

	public static void main(String[] args) {

		Socket socket = null;
		try {
			// 1. Create Socket
			socket = new Socket();

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
