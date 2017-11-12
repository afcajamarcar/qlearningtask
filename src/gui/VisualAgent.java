package gui;

import processing.core.PApplet;
import fisica.*;

public class VisualAgent extends PApplet {
	
	
	FWorld world; //Complete environment
	
	
	FCircle ball; //The ball or obstacle for the agent to jump
	int rad = 25; //Ball radio
	private double xspeed = 2.5;//Ball speed
	float xposBall = width-rad;
	float yposBall = 185; 
	
	FBox floor; //The floor is set static
	
	//Tree triangles representing the creature or agent
	//FPoly head;
	//FPoly torso;
	//FPoly tail;
	//FDistanceJoint ht, tt;
	
	//cube creature
	FBox creature;
	
	
	
	
	

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
		
		creature = new FBox(40, 40);
		creature.setPosition(120, 178);
		world.add(creature);
		
		
		System.out.println(creature.getForceX());
		System.out.println(creature.getForceY());
		
		
		/**
		 * 
		 
		head = createTriangle(headVertex());
		head.setFill(155);
		head.setPosition(80, 50);
		world.add(head);
		
		torso = createTriangle(torsoVertex());
		torso.setFill(255);
		torso.setPosition(80, 60);
		world.add(torso);
		
		tail = createTriangle(tailVertex());
		tail.setFill(255);
		tail.setPosition(80, 50);
		world.add(tail);
		
		
		
		ht = new FDistanceJoint(head, torso);
		ht.setCollideConnected(true);
		ht.setAnchor1(0, 10);
		ht.setAnchor2(20, 25);
		world.add(ht);
		tt = new FDistanceJoint(torso, tail);
		tt.setCollideConnected(true);
		tt.setAnchor1(40, 10);
		tt.setAnchor2(40, 45);
		world.add(tt);
		*/
		
	}

	public void draw(){
		//fill background with black color
		background(0,0,0);
		world.step();
		world.draw();
		if(xposBall < 0-rad) {
			xposBall = width;
			
		}
		ellipse(xposBall,yposBall,rad,rad);
		xposBall =  (float) (xposBall + (xspeed*-1));
		System.out.println(getBallPostition());
		if(creature.isResting()) {
			System.out.println("Saltando...");
			jump(-2000);
		}
		
	}
	
	public float getxposBall() {
		return this.xposBall;
	}
	
	public float getyposBall() {
		return this.yposBall;
	}
	
	public String getBallPostition() {
		String x = Float.toString(getxposBall());
		String y = Float.toString(getyposBall());
		return x + " "+ y;
	}
	
	public void jump(int forceY) {
		creature.adjustVelocity(0,-forceY);
	}
	/**
	 * Receives the three vertex inside an arraylist (9 values)
	 * @param vertex
	 * @return a Fpoly object with the shape of a triangle.
	 
	public FPoly createTriangle(ArrayList<Integer> vertex) {
		FPoly triangle = new FPoly();
		triangle.vertex(vertex.get(0), vertex.get(1));
		triangle.vertex(vertex.get(2),vertex.get(3));
		triangle.vertex(vertex.get(4),vertex.get(5));
		return triangle;
		
	}
	*/
	/**
	 * Fills an arraylist with the vertex of a triangle (head)
	 * could be changed in order to make easier the jump or the overall form
	 * of the creature
	 * @return arraylist with vertex, x odd positions, y even positions
	 
	public ArrayList<Integer> headVertex(){
		ArrayList<Integer> headVertex = new ArrayList<>();
		headVertex.add(35);
		headVertex.add(30);
		headVertex.add(0);
		headVertex.add(10);
		headVertex.add(40);
		headVertex.add(10);
		return headVertex;
	}
	*/
	/**
	 * Fills an arraylist with the vertex of a triangle (torso)
	 * could be changed in order to make easier the jump or the overall form
	 * of the creature
	 * @return arraylist with vertex, x odd positions, y even positions
	 
	public ArrayList<Integer> torsoVertex(){
		ArrayList<Integer> headVertex = new ArrayList<>();
		headVertex.add(20);
		headVertex.add(30);
		headVertex.add(0);
		headVertex.add(10);
		headVertex.add(40);
		headVertex.add(10);
		return headVertex;
	}
	*/
	
	/**
	 * Fills an arraylist with the vertex of a triangle (torso)
	 * could be changed in order to make easier the jump or the overall form
	 * of the creature
	 * @return arraylist with vertex, x odd positions, y even positions
	 
	public ArrayList<Integer> tailVertex(){
		ArrayList<Integer> headVertex = new ArrayList<>();
		headVertex.add(-30);
		headVertex.add(10);
		headVertex.add(20);
		headVertex.add(20);
		headVertex.add(40);
		headVertex.add(45);
		return headVertex;
	}
	*/
	public static void main(String[] args) {
		PApplet.main(VisualAgent.class.getName());

	}
}