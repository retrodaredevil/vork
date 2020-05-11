package com.yoshiplex.vork.locations;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.SendManager;
import com.yoshiplex.vork.User;
import com.yoshiplex.vork.enemies.Monster;
import com.yoshiplex.vork.util.VorkUtil;

public class SpaceMountain extends Location{

	private static SpaceMountain instance;
	
	private boolean inside = false;
	private boolean light = false;
	private boolean fighting = false;
	private Monster m = null;
	
	protected SpaceMountain() {
		super("Space Mountain", "Welcome to Space Mountain!");
	}

	@Override
	public void onCome(User user) {
		user.sendMessage("You are at the entrance. Use your key to get in.");
	}
	
	public void delay(long l){
		VorkUtil.delay(l);
	}
	
	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		if(fighting){
			if(m == null){
				m = new Monster(30);
			}
			if(args.length == 0){
				user.sendMessage("What do you want to attack with?");
				return true;
			}
			if(m.receiveAttack(user, args[0], full)){
				user.sendMessage("You defeated the monster!!! You won!!!");
				String blank = "//> ";
				for(int i = 0; i < 10; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("Creator: Josh Shannon aka retrodaredevil");
				for(int i = 0; i < 20; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("Created with the awesome and best programming language of all time: The Java Programming language.");
				for(int i = 0; i < 20; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("C++ is bad.");
				for(int i = 0; i < 20; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("Go learn java.");
				for(int i = 0; i < 20; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("It's cool.");
				for(int i = 0; i < 20; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("This is the end.");
				for(int i = 0; i < 20; i++){
					delay(200);
					user.sendMessage(blank);
				}
				user.sendMessage("You can stop looking at this now.");
			}
			return true;
			
		}
		if(label.equals("use") || label.equals("unlock")){
			if(!this.inside){
				if(!user.hasKey("space mountain")){
					user.sendMessage("You don't have the key! I thought you go the key earlier...");
					return true;
				}
				this.inside = true;
				user.sendMessage("You are using the key to unlock the door.");
				VorkUtil.delay(500);
				user.sendMessage("You are now inside.");
				user.sendMessage("It is very dark.");
				user.sendMessage("Maybe you can find a light switch.");
				
			}
		} 
		if(!inside){
			return false;
		}
		if(label.equals("look")){
			if(!light){
				user.sendMessage("You cannot see");
				return true;
			}
		} else if(label.equals("feel")){
			if(!light){
				user.sendMessage("You feel a light switch");
				user.sendMessage("You turned it on.");
				this.light = true;
				return true;
			}
		} else if(label.equals("listen")){
			user.sendMessage("You hear the ride running.");
			return true;
		} else if(label.equals("go") || label.equals("move")){
			if(!light){
				user.sendMessage("You cannot see where you want to go. Try turning on a light.");
				return true;
			}
			user.sendMessage("Random person: Hello there, " + user.getName() + ". I've been watching you.");
			user.sendMessage("It's very impressive that you were able to defeat my monster.");
			user.sendMessage("Random person: Now you have to defeat me.");
			user.sendMessage("Random person: By the way, my name is Pete.");
			user.sendMessage("Pete: I'm only trying to kill you because human is my favorite kind of food.");
			user.sendMessage("Pete: So hopefully there are no hard feelings.");
			user.sendMessage("Pete: What do you have to say about this?");
			String input = SendManager.getScanner().nextLine();
			if(input.contains("kill") && input.contains("you")){
				user.sendMessage("Pete: Hey! I said no hard feelings.");
			} else if(input.contains("you") && input.contains("give")){
				user.sendMessage("Pete: Wow, that's very kind of you.");
			} else if(input.contains("that's fine") || input.contains("ok") ||input.contains("kk")|| (input.length() <= 3 && input.contains("k"))){
				user.sendMessage("Pete: I thought you would be a little more upset by this by ok.");
			} else {
				user.sendMessage("Pete: Ok then, I'm not going to respond to that mostly because I wasn't told to. You see, I'm a computer. And computers can't always compute every little thing. ");
			}
			user.sendMessage("Pete: Anyway, let's get on with the fight. I would kill you with a gun, but when I came here they had metal detecters so I couldn't get it into my secret layer. Ugh, security these days. Am I right or am I right?");
			user.sendMessage("You now have 30 hearts.");
			user.setHealth(30);
			this.fighting = true;
			return true;
		}
		
		return false;
	}
	
	public static SpaceMountain getInstance(){
		if(instance == null){
			instance = new SpaceMountain();
		}
		return instance;
	}

}
