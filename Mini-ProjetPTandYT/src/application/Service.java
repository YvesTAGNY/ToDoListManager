package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

public class Service implements Runnable {

	private Socket serverService;
	
	Service(Socket s){serverService = s;}

	@Override
	public void run() {
		
		try {
			InputStream in = serverService.getInputStream();
			OutputStream out = serverService.getOutputStream();
			
			BufferedReader bin = new BufferedReader(new InputStreamReader(in));
			String msg = bin.readLine();
			System.out.println("recu : " + msg);
			
			 PrintStream pout = new PrintStream(out);
		     pout.println("Combien ?");
			
			serverService.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
