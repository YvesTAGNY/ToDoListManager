import java.util.ArrayList;

public class User {

	private String UserName;
	private ArrayList<Task> ToDOList = new ArrayList<Task>();
	
	User (String Name , ArrayList<Task> t){
		UserName=Name;
		ToDOList=t;
		
	}
	
	String getUserName(){
		return UserName;
	}
	
	
	 ArrayList<Task> getToDOList(){
		return ToDOList;
	}
	 
	 void setUserName(String Name){
		 UserName=Name; 
	 }
	 
 void setToDOList(ArrayList<Task> tdl){
	 ToDOList=tdl;
		 
	 }
}
