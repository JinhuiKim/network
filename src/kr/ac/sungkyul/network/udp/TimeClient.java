package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class TimeClient {
	private static final String SERVER_IP = "220.67.115.217";
	private static final int SERVER_PORT = 9999;
	private static final int BUFFER_SIZE = 256;

	public static void main(String[] args) {
		DatagramSocket socket = null;

		try {
			socket = new DatagramSocket();

			byte[] sendData = "".getBytes(StandardCharsets.UTF_8);
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
					new InetSocketAddress(SERVER_IP, SERVER_PORT));

			socket.send(sendPacket);

			DatagramPacket receivePacket = new DatagramPacket(new byte[BUFFER_SIZE], BUFFER_SIZE);
			socket.receive(receivePacket);
			String date = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
			System.out.println(date);

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
