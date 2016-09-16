package com.onkaar.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.onkaar.bean.Expenditure;
import com.onkaar.bean.Group;
import com.onkaar.bean.User;
import com.onkaar.service.UserService;

public class Main {

	public static void main(String[] args) {

		UserService service = new UserService();
		//service.createUser(new User("user4", "Sajid","Khan"));
  
		
		List<User> users = service.getAllUsers();
		//System.out.println(users);
		Group group = new Group("group1","Mumbai Trip", users);
		//service.createGroup(group);

		//Expenditure expenditure = new Expenditure("exp2",group, users.get(0), "Dinner @ Haldirams", 4500);
		
		System.out.println(service.NormalizeAmount(group));
		//service.createExpenditure(expenditure);
		//System.out.println(service.getAllExpenditures());
	}

}
