package pt.iul.ista.poo.farm.objects;


import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;

public class Chicken extends Animal{

	
	private static final long serialVersionUID = 6029109197501239071L;

	public Chicken(Point2D p) {
		super(p);
	}

	@Override
	public String getName() {

		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public void interact() {

		Farm.getInstance().removeObject(this);
		Farm.getInstance().SomarPontos(2);
	}

	@Override
	public void update() {

		IncrementaCiclos();

		if(getCiclos()%2==0) {
			Random();
			if(TomatoEncounter()) 
				eatVegetable();
			else{
				infoMovement();
				if(TomatoEncounter()) 
					eatVegetable();
				else
					if(isHaveMove()){
						setPosition(this.getPosition().plus(getVector()));
						setHaveMove(false);
					}
			}
		}

		if(getCiclos()%10 == 0) {
			Random();
			Vector2D v = getVector();
			if(isValidPosition(v)){
				Chicken u = new Egg (this.getPosition().plus(v));
				Farm.getInstance().addObject(u);
			}else{
				for(int i = 0; i != 4;i++){
					Vector2D k = Direction.values()[i].asVector();
					if(k!=v)
						if(isValidPosition(k)){
							Chicken u = new Egg (this.getPosition().plus(k));
							Farm.getInstance().addObject(u);
							return;
						}
				}
			}
		}
	}

}








