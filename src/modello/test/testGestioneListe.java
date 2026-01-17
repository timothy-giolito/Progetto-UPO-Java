package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.GestioneListe;
import modello.Categoria;
import modello.eccezioni.*;
import modello.ListaDiArticoli;
import modello.eccezioni.GestioneListeException;

/**
 * Test unitari per la classe statica {@link modello.GestioneListe}.
 * <p>
 * Verifica le operazioni globali di gestione liste, categorie e catalogo articoli,
 * assicurando la coerenza dei dati e la gestione degli errori (es. duplicati).
 * </p>
 * @author Timothy Giolito 20054431
 */

class testGestioneListe {

	/**
     * Resetta lo stato delle liste, categorie e articoli prima di ogni test
     * per garantire un ambiente pulito.
     */
    @BeforeEach
    void resetState() {
        GestioneListe.getListeArticoli().clear();
        GestioneListe.getCategorie().clear();
        GestioneListe.getArticoli().clear();
    }

   
    /**
     * Verifica che una nuova lista venga creata e memorizzata correttamente nella mappa globale.
     */
    @Test
    void testCreaListaSuccesso() {
    	
        try {
        	
            GestioneListe.creaLista("Spesa Settimanale");
            
            // Verifica che la lista sia stata creata e inserita nella mappa
            assertTrue(GestioneListe.getListeArticoli().containsKey("Spesa Settimanale"), 
                "La lista dovrebbe essere presente nella mappa");
            
            assertNotNull(GestioneListe.getListeArticoli().get("Spesa Settimanale"), 
                "L'oggetto ListaDiArticoli non dovrebbe essere null");

        } catch (GestioneListeException e) {
            fail("Non doveva lanciare eccezione per una creazione valida: " + e.getMessage());
        }
    }

    /**
     * Verifica che vengano gestiti correttamente i tentativi di creare liste
     * con nomi non validi o duplicati.
     */
    @Test
    void testCreaListaErrori() {
    	
        // Test 1: Nome null
        assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista(null),
            "Doveva lanciare eccezione per nome null");

