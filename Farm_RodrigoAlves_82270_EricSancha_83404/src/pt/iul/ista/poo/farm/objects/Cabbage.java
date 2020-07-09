package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

public class Cabbage extends Vegetable {

	
	private static final long serialVersionUID = 8181246133482406417L;
	private	static final int AMADURECE = 10;
	private static final int APODRECE = 30;

	public Cabbage(Point2D p) {
		super(p);		
	}

	@Override
	public String getName() {
		if(isMADURO())
			return getClass().getSimpleName().toLowerCase();
		if(isPODRE())
			return "bad_cabbage";
		return "small_cabbage";
	}

	@Override
	public void interact() {

		if(getName().equals( "small_cabbage"))
			IncrementaCiclos();

		if(isMADURO()){
			Farm.getInstance().removeObject(this);
			Farm.getInstance().SomarPontos(2);
		}

		if(isPODRE())
			Farm.getInstance().removeObject(this);
	}

	@Override
	public void update() {

		IncrementaCiclos();

		//é preciso o  AMADURECE + 1 pk se eu acelerar crescimento no ciclo 9 , passa para 10, mas qd for dar update ja ta no ciclo 11
		//logo nao mudava a imagem
		if(getCiclos() == AMADURECE || getCiclos() == AMADURECE + 1)
			setMADURO(true);

		if(getCiclos() == APODRECE  ) {
			setMADURO(false);
			setPODRE(true);
		}
	}
}
