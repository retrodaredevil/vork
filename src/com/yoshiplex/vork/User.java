package com.yoshiplex.vork;

import java.util.ArrayList;
import java.util.List;

import com.yoshiplex.vork.locations.Entrance;
import com.yoshiplex.vork.util.Entity;
import com.yoshiplex.vork.util.VorkUtil;

public class User implements Entity{
	private Location loc;
	private String name;
	
	private List<String> obtainedKeys = new ArrayList<>();
	private int health = 20;
	
	
	public User(String name){
		this.name = name;
		this.loc = Entrance.getInstance();
		
	}
	public void addKey(String key){
		this.obtainedKeys.add(key);
	}
	public boolean hasKey(String key){
		return this.obtainedKeys.contains(key);
	}
	
	public Location getLocation(){
		return loc;
	}
	public String getName(){
		return this.name;
	}
	public void onRestart(){
		this.loc = Entrance.getInstance();
		this.sendMessage(loc.getDescription());
		this.obtainedKeys.clear();
		this.health = 5;
		this.sendMessage("//>You regained 5 hearts.");
		
	}
	public void sendMessage(String message){
		if(message.startsWith("//>")){
			System.out.println("\u001B[36m" +message.replaceFirst("//>", "") + "\u001B[37m");
			
			return;
		}
		this.sendSlowMessage(message);
	}
	private void sendSlowMessage(String message){
		System.out.print("\u001B[36m");
		for(int i = 0; i < message.length();i++){
			System.out.print(message.charAt(i));
			VorkUtil.delay(20);
		}
		System.out.print("\n\u001B[37m");
	}
	public void setLocation(Location loc){
//		if(!this.loc.canLeave(loc)){
//			this.sendMessage(this.loc.getDisallowedLeaveMessage());
//			return;
//		}
//		if(!loc.canCome(this.loc)){
//			this.sendMessage(loc.getDisallowedComeMessage());
//			return;
//		}
		this.loc = loc;
		this.sendMessage(loc.getDescription());
		loc.onCome(this);
		
	}
	public void removeKey(String string) {
		this.obtainedKeys.remove(string);
	}
	public boolean isDead() {
		return this.health  == 0;
	}
	public void heal(int hearts){
		this.health += hearts;
	}
	public void harm(int hearts){
		this.health -= hearts;
		if(this.health < 0){
			this.health = 0;
		}
	}
	@Override
	public int getHealth() {
		return health;
	}
	public void setHealth(int i) {
		this.health = i;
		
	}
}
