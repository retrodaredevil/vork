package com.yoshiplex.vork;

import java.util.Random;

public class VorkMain {

	private static Random random = new Random();
	
	public static void main(String[] args){
		new VorkMain();
	}
	
	public VorkMain(){
		new SendManager();
		
	}
	
	public static Random getRandom(){
		return random;
	}
	
	
}
