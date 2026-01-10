package interfaccia.cli;

import java.util.Scanner;
import java.util.Map;
import java.util.List;

import modello.Articolo;
import modello.GestioneListe;
import modello.ListaDiArticoli;
import modello.Reparto;
import modello.eccezioni.ArticoloException;
import modello.eccezioni.GestioneListeException;
import modello.eccezioni.ListaDiArticoliException;

/**
 * Gestione interfaccia utente da terminale.
 * Include gestione Liste, Catalogo Globale e Cestino.
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 
 */
public class RigaDiComando {

    private Scanner scanner;

    public RigaDiComando(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        int scelta = -1;
        System.out.println("Benvenuto in Progetto UPO Java - CLI");

        while (scelta != 0) {
            mostraMenuPrincipale();
            scelta = leggiIntero("Inserisci la tua scelta: ");

            switch (scelta) {
                case 1: menuGestioneListe(); break;
                case 2: menuCatalogoArticoli(); break;
                case 3: menuGestioneCategorie(); break;
                case 0: System.out.println("Arrivederci!"); break;
                default: System.out.println("Scelta non valida.");
            }
        }
    }

    private void mostraMenuPrincipale() {
        System.out.println("\n--- MENU PRINCIPALE ---");
        System.out.println("1. Gestione Liste della Spesa");
        System.out.println("2. Gestione Catalogo Globale Articoli");
        System.out.println("3. Gestione Categorie");
        System.out.println("0. Esci");
    }

    // --- SEZIONE LISTE ---

    private void menuGestioneListe() {
        int scelta = -1;
        while (scelta != 0) {
            System.out.println("\n--- GESTIONE LISTE ---");
            System.out.println("1. Crea nuova lista");
            System.out.println("2. Visualizza tutte le liste");
            System.out.println("3. Apri/Gestisci una lista specifica");
            System.out.println("4. Elimina una lista");
            System.out.println("0. Torna indietro");

            scelta = leggiIntero("Scelta: ");

            try {
                switch (scelta) {
                    case 1:
                        String nomeNuova = leggiStringa("Nome nuova lista: ");
                        GestioneListe.creaLista(nomeNuova);
                        System.out.println("Lista creata.");
                        break;
                    case 2:
                        stampaElencoListe();
                        break;
                    case 3:
                        gestisciListaSpecifica();
                        break;
                    case 4:
                        String nomeElimina = leggiStringa("Nome lista da eliminare: ");
                        GestioneListe.cancellaLista(nomeElimina);
                        System.out.println("Lista eliminata.");
                        break;
                    case 0: break;
                    default: System.out.println("Opzione non valida.");
                }
            } catch (GestioneListeException e) {
                System.out.println("ERRORE: " + e.getMessage());
            }
        }
    }

