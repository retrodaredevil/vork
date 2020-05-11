package com.yoshiplex.vork.enemies;

import java.util.Random;

import com.yoshiplex.vork.SendManager;
import com.yoshiplex.vork.User;
import com.yoshiplex.vork.util.Entity;
import com.yoshiplex.vork.util.VorkUtil;

public class Monster implements Entity{

	
	protected int maxHealth = 20;
	protected int health = 0;
	
	public Monster(int maxHealth){
		this.maxHealth = maxHealth;
		this.health = maxHealth;
	}
	
	
	public void tryDealth(User user){
		if(health <= 0){
			user.sendMessage("You killed the monster.");
			return;
		}
	}


	public boolean isDead() {
		return this.health  == 0;
	}
	public void heal(int hearts){
		this.health += hearts;
		if(this.health > maxHealth){
			this.health = maxHealth;
		}
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
	/**
	 * 
	 * @param user
	 * @param a
	 * @param full
	 * @return if you should set m to null
	 */
	public boolean receiveAttack(User user, String a, String full){
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
			this.harm(damage);
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
		user.sendMessage("You can attack or run away. Which would you like to do?");
		user.sendMessage("//>The monster has " + this.getHealth() + " hearts left. You have " + user.getHealth() + " hearts left.");
		if(this.isDead()){
			user.sendMessage("You killed the monster!");
			user.addKey("space mountain");
		} else if(user.isDead()){
			user.sendMessage("You died. Enter any command to continue.");
			SendManager.getScanner().nextLine();
			user.sendMessage("Restarting...");
			user.onRestart();
		}
		if(user.isDead()){
			user.setHealth(5);
		}
		return this.isDead();
	}
}
