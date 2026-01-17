package modello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modello.eccezioni.GestioneListeException;

/**
 * Gestisce centralmente le liste della spesa, le categorie e l'elenco globale degli articoli.
 * <p>
 * Questa classe funge da database in memoria per l'applicazione, utilizzando campi statici
 * per mantenere lo stato condiviso. Fornisce funzionalità per creare e rimuovere liste,
 * gestire le categorie disponibili e amministrare il catalogo generale degli articoli.
 * </p>
 * @author Timothy Giolito 20054431
 */

public class GestioneListe {

    // Campo statico: associazione fra il nome di una lista e l'oggetto ListaDiArticoli
    private static Map<String, ListaDiArticoli> listeArticoli = new HashMap<>();
    
    // Lista dinamica di stringhe
    private static List<String> categorie = new ArrayList<>(); 
    private static List<Articolo> articoli = new ArrayList<>();
    
    // Blocco statico per inizializzare alcune categorie di default all'avvio
    static {
        categorie.add("Non categorizzato"); // Default fondamentale
        categorie.add("Ortofrutta");
        categorie.add("Macelleria");
        categorie.add("Elettronica");
        categorie.add("Fai da te");
    }

    // METODI PER LE LISTE

    /**
     * Crea una nuova lista della spesa e la memorizza nel sistema.
     * @param nome Il nome univoco della nuova lista.
     * @throws GestioneListeException Se il nome è vuoto o se esiste già una lista con lo stesso nome.
     */
    
    public static void creaLista(String nome) throws GestioneListeException {
    	
        if (nome == null || nome.trim().isEmpty()) {
        	
            throw new GestioneListeException("Il nome della lista non può essere vuoto.");
        }
        
        if (listeArticoli.containsKey(nome)) {
        	
            throw new GestioneListeException("La lista '" + nome + "' esiste già.");
        }
        
        try {
        	
            listeArticoli.put(nome, new ListaDiArticoli(nome));
            
        } catch (Exception e) {
        	
            throw new GestioneListeException("Errore nella creazione della lista: " + e.getMessage());
        }
    }

    
    /**
     * Rimuove una lista dal sistema di gestione delle liste.
     * @param nome: Il nome della lista da eliminare.
     * @throws GestioneListeException Se la gestione delle liste non contiene il nome
     * della lista che si vuole eliminare.
     */
    
    public static void cancellaLista(String nome) throws GestioneListeException {
    	
        if (!listeArticoli.containsKey(nome)) {
        	
            throw new GestioneListeException("Lista '" + nome + "' non trovata.");
        }
        
        listeArticoli.remove(nome);
    }

    // METODI PER LE CATEGORIE
    
    /**
     * Inserisce una nuova categoria nel sistema.
     * @param categoria Il nome della categoria da aggiungere.
     * @throws GestioneListeException Se la categoria è vuota o esiste già.
     */

    public static void inserisciCategoria(String categoria) throws GestioneListeException {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new GestioneListeException("La categoria non può essere vuota.");
        }
        // Controllo case-insensitive per evitare duplicati come "Frutta" e "frutta"
        for (String c : categorie) {
            if (c.equalsIgnoreCase(categoria)) {
                throw new GestioneListeException("La categoria '" + categoria + "' esiste già.");
            }
        }
        categorie.add(categoria);
    }

    /**
     * Rimuove la categoria che va a definire un determinato oggetto all'interno della lista.
     * @param articolo L'oggetto {@link Categoria} da registrare.
     * @throws GestioneListeException Se la categoria non esite.
     */
    public static void cancellaCategoria(String categoria) throws GestioneListeException {
        if (!categorie.contains(categoria)) {
            throw new GestioneListeException("Categoria '" + categoria + "' non trovata.");
        }
        // Impediamo di cancellare il default
        if (categoria.equals("Non categorizzato")) {
            throw new GestioneListeException("Non puoi cancellare la categoria di default.");
        }
        categorie.remove(categoria);
    }

    // METODI PER GLI ARTICOLI

    /**
     * Registra un nuovo articolo nell'elenco globale degli articoli disponibili.
     * @param articolo L'oggetto {@link Articolo} da registrare.
     * @throws GestioneListeException Se l'articolo è null o è già presente nell'elenco.
     */
    
    public static void inserisciArticolo(Articolo articolo) throws GestioneListeException {
    	
        if (articolo == null) {
        	
            throw new GestioneListeException("L'articolo non può essere nullo.");
        }
        
        if (articoli.contains(articolo)) {
        	
            throw new GestioneListeException("L'articolo è già presente nell'elenco generale.");
        }
        
        articoli.add(articolo);
    }

    /**
     * Rimuove un articolo dall'elenco globale degli articoli disponibili.
     * @param articolo L'oggetto {@link Articolo} da rimuovere.
     * @throws GestioneListeException Se l'articolo è null o non è presente nell'elenco.
     */
    
    public static void cancellaArticolo(Articolo articolo) throws GestioneListeException {
    	
        if (!articoli.contains(articolo)) {
        	
            throw new GestioneListeException("Articolo non trovato nell'elenco generale.");
        }
        
        articoli.remove(articolo);
    }

    // GETTER opzionali da usare solo per l'interfaccia

    public static Map<String, ListaDiArticoli> getListeArticoli() {
    	
        return listeArticoli;
    }

    public static List<String> getCategorie() {
    	
        return categorie;
    }

    public static List<Articolo> getArticoli() {
    	
        return articoli;
    }
}