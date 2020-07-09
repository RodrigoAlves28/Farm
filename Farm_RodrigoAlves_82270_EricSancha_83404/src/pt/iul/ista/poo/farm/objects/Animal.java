package pt.iul.ista.poo.farm.objects;

import java.util.ArrayList;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class Animal extends FarmObject implements Interactable,Updatable {

	
	private static final long serialVersionUID = 3393904610613458990L;
	private int Nciclos;
	private Vector2D e;
	private boolean HaveMove;

	public Animal(Point2D p) {
		super(p);
	}

	public void Random() {
		e = Direction.random().asVector();
	}

	public  void infoMovement() {
		Vector2D z = e;
		if(isValidPosition(e)){
			HaveMove = true;
		}
		else{
			movimentosPossiveis(z);
		}
	}

	public Vector2D getVector() {
		return e;
	}

	public void movimentosPossiveis(Vector2D z){
		ArrayList<Vector2D> random = new ArrayList<Vector2D>();
		for(int i = 0; i != 4;i++){
			Vector2D p = Direction.values()[i].asVector();
			if(p != z)
				if(isValidPosition(p)){
					random.add(p);
				}
		}
		if(random.size() != 0){
			e = random.get((int)(Math.random()*random.size()));
			HaveMove = true;
		}
	}

	public boolean isValidPosition(Vector2D p) {
		if(ImageMatrixGUI.getInstance().isWithinBounds(this.getPosition().plus(p)) && 
				Farm.getInstance().isNotFarmer(this.getPosition().plus(p)) && Farm.getInstance().isNotAnimal(this.getPosition().
						plus(p)))
			return true;
		return false;
	}

	public void eatVegetable() {
		if(VeggieEncounter()) 
			Farm.getInstance().eat(this.getPosition().plus(e));
	}

	public boolean VeggieEncounter() {
		if(Farm.getInstance().isVegetable(this.getPosition().plus(e))) 
			return true;
		return false;
	}

	public boolean TomatoEncounter() {
		if(Farm.getInstance().isVegetable(this.getPosition().plus(e))) {
			if(Farm.getInstance().isTomato(this.getPosition().plus(e))) 
				return true;
		}
		return false;
	}

	@Override
	public void update() {}

	@Override
	public int getLayer() {
		return 2;
	}

	public int getCiclos() {
		return Nciclos;
	}

	public void IncrementaCiclos() {
		Nciclos++;
	}

	@Override
	public void interact() {}

	public void resetCiclos() {
		Nciclos=0;
	}

	public boolean isHaveMove() {
		return HaveMove;
	}

	public void setHaveMove(boolean haveMove) {
		HaveMove = haveMove;
	}
}
