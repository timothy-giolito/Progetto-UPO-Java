package modello.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.ListaDiArticoli;
import modello.Categoria;
import modello.eccezioni.ListaDiArticoliException;

/**
 * Suite di test unitari (JUnit) per la classe {@link ListaDiArticoli}.
 * <p>
 * Questa classe verifica che tutti i requisiti funzionali relativi alla gestione
 * delle liste della spesa siano soddisfatti, inclusi:
 * <ul>
 * <li>Gestione del "Cestino" (articoli cancellati).</li>
 * <li>Ripristino degli articoli.</li>
 * <li>Calcolo del totale.</li>
 * <li>Iterazione completa su articoli attivi e cancellati.</li>
 * <li>Ricerca "smart" per prefisso.</li>
 * </ul>
 * </p>
 * * @author Luca Franzon 20054744
 * @author Timothy Giolito 20054431
 */
class testListaDiArticoli {
    
    /**
     * Verifica che il costruttore inizializzi correttamente una lista vuota
     * e che il nome sia assegnato correttamente.
     */
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

    /**
     * Verifica il requisito fondamentale del "Cestino".
     * <p>
     * Quando un articolo viene rimosso con {@code RimuoviArticolo}, non deve sparire,
     * ma deve essere spostato nella lista dei cancellati.
     * </p>
     */
    @Test
    void testAggiungiERimuoviInCestino() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("TestList");
            Articolo a = new Articolo("Latte", Categoria.ALTRO, 1.5, "");
            
            // Aggiunta
            lista.AggiungiArticolo(a);
            assertEquals(1, lista.getNumeroArticoli(), "La lista deve contenere 1 articolo");
            
            // Rimozione -> Deve finire nel cestino
            lista.RimuoviArticolo(a);
            
            assertEquals(0, lista.getNumeroArticoli(), "La lista attiva deve essere vuota");
            assertEquals(1, lista.getNumeroArticoliCancellati(), "Il cestino deve contenere 1 articolo");
            assertTrue(lista.getCancellati().contains(a), "L'articolo rimosso deve essere nel cestino");
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }
    
    /**
     * Verifica la funzionalità di ripristino.
     * <p>
     * Un articolo nel cestino deve poter essere riportato nella lista degli
     * articoli da acquistare tramite {@code RipristinaArticolo}.
     * </p>
     */
    @Test
    void testRipristino() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("TestList");
            Articolo a = new Articolo("Pane", Categoria.ALTRO, 1.0, "");
            
            lista.AggiungiArticolo(a);
            lista.RimuoviArticolo(a); // Ora è nel cestino
            
            // Ripristino
            boolean esito = lista.RipristinaArticolo(a);
            
            assertTrue(esito, "Il metodo RipristinaArticolo deve restituire true");
            assertEquals(1, lista.getNumeroArticoli(), "L'articolo deve essere tornato attivo");
            assertEquals(0, lista.getNumeroArticoliCancellati(), "Il cestino deve essere vuoto");
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }
    
    /**
     * Verifica il corretto calcolo del prezzo totale.
     * <p>
     * Il totale deve considerare solo gli articoli attivi (da comprare),
     * ignorando quelli nel cestino.
     * </p>
     */
    @Test
    void testPrezzoTotale() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Spesa");
            lista.AggiungiArticolo(new Articolo("A", Categoria.ALTRO, 10.0, ""));
            lista.AggiungiArticolo(new Articolo("B",  Categoria.ALTRO, 20.0, ""));
            
            assertEquals(30.0, lista.getPrezzoTotale(), "Il totale iniziale deve essere 30.0");
            
            // Rimuoviamo un articolo (prezzo 10.0)
            lista.RimuoviArticolo(lista.getArticoli().get(0));
            
            assertEquals(20.0, lista.getPrezzoTotale(), "Il totale deve aggiornarsi escludendo l'articolo cancellato");
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }
    
    /**
     * Verifica l'implementazione dell'interfaccia {@link Iterable}.
     * <p>
     * Il requisito specifica che l'iteratore deve scorrere sia sugli articoli
     * della lista che su quelli cancellati.
     * </p>
     */
    @Test
    void testIterazioneCompleta() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Iter");
            Articolo a1 = new Articolo("Attivo", Categoria.ALTRO,  1.0, "");
            Articolo a2 = new Articolo("Cancellato", Categoria.ALTRO, 1.0, "");
            
            lista.AggiungiArticolo(a1);
            lista.AggiungiArticolo(a2);
            lista.RimuoviArticolo(a2); // Sposta a2 nel cestino
            
            // L'iteratore deve vederne 2 (1 attivo + 1 cestino)
            int count = 0;
            for(Articolo a : lista) {
                assertNotNull(a);
                count++;
            }
            assertEquals(2, count, "L'iteratore deve contare sia gli articoli attivi che quelli cancellati");
            
        } catch (Exception e) { 
            fail(e.getMessage()); 
        }
    }

    /**
     * Verifica la ricerca per prefisso (Smart Search).
     * <p>
     * Il metodo {@code TrovaArticoloPerPrefisso} deve cercare l'articolo
     * prima nella lista attiva e, se non trovato, nella lista dei cancellati.
     * </p>
     */
    @Test
    void testRicercaPrefisso() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Search");
            Articolo a1 = new Articolo("Mela Golden", Categoria.ALTRO, 1.0, ""); // Attivo
            Articolo a2 = new Articolo("Melone",  Categoria.ALTRO, 2.0, "");      // Attivo poi Cancellato
            
            lista.AggiungiArticolo(a1);
            lista.AggiungiArticolo(a2);
            lista.RimuoviArticolo(a2); // Melone va nel cestino
            
            // Caso 1: Cerca "Mela" -> deve trovare Mela Golden (nella lista attiva)
            List<Articolo> trovatoAttivo = lista.TrovaArticoliPerPrefisso("Mela");
            assertEquals(a1, trovatoAttivo, "Deve trovare l'articolo nella lista attiva");
            
            // Caso 2: Cerca "Melo" -> deve trovare Melone (nel cestino)
            List<Articolo> trovatoCancellato = lista.TrovaArticoliPerPrefisso("Melo");
            assertEquals(a2, trovatoCancellato, "Deve trovare l'articolo anche se è nel cestino");
            
            // Caso 3: Cerca "Pera" -> non esiste
            assertNull(lista.TrovaArticoliPerPrefisso("Pera"), "Non deve trovare articoli inesistenti");
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}