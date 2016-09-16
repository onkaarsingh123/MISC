package com.psl.bean;

public class Doctor {
	
	int doctorId;
	String fullName;
	String designation;
	String speciality;
	
	public Doctor(int doctorId, String fullName, String designation, String speciality){
		this.doctorId = doctorId;
		this.fullName = fullName;
		this.designation = designation;
		this.speciality = speciality;
	}

	@Override
	public String toString() {
		return "Doctor [doctorId=" + doctorId + ", fullName=" + fullName + ", designation="
				+ designation + ", speciality=" + speciality + "]";
	}

}
