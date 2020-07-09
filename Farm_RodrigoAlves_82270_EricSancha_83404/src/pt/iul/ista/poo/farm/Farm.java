package pt.iul.ista.poo.farm;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.Scanner;

import pt.iul.ista.poo.farm.objects.Animal;
import pt.iul.ista.poo.farm.objects.Chicken;
import pt.iul.ista.poo.farm.objects.FarmObject;
import pt.iul.ista.poo.farm.objects.Farmer;
import pt.iul.ista.poo.farm.objects.Interactable;
import pt.iul.ista.poo.farm.objects.Land;
import pt.iul.ista.poo.farm.objects.Sheep;
import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.farm.objects.Updatable;
import pt.iul.ista.poo.farm.objects.Vegetable;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;


public class Farm  implements Observer {

	// TODO
	private static final String SAVE_FNAME = "config/savedGame";
	private List<FarmObject> objects = new ArrayList<FarmObject>();
	private static final int MIN_X = 5;
	private static final int MIN_Y = 5;
	private Farmer farmer;
	private static Farm INSTANCE;
	private boolean SPACE;
	private boolean s;
	private boolean l;
	private int max_x;
	private int max_y;
	private int ciclos;
	private int pontos;

	private Farm(int max_x, int max_y) {
		if (max_x < 5 || max_y < 5)
			throw new IllegalArgumentException();

		this.max_x = max_x;
		this.max_y = max_y;

		INSTANCE = this;

		ImageMatrixGUI.setSize(max_x, max_y);

		// Não usar ImageMatrixGUI antes de inicializar o tamanho		
		// TODO

		loadScenario();
	}

	private void registerAll() {
		// TODO
		List<ImageTile> images = new ArrayList<ImageTile>();
		for(FarmObject m : objects)
			if(m instanceof ImageTile)
				images.add((ImageTile)m);
		// images.addAll(...);
		ImageMatrixGUI.getInstance().addImages(images);
		ImageMatrixGUI.getInstance().update();
	}

	private void loadScenario() {
		Random v = new Random();
		farmer = new Farmer(new Point2D(0,0));
		objects.add(farmer);
		for(int x = 0; x != max_x;x++)
			for(int y = 0; y != max_y;y++)
				objects.add(new Land(new Point2D(x,y)));

		for(int k = 0; k != 2; k++) {	
			Point2D c = new Point2D(v.nextInt(max_x),v.nextInt(max_y));
			Point2D s = new Point2D(v.nextInt(max_x),v.nextInt(max_y));

			while(!isNotAnimal(c) || !isNotFarmer(c))
				c = new Point2D(v.nextInt(max_x),v.nextInt(max_y));

			objects.add(new Chicken(c));

			while(!isNotAnimal(s) || !isNotFarmer(s))
				s = new Point2D(v.nextInt(max_x),v.nextInt(max_y));

			objects.add(new Sheep(s));	
		}
		// TODO
		registerAll();
	}

	public int maxX() {
		return max_x;
	}

	public int maxY() {
		return max_y;
	}

	@Override
	public void update(Observable gui, Object a) {
		System.out.println("Update sent " + a);
		PressedKey(a);
		ImageMatrixGUI.getInstance().setStatusMessage(" Pontos: " + pontos + "         Ciclos: " + ciclos);
		ImageMatrixGUI.getInstance().update();
	}

	// Não precisa de alterar nada a partir deste ponto
	private void play() {
		ImageMatrixGUI.getInstance().addObserver(this);
		ImageMatrixGUI.getInstance().go();
	}

	public void PressedKey(Object a){
		if (a instanceof Integer) {
			int key = (Integer) a;
			if(key == KeyEvent.VK_SPACE) 
				SPACE = true;

			if(key == KeyEvent.VK_S)
				s = true;

			if(key == KeyEvent.VK_L)
				l = true;

			WhichKey(key);
		}
	}

	public void WhichKey(int key) {

		if(!SPACE && Direction.isDirection(key)){
			ciclos++;
			System.out.println("Update is a Direction " + Direction.directionFor(key));
			farmer.move(Direction.directionFor(key).asVector());
			updateFarmObjects();
		} else if (Direction.isDirection(key)) {
			System.out.println("Update is a Direction " + Direction.directionFor(key));
			interactALL(key);
		}
		if(s){
			save();
		}

		if(l){
			load();
		}
	}


	private void save() {
		try(ObjectOutputStream output = new ObjectOutputStream( new FileOutputStream(SAVE_FNAME));){
			output.writeObject(objects);
			output.writeInt(max_x);
			output.writeInt(max_y);
			output.writeInt(pontos);
			output.writeInt(ciclos);
			output.close();
			s = false;
			System.out.println("Jogo salvo com sucesso!");
		} catch(Exception e){
			if( e instanceof IOException)
				System.out.println("Não foi possível escrever no ficheiro");
			if( e instanceof FileNotFoundException)
				System.out.println("Não foi possível encontrar o ficheiro");
		}
	}


