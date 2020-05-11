package com.yoshiplex.vork.util;

import java.util.Random;

public class VorkUtil {

	private static Random random = new Random();
	
	public static void delay(long millis){
		try{
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param percent a number from 0 to 100
	 * @return thingy
	 */
	public static boolean getRandomBooleanFromPercent(int percent){
		int a = random.nextInt(100) + 1;
		if(percent == 100 || a == 100){
			return true;
		}
		if(percent == 0 || a == 0){
			return false;
		}
		
		return a <= percent;
	}
	public static String getLongString(String c, int repeat){
		String s = c;
		for(int i = 0; i < repeat;i++){
			s += c;
		}
		return s;
	}

	public static int getRandom(int a, int b){
		return a + random.nextInt(b - a + 1);
	}
	
}
