package com.yoshiplex.vork.util;

public interface Entity {

	public boolean isDead();
	public void harm(int hearts);
	public void heal(int hearts);
	public int getHealth();
	
}
