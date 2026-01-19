package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

import modello.Articolo;
import modello.ListaDiArticoli;
import modello.eccezioni.ListaDiArticoliException;

/**
 * Test unitari (JUnit) per la classe {@link ListaDiArticoli}.
 * @author Luca Franzon 20054744
 */
class testListaDiArticoli {
    
    @Test
    void testCostruttoreValido() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Spesa Settimanale");
            assertEquals("Spesa Settimanale", lista.getListaNome());
            assertEquals(0, lista.getNumeroArticoli());
            assertEquals(0, lista.getNumeroArticoliCancellati());
        } catch (ListaDiArticoliException e) {
            fail("Eccezione inattesa nel costruttore: " + e.getMessage());
        }
    }

    @Test
    void testAggiungiERimuoviInCestino() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("TestList");
            Articolo a = new Articolo("Latte", "Non categorizzato", 1.5, "");
            
            lista.AggiungiArticolo(a);
            assertEquals(1, lista.getNumeroArticoli());
            
            lista.RimuoviArticolo(a);
            
            assertEquals(0, lista.getNumeroArticoli());
            assertEquals(1, lista.getNumeroArticoliCancellati());
            assertTrue(lista.getCancellati().contains(a));
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }
    
    @Test
    void testRipristino() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("TestList");
            Articolo a = new Articolo("Pane", "Non categorizzato", 1.0, "");
            
            lista.AggiungiArticolo(a);
            lista.RimuoviArticolo(a); 
            
            boolean esito = lista.RipristinaArticolo(a);
            
            assertTrue(esito);
            assertEquals(1, lista.getNumeroArticoli());
            assertEquals(0, lista.getNumeroArticoliCancellati());
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }
    
    @Test
    void testPrezzoTotale() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Spesa");
            lista.AggiungiArticolo(new Articolo("A", "Non categorizzato", 10.0, ""));
            lista.AggiungiArticolo(new Articolo("B", "Non categorizzato", 20.0, ""));
            
            assertEquals(30.0, lista.getPrezzoTotale());
            
            lista.RimuoviArticolo(lista.getArticoli().get(0));
            
            assertEquals(20.0, lista.getPrezzoTotale());
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }
    
    @Test
    void testIterazioneCompleta() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Iter");
            Articolo a1 = new Articolo("Attivo", "Non categorizzato",  1.0, "");
            Articolo a2 = new Articolo("Cancellato", "Non categorizzato", 1.0, "");
            
            lista.AggiungiArticolo(a1);
            lista.AggiungiArticolo(a2);
            lista.RimuoviArticolo(a2); 
            
            int count = 0;
            for(Articolo a : lista) {
                assertNotNull(a);
                count++;
            }
            assertEquals(2, count);
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }

    @Test
    void testRicercaPrefisso() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Search");
            Articolo a1 = new Articolo("Mela Golden", "Frutta", 1.0, "");
            Articolo a2 = new Articolo("Melone", "Frutta", 2.0, "");
            
            lista.AggiungiArticolo(a1);
            lista.AggiungiArticolo(a2);
            lista.RimuoviArticolo(a2);
            
            List<Articolo> trovatoAttivo = lista.TrovaArticoliPerPrefisso("Mela");
            assertEquals(a1, trovatoAttivo.get(0));
            
            List<Articolo> trovatoCancellato = lista.TrovaArticoliPerPrefisso("Melo");
            assertEquals(a2, trovatoCancellato.get(0));
            
            assertTrue(lista.TrovaArticoliPerPrefisso("Pera").isEmpty());
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    void testEccezioniLista() {
        // 1. Costruttore con nome non valido
        ListaDiArticoliException e1 = assertThrows(ListaDiArticoliException.class, () -> new ListaDiArticoli(null));
        assertEquals("Errore, è necessario il nome della lista", e1.getMessage()); 
        
        ListaDiArticoliException e2 = assertThrows(ListaDiArticoliException.class, () -> new ListaDiArticoli(""));
        assertEquals("Errore, è necessario il nome della lista", e2.getMessage()); 

        try {
            ListaDiArticoli lista = new ListaDiArticoli("Mia Lista");
            Articolo a = new Articolo("Latte", "Bevande", 1.0, "");

            // 2. Aggiunta articolo null
            ListaDiArticoliException e3 = assertThrows(ListaDiArticoliException.class, () -> lista.AggiungiArticolo(null));
            assertEquals("Errore, è necessario l'articolo", e3.getMessage()); 

            // 3. Aggiunta articolo duplicato
            lista.AggiungiArticolo(a);
            ListaDiArticoliException e4 = assertThrows(ListaDiArticoliException.class, () -> lista.AggiungiArticolo(a));
            assertEquals("L'articolo è già presente nella lista!", e4.getMessage()); 

        } catch (Exception e) {
            fail("Errore imprevisto durante il test delle eccezioni: " + e.getMessage());
        }
    }
}