package com.user;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class UserManager{
	private final static String userFilePath = "userFile.json";
	
	private void saveUserFile(ArrayList<User> users) {
		Gson gsonBuilder = new GsonBuilder().create();

		String jsonUserList = gsonBuilder.toJson(users);
		jsonUserList = "{ \"users\":" + jsonUserList + "}";
		try {
			Files.write( Paths.get(userFilePath), jsonUserList.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<User> getUserFile(){
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ArrayList<User> userList = new ArrayList<User>();
		try { 
			JSONObject usersFile = (JSONObject) new JSONParser().parse(new FileReader(userFilePath));
			JSONArray users = (JSONArray) usersFile.get("users");
			for(Object u: users) {
				User userObj = gson.fromJson(u.toString(), User.class);
				userList.add(userObj);
			}
	        
		} catch (FileNotFoundException e) {
			try {
				FileWriter file = new FileWriter(userFilePath);
				file.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		return userList;
	}
	
	public void CreateUser(User user) {
		if(FindUserByUserName(user.getUserName()).getUserName() == null) {
			ArrayList<User> users = this.getUserFile();
			users.add(user);
			this.saveUserFile(users);
		}
	}
	
	public User FindUserByUserName(String userName) {
		ArrayList<User> userList = this.getUserFile();
		User user = new User();
		for(Object u: userList) {
			if(((User)u).getUserName().equals(userName)) {
				user.setEmail(((User)u).getEmail());
				user.setName(((User)u).getName());
				user.setUserName(((User)u).getUserName());
			}
		}
		
		return user;
	}
	
	public void DeleteUser(User user) {
		if(FindUserByUserName(user.getUserName()).getUserName() != null) {
			ArrayList<User> users = this.getUserFile();
	        for(int i = 0; i < users.size(); i++){
	            if(users.get(i).getUserName().equals(user.getUserName())){
	                users.remove(i);
	                break;
	            }
	        }
			this.saveUserFile(users);
		}
	}
	
	public void Update(User user) {
		if(FindUserByUserName(user.getUserName()).getUserName() != null) {
			ArrayList<User> users = this.getUserFile();
	        for(int i = 0; i < users.size(); i++){
	            if(users.get(i).getUserName().equals(user.getUserName())){
	                users.remove(i);
	                users.add(user);
	                break;
	            }
	        }
			this.saveUserFile(users);
		}
	}
}