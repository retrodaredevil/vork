package com.yoshiplex.vork.locations;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.SendManager;
import com.yoshiplex.vork.Sender;
import com.yoshiplex.vork.User;

public class Entrance extends Location{

	private static Entrance instance;
	
	private boolean displayedMessage = false;
	
	private Entrance(){
		super("Entrance", "You are at the entrance! Remember to check out Space Mountain.");
	}
	
	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		label = label.toLowerCase();
		if(label.equals("look") || label.equals("l")){
			user.sendMessage("You see a magical land that's abandon. There are lockers to your left and bathrooms to your right.");
			return true;
		} else if(label.equals("go") || label.equals("move")){
			if(args.length == 0){
				user.sendMessage("Where do you want to go?");
				return true;
			} 
			String go = args[0];
			if(go.toLowerCase().startsWith("locker") || go.equals("left")){
				user.setLocation(EntranceLockers.getInstance());
			} else if(go.equalsIgnoreCase("bathroom") || go.equalsIgnoreCase("restroom") || go.equals("right")){
				user.setLocation(EntranceBathroom.getInstance());
			} else{
				user.sendMessage("You cannot go there right now.");
			}
			return true;
		}

		
		return false;
	}

//	@Override
//	public boolean canLeave(Location to) {
//		return true;
//	}
//
//	@Override
//	public boolean canCome(Location from) {
//		return true;
//	}
//	@Override
//	public boolean shouldCome(String name) {
//		name = name.toLowerCase();
//		return name.equals("entrance") || name.equals("en") || name.equals("start");
//	}
	public static Entrance getInstance(){
		if(instance == null){
			instance = new Entrance();
		}
		return instance;
	}

	@Override
	public void onCome(User user) {
		if(user.hasKey("space mountain") && !EntranceBathroom.getInstance().hadMonster() && !this.displayedMessage){
			user.sendMessage("The monster just stole your key! He went into the bathroom.");
			user.removeKey("space mountain");
			this.displayedMessage = true;
			EntranceBathroom.getInstance().spawnMonster();
		} else if(EntranceBathroom.getInstance().hadMonster() && !EntranceBathroom.getInstance().hasMonster()){
			user.sendMessage("Which way would you like to go? Forward or to the right?");
			Sender sender = new Sender() {
				
				@Override
				public boolean onSend(String full, String label, String[] split, String[] args, User user) {
					if(full.contains("forward")){
						user.setLocation(EntranceForwardCircle.getInstance());
						return false;
					} else if(full.contains("right")){
						user.setLocation(Forest.getInstance());
						return false;
					} else if(full.contains("stay")){
						user.sendMessage("You cannot stay.");
						return true;
					}
					user.sendMessage("You cannot do that. You must choose. Which way do you want to go?");
					return true;
				}
			};
			while(true){
				if(!sender.onSend(SendManager.getScanner().nextLine(), user)){
					break;
				}
				
			}
			
		}
	}
	
}
