package com.psl;

public class Main 
{
	public static void main(String[] args) {
		
		LotterySystem lotterySystem= new LotterySystem();
		
		int arr1[]=lotterySystem.generateNumbers();
		for(int i:arr1)
			System.out.println(i);
		
		int arr2[] = lotterySystem.inputNumbers();
		for(int i:arr2)
			System.out.println(i);
		
		int arr[] = lotterySystem.compareArrays(arr1, arr2);
	for(int i:arr)
			System.out.println(i);
		
		
		
		
	}
}
