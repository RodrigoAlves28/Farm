package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Point2D;
import pt.iul.ista.poo.utils.Vector2D;
public class Farmer extends FarmObject {

	
	private static final long serialVersionUID = 7319272768473896128L;

	public Farmer(Point2D p) {
		super(p);
	}

	@Override
	public int getLayer() {
		return 5;
	}

	public  void move(Vector2D key) {
		if(ImageMatrixGUI.getInstance().isWithinBounds(this.getPosition().plus(key)) &&
				Farm.getInstance().isNotAnimal(this.getPosition().plus(key))){
			setPosition(this.getPosition().plus(key));
		}
	}
}
