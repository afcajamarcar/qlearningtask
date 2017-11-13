package gui;

import processing.core.PApplet;
import fisica.*;

import java.util.Random;

public class VisualAgent extends PApplet {

	int itera = 0;
	int [] actions = {-700,-1820,-810,-1920,-350,-70,-2330,-1200,-1000, -1000, -900, -1200,-120,-1210,-1230,-1590,-2070,-1330,-1600,-1000, -790, -500 -1200 , -1500, -1800, -1850, -1900, -2000,-2500,-3000};
	double [] Q_matrix = new double[actions.length];
	double [] R_matrix = new double[actions.length];
	private double gamma = 0.8;

	Random random;

	FWorld world; //Complete environment

	int rad = 45; //Ball radio
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


		for(int i  = 0; i < Q_matrix.length; i++){
			Q_matrix[i] = 0;
			R_matrix[i] = actions[i] > -1800? -10: 10;
		}

		for (Double x:R_matrix)
			System.out.println("r_matrix: " + x);


		frameRate(5000);
		random = new Random();
		System.out.println(creature.getY());
		System.out.println(floor.getY());
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

			int j;
//			System.out.println("j: " + j);
//			System.out.println("action j:" + actions[j]);
			if(itera == 0) {
				j = random.nextInt(actions.length);
				jump(j);
				itera++;
			}else
				j = indexOfQMax();
				jump(actions[j]);

			System.out.println("touching?" + (Math.abs(creature.getY()+creatureDim/2-floor.getY()) > rad? "yes": "no" ));
			//System.out.println(creature.getY());
			updateQMatrix(j);
			System.out.println("Q_MATRIXXXXXXX");
			for(Double x: Q_matrix)
				System.out.println("Q_matrix : " + x);
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

	public void updateQMatrix(int action){
		Q_matrix[action] = R_matrix[action] + gamma * q_max();
	}

	public double q_max(){
		double max = Q_matrix[0];
		for(int i = 0; i < Q_matrix.length; i++)
			max = max < Q_matrix[i]? Q_matrix[i]: max;
		return max;
	}

	public int indexOfQMax(){
		int index = 0;
		double max = Q_matrix[0];
		for(int i = 0; i < Q_matrix.length; i++) {
			if(max < Q_matrix[i]){
				max = Q_matrix[i];
				index = i;
			}

		}
		return index;
	}
}