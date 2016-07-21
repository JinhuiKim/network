package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoClient {
	private static final String SERVER_IP = "";
	private static final int SERVER_PORT = 2000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		
		try {
			//1. 소켓 생성
			socket = new DatagramSocket();
			
			//2. 데이터 송신
			String message = "Hello World";
			byte[] sendData = message.getBytes( StandardCharsets.UTF_8 );
			
			DatagramPacket sendPacket = 
				new DatagramPacket(
				sendData,
				sendData.length,
				new InetSocketAddress( SERVER_IP, SERVER_PORT ));	
					
			socket.send( sendPacket );
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( socket != null && socket.isClosed() == false ) {
				socket.close();
			}
		}
		
	}

}
