package com.yoshiplex.vork.locations;

import java.util.Random;
import java.util.Scanner;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.SendManager;
import com.yoshiplex.vork.Sender;
import com.yoshiplex.vork.User;
import com.yoshiplex.vork.enemies.Monster;
import com.yoshiplex.vork.util.VorkUtil;

public class EntranceBathroom extends Location {

	private static EntranceBathroom instance;
	
	private Spot s = Spot.OUTSIDE;
	private Monster m = null;
	private boolean hadMonster = false;
	
	public EntranceBathroom() {
		super("Bathrooms", "You are right next to the entrance to the bathrooms. Go left to go into the mens, go right to go into the womens");

	}

	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		
		if(label.equals("go") || label.equals("move")){
			if(args.length == 0){
				user.sendMessage("Where do you want to go?");
				return true;
			}
			String a = args[0];
			if(a.equals("left")){
				if(s == Spot.MEN){
					user.sendMessage("You are already in the bathroom.");
					return true;
				}
				s = Spot.MEN;
				user.sendMessage("You went into the mens bathroom.");
				return true;
			} else if(a.equals("right")){
				if(s == Spot.WOMEN){
					user.sendMessage("You are already in the bathroom.");
					return true;
				}
				s = Spot.WOMEN;
				user.sendMessage("You went into the womens bathroom.");
				return true;
			} else if(a.equals("outside") || a.equals("out")){
				if(s == Spot.OUTSIDE){
					user.sendMessage("You are already outside the bathroom.");
					return true;
				}
				s = Spot.OUTSIDE;
				user.sendMessage("You went outside.");
				return true;
			} else if(a.equals("entrance")){
				user.setLocation(Entrance.getInstance());
				return true;
			}
			user.sendMessage("You cannot go there.");
			return true;
		} else if(label.equals("fight")){
			if(!this.hasMonster()){
				user.sendMessage("There's nothing to fight!");
				return true;
			}
			if(s == Spot.OUTSIDE){
				user.sendMessage("There's a monster inside! You are outside.");
				return true;
			}
			
			Sender sender = new Sender() {
				/**
				 * returning false will stop the fight (when they type leave)
				 */
				@Override
				public boolean onSend(String full, String label, String[] split, String[] args, User user) {
					if(label.equals("leave") || label.equals("l") || label.equals("run")){
						return false;
					} else if(label.equals("attack")){
						if(args.length == 0){
							user.sendMessage("What would you like to attack with?");
							user.sendMessage("//>The monster has " + m.getHealth() + " hearts left.");
							return true;
						}
						String a = args[0];
						int fail = 100;
						int damage = 1;
						String failMessage = "You missed your attack.";
						String successMessage = "Your attack hit!";
						
						if(full.startsWith("attack magical rainbow unicorn sword of awesomeness")){
							damage = 5;
						} else if(a.equals("fist")){
							damage = 2;
							fail = 85;
							failMessage = "Your fist missed the monster's face.";
							successMessage = "Your fist went right into the monster's face!";
						} else if(a.equals("kicking") || a.equals("kick") || a.equals("leg") || a.equals("feet")){
							fail = 65;
							damage = 4;
							failMessage = "You need to get better at kicking monsters. Your attack missed.";
							successMessage = "Dang! Right in the monster parts";
						} else if(a.equals("head") ||a.equals("face")){
							fail = 30;
							damage = 10;
							failMessage = "Looks like you missed your attack.";
							successMessage = "Your head didn't crack, but the monster's did!";
						}
						boolean success = VorkUtil.getRandomBooleanFromPercent(fail);
						if(success){
							m.harm(damage);
							user.sendMessage("//>" + successMessage);
							user.sendMessage("The monster is fighting back!");
							int harm = 0;
							switch(new Random().nextInt(5)){
							case 0: 
								harm = 0;
								break;
							case 1 : 
								harm = 3;
								break;
							case 2 :
								harm = 1;
								break;
							case 3: 
								harm = 2;
								break;
							case 4:
								harm = 5;
								break;
								
							}
							user.harm(harm);
							user.sendMessage("You lost " + harm + " hearts.");
						} else{
							user.harm(damage);
							user.sendMessage("//>" + failMessage);
							user.sendMessage("You lost " + damage + " hearts.");
						}
						return true;
					}
					user.sendMessage("You must leave or deafeat the monster to do that.");
					return true;
				}
			};
			Scanner input = SendManager.getScanner();
			while(!user.isDead() && !m.isDead()){
				user.sendMessage("You can attack or run away. Which would you like to do?");
				user.sendMessage("//>The monster has " + m.getHealth() + " hearts left. You have " + user.getHealth() + " hearts left.");
				if(!sender.onSend(input.nextLine(), user)){
					user.sendMessage("You ran away from the fight! You are now outside");
					this.s = Spot.OUTSIDE;
					
					break;
				} 
				
			}

			if(m.isDead()){
				user.sendMessage("You killed the monster! You get your key back!");
				user.addKey("space mountain");
				m = null;
			} else if(user.isDead()){
				user.sendMessage("You died. Enter any command to continue.");
				SendManager.getScanner().nextLine();
				user.sendMessage("Restarting...");
				user.onRestart();
			}
			return true;
		}
		
		return false;
	}
	public boolean hasMonster(){
		return m != null && !m.isDead();
	}
	public boolean hadMonster(){
		return this.hadMonster;
	}
	public void spawnMonster(){
		this.m = new Monster(20);
		this.hadMonster = true;
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
//
//	@Override
//	public boolean shouldCome(String name) {
//	return name.startsWith("bathroom") || name.startsWith("restroom");
//	}
	public static EntranceBathroom getInstance(){
		if(instance == null){
			instance = new EntranceBathroom();
		}
		return instance;
	}
	
	private static enum Spot{
		MEN,WOMEN,OUTSIDE;
	}

	@Override
	public void onCome(User user) {
		if(this.hasMonster()){
			user.sendMessage("There's a monster in here! You can fight it, but it doesn't look like it will hurt you right now.");
		}
	}


}
