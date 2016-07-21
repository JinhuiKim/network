package kr.ac.sungkyul.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class EchoServer {
	private static final int PORT = 2000;
	private static final int BUFFER_SIZE = 1024;
	
	public static void main(String[] args) {
		DatagramSocket socket = null;
		
		try {
			// 1. 소켓생성
			socket = new DatagramSocket( PORT );
			
			// 2. 수신 대기
			System.out.println( "수신대기" );
			DatagramPacket receivePacket = 
				new DatagramPacket( new byte[ BUFFER_SIZE ], BUFFER_SIZE );
			socket.receive( receivePacket ); //blocking
			
			// 3. 데이터 수신
			String message = new String(
				receivePacket.getData(),
				0,
				receivePacket.getLength(),
				StandardCharsets.UTF_8 );
					
			System.out.println( "수신:" + message );
			
		} catch (SocketException e) {
			e.printStackTrace();
		} catch ( IOException e ) {
			e.printStackTrace();
		} finally {
			if( socket != null && socket.isClosed() == false ) {
				socket.close();
			}
		}

	}

}
