package pt.iul.ista.poo.farm.objects;

import pt.iul.ista.poo.farm.Farm;
import pt.iul.ista.poo.utils.Point2D;

/**
 * class tomato que representa 
 * um vegetal
 * @author Rodrigo Alves e Éric Sancha
 * @version 2.0
 */
public class Tomato extends Vegetable {

	private static final long serialVersionUID = -6922020095404237359L;
	private static final int AMADURECE = 15;
	private static final int APODRECE = 25;
	private boolean cuidar;

	/**
	 * Constructs and initialize a Tomato on point p
	 * @param p local onde vai aparecer o tomate */
	public Tomato(Point2D p) {
		super(p);		
	}

	/** @return devolve o nome do estado do tomate, que vai corresponder à imagem do estado e
	 *assim  mudar a imagem */
	/** Serve para mudar o estado do tomate */
	@Override
	public String getName() {

		if(isMADURO())
			return getClass().getSimpleName().toLowerCase();

		if(isPODRE())
			return "bad_tomato";

		return "small_tomato";
	}

	/** Serve para interagir com o tomate numa dada posição 
	 */
	@Override
	public void interact() {

		cuidar = true;	

		if(isMADURO()){
			Farm.getInstance().removeObject(this);
			Farm.getInstance().SomarPontos(3);
		}

		if(isPODRE())
			Farm.getInstance().removeObject(this);		
	}

	/** Atualiza o tomate numa dada posição */
	@Override
	public void update() {

		IncrementaCiclos();

		if(cuidar)
			if(getCiclos() >= AMADURECE && getCiclos() < APODRECE)
				if(!isMADURO())
					setMADURO(true);

		if(getCiclos() == APODRECE){
			if(isMADURO())
				setMADURO(false);
			setPODRE(true);
		}	
	}
	/** Para saber se o tomate foi cuidado
	* @return devolve o valor do boolean cuidar
	*/
	public boolean isCuidar() {
		return cuidar;
	}
}




