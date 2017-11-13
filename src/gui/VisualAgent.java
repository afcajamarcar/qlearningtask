package gui;

import processing.core.PApplet;
import fisica.*;

public class VisualAgent extends PApplet {
	
	
	FWorld world; //Complete environment
	
	int rad = 25; //Ball radio
	private double xspeed = 2.5;//Ball speed
	float xposBall = 800;
	float yposBall = 185; 
	FBox floor; //The floor is set static
	FBox creature; //cube creature
	int creatureDim = 40;
	
	
	public void settings(){
		size(800,300);
	}

	public void setup(){
		Fisica.init(this);
		world = new FWorld();
		
		floor = new FBox(width*2, 3);
		floor.setPosition(0, 200);
		floor.setStatic(true);
		floor.setGrabbable(false);
		floor.setFill(255);
		world.add(floor);
		
		creature = new FBox(creatureDim, creatureDim);
		creature.setPosition(120, 178);
		creature.setGrabbable(false);
		world.add(creature);
		
	}

	public void draw(){
		//fill background with black color
		background(0,0,0);
		world.step();
		world.draw();
		
		//Check if the ball has passed the final of the frame
		if(xposBall < 0-rad) {
			xposBall = width;
		}
		
		ellipse(xposBall,yposBall,rad,rad);
		xposBall =  (float) (xposBall + (xspeed*-1));
		//System.out.println(distanceToBall());
		
		if(distanceToBall() <= 70 && distanceToBall() > 0 && creature.isResting()) {
			//System.out.println("Saltando...");
			jump(-2000);
			System.out.println(creature.getY());
		}
		
		
	}
	
	public float getxposBall() {
		return this.xposBall;
	}
	
	public float getyposBall() {
		return this.yposBall;
	}
	
	/**
	 * Returns the x, y position of the ball
	 */
	public String getBallPostition() {
		String x = Float.toString(getxposBall());
		String y = Float.toString(getyposBall());
		return x + " "+ y;
	}
	
	/**
	 * Makes the creature jump with the given force 
	 * @param forceY
	 */
	public void jump(int forceY) {
		creature.adjustVelocity(0,-forceY);
	}	 
	
	public int distanceToBall() {
		return (int) (getxposBall() - creature.getX());
	}
	public static void main(String[] args) {
		PApplet.main(VisualAgent.class.getName());

	}
}