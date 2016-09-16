package com.psl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LotterySystem 
{
	
	
	
	int[] generateNumbers(){
		
			int arr[]=new int[6];
			int n;
			Random random = new Random();
			
			for(int i=0;i<6;i++)
			{
				n = random.nextInt(49);
				while(checkContains(n, arr))
				{
					n = random.nextInt(49);
				}
				arr[i] = n;				
			}
				
		
			
			return arr;
	}
	
	
	int[] inputNumbers(){
			
			int arr[]=new int[6];
			BufferedReader bufferRead ;
			int n;
			
			for(int i=0;i<6;i++)
			{
				 bufferRead = new BufferedReader(new InputStreamReader(System.in));
				    try {
						String s = bufferRead.readLine();
						n =  Integer.parseInt(s);
						
						if(n<49 && n>0 && (!checkContains(n, arr)))
						{
							arr[i] = n;
						}
						else
						{
							
							while(n>49 || n<=0 || checkContains(n,arr))
							{
								System.out.println("Either the number is not in the valid range or you hav already chosen the number..!!");	
								s = bufferRead.readLine();
								n =  Integer.parseInt(s);
							}
							arr[i] = n;
						}
						
					} 
				    catch (IOException e) {
						e.printStackTrace();
					}
			}
		
			return arr;
}
	int[] compareArrays(int[] arr1,int[] arr2){
		
		int arr[]=new int[6];
		int k=0;
		
		for(int i=0;i<6;i++)
			for(int j=0;j<6;j++)
			{
				if(arr1[i] == arr2[j])
					{
					arr[k] = arr1[i];
					k++;
					}
			}
		
		return arr;
	}
	
	boolean checkContains(int n,int[] arr){
	
		for(int i:arr)
		{
			if(i==n)
				return true;
		}
		return false;
	}
	
	
}
