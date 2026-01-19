package interfaccia.mvc;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;

import modello.*;
import modello.eccezioni.GestioneListeException;

/**
 * Classe che gestisce le azioni dei vari bottoni della GUI.
 * E' stata utilizzata un'impostazione MVC, in modo da tenere divisi:
 * <ul>
 * <li>Dati (Model)</li>
 * <li>Interfaccia (View)</li>
 * <li>Controllo (Controller)</li>
 * </ul>
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 20054744
 * 
 * */

public class ControllerGUI implements ActionListener, ListSelectionListener {

    private VistaGUI vista;
    
    // Stato dell'applicazione
    private ListaDiArticoli listaCorrente = null;
    private boolean modalitaCatalogo = false;
    private boolean visualizzaCestino = false;

    public ControllerGUI(VistaGUI vista) {
        this.vista = vista;
        
        vista.registraAscoltatori(this, this);
        vista.aggiornaElencoListe(GestioneListe.getListeArticoli().keySet());
        impostaStatoVuoto();
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();
        
        try {
            switch (comando) {
                case "VEDI_CATALOGO":
                    attivaModalitaCatalogo();
                    break;
                case "NUOVA_LISTA":
                    azioneCreaLista();
                    break;
                case "ELIMINA_LISTA":
                    azioneEliminaLista();
                    break;
                case "NUOVA_CATEGORIA":
                    azioneNuovaCategoria();
                    break;
                case "ELIMINA_CATEGORIA": 
                    azioneEliminaCategoria();
                    break;
                case "VEDI_CESTINO":
                    visualizzaCestino = true;
                    aggiornaVista();
                    break;
                case "INDIETRO":
                    visualizzaCestino = false;
                    aggiornaVista();
                    break;
                case "AGGIUNGI":
                    azioneAggiungi();
                    break;
                case "AGGIUNGI_DA_CATALOGO":
                    azioneCopiaDaCatalogo();
                    break;
                case "CERCA":
                    azioneCerca();
                    break;
                case "RIMUOVI":
                    azioneRimuovi();
                    break;
                case "RIPRISTINA":
                    azioneRipristina();
                    break;
                case "SVUOTA_CESTINO":
                    azioneSvuotaCestino();
                    break;
                case "MODIFICA":
                	azioneModifica();
                	break;
            }
        } catch (Exception ex) {
            vista.mostraMessaggioErrore(ex.getMessage());
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String nomeLista = vista.getListaSelezionata();
            if (nomeLista != null) {
                caricaLista(nomeLista);
            }
        }
    }

    // --- LOGICA DI NAVIGAZIONE E AGGIORNAMENTO ---

    private void impostaStatoVuoto() {
        listaCorrente = null;
        modalitaCatalogo = false;
        visualizzaCestino = false;
        
        vista.setTitolo("Benvenuto! Seleziona una lista", Color.BLACK);
        vista.aggiornaTabella(List.of()); 
        vista.configBottoniVuoto();
        vista.nascondiTotale();
    }
    
    private void attivaModalitaCatalogo() {
        modalitaCatalogo = true;
        listaCorrente = null;
        visualizzaCestino = false;
        
        vista.clearSelezioneLista();
        vista.setTitolo("VISUALIZZAZIONE CATALOGO GLOBALE", new Color(0, 100, 0));
        vista.configBottoniPerCatalogo();
        vista.aggiornaTabella(GestioneListe.getArticoli());
        vista.nascondiTotale();
    }
    
    private void caricaLista(String nomeLista) {
        modalitaCatalogo = false;
        listaCorrente = GestioneListe.getListeArticoli().get(nomeLista);
        visualizzaCestino = false; // Reset al default
        aggiornaVista();
    }
    
    private void aggiornaVista() {
        if (modalitaCatalogo) {
            attivaModalitaCatalogo();
            return;
        }
        
        if (listaCorrente == null) {
            impostaStatoVuoto();
            return;
        }

        vista.configBottoniPerLista(visualizzaCestino);
        
        if (visualizzaCestino) {
            vista.setTitolo("Cestino di: " + listaCorrente.getListaNome(), Color.GRAY);
            vista.aggiornaTabella(listaCorrente.getCancellati());
            vista.setTotale(listaCorrente.getPrezzoTotaleCestino());
        } else {
            vista.setTitolo("Lista: " + listaCorrente.getListaNome(), Color.BLACK);
            vista.aggiornaTabella(listaCorrente.getArticoli());
            vista.setTotale(listaCorrente.getPrezzoTotale());
        }
    }

