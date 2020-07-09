package pt.iul.ista.poo.farm.objects;


import java.util.Random;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Land extends FarmObject implements Interactable{

	
	private static final long serialVersionUID = 3560157175681529880L;
	private boolean isLavrado;
	private boolean readyToPlant;
	private boolean plantado;
	private boolean rocha;
	private boolean escolhido;

	public Land(Point2D p) {
		super(p);
	}

	@Override
	public String getName() {

		Random a = new Random();

		if(!escolhido){
			if(a.nextInt(10)==0)
				rocha = true;
			escolhido= true;
		}

		if(isLavrado){
			readyToPlant= true;
			return "plowed";
		}

		if(!isLavrado){
			readyToPlant = false;
			plantado = false;
		}	

		if(rocha) 
			return "rock";

		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public void interact() {

		if(!rocha && !isLavrado){
			isLavrado = true;
		}

		if(isReadyToPlant() && !plantado){
			if((int)(Math.random()*2) == 0) {
				Farm.getInstance().addObject(new Tomato(this.getPosition()));
			} else {
				Farm.getInstance().addObject(new Cabbage(this.getPosition()));
			}
			plantado = true;
		}	
	}

	public boolean isReadyToPlant() {
		return readyToPlant;
	}

	public boolean isPlantado() {
		return plantado;
	}

	public void setLavrado(boolean v) {
		isLavrado = v;
	}

	public void setReadytoPlant(boolean v) {
		readyToPlant = v;
	}

	public void setPlantado(boolean v) {
		plantado = v;
	}
}






