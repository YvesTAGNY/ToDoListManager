import java.util.ArrayList;

public class User {

	private String UserName;
	private ArrayList<String> ToDOList = new ArrayList<String>();
	
	User (String Name , ArrayList<String> t){
		UserName=Name;
		ToDOList=t;
		
	}
}
