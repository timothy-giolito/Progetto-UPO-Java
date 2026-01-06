package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.Reparto;
import modello.ListaDiArticoli;
import modello.eccezioni.ArticoloException;
import modello.eccezioni.ListaDiArticoliException;

/**
 * Suite di test per la classe {@link modello.ListaDiArticoli}.
 * <p>
 * Controlla la creazione delle liste, la gestione del nome della lista
 * e le operazioni di aggiunta e rimozione degli articoli.
 * </p>
 * @author Luca Franzon 20054744
 */

class testListaDiArticoli {
	
	/**
     * Verifica che una lista venga creata correttamente con un nome valido
     * e che sia inizialmente vuota.
     */
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

	/**
     * Verifica che venga lanciata un'eccezione se si tenta di creare
     * una lista con nome null o vuoto.
     */
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

	/**
     * Verifica la corretta modifica del nome della lista tramite il metodo setter.
     */
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

	/**
     * Verifica che non sia possibile rinominare la lista con un nome non valido.
     */
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

	/**
     * Verifica che sia possibile aggiungere un articolo valido alla lista
     * e che il contatore degli articoli aumenti di conseguenza.
     */
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

	/**
     * Verifica che l'aggiunta di un articolo nullo provochi un'eccezione.
     */
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

	/**
     * Verifica che un articolo venga correttamente rimosso dalla lista
     * e che il numero totale di articoli diminuisca.
     */
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