    // --- LOGICA DELLE AZIONI ---

    private void azioneCreaLista() throws GestioneListeException {
        String nome = vista.chiediInput("Nome lista:");
        if (nome != null && !nome.trim().isEmpty()) {
            GestioneListe.creaLista(nome);
            vista.aggiornaElencoListe(GestioneListe.getListeArticoli().keySet());
        }
    }
    
    private void azioneNuovaCategoria() throws GestioneListeException {
        String cat = vista.chiediInput("Inserisci nome nuova categoria:");
        if (cat != null && !cat.trim().isEmpty()) {
            GestioneListe.inserisciCategoria(cat);
            vista.mostraMessaggio("Categoria '" + cat + "' aggiunta con successo!");
        }
    }
    
    // NUOVO METODO PER ELIMINARE CATEGORIE
    private void azioneEliminaCategoria() throws GestioneListeException {
        List<String> cats = GestioneListe.getCategorie();
        Object[] choices = cats.toArray();
        
        // Mostriamo un dropdown per far scegliere all'utente
        String selected = (String) JOptionPane.showInputDialog(
            vista,
            "Scegli la categoria da eliminare:",
            "Elimina Categoria",
            JOptionPane.WARNING_MESSAGE,
            null,
            choices,
            choices[0]);

        if (selected != null) {
            // Se l'utente prova a cancellare "Non categorizzato", GestioneListe lancerà un'eccezione
            // che verrà catturata dal catch nel metodo actionPerformed
            GestioneListe.cancellaCategoria(selected);
            vista.mostraMessaggio("Categoria '" + selected + "' eliminata.");
        }
    }

    private void azioneEliminaLista() throws GestioneListeException {
        String nome = vista.getListaSelezionata();
        if (nome != null) {
            if (vista.chiediConferma("Eliminare " + nome + "?", "Conferma") == JOptionPane.YES_OPTION) {
                GestioneListe.cancellaLista(nome);
                vista.aggiornaElencoListe(GestioneListe.getListeArticoli().keySet());
                impostaStatoVuoto();
            }
        }
    }

    private void azioneAggiungi() throws Exception {
        Object[] dati = vista.chiediDatiArticolo(modalitaCatalogo);
        if (dati == null) return;
        
        String nome = (String) dati[0];        
        
        double prezzo = 0.0;
        try {
            prezzo = Double.parseDouble((String) dati[1]); 
        } catch (NumberFormatException e) { /* default 0 */ }
        
        String nota = (String) dati[2]; 
      
        String categoria = (String) dati[3]; 

        Articolo nuovo = new Articolo(nome, categoria, prezzo, nota);

        if (modalitaCatalogo) {
            GestioneListe.inserisciArticolo(nuovo);
            vista.mostraMessaggio("Aggiunto al Catalogo!");
        } else if (listaCorrente != null) {
            listaCorrente.AggiungiArticolo(nuovo);
        }
        aggiornaVista();
    }
    
    private void azioneRimuovi() throws GestioneListeException {
        int riga = vista.getRigaSelezionata();
        if (riga == -1) {
            vista.mostraMessaggio("Seleziona un articolo.");
            return;
        }

        if (modalitaCatalogo) {
            Articolo a = GestioneListe.getArticoli().get(riga);
            if (vista.chiediConferma("Eliminare definitivamente '" + a.getNome() + "'?", "Elimina") == JOptionPane.YES_OPTION) {
                GestioneListe.cancellaArticolo(a);
            }
        } else if (listaCorrente != null) {
            Articolo a = listaCorrente.getArticoli().get(riga);
            listaCorrente.RimuoviArticolo(a);
        }
        aggiornaVista();
    }

