package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer {
	private static final int SERVER_PORT = 3000;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			// 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			// Time-Wait 상태에서 포트 재할당을 가능하게 하기 위하여
			serverSocket.setReuseAddress( true );
			
			// 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			String localHostAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress, SERVER_PORT);
			serverSocket.bind(inetSocketAddress);
			System.out.println("[echo server] binding " + localHostAddress + ":" + SERVER_PORT);

			// accept: 연결 요청 대기
			Socket socket = serverSocket.accept();

			// 연결
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			System.out.println("[echo server] 연결 from " + remoteHostAddress + ":" + remoteHostPort);

			try {
				// IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {
					// 데이터 읽기
					byte[] buffer = new byte[256];
					int readBytes = is.read(buffer); // blocked
					if (readBytes <= -1) {
						// 클라이언트로 부터 정상 종료
						System.out.println("[echo server] closed by client");
						return;
					}

					// 출력
					String data = new String(buffer, 0, readBytes, "utf-8");
					System.out.println("[echo server] received :" + data);

					// 데이터 쓰기(echo)
					os.write(data.getBytes("utf-8"));
				}
			} catch (SocketException e) {
				System.out.println("[echo server] 비정상적으로 클라이언트가 연결을 끊었습니다." + e );
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 데이터 통신 소켓 닫기
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 서버 소켓 닫기
				if (serverSocket != null && serverSocket.isClosed() == false) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