	private void load() {
		try(ObjectInputStream input = new ObjectInputStream(
				new FileInputStream(SAVE_FNAME));){
			List<FarmObject> aux = new ArrayList<FarmObject>();
			@SuppressWarnings("unchecked")
			List<FarmObject> load = (List<FarmObject>) input.readObject();


			int DimensaoX= input.readInt();
			int DimensaoY =	input.readInt();

			if(max_x != DimensaoX || max_y != DimensaoY) {
				l= false;
				throw new IllegalArgumentException();
			}
			else{
				for(FarmObject o: objects)
					aux.add(o);

				for(FarmObject o: aux)
					removeObject(o);

				for(FarmObject o: load){
					addObject(o);
					if(o instanceof Farmer)
						farmer = (Farmer) o;
				}
				pontos =input.readInt();
				ciclos =input.readInt();
			}

			l = false;
			input.close();



			System.out.println("Jogo carregado com sucesso!");
		} catch(Exception e) {
			if( e instanceof IOException)
				System.out.println("Não foi possível ler o ficheiro");
			if( e instanceof FileNotFoundException)
				System.out.println("Não foi possível encontrar o ficheiro");
			if( e instanceof IllegalArgumentException)
				System.out.println("As dimensões da Farm não correspondem à do ficheiro carregado");
		}
	}



	public boolean isNotAnimal(Point2D p) {
		for(FarmObject a : objects) 
			if(a instanceof Animal && a.getPosition().equals(p)) 
				return false;
		return true;
	}

	public void interactALL(int key){
		FarmObject v = null;
		for (FarmObject m : objects){
			if(m instanceof Land && m.getPosition().equals(farmer.getPosition().plus(Direction.directionFor(key).asVector())) ){
				v = m;
				for(FarmObject k: objects)
					if(k.getPosition().equals(m.getPosition())){
						if(layer(m.getPosition())){
							if(k instanceof Animal)
								v=k;
						}else{
							if(k instanceof Vegetable){
								if(((Vegetable)k).isMADURO() || ((Vegetable)k).isPODRE())
									((Land)m).setLavrado(false);

								v=k;
							}
						}
					}
			}

			SPACE=false;
		}

		if(v != null)	
			((Interactable)v).interact();
	}

	public boolean layer(Point2D v) {
		for(FarmObject b : objects)
			if(b.getPosition().equals(v) && b.getLayer()==2)
				return true;
		return false;
	}
	public boolean isTomato(Point2D p) {
		for(FarmObject v :  objects)
			if(v instanceof Tomato && v.getPosition().equals(p))
				return true;
		return false;
	}

	public boolean isNotFarmer(Point2D p) {
		if(farmer.getPosition().equals(p)) 
			return false;
		return true;
	}

	public static Farm getInstance() {
		assert (INSTANCE != null);
		return INSTANCE;
	}

	public boolean isVegetable(Point2D p) {
		for(FarmObject v: objects) 
			if(v instanceof Vegetable && v.getPosition().equals(p)) 
				return true;
		return false;
	}

	public void eat(Point2D t){
		FarmObject k = null;
		for(FarmObject l: objects){
			if(l instanceof Vegetable && l.getPosition().equals(t))
				k = l;
		}
		if(k!=null)
			removeObject(k);
	}

	public void addObject(FarmObject o){
		objects.add(o);
		ImageMatrixGUI.getInstance().addImage(o);
	}

	public void updateFarmObjects(){
		ArrayList<Updatable> aux = new ArrayList<Updatable>(); // é necessário criar uma lista aux porque ao dar update dou remove na mesma lista
		for(FarmObject p : objects){
			if(p instanceof Animal)
				aux.add((Updatable)p);
			if(p instanceof Vegetable)
				((Updatable)p).update();
		}
		for(Updatable p: aux)
			p.update();	
	}

	public void removeObject(FarmObject p ) {
		objects.remove(p);
		ImageMatrixGUI.getInstance().removeImage(p);
	}

	public void SomarPontos(int k) {
		pontos+=k;
	}


	public static void main(String[] args) {
		/*é melhor abrir o scanner e o printwritter dentro dos parenteses do try
		 * porque assim não é preciso fazer close() no final, pois ele já fecha o
		 * canal de escrita/leitura automaticamente.
		 */
		try(Scanner scanner = new Scanner(new File("Dimensão.txt"));){
			int[] dimensao = new int[2];
			String line = scanner.nextLine();
			String[] values = line.split(" ");
			dimensao[0] = Integer.parseInt(values[0]);
			dimensao[1] = Integer.parseInt(values[1]);
			int Nlinhas = dimensao[0];
			int Ncolunas = dimensao[1];
			
			Farm f = new Farm(Ncolunas, Nlinhas);
			f.play();
			
		}catch(IllegalArgumentException e){
			System.out.println("A dimensão da Farm tem de ser no mínimo : 5x5");
		}
		catch(FileNotFoundException e) {
			System.out.println("O ficheiro não foi encontrado");
		}

	}
}
