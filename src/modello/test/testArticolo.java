package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.Reparto;
import modello.eccezioni.ArticoloException;

class testArticolo {

	@Test
	void testCostruttore() {
		
		try {
			
			Articolo mela = new Articolo("Mela", "Frutta", 1.50, "Mela rossa", Reparto.ORTOFRUTTA);
			
			// verifica correttezza dati
			assertEquals("Mela", mela.getNome(), "Il nome dovrebbe essere Mela");
			assertEquals("Frutta", mela.getCategoria(), "La categoria dovrebbe essere Frutta");
            assertEquals(1.50, mela.getPrezzo(), "Il prezzo dovrebbe essere 1.50");
            assertEquals("Mela rossa", mela.getNota(), "La nota non corrisponde");
            
            // verifica correttezza reparto
            assertEquals(Reparto.ORTOFRUTTA, mela.getReparto(), "Il reparto non corrisponde!");
		
		} catch(ArticoloException e) {
			
			fail("No eccezione per articolo non valido!");
		} 
	}
	
	@Test
	void testNomeNonValido() {
		
		// reparto a caso per il test
		Reparto r = Reparto.REPARTO_CUCINA;
		
		// Caso 1: nome nullo
		assertThrows(ArticoloException.class, () -> {
            new Articolo(null, "Cat", 1.0, "Nota", r);
        }, "Doveva lanciare eccezione per nome null");
		
		// Caso 2: Nome vuoto -> Deve lanciare ArticoloException
        assertThrows(ArticoloException.class, () -> {
            new Articolo("", "Cat", 1.0, "Nota", r);
        }, "Doveva lanciare eccezione per nome vuoto");
        
        // Caso 3: Nome solo spazi -> Deve lanciare ArticoloException
        assertThrows(ArticoloException.class, () -> {
            new Articolo("   ", "Cat", 1.0, "Nota", r);
        }, "Doveva lanciare eccezione per nome di soli spazi");
	}
	
	@Test
	void testValoriDefault() {
		
		try {
            // Passiamo null per categoria, nota, reparto e un prezzo negativo
            Articolo articoloDefault = new Articolo("Pane", null, -5.0, null, null);
            
            assertEquals("Pane", articoloDefault.getNome());
            
            // Verifiche dei default
            assertEquals("Non categorizzato", articoloDefault.getCategoria());
            assertEquals(0.0, articoloDefault.getPrezzo());
            assertEquals("", articoloDefault.getNota());
            
            // Verifica fondamentale: il default deve essere Reparto.NESSUNO
            assertEquals(Reparto.NESSUNO, articoloDefault.getReparto());
            
        } catch (ArticoloException e) {
            fail("Errore inatteso durante il test dei default: " + e.getMessage());
        }
	}
	
	@Test
    void testSetters() {
        try {
            Articolo a = new Articolo("Acqua", "Bibite", 0.50, "", Reparto.REPARTO_BEVANDE);
            
            // Cambio il prezzo
            a.setPrezzo(0.80);
            assertEquals(0.80, a.getPrezzo());
            
            // Testiamo il cambio di Reparto
            a.setReparto(Reparto.REPARTO_SURGELATI);
            assertEquals(Reparto.REPARTO_SURGELATI, a.getReparto());
            
            // dovrebbe mettere NESSUNO
            a.setReparto(null);
            assertEquals(Reparto.NESSUNO, a.getReparto());

        } catch (ArticoloException e) {
            fail("Errore durante il test dei setter");
        }
    }
	
	@Test
    void testToString() {
        try {
            Articolo a = new Articolo("Martello", "Brico", 15.0, "Manico legno", Reparto.REPARTO_UTENSILI);
            
            String descrizione = a.toString();
            
            assertTrue(descrizione.contains("Martello"));
            assertNotNull(descrizione); 
            
        } catch (ArticoloException e) {
            fail("Errore");
        }
    }
}
