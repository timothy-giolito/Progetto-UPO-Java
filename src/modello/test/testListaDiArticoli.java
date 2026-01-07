package modello.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import modello.Articolo;
import modello.ListaDiArticoli;
import modello.Reparto;

class testListaDiArticoli {
    
    @Test
    void testAggiungiERimuoviInCestino() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("TestList");
            Articolo a = new Articolo("Latte", "Food", 1.5, "", Reparto.NESSUNO);
            
            lista.AggiungiArticolo(a);
            assertEquals(1, lista.getNumeroArticoli());
            
            // Rimuovendo, deve finire nel cestino
            lista.RimuoviArticolo(a);
            assertEquals(0, lista.getNumeroArticoli());
            assertEquals(1, lista.getNumeroArticoliCancellati());
            assertTrue(lista.getCancellati().contains(a));
            
        } catch (Exception e) { fail(e.getMessage()); }
    }
    
    @Test
    void testRipristino() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("TestList");
            Articolo a = new Articolo("Pane", "Food", 1.0, "", Reparto.NESSUNO);
            
            lista.AggiungiArticolo(a);
            lista.RimuoviArticolo(a);
            
            // Ripristino
            assertTrue(lista.RipristinaArticolo(a));
            assertEquals(1, lista.getNumeroArticoli());
            assertEquals(0, lista.getNumeroArticoliCancellati());
            
        } catch (Exception e) { fail(e.getMessage()); }
    }
    
    @Test
    void testPrezzoTotale() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Spesa");
            lista.AggiungiArticolo(new Articolo("A", "", 10.0, "", Reparto.NESSUNO));
            lista.AggiungiArticolo(new Articolo("B", "", 20.0, "", Reparto.NESSUNO));
            assertEquals(30.0, lista.getPrezzoTotale());
        } catch (Exception e) { fail(e.getMessage()); }
    }
    
    @Test
    void testIterazioneCompleta() {
        try {
            ListaDiArticoli lista = new ListaDiArticoli("Iter");
            Articolo a1 = new Articolo("Vivo", "", 1.0, "", Reparto.NESSUNO);
            Articolo a2 = new Articolo("Morto", "", 1.0, "", Reparto.NESSUNO);
            lista.AggiungiArticolo(a1);
            lista.AggiungiArticolo(a2);
            lista.RimuoviArticolo(a2);
            
            // L'iteratore deve vederne 2 (1 attivo + 1 cestino)
            int count = 0;
            for(Articolo a : lista) count++;
            assertEquals(2, count);
            
        } catch (Exception e) { fail(e.getMessage()); }
    }
}