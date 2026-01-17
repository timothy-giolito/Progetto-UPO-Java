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
        
        // Ripristiniamo il default essenziale
        try {
            GestioneListe.inserisciCategoria("Non categorizzato");
        } catch (GestioneListeException e) {
            // Ignora se esiste (ma abbiamo appena fatto clear)
        }
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
        assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista(null));
        assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista(""));

        try {
            GestioneListe.creaLista("Lista Doppia");
            assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista("Lista Doppia"));
        } catch (GestioneListeException e) {
            fail("Errore nel setup del test duplicati");
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
            assertTrue(GestioneListe.getCategorie().contains("Elettronica"));
            
            // Test duplicato
            assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria("Elettronica"));
            
        } catch (GestioneListeException e) {
            fail("Errore inserimento categoria");
        }
    }

    @Test
    void testCancellaCategoria() {
        try {
            GestioneListe.inserisciCategoria("Frutta");
            GestioneListe.cancellaCategoria("Frutta");
            assertFalse(GestioneListe.getCategorie().contains("Frutta"));
            
            // Test cancellazione inesistente
            assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaCategoria("Frutta"));
            
            // Test cancellazione default (Non categorizzato)
            assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaCategoria("Non categorizzato"));
                
        } catch (GestioneListeException e) {
            fail("Errore cancellazione categoria: " + e.getMessage());
        }
    }
    
    @Test
    void testCategoriaNonValida() {
        assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(null));
        assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(""));
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