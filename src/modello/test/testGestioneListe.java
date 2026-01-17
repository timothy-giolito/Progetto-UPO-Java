package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.eccezioni.ArticoloException;
import modello.eccezioni.GestioneListeException;

/**
 * Test unitari per la classe statica {@link modello.GestioneListe}.
 */
class testGestioneListe {

    /**
     * Resetta lo stato delle liste, categorie e articoli prima di ogni test.
     * Importante: ripristina la categoria di default necessaria al sistema.
     */
    @BeforeEach
    void resetState() {
        GestioneListe.getListeArticoli().clear();
        GestioneListe.getCategorie().clear();
        GestioneListe.getArticoli().clear();
        try { GestioneListe.inserisciCategoria("Non categorizzato"); } catch (Exception e) {}
    }

    @Test
    void testCreaListaSuccesso() {
        try {
            GestioneListe.creaLista("Spesa Settimanale");
            assertTrue(GestioneListe.getListeArticoli().containsKey("Spesa Settimanale"));
            assertNotNull(GestioneListe.getListeArticoli().get("Spesa Settimanale"));
        } catch (GestioneListeException e) {
            fail("Non doveva lanciare eccezione per una creazione valida: " + e.getMessage());
        }
    }

    @Test
    void testCreaListaErrori() {
        // Caso: Nome null
        GestioneListeException e1 = assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista(null));
        assertEquals("Il nome della lista non può essere vuoto.", e1.getMessage()); //

        // Caso: Nome vuoto
        GestioneListeException e2 = assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista("   "));
        assertEquals("Il nome della lista non può essere vuoto.", e2.getMessage()); //

        // Caso: Duplicato
        try {
            GestioneListe.creaLista("Lista Doppia");
            GestioneListeException e3 = assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista("Lista Doppia"));
            // Messaggio dinamico: "La lista '" + nome + "' esiste già."
            assertEquals("La lista 'Lista Doppia' esiste già.", e3.getMessage()); //
        } catch (GestioneListeException e) {
            fail("Errore setup duplicati");
        }
    }

    @Test
    void testCancellaLista() {
        try {
            GestioneListe.creaLista("Da Cancellare");
            assertTrue(GestioneListe.getListeArticoli().containsKey("Da Cancellare"));

            GestioneListe.cancellaLista("Da Cancellare");
            assertFalse(GestioneListe.getListeArticoli().containsKey("Da Cancellare"));

        } catch (GestioneListeException e) {
            fail("Errore durante cancellazione lista: " + e.getMessage());
        }
    }
    
    @Test
    void testInserisciCategoria() {
        try {
            GestioneListe.inserisciCategoria("Elettronica");
            
            // Test duplicato
            GestioneListeException e = assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria("Elettronica"));
            assertEquals("La categoria 'Elettronica' esiste già.", e.getMessage()); //
            
        } catch (GestioneListeException e) {
            fail("Errore inserimento categoria");
        }
    }

    @Test
    void testCancellaCategoria() {
        try {
            // Test cancellazione inesistente
            GestioneListeException e1 = assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaCategoria("Inesistente"));
            assertEquals("Categoria 'Inesistente' non trovata.", e1.getMessage()); //
            
            // Test cancellazione default
            GestioneListeException e2 = assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaCategoria("Non categorizzato"));
            assertEquals("Non puoi cancellare la categoria di default.", e2.getMessage()); //
                
        } catch (Exception e) {
            fail("Errore test cancellazione");
        }
    }
    
    @Test
    void testCategoriaNonValida() {
        GestioneListeException e1 = assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(null));
        assertEquals("La categoria non può essere vuota.", e1.getMessage()); //

        GestioneListeException e2 = assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(""));
        assertEquals("La categoria non può essere vuota.", e2.getMessage()); //
    }

    @Test
    void testInserisciECancellaArticolo() {
        try {
            Articolo art = new Articolo("Coca Cola", "Bevande", 1.50, "");

            GestioneListe.inserisciArticolo(art);
            assertTrue(GestioneListe.getArticoli().contains(art));
            assertEquals(1, GestioneListe.getArticoli().size());

            GestioneListe.cancellaArticolo(art);
            assertFalse(GestioneListe.getArticoli().contains(art));
            assertEquals(0, GestioneListe.getArticoli().size());

        } catch (Exception e) {
            fail("Errore imprevisto test articoli: " + e.getMessage());
        }
    }

    @Test
    void testArticoliNonAlimentari() {
        try {
            // Test 1: Articolo di Elettronica
            Articolo mouse = new Articolo("Mouse Wireless", "Elettronica", 25.99, "Batterie incluse");
            
            assertEquals("Mouse Wireless", mouse.getNome());
            assertEquals("Elettronica", mouse.getCategoria());

            // Test 2: Articolo Fai-da-te
            Articolo trapano = new Articolo("Trapano a percussione", "Fai_da_te", 89.90, "Garanzia 2 anni");
            assertEquals("Fai_da_te", trapano.getCategoria());
            assertTrue(trapano.toString().contains("Trapano"));
            
        } catch (ArticoloException e) {
            fail("Errore nella creazione di articoli non alimentari: " + e.getMessage());
        }
    }
    
    @Test
    void testListaProgettoRistrutturazione() {
        try {
            ListaDiArticoli listaBrico = new ListaDiArticoli("Ristrutturazione Bagno");
            
            Articolo piastrelle = new Articolo("Piastrelle Marmo", "Piastrelle", 40.0, "Mq");
            Articolo rubinetto = new Articolo("Rubinetto Miscelatore", "Idraulica", 75.50, "Acciaio inox");
            Articolo vernice = new Articolo("Vernice Bianca", "Vernici", 15.0, "Antimuffa");
            
            listaBrico.AggiungiArticolo(piastrelle);
            listaBrico.AggiungiArticolo(rubinetto);
            listaBrico.AggiungiArticolo(vernice);
            
            assertEquals(3, listaBrico.getNumeroArticoli());
            assertTrue(listaBrico.getArticoli().contains(rubinetto));
            
            listaBrico.RimuoviArticolo(piastrelle);
            assertEquals(2, listaBrico.getNumeroArticoli());
            
        } catch (Exception e) {
            fail("Errore nella gestione di una lista bricolage");
        }
    }
}