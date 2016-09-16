package com.onkaar.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jws.soap.SOAPBinding.Use;

import jdk.internal.dynalink.beans.StaticClass;

import com.onkaar.bean.Expenditure;
import com.onkaar.bean.Group;
import com.onkaar.bean.NormalizedData;
import com.onkaar.bean.User;

public class UserService {
	
	String relPath = "D:/REST_WEB_SERVICES/Splitwise Application/";
	File usersFile = new File(relPath+"data/users.ser");
	File groupFile = new File(relPath+"data/groups.ser");
	File expenditureFile = new File(relPath+"data/expenditures.ser");
	
	private List<User> users = this.getAllUsers();
	private List<Group> groups = this.getAllGroups();
	private List<Expenditure> expenditures = this.getAllExpenditures();
	
	
	
	//Method to return all users
	public List<User> getAllUsers(){
		List<User> list = null;
		try {
		list = this.deserializeUserFile(usersFile);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	//Get all groups
	public List<Group> getAllGroups() {
		List<Group> list = null;
		try {
		list = this.deserializeGroupFile(groupFile);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//Get a group
	public Group getAGroup(String groupId){
		List<Group> list = this.getAllGroups();
		Iterator<Group> iterator = list.iterator();
		while(iterator.hasNext()){
			Group group = iterator.next();
			if(group.getGroupId().equals(groupId)){
				return group;
			}
		}
		
		return null;
	}
	
	//Get all expenditures
	public List<Expenditure> getAllExpenditures() {
		List<Expenditure> list = null;
		try {
		list = this.deserializeExpenditureFile(expenditureFile);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	//Create New User
	public void createUser(User user){
		
		try {
			List<User> list = this.deserializeUserFile(usersFile);
			Thread.sleep(100);
			list.add(user);
			this.SerializeUseList(list, usersFile);
			
			users = list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Create New Group
	public void createGroup(Group group){
		try {
			List<Group> list = this.deserializeGroupFile(groupFile);
			Thread.sleep(100);
			list.add(group);
			this.SerializeGroupList(list, groupFile);
			
			groups = list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Create New Expenditure
	public void createExpenditure(Expenditure expenditure){
		try {
			List<Expenditure> list = this.deserializeExpenditureFile(expenditureFile);
			Thread.sleep(100);
			list.add(expenditure);
			this.SerializeExpenditureList(list, expenditureFile);
			
			expenditures = list;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Get Users Inside A Group
	public List<User> getAllUsersInAGroup(String groupId){
	List<Group> list = this.groups;
	
	Iterator<Group> iterator = list.iterator();
	while(iterator.hasNext()){
		Group group = iterator.next();
		if(group.getGroupId().equals(groupId)){
			return group.getUsers();
		}
	}
	
	return null;
	}
	
	//Get All Expenditures in group
	public List<Expenditure> getAllExpenditureInAGroup(String groupId){
		List<Expenditure> list = this.expenditures;
		List<Expenditure> newList = new ArrayList<>();
		
		Iterator<Expenditure> iterator = list.iterator();
		while(iterator.hasNext())
		{
			Expenditure expenditure = iterator.next();
			if(expenditure.getGroup().getGroupId().equals(groupId)){
				newList.add(expenditure);
			}
		}
		return newList;
	}
	
	//BusinessLogic
	public List<NormalizedData> NormalizeAmount(Group group){
		
		List<NormalizedData> normailzedList = new ArrayList<>();
		List<Expenditure> expenditureList = this.getAllExpenditureInAGroup(group.getGroupId());
		List<User> userList = this.getAllUsersInAGroup(group.getGroupId());
		float totalSpendings = 0f;
		int totalMember = userList.size();
		
		Iterator<User> iterator = userList.iterator();
		while(iterator.hasNext()){
			User user = iterator.next();
			float cost = this.userSpending(user, expenditureList);
			normailzedList.add(new NormalizedData(user, group, cost));
			totalSpendings+=cost;
		}
		 
		float costPerHead = totalSpendings/totalMember;
		Iterator<NormalizedData> it = normailzedList.iterator();
		while(it.hasNext()){
			NormalizedData nData = it.next();
			nData.setBalance(nData.getBalance()-costPerHead);
		}
		
		return normailzedList;
		
	}
	
	private int userSpending(User user, List<Expenditure> data){
		int userSpending = 0;
		Iterator<Expenditure> iterator = data.iterator();
		while(iterator.hasNext()){
			Expenditure expenditure = iterator.next();
			if(user.getUserId().equals(expenditure.getUser().getUserId())){
				userSpending += expenditure.getSpendingAmount();
			}
		}
		return userSpending;
	}
	
	//De serialize UserFile
	private List<User> deserializeUserFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException{
		List<User> list = new ArrayList<>();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		list = (List<User>) ois.readObject();
		ois.close();
		return list;
	}
	
	//Serialize UserFile
	private void SerializeUseList(List<User> list, File file) throws FileNotFoundException, IOException{
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject(list);
		outputStream.close();
	}
	
	//Serialize Group File
	private void SerializeGroupList(List<Group> list, File file) throws FileNotFoundException, IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject(list);
		outputStream.close();
		
	}

	//De Serialize Group
	private List<Group> deserializeGroupFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		List<Group> list = new ArrayList<>();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		list = (List<Group>) ois.readObject();
		ois.close();
		return list;
	}
			
	//Serialize Expenses
	private void SerializeExpenditureList(List<Expenditure> list, File file) throws FileNotFoundException, IOException {
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
		outputStream.writeObject(list);
		outputStream.close();
	}

	//Desrialize Expenses
	private List<Expenditure> deserializeExpenditureFile(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
		List<Expenditure> list = new ArrayList<>();
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
		list = (List<Expenditure>) ois.readObject();
		ois.close();
		return list;
	}
	
	
}
