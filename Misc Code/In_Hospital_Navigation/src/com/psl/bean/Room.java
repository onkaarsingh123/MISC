package com.psl.bean;

public class Room {
	
	int roomId;
	String roomName;
	String roomType;
	
	public Room(int roomId, String roomName, String roomType) {
		super();
		this.roomId = roomId;
		this.roomName = roomName;
		this.roomType = roomType;
	}
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomName=" + roomName
				+ ", roomType=" + roomType + "]";
	}
	
	
	
}
