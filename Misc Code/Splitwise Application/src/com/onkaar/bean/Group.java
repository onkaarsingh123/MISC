package com.onkaar.bean;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Group implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8824774752377684247L;
	private String groupId;
	private String groupName;
	private List<User> users;
	
	
	public Group(String groupName) {
		super();
		this.groupName = groupName;
	}

	public Group(String groupId, String groupName, List<User> users) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.users = users;
	}

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getGroupId() {
		return groupId;
	}
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user){
		this.users.add(user);
	}

	public String getIdOfAllUsers(){
		
		String userList = "";
		Iterator<User> iterator = this.users.iterator();
		while(iterator.hasNext()){
			userList = userList + iterator.next().getUserId() + ","; 
		}
		
		return userList;
	}
	
	@Override
	public String toString() {
		return "Group [groupId=" + groupId + ", groupName=" + groupName
				+ ", users=" + users + "]";
	}
	
	

}
