package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.GestioneListe;
import modello.Reparto;
import modello.eccezioni.*;
import modello.ListaDiArticoli;
import modello.eccezioni.GestioneListeException;

class testGestioneListe {

    @BeforeEach
    void resetState() {
        GestioneListe.getListeArticoli().clear();
        GestioneListe.getCategorie().clear();
        GestioneListe.getArticoli().clear();
    }

   
    // TEST GESTIONE LISTE (Creazione e Cancellazione Liste della spesa)
    @Test
    void testCreaListaSuccesso() {
    	
        try {
        	
            GestioneListe.creaLista("Spesa Settimanale");
            
            // Verifico che la lista sia stata creata e inserita nella mappa
            assertTrue(GestioneListe.getListeArticoli().containsKey("Spesa Settimanale"), 
                "La lista dovrebbe essere presente nella mappa");
            
            assertNotNull(GestioneListe.getListeArticoli().get("Spesa Settimanale"), 
                "L'oggetto ListaDiArticoli non dovrebbe essere null");

        } catch (GestioneListeException e) {
            fail("Non doveva lanciare eccezione per una creazione valida: " + e.getMessage());
        }
    }

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
            // Riprovo a creare la stessa lista
            assertThrows(GestioneListeException.class, () -> GestioneListe.creaLista("Lista Doppia"),
                "Doveva lanciare eccezione per lista già esistente");
            
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
            assertFalse(GestioneListe.getListeArticoli().containsKey("Da Cancellare"), 
                "La lista non dovrebbe più esistere");

        } catch (GestioneListeException e) {
        	
            fail("Errore durante cancellazione lista: " + e.getMessage());
        }
    }
    
    @Test
    void testCancellaListaInesistente() {
    	
        assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaLista("Fantasma"),
            "Doveva lanciare eccezione se provo a cancellare una lista che non esiste");
    }
    
    // TEST GESTIONE CATEGORIE
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
    
    @Test
    void testCategoriaNonValida() {
    	
        assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(null));
        assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciCategoria(""));
    }

    // TEST GESTIONE ARTICOLI (Elenco globale)
    @Test
    void testInserisciECancellaArticolo() {
    	
        try {
        	
            // Creazione di un articolo di prova
            Articolo art = new Articolo("Coca Cola", "Bibite", 1.50, "", Reparto.REPARTO_BEVANDE);

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

    @Test
    void testArticoloDuplicatoONull() {
    	
        try {
        	
            Articolo art = new Articolo("Acqua", "Bibite", 0.50, "", Reparto.REPARTO_BEVANDE);
            GestioneListe.inserisciArticolo(art);

            // Provo a inserire lo stesso oggetto articolo
            assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciArticolo(art),
                "Doveva impedire l'inserimento di un duplicato");

            // Provo a inserire null
            assertThrows(GestioneListeException.class, () -> GestioneListe.inserisciArticolo(null),
                "Doveva impedire l'inserimento di null");

        } catch (Exception e) {
        	
            fail("Errore setup test duplicati");
        }
    }
    
    @Test
    void testCancellaArticoloInesistente() {
    	
        try {
        	
            Articolo art = new Articolo("Vino", "Alcolici", 10.0, "", Reparto.REPARTO_BEVANDE);
            // Provo a cancellarlo senza averlo mai inserito
            assertThrows(GestioneListeException.class, () -> GestioneListe.cancellaArticolo(art));
            
        } catch (Exception e) {
        	
            fail("Errore creazione articolo test");
        }
    }
    
    @Test
	void testArticoliNonAlimentari() {
    	
		try {
			
			// Test 1: Articolo di Elettronica
			Articolo mouse = new Articolo("Mouse Wireless", "Periferiche PC", 25.99, "Batterie incluse", Reparto.REPARTO_ELETTRONICA);
			
			assertEquals("Mouse Wireless", mouse.getNome());
			assertEquals(Reparto.REPARTO_ELETTRONICA, mouse.getReparto());
			assertEquals("Periferiche PC", mouse.getCategoria());

			// Test 2: Articolo Fai-da-te (Verifica enum complessi)
			Articolo trapano = new Articolo("Trapano a percussione", "Elettroutensili", 89.90, "Garanzia 2 anni", Reparto.REPARTO_FAI_DA_TE);
			
			assertEquals(Reparto.REPARTO_FAI_DA_TE, trapano.getReparto());
			assertTrue(trapano.toString().contains("Trapano"));
			
			// Test 3: Articolo Giardinaggio (Verifica prezzi alti o specifici)
			Articolo tosaerba = new Articolo("Tosaerba", "Macchine agricole", 250.00, "", Reparto.REPARTO_GIARDINAGGIO);
			assertEquals(250.00, tosaerba.getPrezzo());

		} catch (ArticoloException e) {
			
			fail("Errore nella creazione di articoli non alimentari: " + e.getMessage());
		}
	}
    
    @Test
	void testListaProgettoRistrutturazione() {
    	
		try {
			// Creiamo una lista specifica per un progetto
			ListaDiArticoli listaBrico = new ListaDiArticoli("Ristrutturazione Bagno");
			
			// Creiamo articoli di reparti completamente diversi
			Articolo piastrelle = new Articolo("Piastrelle Marmo", "Pavimenti", 40.0, "Mq", Reparto.REPARTO_PIASTRELLE);
			Articolo rubinetto = new Articolo("Rubinetto Miscelatore", "Sanitari", 75.50, "Acciaio inox", Reparto.REPARTO_IDRAULICA);
			Articolo vernice = new Articolo("Vernice Bianca", "Pitture", 15.0, "Antimuffa", Reparto.REPARTO_VERNICI);
			
			// Aggiungiamo tutto alla lista
			listaBrico.AggiungiArticolo(piastrelle);
			listaBrico.AggiungiArticolo(rubinetto);
			listaBrico.AggiungiArticolo(vernice);
			
			// Verifiche
			assertEquals(3, listaBrico.getNumeroArticoli());
			assertEquals("Ristrutturazione Bagno", listaBrico.getListaNome());
			
			// Verifichiamo che contenga un oggetto specifico
			assertTrue(listaBrico.getArticoli().contains(rubinetto));
			
			// Simuliamo l'acquisto (rimozione) delle piastrelle
			listaBrico.RimuoviArticolo(piastrelle);
			assertEquals(2, listaBrico.getNumeroArticoli());
			
		} catch (Exception e) {
			
			fail("Errore nella gestione di una lista bricolage");
		}
	}
    
    @Test
	void testCategorieDiverse() {
    	
		try {
			
			// Inseriamo categorie merceologiche varie
			GestioneListe.inserisciCategoria("Informatica");
			GestioneListe.inserisciCategoria("Arredamento Interni");
			GestioneListe.inserisciCategoria("Materiale Edile");
			
			assertTrue(GestioneListe.getCategorie().contains("Informatica"));
			assertTrue(GestioneListe.getCategorie().contains("Materiale Edile"));
			
			// Verifica che non ci siano conflitti con categorie simili
			GestioneListe.inserisciCategoria("Arredamento Esterni");
			assertEquals(4, GestioneListe.getCategorie().size()); // 3 sopra + 1 nuova
			
		} catch (GestioneListeException e) {
			
			fail("Errore inserimento categorie varie");
		}
	}    
}