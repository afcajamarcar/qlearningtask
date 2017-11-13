package gui;

import com.sun.org.apache.xpath.internal.SourceTree;
import processing.core.PApplet;
import fisica.*;

import java.util.Random;


public class VisualAgent extends PApplet {
	//int state;
	int itera = 0;
	int[] actions = new int[3];
	int NEAR = 0;
	int FAR = 1;
	int STATE;

	double [][] Q_matrix = new double[2][3];
	double [][] R_matrix = new double[2][3];
	private double gamma = 0.8;

	Random random;

	FWorld world; //Complete environment

	int rad = 28; //Ball radio
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


		for(int i  = 0; i < 2; i++)
			for(int j = 0; j < 3; j++)
				Q_matrix[i][j] = 0;

		R_matrix[0][0] = -10;
		R_matrix[0][1] = -10;
		R_matrix[0][2] = 20;

		R_matrix[1][0] = 20;
		R_matrix[1][1] = -10;
		R_matrix[1][2] = -10;

		actions[0] = 0;
		actions[1] = -1200;
		actions[2] = -2100;
		frameRate(100);
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

		if(distanceToBall() <= 90 && distanceToBall() > 0) {
			STATE = NEAR;
			System.out.println("Saltando...");
			if(creature.isResting()){
				int j;

				int action;
				if(itera == 0) {
					j = random.nextInt(3);
					System.out.println("j: " + j);
					System.out.println("action j:" + actions[j]);
					itera++;
				}else
					j = indexOfQMaxN();
				System.out.println("maxq: " +STATE + j);
				jump(actions[j]);

				System.out.println("touching?" + (Math.abs(creature.getY()+creatureDim/2-floor.getY()) > rad? "yes": "no" ));
				System.out.println("y creature:" + creature.getY());
				
				updateQMatrix(j, STATE);
				
				//printQMatrix(j);
			}
		}
		else{
			STATE = FAR;
			if(creature.isResting()){
				int j;
				if(itera == 0) {
					j = random.nextInt(3);
					System.out.println("j: " + j);
					System.out.println("action j:" + actions[j]);
				}else
					j = indexOfQMaxF();

				System.out.println("maxq: "  +STATE + j);
				jump(actions[j]);

				updateQMatrix(j, STATE);
				
				//printQMatrix(j);
				
			}

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
		System.out.println("STATE: " + STATE);
		System.out.println("jumped with force: " + forceY);
		creature.adjustVelocity(0, -forceY);
	}	 
	
	public int distanceToBall() {
		return (int) (getxposBall() - creature.getX());
	}

	public void updateQMatrix(int action, int state){
		Q_matrix[state][action] = R_matrix[state][action] + gamma * q_max(state);
	}

	public double q_max(int index){
		double max = Q_matrix[0][0];
			for(int j = 0; j < 3; j++)
				max = max < Q_matrix[index][j]? Q_matrix[index][j]: max;
		return max;
	}

	public int indexOfQMaxN(){

		int index = 0;
		double max = Q_matrix[0][0];
			for(int j = 0; j < 3; j++){
				if(max < Q_matrix[0][j]){
					max = Q_matrix[0][j];
					index = j;
				}
			}
		return index;
	}

	public int indexOfQMaxF(){

		int index = 0;
		double max = Q_matrix[1][0];
		for(int j = 0; j < 3; j++){
			if(max < Q_matrix[1][j]){
				max = Q_matrix[1][j];
				index = j;
			}
		}
		return index;
	}
	
	public void printQMatrix(int j) {
		System.out.println("Q_MATRIXXXXXXX");
		for(int i = 0; i < 2; i ++){
			for(int k = 0; k < 3; k++)
				System.out.println("Q_matrix : " + Q_matrix[i][j]);
		}
	}
	
	public static void main(String[] args) {
		PApplet.main(VisualAgent.class.getName());

	}
}