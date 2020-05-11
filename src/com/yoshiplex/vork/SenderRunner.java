package com.yoshiplex.vork;

import java.util.ArrayList;
import java.util.List;

public abstract class SenderRunner extends Sender{
	protected List<Sender> list = new ArrayList<>();
	
	protected boolean running = false;
	private List<Sender> remove = new ArrayList<>();
	private List<Sender> add = new ArrayList<>();
	
	protected void resetList(){
		for(Sender s : remove){
			this.list.remove(s);
		}
		for(Sender s : add){
			this.list.add(s);
		}
	}
	public void add(Sender s){
		if(running){
			add.add(s);
		} else {
			this.list.add(s);
		}
	}
	public void remove(Sender s){
		if(running){
			remove.add(s);
		} else {
			this.list.remove(s);
		}
	}
}
