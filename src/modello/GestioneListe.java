package modello;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modello.eccezioni.GestioneListeException;

public class GestioneListe {

    // Campo statico: associazione fra il nome di una lista e l'oggetto ListaDiArticoli
    private static Map<String, ListaDiArticoli> listeArticoli = new HashMap<>();

    // Campo statico: lista di tutte le categorie disponibili
    private static List<String> categorie = new ArrayList<>();

    // Campo statico: elenco di tutti gli articoli
    private static List<Articolo> articoli = new ArrayList<>();

    // METODI PER LE LISTE

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

    public static void cancellaLista(String nome) throws GestioneListeException {
    	
        if (!listeArticoli.containsKey(nome)) {
        	
            throw new GestioneListeException("Lista '" + nome + "' non trovata.");
        }
        
        listeArticoli.remove(nome);
    }

    // METODI PER LE CATEGORIE

    public static void inserisciCategoria(String categoria) throws GestioneListeException {
    	
        if (categoria == null || categoria.trim().isEmpty()) {
        	
            throw new GestioneListeException("La categoria non può essere vuota.");
        }
        
        if (categorie.contains(categoria)) {
        	
            throw new GestioneListeException("La categoria '" + categoria + "' esiste già.");
            
        }
        categorie.add(categoria);
    }

    public static void cancellaCategoria(String categoria) throws GestioneListeException {
    	
        if (!categorie.contains(categoria)) {
        	
            throw new GestioneListeException("Categoria '" + categoria + "' non trovata.");
        }
        
        categorie.remove(categoria);
    }

    // METODI PER GLI ARTICOLI

    public static void inserisciArticolo(Articolo articolo) throws GestioneListeException {
    	
        if (articolo == null) {
        	
            throw new GestioneListeException("L'articolo non può essere nullo.");
        }
        
        if (articoli.contains(articolo)) {
        	
            throw new GestioneListeException("L'articolo è già presente nell'elenco generale.");
        }
        
        articoli.add(articolo);
    }

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