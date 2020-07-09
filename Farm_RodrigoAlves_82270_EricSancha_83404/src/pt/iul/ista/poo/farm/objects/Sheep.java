package pt.iul.ista.poo.farm.objects;


import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Sheep extends Animal {

	
	private static final long serialVersionUID = 1914246036982892142L;
	private boolean esfomeada;


	public Sheep(Point2D p) {
		super(p);
	}

	@Override
	public String getName() {

		if(esfomeada)
			return "famished_sheep";

		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public void interact() {

		resetCiclos();
		esfomeada = false;
	}

	@Override
	public void update() {

		IncrementaCiclos();

		if(getCiclos() >= 21) {
			esfomeada = true;
		}
		if(getCiclos()>10 && getCiclos()<21) {
			Random();
			if(VeggieEncounter()) {
				eatVegetable();
				resetCiclos();
			}
			else {
				infoMovement();
				if(VeggieEncounter()) {
					eatVegetable();
					resetCiclos();
				} else if(isHaveMove()){
					setPosition(this.getPosition().plus(getVector()));
					setHaveMove(false);
				}
			}
		}

		if(!esfomeada)
			Farm.getInstance().SomarPontos(1);
	}
}

