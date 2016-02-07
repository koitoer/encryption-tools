package com.koitoer.encryption.tools.security.ssl;

import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by mauricio.mena on 28/05/2015.
 */
public class HTTPSServerExample {

	public static void main(String[] args) throws IOException {

		//
		// create an SSL socket using the factory and pick port 8080
		SSLServerSocketFactory sslsf =
				(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
		ServerSocket ss = sslsf.createServerSocket(8080);
		//
		// loop forever
		while (true) {
			try {
				//
				// block waiting for client connection
				Socket s = ss.accept();
				System.out.println( "Client connection made" );
				// get client request
				BufferedReader in = new BufferedReader(
						new InputStreamReader(s.getInputStream()));
				System.out.println(in.readLine());
				//
				// make an HTML response
				PrintWriter out = new PrintWriter( s.getOutputStream() );
				out.println("<HTML><HEAD><TITLE>HTTPS Server Example</TITLE>" +
						"</HEAD><BODY><H1>Hello World!</H1></BODY></HTML>\n");
				//
				// Close the stream and socket
				out.close();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
