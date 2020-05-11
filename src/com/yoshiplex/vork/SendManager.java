package com.yoshiplex.vork;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SendManager extends SenderRunner implements Runnable{

	
	
	private static final Scanner input = new Scanner(System.in);
	
	private User user = null;
	
	public SendManager(){
		
		System.out.println("Please enter your name.");
		user = new User(input.nextLine());
		System.out.println("Hello, " + user.getName() + "! Welcome to DisneyLand. Feel free to look around.");
		this.start();
	}
	@Override
	public void run() {
		String full = input.nextLine().toLowerCase();
		this.onSend(full, user);
	}
	public static String getFull(String full){
		full = full.replaceAll("inside", "in");
		List<String> remove = Arrays.asList("to", "a", "the", "at", "in", "into", "by", "with", "my");
		full = full.replaceAll(" ", "  ");
		for(String s : remove){
			full = full.replaceAll(" " + s + " ", "");
		}
		full = full.replaceAll("pick up", "pick");
		
		while(full.contains("  ")){
			full = full.replace("  ", " ");
		}
		return full;
	}
	public void start(){
		System.out.println(this.user.getLocation().getDescription());
		while(true){
			this.run();
		}
	}
	public static Scanner getScanner(){
		return input;
	}
	
	/**
	 * @return return if running the command was successful
	 */
	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		running = true;
		boolean broke = false;
		for(Sender s : list){
			if(s.onSend(full, label, split, args, user)){
				broke = true;
				break;
			}
			
		}
		if(user.getLocation().onSend(full, user)){
			broke = true;
		}
		if(!broke){
			user.sendMessage("Sorry, you can't do that.");
		}
		this.resetList();
		
		running = false;
		return false;
	}

	
	
}
