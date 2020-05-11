package com.yoshiplex.vork.locations;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.User;
import com.yoshiplex.vork.util.VorkUtil;

public class EntranceForwardCircle extends Location{

	private static EntranceForwardCircle instance;
	
	private EntranceForwardCircle() {
		super("Main Street Round-a-bout", "You are now at a round-a-bout with a statue in the middle.");
	}

	@Override
	public void onCome(User user) {
		user.sendMessage("You can either go to the right or run in a circle");
		
	}

	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		if(full.contains("circle")){
			user.sendMessage("You are now running in a circle...");
			VorkUtil.delay(5000);
			user.sendMessage("You are still running in a circle.");
			VorkUtil.delay(5000);
			user.sendMessage("You hear a stone sliding across concrete.");
			user.sendMessage(VorkUtil.getLongString (".", 500));
			user.sendMessage("//>" + VorkUtil.getLongString("\t\n", 1000));
			user.setLocation(UndergroundCircle.getInstance());
			return true;
		} else if(full.contains("right")){
			user.sendMessage("//>Person in Scooby-Do costume: BAAAAAAAAAAAAAAAAAHHHHH.");
			user.sendMessage("Person in Scooby-Do costume: Hello there.");
			user.sendMessage("Person in Scooby-Do costume: You should not be here.");
			user.sendMessage("Person in Scooby-Do costume: I'm putting you where do should be.");
			user.sendMessage("Person in Scooby-Do costume: There's no stopping me.");
			user.sendMessage(VorkUtil.getLongString (".", 500));
			user.sendMessage("//>" + VorkUtil.getLongString("\t\n", 1000));
		} else if(full.contains("stay")){
			user.sendMessage("You cannot stay.");
			return true;
		}
		user.sendMessage("You cannot do that. You must choose. Which way do you want to go?");
		return true;
	}
	
	public static EntranceForwardCircle getInstance(){
		if(instance == null){
			instance = new EntranceForwardCircle();
		}
		return instance;
	}
	
	
}
