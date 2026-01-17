package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.Categoria;
import modello.eccezioni.ArticoloException;

/**
 * Classe di test JUnit per la verifica del comportamento della classe {@link modello.Articolo}.
 * <p>
 * Verifica il corretto funzionamento dei costruttori, dei metodi setter,
 * della gestione dei valori di default e delle eccezioni attese.
 * </p>
 * @author Timothy Giolito 20054431
 */

class testArticolo {

	/**
     * Verifica che il costruttore crei correttamente un articolo valido
     * e che tutti i getter restituiscano i valori attesi.
     */
	@Test
	void testCostruttore() {
		
		try {
			
			Articolo mela = new Articolo("Mela",Categoria.ORTOFRUTTA, 1.50, "Mela rossa");
			
			// verifica correttezza dati
			assertEquals("Mela", mela.getNome(), "Il nome dovrebbe essere Mela");
            assertEquals(Categoria.ORTOFRUTTA, mela.getCategoria(), "La categoria non corrisponde");
            assertEquals(1.50, mela.getPrezzo(), "Il prezzo dovrebbe essere 1.50");
            assertEquals("Mela rossa", mela.getNota(), "La nota non corrisponde");
            
         
		
		} catch(ArticoloException e) {
			
			fail("No eccezione per articolo non valido!");
		} 
	}
	
	/**
     * Verifica il funzionamento del metodo setNome e controlla che vengano
     * lanciate le eccezioni corrette in caso di nomi non validi (null, vuoti o solo spazi).
     */
	@Test
	void testSetNome() {
		try {
			Articolo a = new Articolo("Latte", Categoria.BEVANDE, 1.20, "");
			
			// modifica valida
			a.setNome("Latte Parzialmente Scremato");
			assertEquals("Latte Parzialmente Scremato", a.getNome(), "Il nome dovrebbe essere stato aggiornato");
			
			// test Eccezioni su setNome
			// caso Null
			assertThrows(ArticoloException.class, () -> a.setNome(null), 
					"Dovrebbe lanciare eccezione se setto il nome a null");
			
			// caso Vuoto
			assertThrows(ArticoloException.class, () -> a.setNome(""), 
					"Dovrebbe lanciare eccezione se setto il nome vuoto");
			
			// caso Spazi vuoti
			assertThrows(ArticoloException.class, () -> a.setNome("   "), 
					"Dovrebbe lanciare eccezione se setto il nome con solo spazi");

		} catch (ArticoloException e) {
			fail("Errore imprevisto durante il setup del testSetNome");
		}
	}
	
	/**
     * Verifica che il costruttore lanci {@link ArticoloException} se si tenta
     * di creare un articolo con un nome non valido (null, vuoto o solo spazi).
     */
	@Test
	void testNomeNonValido() {
		
		// reparto a caso per il test
		Categoria c = Categoria.CUCINA;
		
		// Caso 1: nome nullo
		assertThrows(ArticoloException.class, () -> {
            new Articolo(null, c, 1.0, "Nota");
        }, "Doveva lanciare eccezione per nome null");
		
		// Caso 2: Nome vuoto -> Deve lanciare ArticoloException
        assertThrows(ArticoloException.class, () -> {
            new Articolo("", c, 1.0, "Nota");
        }, "Doveva lanciare eccezione per nome vuoto");
        
        // Caso 3: Nome solo spazi -> Deve lanciare ArticoloException
        assertThrows(ArticoloException.class, () -> {
            new Articolo("   ", c, 1.0, "Nota");
        }, "Doveva lanciare eccezione per nome di soli spazi");
	}
	
	/**
     * Verifica che vengano assegnati correttamente i valori di default
     * quando si passano parametri null o non validi al costruttore (es. prezzo negativo).
     */
	@Test
	void testValoriDefault() {
		
		try {
            // Passiamo null per categoria, nota, reparto e un prezzo negativo
            Articolo articoloDefault = new Articolo("Pane", null, -5.0, null);
            
            assertEquals("Pane", articoloDefault.getNome());
            
            // Verifiche dei default
            assertEquals("Non categorizzato", articoloDefault.getCategoria());
            assertEquals(0.0, articoloDefault.getPrezzo());
            assertEquals("", articoloDefault.getNota());
            
            // Verifica fondamentale: il default deve essere Reparto.NESSUNO
            assertEquals(Categoria.ALTRO, articoloDefault.getCategoria());
            
        } catch (ArticoloException e) {
            fail("Errore inatteso durante il test dei default: " + e.getMessage());
        }
	}
	
	/**
     * Verifica il corretto funzionamento dei metodi setter per prezzo e reparto,
     * assicurandosi che le modifiche vengano salvate.
     */
	@Test
    void testSetters() {
        try {
            Articolo a = new Articolo("Acqua", Categoria.BEVANDE,  0.50, "");
            
            // Cambio il prezzo
            a.setPrezzo(0.80);
            assertEquals(0.80, a.getPrezzo());
            
            // Test cambio di Reparto
            a.setCategoria(Categoria.SURGELATI);
            assertEquals(Categoria.SURGELATI, a.getCategoria());
            
            // dovrebbe mettere NESSUNO
            a.setCategoria(null);
            assertEquals(Categoria.ALTRO, a.getCategoria());

        } catch (ArticoloException e) {
            fail("Errore durante il test dei setter");
        }
    }
	
	/**
     * Verifica che il metodo toString restituisca una stringa non nulla
     * e contenente almeno il nome dell'articolo.
     */
	@Test
    void testToString() {
        try {
            Articolo a = new Articolo("Martello", Categoria.UTENSILI, 15.0, "Manico legno");
            
            String descrizione = a.toString();
            
            assertTrue(descrizione.contains("Martello"));
            assertNotNull(descrizione); 
            
        } catch (ArticoloException e) {
            fail("Errore");
        }
    }
	
	/**
     * Verifica la logica di fallback dei setter (es. impostare null come categoria
     * deve risultare in "Non categorizzato").
     */	
	@Test
	void testLogicaSetter() {
		
		try {
			
			Articolo a = new Articolo("Pane", Categoria.PANETTERIA, 1.0, "Integrale");
			
			// prova categoria --> null
			a.setCategoria(null);
			assertEquals("Non categorizzato", a.getCategoria(), "Dovrebbe uscire --> Non categorizzato!");
			
			// test Nota
			a.setNota("Nuova nota");
			assertEquals("Nuova nota", a.getNota());
			
			// test Nota --> null
			a.setNota(" ");
			assertEquals(" ", a.getNota(), "Dovrebbe uscire --> stringa vuota!");
			
			// test assegnazione reparto
			a.setCategoria(null);
			assertEquals(Categoria.ALTRO, a.getCategoria(), "Dovrebbe uscire --> Nessuno!");
		
		} catch(ArticoloException e) {
			
			fail("Errore imprevisto nei setter!");
		}
	}
}
