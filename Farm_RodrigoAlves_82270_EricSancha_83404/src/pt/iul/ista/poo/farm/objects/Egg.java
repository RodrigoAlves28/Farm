package pt.iul.ista.poo.farm.objects;

import java.util.ArrayList;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class Egg  extends Chicken{

	
	private static final long serialVersionUID = 8302774237333868690L;

	public Egg(Point2D p) {
		super(p);
	}

	@Override
	public String getName() {

		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public void interact() {

		Farm.getInstance().removeObject(this);
		Farm.getInstance().SomarPontos(1);
	}

	@Override
	public void update() {
		
		IncrementaCiclos();
		//getCiclos()>=20 pk pode não ter espaço para chocar no ciclo 20, e assim que houver espaço livre, o ovo choca
		if(getCiclos() >= 20){
			ArrayList<Vector2D> random = new ArrayList<Vector2D>();
			for(int i = 0; i != 4;i++){
				Point2D k = Direction.getNeighbourhoodPoints(this.getPosition()).get(i);
				Vector2D p = Vector2D.movementVector(this.getPosition(),k);
				if(isValidPosition(p)){
					random.add(p);
				}
			}
			
			if(random.size() != 0){
				Chicken o = new Chicken(this.getPosition().plus(random.get((int)(Math.random()*random.size()))));
				Farm.getInstance().addObject(o);
				Farm.getInstance().removeObject(this);
			}
		}
	}
}


