package pt.iul.ista.poo.farm.objects;

import java.io.Serializable;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class FarmObject  implements ImageTile,Serializable {

	private static final long serialVersionUID = 5917781120981090808L;
	private Point2D position;

	public FarmObject(Point2D p) {
		this.position = p;
	}      
	
	@Override
	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}
	
	@Override
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D position) {
		this.position = position;
	}

	@Override
	public int getLayer() {
		return 0;
	}
}
