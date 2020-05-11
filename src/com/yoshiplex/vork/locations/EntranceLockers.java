package com.yoshiplex.vork.locations;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.User;

public class EntranceLockers extends Location{

	private static EntranceLockers instance;
	
	
	private EntranceLockers(){
		super("Lockers", "You are in the room with lockers.");
	}
	private int timesLooked = 0;
	private boolean movedPlant = false;
	private boolean openedLocker = false;
	
	private boolean tookNote = false;
	
	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
	
		if(label.equals("look") || label.equals("l") || label.equals("plant")){
			if(args.length > 0 && !args[0].equals("left")&& !args[0].equals("right")){
				if(timesLooked > 1){
					if(args[0].equalsIgnoreCase("plant")){
						user.sendMessage("There is it. You see a plant that has turned yellow. It look like a locker is unlocked behind it.");
						
						return true;
					} else if(args[0].equals("locker") || args[0].equals("in")){
						if(this.openedLocker){
							user.sendMessage("You see a note inside.");
							return true;
						}
						user.sendMessage("You see lots of lockers.");
						return true;
					}
					
				}
				user.sendMessage("You don't see that.");
				timesLooked++;
				return true;
			}
			if(this.openedLocker){
				user.sendMessage("You see an open locker. You should probably look inside.");
				return true;
			}
			if(timesLooked == 0){
				user.sendMessage("All of the lockers are locked. Maybe there's a key. You might be able to find it if you keep looking.");
			} else if(timesLooked == 1){
				user.sendMessage("You see a plant placed in front of a locker.");
			} else {
				if(this.movedPlant){
					user.sendMessage("You see a plant next to a locker that is unlocked.");
				} else {
					user.sendMessage("That plant is still there");
				}
			}
			timesLooked++;
			return true;
		} else if(label.equals("go") || label.equals("move")){
			if(args.length == 0){
				user.sendMessage("Where do you want to go?");
				return true;
			} 
			String go = args[0];
			if(go.equals("plant")){
				this.movedPlant = true;
				user.sendMessage("You moved the plant out of the way.");
				return true;
			}
			if(go.startsWith("locker")){
				user.sendMessage("You're already there!");
			} else if(go.equals("bathroom") || go.equals("restroom")){
				user.sendMessage("You must leave here first.");
			} else if(go.equals("entrance")){
				user.setLocation(Entrance.getInstance());
			}else{
				user.sendMessage("You cannot go there right now.");
			}
			return true;
		} else if(label.equals("open")){
			if(args.length == 0){
				user.sendMessage("What do you want to open?");
				return true;
			}
			String o = args[0];
			if(o.equals("locker")){
				if(movedPlant){
					this.openedLocker = true;
					user.sendMessage("You opened the locker. You should probably look inside.");
				} else {
					user.sendMessage("You don't see any open lockers.");
				}
				return true;
			} 
			user.sendMessage("You can't open that.");
			return true;
		} else if(label.equals("take") || label.equals("pick")){
			if(args.length == 0){
				user.sendMessage("What do you want to pick up?");
				return true;
			}
			String a = args[0];
			if(a.equals("plant")){
				user.sendMessage("That's a little to heavy. Maybe try moving it.");
				return true;
			} else if(a.equals("note") || a.equals("letter")){
				if(!this.openedLocker){
					user.sendMessage("What note? I don't know what you're talking about...");
					return true;
				}
				this.tookNote = true;
				user.sendMessage("You picked up the note. Maybe you should read it.");
				return true;
			}
		} else if(label.equals("read")){
			if(args.length == 0){
				user.sendMessage("What do you want to read?");
				return true;
			}
			String a = args[0];
			if(a.equals("note")){
				if(!this.tookNote){
					user.sendMessage("What note? There's no note.");
					return true;
				}
				user.sendMessage("The note says:\n'Meet me inside Space Mountain'");
				if(!user.hasKey("space mountain")){
					user.sendMessage("\nYou got a key to the Space Mountain entrance.");
					user.addKey("space mountain");
				}
				return true;
			}
		} else if(label.equals("key")){
			user.sendMessage("Maybe there is no key... Do we need one?");
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
	public static EntranceLockers getInstance(){
		if(instance == null){
			instance = new EntranceLockers();
		}
		return instance;
	}

	@Override
	public void onCome(User user) {
		// TODO Auto-generated method stub
		
	}
}
