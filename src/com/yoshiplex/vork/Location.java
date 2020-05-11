package com.yoshiplex.vork;

public abstract class Location extends Sender{
	
	private String name;
	private String description;
	
	protected Location(String name, String description){
		this.name = name;
		this.description = description;
	}
	public String getName(){
		return name;
	}
	
//	public abstract boolean canLeave(Location to);
//	
//	public abstract boolean canCome(Location from);
//	public abstract boolean shouldCome(String name);
	public abstract void onCome(User user);
	
	public String getDisallowedLeaveMessage(){
		return "You cannot leave";
	}
	public String getDisallowedComeMessage(){
		return "You cannot come here";
	}
	public String getDescription() {
		return this.description;
	}
	

	
}