    private void gestisciListaSpecifica() {
        stampaElencoListe();
        String nomeLista = leggiStringa("Inserisci il nome della lista da aprire: ");
        
        Map<String, ListaDiArticoli> mappa = GestioneListe.getListeArticoli();
        if (!mappa.containsKey(nomeLista)) {
            System.out.println("Lista non trovata.");
            return;
        }

        ListaDiArticoli lista = mappa.get(nomeLista);
        int scelta = -1;

        while(scelta != 0) {
            System.out.println("\n--- LISTA: " + lista.getListaNome() + " ---");
            System.out.println("TOTALE: " + String.format("%.2f", lista.getPrezzoTotale()) + " EUR");
            System.out.println("1. Visualizza articoli DA COMPRARE");
            System.out.println("2. Aggiungi nuovo articolo (manuale)");
            System.out.println("3. Rimuovi articolo (sposta nel cestino)");
            System.out.println("4. Visualizza CESTINO");
            System.out.println("5. Ripristina articolo dal cestino");
            System.out.println("6. Svuota cestino");
            System.out.println("7. Cerca articolo (Smart Search)");
            System.out.println("8. Aggiungi articolo DA CATALOGO GLOBALE (Veloce)");
            System.out.println("0. Torna al menu liste");
            
            scelta = leggiIntero("Scelta: ");
            
            try {
                switch(scelta) {
                    case 1:
                        if(lista.getNumeroArticoli() == 0) System.out.println("Lista vuota.");
                        else lista.getArticoli().forEach(System.out::println);
                        break;
                    case 2:
                        Articolo nuovo = creaArticoloInterattivo();
                        if(nuovo != null) {
                            lista.AggiungiArticolo(nuovo);
                            System.out.println("Aggiunto.");
                        }
                        break;
                    case 3:
                        String nomeDel = leggiStringa("Nome articolo da togliere: ");
                        Articolo aDel = trovaArticolo(lista.getArticoli(), nomeDel);
                        if(aDel != null) {
                            lista.RimuoviArticolo(aDel);
                            System.out.println("Spostato nel cestino.");
                        } else System.out.println("Non trovato.");
                        break;
                    case 4:
                        if(lista.getCancellati().isEmpty()) System.out.println("Cestino vuoto.");
                        else {
                        	
                        	// Mostriamo il totale del cestino
                            System.out.println("--- CESTINO (Totale: " + String.format("%.2f", lista.getPrezzoTotaleCestino()) + "EUR) ---");
                            lista.getCancellati().forEach(System.out::println);
                        }
                        break;
                    case 5:
                        String nomeRip = leggiStringa("Nome articolo da ripristinare: ");
                        Articolo aRip = trovaArticolo(lista.getCancellati(), nomeRip);
                        if(aRip != null && lista.RipristinaArticolo(aRip)) {
                            System.out.println("Ripristinato.");
                        } else System.out.println("Non trovato nel cestino.");
                        break;
                    case 6:
                        lista.SvuotaCestino();
                        System.out.println("Cestino svuotato.");
                        break;
                    case 7:
                    	String q = leggiStringa("Inserisci prefisso: ");
                        
                        List<Articolo> trovati = lista.TrovaArticoliPerPrefisso(q);
                        
                        if (trovati.isEmpty()) {
                            System.out.println("Nessuna corrispondenza.");
                        } else {
                            System.out.println("Trovati " + trovati.size() + " articoli:");
                            for (Articolo a : trovati) {
                                System.out.println("- " + a);
                            }
                        }
                        break;
                    case 8:
                        aggiungiDaCatalogo(lista);
                        break;
                    case 0: break;
                    default: System.out.println("Scelta non valida.");
                }
            } catch (ListaDiArticoliException e) {
                 System.out.println("ERRORE: " + e.getMessage());
            }
        }
    }

    private void aggiungiDaCatalogo(ListaDiArticoli listaDestinazione) {
        List<Articolo> catalogo = GestioneListe.getArticoli(); // Assume getter statico
        if(catalogo == null || catalogo.isEmpty()) {
            System.out.println("Catalogo vuoto. Vai nel menu principale (2) per riempirlo.");
            return;
        }

        System.out.println("\n--- CATALOGO GLOBALE ---");
        for(int i=0; i<catalogo.size(); i++) {
            Articolo a = catalogo.get(i);
            System.out.println((i+1) + ". " + a.getNome() + " (" + a.getPrezzo() + " EUR)");
        }

        int idx = leggiIntero("Scegli numero (0 annulla): ");
        if(idx > 0 && idx <= catalogo.size()) {
            try {
                // Clona l'articolo per permettere modifiche indipendenti (opzionale ma consigliato)
                Articolo originale = catalogo.get(idx - 1);
                Articolo copia = new Articolo(originale.getNome(), originale.getCategoria(), 
                                              originale.getPrezzo(), originale.getNota(), originale.getReparto());
                
                listaDestinazione.AggiungiArticolo(copia);
                System.out.println("Aggiunto: " + copia.getNome());
            } catch (Exception e) {
                System.out.println("Errore inserimento: " + e.getMessage());
            }
        }
    }

    // --- SEZIONE CATALOGO GLOBALE ---