        // Test 2: Nome vuoto
        assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista(""),
            "Doveva lanciare eccezione per nome vuoto");

        // Test 3: Lista duplicata
        try {
        	
            GestioneListe.creaLista("Lista Doppia");
            // Riprova a creare la stessa lista
            assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista("Lista Doppia"),
                "Doveva lanciare eccezione per lista già esistente");
            
        } catch (GestioneListeException e) {
        	
            fail("Errore nel setup del test duplicati");
        }
    }

    /**
     * Verifica la corretta cancellazione di una lista esistente.
     */
    @Test
    void testCancellaLista() {
    	
        try {
            GestioneListe.creaLista("Da Cancellare");
            assertTrue(GestioneListe.getListeArticoli().containsKey("Da Cancellare"));

            GestioneListe.cancellaLista("Da Cancellare");
            assertFalse(GestioneListe.getListeArticoli().containsKey("Da Cancellare"), 
                "La lista non dovrebbe più esistere");

        } catch (GestioneListeException e) {
        	
            fail("Errore durante cancellazione lista: " + e.getMessage());
        }
    }
    
    /**
     * Verifica che il tentativo di cancellare una lista inesistente sollevi un'eccezione.
     */
    @Test
    void testCancellaListaInesistente() {
    	
        assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaLista("Fantasma"),
            "Doveva lanciare eccezione se provo a cancellare una lista che non esiste");
    }
    
    /**
     * Verifica l'inserimento di nuove categorie e il blocco dei duplicati.
     */
    @Test
    void testInserisciCategoria() {
    	
        try {
        	
            GestioneListe.inserisciCategoria("Elettronica");
            assertTrue(GestioneListe.getCategorie().contains("Elettronica"));
            
            // Test duplicato
            assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria("Elettronica"),
                "Non dovrebbe permettere categorie duplicate");
            
        } catch (GestioneListeException e) {
        	
            fail("Errore inserimento categoria");
        }
    }

    /**
     * Verifica la rimozione delle categorie e la gestione dell'errore se la categoria non esiste.
     */
    @Test
    void testCancellaCategoria() {
    	
        try {
        	
            GestioneListe.inserisciCategoria("Frutta");
            GestioneListe.cancellaCategoria("Frutta");
            assertFalse(GestioneListe.getCategorie().contains("Frutta"));
            
            // Test cancellazione inesistente
            assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaCategoria("Frutta"),
                "Doveva lanciare eccezione rimuovendo categoria non presente");
                
        } catch (GestioneListeException e) {
        	
            fail("Errore cancellazione categoria");
        }
    }
    
    /**
     * Verifica che categorie nulle o vuote non vengano accettate.
     */
    @Test
    void testCategoriaNonValida() {
    	
        assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(null));
        assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(""));
    }

    /**
     * Verifica il ciclo di vita (inserimento e cancellazione) di un articolo nell'elenco globale.
     */
    @Test
    void testInserisciECancellaArticolo() {
    	
        try {
        	
            // Creazione di un articolo di prova
            Articolo art = new Articolo("Coca Cola", Categoria.BEVANDE, 1.50, "");

            // Inserimento
            GestioneListe.inserisciArticolo(art);
            assertTrue(GestioneListe.getArticoli().contains(art), "L'articolo dovrebbe essere in elenco");
            assertEquals(1, GestioneListe.getArticoli().size());

            // Cancellazione
            GestioneListe.cancellaArticolo(art);
            assertFalse(GestioneListe.getArticoli().contains(art), "L'articolo dovrebbe essere stato rimosso");
            assertEquals(0, GestioneListe.getArticoli().size());

        } catch (Exception e) {
        	
            fail("Errore imprevisto test articoli: " + e.getMessage());
        }
    }

    /**
     * Verifica che non sia possibile inserire articoli duplicati o nulli nell'elenco globale.
     */
    @Test
    void testArticoloDuplicatoONull() {
    	
        try {
        	
            Articolo art = new Articolo("Acqua", Categoria.BEVANDE, 0.50, "");
            GestioneListe.inserisciArticolo(art);

            // Prova a inserire lo stesso oggetto articolo
            assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciArticolo(art),
                "Doveva impedire l'inserimento di un duplicato");

            // Prova a inserire null
            assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciArticolo(null),
                "Doveva impedire l'inserimento di null");

        } catch (Exception e) {
        	
            fail("Errore setup test duplicati");
        }
    }
    
    /**
     * Verifica il comportamento quando si tenta di cancellare un articolo non presente.
     */
    @Test
    void testCancellaArticoloInesistente() {
    	
        try {
        	
            Articolo art = new Articolo("Vino", Categoria.BEVANDE, 10.0, "");
            // Prova a cancellarlo senza averlo mai inserito
            assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaArticolo(art));
            
        } catch (Exception e) {
        	
            fail("Errore creazione articolo test");
        }
    }
    
    /**
     * Test specifico per verificare la creazione di articoli appartenenti a reparti
     * non alimentari (Elettronica, Fai-da-te, Giardinaggio).
     */
    @Test
	void testArticoliNonAlimentari() {
    	
		try {
			
			// Test 1: Articolo di Elettronica
			Articolo mouse = new Articolo("Mouse Wireless",  Categoria.ELETTRONICA, 25.99, "Batterie incluse");
			
			assertEquals("Mouse Wireless", mouse.getNome());
			assertEquals(Categoria.ELETTRONICA, mouse.getCategoria());
			assertEquals("Periferiche PC", mouse.getCategoria());

			// Test 2: Articolo Fai-da-te
			Articolo trapano = new Articolo("Trapano a percussione", Categoria.FAI_DA_TE, 89.90, "Garanzia 2 anni");
			
			assertEquals(Categoria.FAI_DA_TE, trapano.getCategoria());
			assertTrue(trapano.toString().contains("Trapano"));
			
			// Test 3: Articolo Giardinaggio
			Articolo tosaerba = new Articolo("Tosaerba", Categoria.GIARDINAGGIO, 250.00, "");
			assertEquals(250.00, tosaerba.getPrezzo());

		} catch (ArticoloException e) {
			
			fail("Errore nella creazione di articoli non alimentari: " + e.getMessage());
		}
	}
    
    /**
     * Simula uno scenario d'uso reale: creazione di una lista per un progetto di ristrutturazione
     * con articoli di vari reparti (Piastrelle, Idraulica, Vernici).
     */
    @Test
	void testListaProgettoRistrutturazione() {
    	
		try {
			// Creazione lista specifica per un progetto
			ListaDiArticoli listaBrico = new ListaDiArticoli("Ristrutturazione Bagno");
			
			// Creiamo articoli di reparti completamente diversi
			Articolo piastrelle = new Articolo("Piastrelle Marmo",  Categoria.PIASTRELLE, 40.0, "Mq");
			Articolo rubinetto = new Articolo("Rubinetto Miscelatore", Categoria.IDRAULICA, 75.50, "Acciaio inox");
			Articolo vernice = new Articolo("Vernice Bianca", Categoria.VERNICI, 15.0, "Antimuffa");
			
			// Aggiunta alla lista
			listaBrico.AggiungiArticolo(piastrelle);
			listaBrico.AggiungiArticolo(rubinetto);
			listaBrico.AggiungiArticolo(vernice);
			
			// Verifiche
			assertEquals(3, listaBrico.getNumeroArticoli());
			assertEquals("Ristrutturazione Bagno", listaBrico.getListaNome());
			
			// Verificha che contenga un oggetto specifico
			assertTrue(listaBrico.getArticoli().contains(rubinetto));
			
			// Simulazione acquisto
			listaBrico.RimuoviArticolo(piastrelle);
			assertEquals(2, listaBrico.getNumeroArticoli());
			
		} catch (Exception e) {
			
			fail("Errore nella gestione di una lista bricolage");
		}
	}
    
    /**
     * Verifica l'inserimento di categorie eterogenee per confermare la flessibilità del sistema.
     */
    @Test
	void testCategorieDiverse() {
    	
		try {
			
			// Inserimento categorie varie
			GestioneListe.inserisciCategoria("Informatica");
			GestioneListe.inserisciCategoria("Arredamento Interni");
			GestioneListe.inserisciCategoria("Materiale Edile");
			
			assertTrue(GestioneListe.getCategorie().contains("Informatica"));
			assertTrue(GestioneListe.getCategorie().contains("Materiale Edile"));
			
			// Verifica che non ci siano conflitti con categorie simili
			GestioneListe.inserisciCategoria("Arredamento Esterni");
			assertEquals(4, GestioneListe.getCategorie().size()); 
			
		} catch (GestioneListeException e) {
			
			fail("Errore inserimento categorie varie");
		}
	}    
}