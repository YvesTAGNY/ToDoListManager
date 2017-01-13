package application;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

public static void main(String[] args) {

    Socket clientSocket = null;
    try {
     clientSocket = new Socket("localhost", 7171);
     
    } catch (UnknownHostException e) {
      System.err.println("Don't know about host");
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to host");
    }

   
    if (clientSocket != null) {
      try {

        System.out.println("The client started.");
        
        PrintStream output = new PrintStream(clientSocket.getOutputStream());
        output.print("des bonbons");
        
        clientSocket.close();
      } catch (UnknownHostException e) {
        System.err.println("Trying to connect to unknown host: " + e);
      } catch (IOException e) {
        System.err.println("IOException:  " + e);
      }
    }
  }
}