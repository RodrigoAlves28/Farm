package teste;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import pt.iul.ista.poo.farm.objects.Tomato;
import pt.iul.ista.poo.utils.Point2D;

public class TomatoTest {

	Tomato t;
	
	@Before
	public void setUp() throws Exception {
		t = new Tomato(new Point2D(0,0)); 
	}

	@Test
	public void testInteract() {
		t.interact();
		assertTrue(t.isCuidar());
		t.setNciclos(14);
		t.update();
		assertTrue(t.isMADURO());
		
	}

	@Test
	public void testMudancasDeEstado() {
		assertFalse(t.isMADURO());
		t.interact();
		t.setNciclos(14);
		t.update();
		assertTrue(t.isMADURO());
		assertEquals("tomato", t.getName());
		t.setNciclos(24);
		t.update();
		assertTrue(t.isPODRE());
		assertEquals("bad_tomato",t.getName());
	}
}
