package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.Reparto;
import modello.ListaDiArticoli;
import modello.eccezioni.ArticoloException;
import modello.eccezioni.ListaDiArticoliException;

class testListaDiArticoli {

	@Test
	void testCostruttoreValido() {
		try {
			ListaDiArticoli lista = new ListaDiArticoli("Spesa Settimanale");
			assertEquals("Spesa Settimanale", lista.getListaNome());
			assertEquals(0, lista.getNumeroArticoli());
		} catch (ListaDiArticoliException e) {
			fail("Eccezione inattesa: " + e.getMessage());
		}
	}

	@Test
	void testCostruttoreNonValido() {
		assertThrows(ListaDiArticoliException.class, () -> {
			new ListaDiArticoli(null);
		});
		
		assertThrows(ListaDiArticoliException.class, () -> {
			new ListaDiArticoli("");
		});
		
		assertThrows(ListaDiArticoliException.class, () -> {
			new ListaDiArticoli("   ");
		});
	}

	@Test
	void testSetNomeValido() {
		try {
			ListaDiArticoli lista = new ListaDiArticoli("Spesa");
			lista.setListaNome("Nuova Spesa");
			assertEquals("Nuova Spesa", lista.getListaNome());
		} catch (ListaDiArticoliException e) {
			fail("Eccezione inattesa: " + e.getMessage());
		}
	}

	@Test
	void testSetNomeNonValido() {
		try {
			ListaDiArticoli lista = new ListaDiArticoli("Spesa");
			
			assertThrows(ListaDiArticoliException.class, () -> {
				lista.setListaNome(null);
			});
			
			assertThrows(ListaDiArticoliException.class, () -> {
				lista.setListaNome("");
			});
			
		} catch (ListaDiArticoliException e) {
			fail("Eccezione inattesa nel setup: " + e.getMessage());
		}
	}

	@Test
	void testAggiungiArticolo() {
		try {
			ListaDiArticoli lista = new ListaDiArticoli("Spesa");
			Articolo art = new Articolo("Pane", "Alimentari", 1.50, "Integrale", Reparto.PANETTERIA);
			
			lista.AggiungiArticolo(art);
			
			assertEquals(1, lista.getNumeroArticoli());
			assertTrue(lista.getArticoli().contains(art));
			
		} catch (ListaDiArticoliException | ArticoloException e) {
			fail("Eccezione inattesa: " + e.getMessage());
		}
	}

	@Test
	void testAggiungiArticoloNull() {
		try {
			ListaDiArticoli lista = new ListaDiArticoli("Spesa");
			
			assertThrows(ListaDiArticoliException.class, () -> {
				lista.AggiungiArticolo(null);
			});
			
		} catch (ListaDiArticoliException e) {
			fail("Eccezione inattesa nel setup: " + e.getMessage());
		}
	}

	@Test
	void testRimuoviArticolo() {
		try {
			ListaDiArticoli lista = new ListaDiArticoli("Spesa");
			Articolo art = new Articolo("Latte", "Alimentari", 1.20, "", Reparto.GASTRONOMIA);
			
			lista.AggiungiArticolo(art);
			assertEquals(1, lista.getNumeroArticoli());
			
			lista.RimuoviArticolo(art);
			assertEquals(0, lista.getNumeroArticoli());
			assertFalse(lista.getArticoli().contains(art));
			
		} catch (ListaDiArticoliException | ArticoloException e) {
			fail("Eccezione inattesa: " + e.getMessage());
		}
	}
}
