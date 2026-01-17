package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.eccezioni.ArticoloException;

/**
 * Classe di test JUnit per la verifica del comportamento della classe {@link modello.Articolo}.
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
            
            a.setNome("Latte Parzialmente Scremato");
            assertEquals("Latte Parzialmente Scremato", a.getNome());
            
            assertThrows(ArticoloException.class, () -> a.setNome(null));
            assertThrows(ArticoloException.class, () -> a.setNome(""));
            assertThrows(ArticoloException.class, () -> a.setNome("   "));

        } catch (ArticoloException e) {
            fail("Errore imprevisto durante il setup del testSetNome");
        }
    }
    
    @Test
    void testNomeNonValido() {
        String c = "Cucina";
        
        assertThrows(ArticoloException.class, () -> {
            new Articolo(null, c, 1.0, "Nota");
        }, "Doveva lanciare eccezione per nome null");
        
        assertThrows(ArticoloException.class, () -> {
            new Articolo("", c, 1.0, "Nota");
        }, "Doveva lanciare eccezione per nome vuoto");
        
        assertThrows(ArticoloException.class, () -> {
            new Articolo("   ", c, 1.0, "Nota");
        }, "Doveva lanciare eccezione per nome di soli spazi");
    }
    
    @Test
    void testValoriDefault() {
        try {
            // Passiamo null per categoria, nota e un prezzo negativo
            Articolo articoloDefault = new Articolo("Pane", null, -5.0, null);
            
            assertEquals("Pane", articoloDefault.getNome());
            
            // Verifiche dei default aggiornati (Stringa invece di Enum)
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
            
            // Se setto null, deve tornare al default
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
            
            // prova categoria --> null (Deve diventare "Non categorizzato")
            a.setCategoria(null);
            assertEquals("Altro", a.getCategoria());
            
            // test Nota
            a.setNota("Nuova nota");
            assertEquals("Nuova nota", a.getNota());
            
            a.setNota(" "); // Nota vuota è permessa ma non è null
            assertEquals(" ", a.getNota());
        
        } catch(ArticoloException e) {
            fail("Errore imprevisto nei setter!");
        }
    }
}