package com.yoshiplex.vork.locations;

import com.yoshiplex.vork.Location;
import com.yoshiplex.vork.User;
import com.yoshiplex.vork.util.VorkUtil;

public class UndergroundCircle extends Location {

	private static UndergroundCircle instance;
	
	private boolean light = false;
	private boolean released = false;
	private boolean feltKey = false;
	private boolean hasKey = false;
	private boolean talkedGuard = false;
	private boolean gotUp = false;
	private long whenBossBack = 0;
	private boolean talkedToBoss = false;
	
	protected UndergroundCircle() {
		super("Underground layer", "You are now in a room.");
	}

	@Override
	public void onCome(User user) {
		VorkUtil.delay(500);
		user.sendMessage("The room is very dark.");
		VorkUtil.delay(500);
		user.sendMessage("You cannot see anything.");
		VorkUtil.delay(500);
		user.sendMessage("Try to use all of your senses.");
		user.sendMessage("You've regained all of your health for sleeping.");
		user.setHealth(20);
	}

	@Override
	public boolean onSend(String full, String label, String[] split, String[] args, User user) {
		if(this.whenBossBack != 0 && this.whenBossBack < System.currentTimeMillis() && !this.talkedToBoss){
			this.talkedToBoss = true;
			user.sendMessage("Guard: Shhhhh. My boss is back. I'll put you back on the chair. I have a script to say so if I say something twice it's just because my boss told me to. I also must turn the lights off. My boss thinks I can see in the dark like him.");
			this.released = false;
			this.gotUp = false;
			this.light = false;
			this.hasKey = false;
			this.talkedGuard = false;
			user.sendMessage("Boss (Yelling): Is the patient awake yet?");
			user.sendMessage("Guard (yelling): No not yet.");
			user.sendMessage("Guard (Whisper): Quick pretend to be sleeping.");
			user.sendMessage("Boss: Okay. We need to wait for him to wake up. There's no point in the expirement if the patient isn't awake to feel the pain.");
			user.sendMessage("Boss: I'll be upstairs. Tell me right away when they wake up.");
			return true;
		}
		if(label.equals("look")){
			if(!light){
				user.sendMessage("You cannot see in the dark.");
				return true;
			}
			user.sendMessage("You see a room with a guard and brick walls with a stairway to a door.");
			return true;
		} else if(label.equals("listen")){
			if(this.talkedGuard){
				user.sendMessage("You hear loud noises and screams coming from rooms close to you.");
			}
			user.sendMessage("You hear a man sleeping on your right.");
			
			return true;
		} else if(label.equals("taste")){
			if(this.released){
				user.sendMessage("You taste nothing.");
				return true;
			}
			user.sendMessage("You taste the fabric of a cloth that is gagging you.");
			
			return true;
		} else if(label.equals("feel")){
			if(this.released){
				user.sendMessage("You feel nothing.");
				return true;
			}
			this.feltKey = true;
			user.sendMessage("You feel strapped to a metal table and you feel a key under you neck.");
			return true;
		} else if(label.equals("go") || label.equals("move")){
			if(!this.released){
				user.sendMessage("You cannot move. You are strapped down.");
				return true;
			}
			if(args.length == 0){
				user.sendMessage("Where do you want to go?");
			}
			String a = args[0];
			if(this.talkedGuard){
				if(full.contains("space")){
					user.sendMessage("Guard: So you want to go to Space Mountain? Alright, let's go.");
					user.setLocation(SpaceMountain.getInstance());
					return true;
				} else if(full.contains("forest")){
					user.sendMessage("Guard: To the forest we go!");
					user.setLocation(Forest.getInstance());
					return true;
				}
				user.sendMessage("That is not a place you can go.");
				return true;
			}
			if(a.equals("left")){
				this.gotUp = true;
				user.sendMessage("You got up and are walking very slowly.");
				VorkUtil.delay(500);
				user.sendMessage("You feel the railing of a wooden stair case.");
				VorkUtil.delay(500);
				user.sendMessage("You start walking up the stairs.");
				VorkUtil.delay(1000);
				user.sendMessage("//>CREEEEKKK");
				VorkUtil.delay(300);
				user.sendMessage("You freeze.");
				user.sendMessage("Guard: SNNNOOOOOOOORRRRRR");
				VorkUtil.delay(500);
				user.sendMessage("You're lucky, the guard didn't wake up.");
				VorkUtil.delay(1000);
				user.sendMessage("You feel the handle of the door and open it.");
				user.sendMessage("You can now see a little bit of light.");
				VorkUtil.delay(1000);
				user.sendMessage("You can see the stone that you heard before everything went black. You move the stone out of the way.");
				VorkUtil.delay(5000);
				user.sendMessage("You are at the round-a-bout.");
				user.setLocation(EntranceForwardCircle.getInstance());
				return true;
			} else if(a.equals("right")){
				this.gotUp = true;
				user.sendMessage("As you step onto the floor you hear a loud creeeeeeek.");
				VorkUtil.delay(500);
				user.sendMessage("...................");
				user.sendMessage("SNNNOOOOOOOOOOORRRRR.");
				VorkUtil.delay(1000);
				user.sendMessage("Guard: Yoouu! How did you escape? Why are you here? Eh. I don't really care. I'm not even getting paid minimum wage. That's why I put that key under your neck. Where do you want to go? I can probably get you there. Let me see where I can take you.");
				user.sendMessage("...............");
				user.sendMessage("Guard: Aha. My boss is on lunch break so I only have time to take you to the forest or Space Mountain. The ride is actually open, if you want to ride it.");
				user.sendMessage("Guard: Let me turn on the lights.");
				VorkUtil.delay(3000);
				this.light = true;
				user.sendMessage("Guard: There we go. You should be able to see now.");
				user.sendMessage("Guard: Feel free to hang around for awhile. Just not for too long or my boss will get back.");
				this.talkedGuard = true;
				this.whenBossBack = System.currentTimeMillis() + 60000;
				return true;
			}
			
		} else if(label.equals("pick") || label.equals("take")){
			if(args.length == 0){
				user.sendMessage("What do you want to pick up?");
				return true;
			}
			String a = args[0];
			if(a.equals("key") && this.feltKey){
				this.hasKey = true;
				user.sendMessage("You picked up the key. Maybe you can unlock yourself.");
				return true;
			}
		} else if(label.equals("unlock")){
			if(args.length == 0){
				user.sendMessage("What do you want to unlocks?");
				return true;
			}
			if(!this.hasKey){
				user.sendMessage("You can't unlock yourself without the key!");
				return true;
			}
			if(this.gotUp){
				user.sendMessage("You're already up! There's nothing to unlock.");
				return true;
			}
			this.released = true;
			user.sendMessage("You unlocked yourself. Which way do you want to go? Left or right? Choose carfully.");
			return true;
		}

		
		return false;
	}

	public static Location getInstance() {
		if(instance == null){
			instance = new UndergroundCircle();
		}
		return instance;
	}

}