    private void azioneCopiaDaCatalogo() {
        if (listaCorrente == null) return;
        List<Articolo> catalogo = GestioneListe.getArticoli();
        if (catalogo.isEmpty()) {
            vista.mostraMessaggio("Il catalogo è vuoto.");
            return;
        }
        
        Articolo scelto = vista.chiediSelezioneDaCatalogo(catalogo.toArray());
        if (scelto != null) {
            try {
                Articolo copia = new Articolo(scelto.getNome(), scelto.getCategoria(), 
                                              scelto.getPrezzo(), scelto.getNota());
                listaCorrente.AggiungiArticolo(copia);
                aggiornaVista();
            } catch (Exception e) {
                vista.mostraMessaggioErrore(e.getMessage());
            }
        }
    }
    
    private void azioneRipristina() {
        int riga = vista.getRigaSelezionata();
        if (riga != -1 && listaCorrente != null && visualizzaCestino) {
            Articolo a = listaCorrente.getCancellati().get(riga);
            listaCorrente.RipristinaArticolo(a);
            aggiornaVista();
            vista.mostraMessaggio("Articolo ripristinato!");
        }
    }
    
    private void azioneSvuotaCestino() {
        if (listaCorrente != null && vista.chiediConferma("Svuotare il cestino?", "Conferma") == JOptionPane.YES_OPTION) {
            listaCorrente.SvuotaCestino();
            aggiornaVista();
        }
    }
    
    private void azioneCerca() {
        if (listaCorrente == null) {
            vista.mostraMessaggio("Seleziona prima una lista.");
            return;
        }
        String prefisso = vista.chiediInput("Inserisci il prefisso da cercare:");
        if (prefisso != null && !prefisso.trim().isEmpty()) {
            List<Articolo> risultati = listaCorrente.TrovaArticoliPerPrefisso(prefisso);
            if (risultati.isEmpty()) {
                vista.mostraMessaggio("Nessun articolo trovato.");
            } else {
                StringBuilder sb = new StringBuilder("Trovati " + risultati.size() + " articoli:\n");
                for (Articolo a : risultati) sb.append("- ").append(a.getNome()).append("\n");
                vista.mostraMessaggio(sb.toString());
            }
        }
    }
    
    private void azioneModifica() {
        int riga = vista.getRigaSelezionata();
        
        // Verifica che sia selezionato qualcosa e che siamo in una lista valida (o catalogo)
        if (riga == -1) {
            vista.mostraMessaggio("Seleziona un articolo da modificare.");
            return;
        }

        Articolo articoloDaModificare = null;
        
        // Recupera l'articolo corretto in base alla modalità
        if (modalitaCatalogo) {
            articoloDaModificare = GestioneListe.getArticoli().get(riga);
        } else if (listaCorrente != null && !visualizzaCestino) {
            articoloDaModificare = listaCorrente.getArticoli().get(riga);
        } else {
            vista.mostraMessaggio("Non puoi modificare elementi nel cestino o nessuna lista selezionata.");
            return;
        }

        // Chiedi i nuovi dati alla vista
        Object[] nuoviDati = vista.chiediModificaArticolo(articoloDaModificare);

        if (nuoviDati != null) {
            try {
                String nuovaCat = (String) nuoviDati[0];
                String prezzoStr = (String) nuoviDati[1];
                String nuovaNota = (String) nuoviDati[2];

                // Applica le modifiche usando i setter del Modello
                articoloDaModificare.setCategoria(nuovaCat);
                articoloDaModificare.setNota(nuovaNota);
                
                // Gestione parsing prezzo
                try {
                    double p = Double.parseDouble(prezzoStr);
                    articoloDaModificare.setPrezzo(p);
                } catch (NumberFormatException ex) {
                    vista.mostraMessaggioErrore("Prezzo non valido, inserire un numero.");
                    return; // Interrompi se il prezzo è sbagliato
                }

                // Aggiorna la vista per mostrare i cambiamenti
                aggiornaVista();
                vista.mostraMessaggio("Articolo modificato con successo.");

            } catch (Exception e) {
                vista.mostraMessaggioErrore("Errore nella modifica: " + e.getMessage());
            }
        }
    }
}