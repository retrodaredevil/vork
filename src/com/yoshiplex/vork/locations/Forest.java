package com.yoshiplex.vork.locations;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.SendManager;
import com.yoshiplex.vork.User;
import com.yoshiplex.vork.enemies.Monster;
import com.yoshiplex.vork.util.VorkUtil;

public class Forest extends Location{

	private static Forest instance;

	private Monster m = null;
	private boolean fighting = false;
	private boolean fought = false;
	private boolean warned = false;
	
	private Forest() {
		super("Forest", "Welcome to the forest. This place is supposed fenced off.");
	}

	@Override
	public void onCome(User user) {
		user.sendMessage("There is a monster infront of you. Would you like to go around it or fight it?");
	}

	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		if(fighting){
			if(label.equals("fight")){
				this.fighting = true;
				user.sendMessage("You are now fighting the monster.");
			} else if(label.equals("attack")){
				if(!this.fighting){
					user.sendMessage("You must choose if you want to fight before attacking.");
					return true;
				}
				if(args.length == 0){
					user.sendMessage("What would you like to attack with?");
					user.sendMessage("//>The monster has " + m.getHealth() + " hearts left.");
					return true;
				}
				String a = args[0];
				if(m.receiveAttack(user, a, full)){
					m = null;
					this.fought = true;
				}
				return true;
			} 
			
			
		}else if(label.equals("go") || label.equals("move") || ((label.equals("yes") || label.equals("y")) && this.warned)){
			if(this.fighting){
				user.sendMessage("You must leave the fight before you can go anywhere!");
				return true;
			}
			if(!this.fought && !this.warned){
				user.sendMessage("It looks like the monster doesn't want you to pass. He might try to kill you.");
				this.warned = true;
				return true;
			}
			if(!this.fought){
				user.sendMessage("The monster is attacking! ");
				int harm = VorkUtil.getRandom(2, 5);
				user.harm(harm);
				user.sendMessage("The monster did " + harm + " damage to you.");
				if(user.isDead()){
					user.sendMessage("You died. Enter any command to continue.");
					SendManager.getScanner().nextLine();
					user.sendMessage("Restarting...");
					user.onRestart();
					return true;
				}
			}
			
			user.sendMessage("You are now on the path to Space Mountain.");
			VorkUtil.delay(1000);
			user.sendMessage("You can almost see the dome.");
			VorkUtil.delay(2000);
			user.setLocation(SpaceMountain.getInstance());
			return true;
		} else if(label.equals("look")){
			if(this.fought){
				user.sendMessage("You see a forest with many trees.");
			} else {
				user.sendMessage("You see a forest and a monster in front of you.");
			}
			return true;
		}
		return false;
	}	
	public void spawnMonster(){
		this.m = new Monster(10);
	}

	public static Forest getInstance() {
		if(instance == null){
			instance = new Forest();
		}
		
		return instance;
	}

}
