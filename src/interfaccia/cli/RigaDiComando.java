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
 * Gestisce l'interazione con l'utente tramite terminale (Command Line Interface).
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 20054744
 */
public class RigaDiComando {

    private Scanner scanner;

    public RigaDiComando() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Avvia il ciclo principale dell'interfaccia testuale.
     */
    public void start() {
        int scelta = -1;

        System.out.println("Benvenuto in Progetto UPO Java - CLI");

        while (scelta != 0) {
            mostraMenuPrincipale();
            scelta = leggiIntero("Inserisci la tua scelta: ");

            switch (scelta) {
                case 1:
                    menuGestioneListe();
                    break;
                case 2:
                    menuCatalogoArticoli();
                    break;
                case 3:
                    menuGestioneCategorie();
                    break;
                case 0:
                    System.out.println("Arrivederci!");
                    break;
                default:
                    System.out.println("Scelta non valida.");
            }
        }
    }

    // --- MENU PRINCIPALI ---

    private void mostraMenuPrincipale() {
        System.out.println("\n--- MENU PRINCIPALE ---");
        System.out.println("1. Gestione Liste della Spesa");
        System.out.println("2. Gestione Catalogo Globale Articoli");
        System.out.println("3. Gestione Categorie");
        System.out.println("0. Esci");
    }

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
                        System.out.println("Lista creata con successo.");
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
                    case 0:
                        break;
                    default:
                        System.out.println("Opzione non valida.");
                }
            } catch (GestioneListeException e) {
                System.out.println("ERRORE: " + e.getMessage());
            }
        }
    }

    private void menuCatalogoArticoli() {
        System.out.println("\n--- CATALOGO GLOBALE ARTICOLI ---");
        System.out.println("1. Visualizza tutti gli articoli globali");
        System.out.println("2. Aggiungi nuovo articolo al catalogo");
        System.out.println("3. Rimuovi articolo dal catalogo");
        System.out.println("0. Indietro");
        
        int scelta = leggiIntero("Scelta: ");
        
        try {
            switch(scelta) {
                case 1:
                    List<Articolo> articoli = GestioneListe.getArticoli();
                    if(articoli.isEmpty()) System.out.println("Nessun articolo nel catalogo.");
                    else articoli.forEach(System.out::println);
                    break;
                case 2:
                    Articolo nuovo = creaArticoloInterattivo();
                    if(nuovo != null) {
                        GestioneListe.inserisciArticolo(nuovo);
                        System.out.println("Articolo aggiunto al catalogo.");
                    }
                    break;
                case 3:
                    // Per semplicità rimuoviamo per nome cercando nella lista
                    String nomeArt = leggiStringa("Nome articolo da rimuovere: ");
                    Articolo daRimuovere = trovaArticoloInLista(GestioneListe.getArticoli(), nomeArt);
                    if(daRimuovere != null) {
                        GestioneListe.cancellaArticolo(daRimuovere);
                        System.out.println("Articolo rimosso.");
                    } else {
                        System.out.println("Articolo non trovato.");
                    }
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        } catch (GestioneListeException e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    private void menuGestioneCategorie() {
        System.out.println("\n--- GESTIONE CATEGORIE ---");
        System.out.println("Attuali: " + GestioneListe.getCategorie());
        System.out.println("1. Aggiungi categoria");
        System.out.println("2. Rimuovi categoria");
        System.out.println("0. Indietro");

        int scelta = leggiIntero("Scelta: ");
        try {
            if (scelta == 1) {
                String nuovaCat = leggiStringa("Nuova categoria: ");
                GestioneListe.inserisciCategoria(nuovaCat);
                System.out.println("Categoria inserita.");
            } else if (scelta == 2) {
                String catDel = leggiStringa("Categoria da rimuovere: ");
                GestioneListe.cancellaCategoria(catDel);
                System.out.println("Categoria rimossa.");
            }
        } catch (GestioneListeException e) {
            System.out.println("ERRORE: " + e.getMessage());
        }
    }

    // --- SOTTO-MENU: GESTIONE SINGOLA LISTA ---

    private void gestisciListaSpecifica() {
        stampaElencoListe();
        String nomeLista = leggiStringa("Inserisci il nome della lista da aprire: ");
        
        Map<String, ListaDiArticoli> mappa = GestioneListe.getListeArticoli();
        if (!mappa.containsKey(nomeLista)) {
            System.out.println("Lista non trovata.");
            return;
        }

        ListaDiArticoli listaCorrente = mappa.get(nomeLista);
        
        int scelta = -1;
        while(scelta != 0) {
            System.out.println("\n--- LISTA: " + listaCorrente.getListaNome() + " ---");
            System.out.println("1. Visualizza articoli nella lista");
            System.out.println("2. Aggiungi articolo (crea nuovo)");
            System.out.println("3. Rimuovi articolo");
            System.out.println("0. Torna al menu liste");
            
            scelta = leggiIntero("Scelta: ");
            
            try {
                switch(scelta) {
                    case 1:
                        if(listaCorrente.getNumeroArticoli() == 0) System.out.println("La lista è vuota.");
                        else listaCorrente.getArticoli().forEach(System.out::println);
                        break;
                    case 2:
                        Articolo nuovo = creaArticoloInterattivo();
                        if(nuovo != null) {
                            listaCorrente.AggiungiArticolo(nuovo);
                            System.out.println("Articolo aggiunto alla lista.");
                        }
                        break;
                    case 3:
                        String nomeDaRimuovere = leggiStringa("Nome articolo da togliere: ");
                        Articolo artRemove = trovaArticoloInLista(listaCorrente.getArticoli(), nomeDaRimuovere);
                        if(artRemove != null) {
                            listaCorrente.RimuoviArticolo(artRemove);
                            System.out.println("Articolo rimosso dalla lista.");
                        } else {
                            System.out.println("Articolo non trovato in questa lista.");
                        }
                        break;
                    case 0:
                        break;
                }
            } catch (ListaDiArticoliException e) {
                 System.out.println("ERRORE LISTA: " + e.getMessage());
            }
        }
    }

    // --- METODI DI SUPPORTO ---

    private Articolo creaArticoloInterattivo() {
        try {
            System.out.println("Creazione nuovo articolo:");
            String nome = leggiStringa("- Nome: ");
            String cat = leggiStringa("- Categoria (invio per default): ");
            double prezzo = leggiDouble("- Prezzo: ");
            String nota = leggiStringa("- Nota: ");
            
            // Usiamo il nuovo metodo intelligente per il reparto
            Reparto repartoScelto = selezionaReparto();

            return new Articolo(nome, cat, prezzo, nota, repartoScelto);

        } catch (ArticoloException e) {
            System.out.println("Errore creazione articolo: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gestisce la selezione del reparto in modo intelligente:
     * - Invio vuoto -> NESSUNO
     * - Testo -> Cerca parzialmente nel nome o descrizione
     * - "?" -> Mostra elenco completo
     */
    private Reparto selezionaReparto() {
        System.out.print("- Reparto (scrivi nome es. 'frutta', invio per saltare, o '?' per lista): ");
        String input = scanner.nextLine().trim();

        // Caso 1: Utente preme Invio (nessun reparto)
        if (input.isEmpty()) {
            return Reparto.NESSUNO;
        }

        // Caso 2: Utente chiede la lista completa
        if (input.equals("?")) {
            Reparto[] reparti = Reparto.values();
            for (int i = 0; i < reparti.length; i++) {
                // Stampa la lista numerata
                System.out.println((i + 1) + ". " + reparti[i].getDescrizione());
            }
            int index = leggiIntero("Inserisci numero reparto: ");
            if (index > 0 && index <= reparti.length) {
                return reparti[index - 1];
            } else {
                System.out.println("Numero non valido. Impostato 'Nessuna Corsia'.");
                return Reparto.NESSUNO;
            }
        }

        // Caso 3: Ricerca testuale (es. utente scrive "surgelati")
        for (Reparto r : Reparto.values()) {
            // Controlla se l'input è contenuto nel nome tecnico o nella descrizione (case-insensitive)
            boolean matchDescrizione = r.getDescrizione().toLowerCase().contains(input.toLowerCase());
            boolean matchNome = r.name().replace("_", " ").toLowerCase().contains(input.toLowerCase());
            
            if (matchDescrizione || matchNome) {
                System.out.println("-> Reparto rilevato: " + r.getDescrizione());
                return r;
            }
        }

        System.out.println("Reparto non trovato. Impostato su 'Nessuna Corsia'.");
        return Reparto.NESSUNO;
    }
    
    private void stampaElencoListe() {
        Map<String, ListaDiArticoli> liste = GestioneListe.getListeArticoli();
        if (liste.isEmpty()) {
            System.out.println("Nessuna lista presente.");
        } else {
            System.out.println("Elenco Liste:");
            for (String nome : liste.keySet()) {
                System.out.println("- " + nome + " (" + liste.get(nome).getNumeroArticoli() + " articoli)");
            }
        }
    }

    // Metodo helper per trovare un articolo in una lista dato il nome
    private Articolo trovaArticoloInLista(List<Articolo> lista, String nome) {
        for(Articolo a : lista) {
            if(a.getNome().equalsIgnoreCase(nome)) {
                return a;
            }
        }
        return null;
    }

    private int leggiIntero(String messaggio) {
        System.out.print(messaggio);
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Valore non valido
        }
    }
    
    private double leggiDouble(String messaggio) {
        System.out.print(messaggio);
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        return scanner.nextLine();
    }
}