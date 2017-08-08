package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		while (true) {
			Scanner sc = new Scanner(System.in);
			String host = sc.next();

			// InetAddress[] InetAddress.getAllByName(host);

			try {

				InetAddress[] inetAddresses = InetAddress.getAllByName(host);
				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}

			} catch (UnknownHostException e) {
				System.out.println("Unkown Host : " + e);
			}
			sc.close();
		}

	}

}
