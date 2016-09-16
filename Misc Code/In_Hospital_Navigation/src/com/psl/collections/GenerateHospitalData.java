package com.psl.collections;

import java.util.ArrayList;
import java.util.List;

import com.psl.bean.Doctor;
import com.psl.bean.Room;

public class GenerateHospitalData {

	public List<Doctor> createDoctors()
	{
		
		List<Doctor> doctors = new ArrayList<Doctor>();
		
		doctors.add(new Doctor(1,"Dr. R. S. Subramanium", "MBBS", "Physician"));
		doctors.add(new Doctor(2,"Dr. R. S. Swamy", "M-Bed", "Orthopaedian"));
		doctors.add(new Doctor(3,"Dr. P. K. Lahoty", "MSc", "Physician"));
		doctors.add(new Doctor(4,"Dr. R. S. Jain", "MBBS", "Dermatologist"));
		doctors.add(new Doctor(5,"Dr. S. R. Khan", "MBBS", "Physician"));
		doctors.add(new Doctor(6,"Dr. Salman. Subramanium", "MBBS", "Optician"));
		doctors.add(new Doctor(7,"Dr. Pandit Nehru", "B-Tech", "Surgeon"));
		doctors.add(new Doctor(8,"Dr. M. K. Gandhi", "B-Des", "Physician"));
				
		return doctors;
	}
	
	public List<Room> createRooms()
	{
		List<Room> rooms = new ArrayList<Room>();
		
		rooms.add(new Room(1, "Room 1", "OT"));
		rooms.add(new Room(2, "Room 2", "CheckUp"));
		rooms.add(new Room(3, "Room 3", "Lab"));
		rooms.add(new Room(4, "Room 4", "CheckUp"));
		rooms.add(new Room(5, "Room 5", "CheckUp"));
		
		return rooms;
	}
	

}
