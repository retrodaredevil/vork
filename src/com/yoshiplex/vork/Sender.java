package com.yoshiplex.vork;

public abstract class Sender {
	public abstract boolean onSend(String full, String label, String[] split, String[] args, User user);
	
	public final boolean onSend(String full, User user){
		full = SendManager.getFull(full);
		//System.out.println("debug: '" + full + "'");
		String[] split = full.split(" ");
		
		String[] args = null;
		if(split.length == 0){
			return false;
		} else {
			args = new String[split.length - 1];
		}
		
		for(int i = 0; i < split.length; i++){
			if(i == 0){
				continue;
			}
			args[i - 1] = split[i];
		}
		return this.onSend(full, split[0], split, args, user);
	}
}
