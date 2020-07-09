package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.utils.Point2D;

public abstract class Vegetable extends FarmObject implements Interactable,Updatable{

	
	private static final long serialVersionUID = 984085372657203464L;
	private int Nciclos;
	private boolean PODRE;
	private boolean MADURO;

	public Vegetable(Point2D p) {
		super(p);
	}

	@Override
	public int getLayer() {
		return 1;
	}

	public void IncrementaCiclos() {
		Nciclos++;
	}

	public void setPODRE(boolean k ) {
		PODRE = k;
	}

	public void setMADURO(boolean k) {
		MADURO = k;
	}

	public int getCiclos() {
		return Nciclos;
	}

	@Override
	public String getName() {
		return getClass().getSimpleName().toLowerCase();
	}

	@Override
	public void interact() {

	}

	@Override
	public void update() {
	}

	public boolean isPODRE() {
		return PODRE;
	}

	public boolean isMADURO() {
		return MADURO;
	}

	public void setNciclos(int nciclos) {
		Nciclos = nciclos;
	}
}
