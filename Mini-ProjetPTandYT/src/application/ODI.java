package application;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ODI extends Remote {

	public String getTodolist() throws RemoteException;
	
}
