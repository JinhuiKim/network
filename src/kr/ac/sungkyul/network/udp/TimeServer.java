package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
	private static final int PORT = 9999;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket( PORT );

			while (true) {
				DatagramPacket receivePacket = new DatagramPacket(new byte[256], 256);

				socket.receive(receivePacket);

				String date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
				byte[] sendData = date.getBytes(StandardCharsets.UTF_8);

				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
						new InetSocketAddress(receivePacket.getAddress(), receivePacket.getPort()));
				socket.send(sendPacket);
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				socket.close();
			}
		}
	}
}