    private void menuCatalogoArticoli() {
        System.out.println("\n--- CATALOGO GLOBALE ---");
        System.out.println("1. Visualizza articoli");
        System.out.println("2. Aggiungi articolo al catalogo");
        System.out.println("3. Rimuovi articolo dal catalogo");
        System.out.println("0. Indietro");
        
        int scelta = leggiIntero("Scelta: ");
        try {
            switch(scelta) {
                case 1:
                    List<Articolo> arts = GestioneListe.getArticoli();
                    if(arts.isEmpty()) System.out.println("Catalogo vuoto.");
                    else arts.forEach(System.out::println);
                    break;
                case 2:
                    Articolo a = creaArticoloInterattivo();
                    if(a != null) {
                        GestioneListe.inserisciArticolo(a);
                        System.out.println("Aggiunto al catalogo.");
                    }
                    break;
                case 3:
                    String n = leggiStringa("Nome da rimuovere: ");
                    Articolo daRimuovere = trovaArticolo(GestioneListe.getArticoli(), n);
                    if(daRimuovere != null) {
                        GestioneListe.cancellaArticolo(daRimuovere);
                        System.out.println("Rimosso.");
                    } else System.out.println("Non trovato.");
                    break;
                case 0: break;
            }
        } catch (Exception e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    // --- SEZIONE CATEGORIE ---
    
    private void menuGestioneCategorie() {
        System.out.println("\n--- CATEGORIE ---");
        System.out.println("Attuali: " + GestioneListe.getCategorie());
        System.out.println("1. Aggiungi");
        System.out.println("2. Rimuovi");
        System.out.println("0. Indietro");
        
        int s = leggiIntero("Scelta: ");
        try {
            if(s==1) GestioneListe.inserisciCategoria(leggiStringa("Nuova categoria: "));
            else if(s==2) GestioneListe.cancellaCategoria(leggiStringa("Categoria da rimuovere: "));
        } catch(Exception e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    // --- UTILITÀ ---

    private Articolo creaArticoloInterattivo() {
        try {
            System.out.println("Nuovo Articolo:");
            String nome = leggiStringa("- Nome: ");
            String cat = leggiStringa("- Categoria: ");
            double pr = leggiDouble("- Prezzo: ");
            String nota = leggiStringa("- Nota: ");
            
            System.out.print("- Reparto (invio per saltare, ? per lista): ");
            String repStr = scanner.nextLine().trim();
            Reparto r = Reparto.NESSUNO;
            
            if(repStr.equals("?")) {
                for(Reparto rep : Reparto.values()) System.out.println(rep);
            } else if(!repStr.isEmpty()) {
                // Logica semplificata per reparto
                for(Reparto rep : Reparto.values()) {
                    if(rep.name().equalsIgnoreCase(repStr)) r = rep;
                }
            }
            
            return new Articolo(nome, cat, pr, nota, r);
        } catch (ArticoloException e) {
            System.out.println("Dati non validi: " + e.getMessage());
            return null;
        }
    }

    private Articolo trovaArticolo(List<Articolo> lista, String nome) {
        for(Articolo a : lista) {
            if(a.getNome().equalsIgnoreCase(nome)) return a;
        }
        return null;
    }

    private void stampaElencoListe() {
        Map<String, ListaDiArticoli> map = GestioneListe.getListeArticoli();
        if(map.isEmpty()) System.out.println("Nessuna lista.");
        else map.forEach((k, v) -> System.out.println("- " + k + " (Tot: " + v.getPrezzoTotale() + " EUR)"));
    }

    private int leggiIntero(String msg) {
        System.out.print(msg);
        try { return Integer.parseInt(scanner.nextLine()); } 
        catch (Exception e) { return -1; }
    }
    
    private double leggiDouble(String msg) {
        System.out.print(msg);
        try { return Double.parseDouble(scanner.nextLine()); } 
        catch (Exception e) { return 0.0; }
    }

    private String leggiStringa(String msg) {
        System.out.print(msg);
        return scanner.nextLine();
    }
}