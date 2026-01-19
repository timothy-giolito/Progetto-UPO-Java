package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.eccezioni.ArticoloException;

/**
 * Test per la verifica del comportamento della classe {@link modello.Articolo}.
 * @author Timothy Giolito 20054431
 */
class testArticolo {

    @Test
    void testCostruttore() {
        try {
            Articolo mela = new Articolo("Mela", "Ortofrutta", 1.50, "Mela rossa");
            
            assertEquals("Mela", mela.getNome());
            assertEquals("Ortofrutta", mela.getCategoria());
            assertEquals(1.50, mela.getPrezzo());
            assertEquals("Mela rossa", mela.getNota());
        
        } catch(ArticoloException e) {
            fail("No eccezione per articolo non valido!");
        } 
    }
    
    @Test
    void testSetNome() {
        try {
            Articolo a = new Articolo("Latte", "Bevande", 1.20, "");
            
            // Caso valido
            a.setNome("Latte Scremato");
            assertEquals("Latte Scremato", a.getNome());
            
            // Caso: Nome Null
            ArticoloException e1 = assertThrows(ArticoloException.class, () -> a.setNome(null));
            assertEquals("Il nome non puo' essere vuoto!", e1.getMessage()); //

            // Caso: Nome Vuoto
            ArticoloException e2 = assertThrows(ArticoloException.class, () -> a.setNome(""));
            assertEquals("Il nome non puo' essere vuoto!", e2.getMessage()); //

            // Caso: Spazi
            ArticoloException e3 = assertThrows(ArticoloException.class, () -> a.setNome("   "));
            assertEquals("Il nome non puo' essere vuoto!", e3.getMessage()); //

        } catch (ArticoloException e) {
            fail("Errore imprevisto nel setup");
        }
    }
    
    @Test
    void testNomeNonValido() {
        String c = "Cucina";
        
        // Caso: Costruttore con nome null
        ArticoloException e1 = assertThrows(ArticoloException.class, () -> {
            new Articolo(null, c, 1.0, "Nota");
        });
   
        assertEquals("ERRORE: il nome dell'articolo non può essere non specificato!", e1.getMessage()); //
        
        // Caso: Costruttore con nome vuoto
        ArticoloException e2 = assertThrows(ArticoloException.class, () -> {
            new Articolo("", c, 1.0, "Nota");
        });
        assertEquals("ERRORE: il nome dell'articolo non può essere non specificato!", e2.getMessage()); //
    }
    
    @Test
    void testSetPrezzoNegativo() {
        try {
            Articolo a = new Articolo("Pane", "Altro", 1.0, "");
            
            // Verifica eccezione su setPrezzo
            ArticoloException e = assertThrows(ArticoloException.class, () -> a.setPrezzo(-5.0));
            assertEquals("Il prezzo non puo' essere negativo!", e.getMessage()); //
            
        } catch (ArticoloException e) {
            fail("Errore nel setup");
        }
    }
    
    @Test
    void testValoriDefault() {
        try {
            // Passiamo null per categoria, nota e un prezzo negativo
            Articolo articoloDefault = new Articolo("Pane", null, -5.0, null);
            
            assertEquals("Pane", articoloDefault.getNome());
            
            //Verfiche dei valori di default
            assertEquals("Altro", articoloDefault.getCategoria());
            assertEquals(0.0, articoloDefault.getPrezzo());
            assertEquals("", articoloDefault.getNota());
            
        } catch (ArticoloException e) {
            fail("Errore inatteso durante il test dei default: " + e.getMessage());
        }
    }
    
    @Test
    void testSetters() {
        try {
            Articolo a = new Articolo("Acqua", "Bevande", 0.50, "");
            
            a.setPrezzo(0.80);
            assertEquals(0.80, a.getPrezzo());
            
            a.setCategoria("Surgelati");
            assertEquals("Surgelati", a.getCategoria());
            
            a.setCategoria(null);
            assertEquals("Altro", a.getCategoria());

        } catch (ArticoloException e) {
            fail("Errore durante il test dei setter");
        }
    }
    
    @Test
    void testToString() {
        try {
            Articolo a = new Articolo("Martello", "Utensili", 15.0, "Manico legno");
            String descrizione = a.toString();
            
            assertTrue(descrizione.contains("Martello"));
            assertTrue(descrizione.contains("Utensili"));
            assertNotNull(descrizione); 
            
        } catch (ArticoloException e) {
            fail("Errore");
        }
    }
    
    @Test
    void testLogicaSetter() {
        try {
            Articolo a = new Articolo("Pane", "Panetteria", 1.0, "Integrale");
            
            // prova categoria --> null (deve diventare non categorizzato)
            a.setCategoria(null);
            assertEquals("Altro", a.getCategoria());
            
            // test Nota
            a.setNota("Nuova nota");
            assertEquals("Nuova nota", a.getNota());
            
         // Nota vuota è permessa ma non è null
            a.setNota(" "); 
            assertEquals(" ", a.getNota());
        
        } catch(ArticoloException e) {
            fail("Errore imprevisto nei setter!");
        }
    }
}