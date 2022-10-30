package ro.ase.ism.sap.day2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
	
	public static class Box {
		Object content;

		public Object getContent() {
			return content;
		}

		public void setContent(Object content) {
			this.content = content;
		} 
	}
	
	//a generic class
	public static class GenericBox<myType>{
		myType content;
		float weight;
		//otherType attribute;
		
		public myType getContent() {
			return this.content;
		}
		
		public void setContent(myType content) {
			this.content = content;
		}
		
	}
	
	public static class User{
		String username;
		int id;
		
		
		public User(String username, int id) {
			super();
			this.username = username;
			this.id = id;
		}


		@Override
		public String toString() {
			return this.username + " - " + this.id;
		}


		@Override
		public int hashCode() {
			//return this.id;
			return this.username.hashCode();
		}


		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof User)) {
				return false;
			}
			
			User userObj = (User)obj;
			return this.username.equals(userObj.username);
		}
	
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Box stringBox = new Box();
		stringBox.setContent("Hello!");
		String msg = (String) stringBox.getContent();
		System.out.println("Message: " + msg);
		
//		Double doubleObject = 23.6;
//		stringBox.setContent(doubleObject);
//		
//		msg = (String) stringBox.getContent();
//		System.out.println("Message: " + msg);
		
		//using the generic box
		GenericBox<String> stringGenericBox = new GenericBox<>();
		GenericBox<Integer> intGenericBox = new GenericBox<>();
		
		stringGenericBox.setContent("How are you ?");
		msg = stringGenericBox.getContent();
		
		System.out.println("Message : " + msg);
		
		//stringGenericBox.setContent(23.6);
		
		//test collections
		List<String> names = new ArrayList<>();
		names.add("John");
		names.add("Alice");
		names.add("Bob");
		names.add("John");
		
		System.out.println("The user names are: ");
		System.out.println(names);
		for(String name : names) {
			System.out.println(name);
		}
		
		Set<String> uniqueNames = new HashSet<>();
		uniqueNames.add("John");
		uniqueNames.add("Alice");
		uniqueNames.add("Bob");
		uniqueNames.add("John");
		
		System.out.println("Unique names");
		System.out.println(uniqueNames);
		
		Map<Integer, String> users = new HashMap<>();
		users.put(1, "John");
		users.put(5, "Alice");
		users.put(20, "Bob");
		users.put(1, "Vader");
		
		System.out.println(users);
		
		String userName = users.get(23);
		if(userName == null) {
			System.out.println("No user with id 23");
		}
		
		userName = users.get(1);
		System.out.println("User: " + userName);
		
		for(Integer key : users.keySet()) {
			System.out.println("User for key " + key);
			System.out.println(users.get(key));
		}
		
		//testing collections with our classes
		Set<User> myUsers = new HashSet<>();
		User user1 = new User("John", 1);
		User user2 = new User("John", 1);
		myUsers.add(user1);
		myUsers.add(user2);
		
		System.out.println(myUsers);
		
	}

}
