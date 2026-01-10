package interfaccia.grafica;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

import modello.*;
import modello.eccezioni.*;

/**
 * Gestisce l'interazione con l'utente tramite interfaccia grafica (GUI).
 * 
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 20054744
 */
public class InterfacciaGrafica extends JFrame {
	
	private static final long serialVersionUID = 1L;
	// Componenti Dati
    private DefaultListModel<String> listModelListe;
    private JList<String> jListListe;
    private DefaultTableModel tableModelArticoli;
    private JTable tabellaArticoli;
    private JLabel lblTotale;
    private JLabel lblTitoloVista; // Indica cosa stiamo guardando
    
    // NUOVI COMPONENTI DI NAVIGAZIONE
    private JButton btnVediCestino;
    private JButton btnIndietro;
    
    // Bottoni che cambiano stato
    private JButton btnAggiungi;
    private JButton btnAggiungiCatalogo;
    private JButton btnRimuovi;
    private JButton btnRipristina;
    private JButton btnSvuotaCestino;

    // Stato corrente
    private ListaDiArticoli listaSelezionata = null;
    private boolean modalitaCatalogo = false; // true = stiamo guardando il catalogo globale
    private boolean visualizzaCestino = false; 

    public InterfacciaGrafica() {
        super("Gestore Liste della Spesa - UPO Java");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        inizializzaComponenti();
        aggiornaElencoListe();
        
        // Stato iniziale: nessuna selezione
        impostaStatoVuoto();
        
        setVisible(true);
    }

    private void inizializzaComponenti() {
        // --- PANNELLO SINISTRO (Navigazione Liste) ---
        JPanel pnlSinistra = new JPanel(new BorderLayout(5, 5));
        pnlSinistra.setBorder(BorderFactory.createTitledBorder("Navigazione"));
        pnlSinistra.setPreferredSize(new Dimension(220, 0));

        // Bottone per vedere il Catalogo Globale
        JButton btnVediCatalogo = new JButton("VEDI CATALOGO GLOBALE");
        btnVediCatalogo.setFont(new Font("Arial", Font.BOLD, 12));
        btnVediCatalogo.setBackground(new Color(230, 240, 255));
        btnVediCatalogo.addActionListener(e -> attivaModalitaCatalogo());
        pnlSinistra.add(btnVediCatalogo, BorderLayout.NORTH);

        // Lista delle spese
        listModelListe = new DefaultListModel<>();
        jListListe = new JList<>(listModelListe);
        jListListe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListListe.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && jListListe.getSelectedValue() != null) {
                caricaListaSelezionata();
            }
        });

        pnlSinistra.add(new JScrollPane(jListListe), BorderLayout.CENTER);

        // Bottoni gestione liste (crea/elimina lista)
        JPanel pnlBottoniListe = new JPanel(new GridLayout(2, 1, 5, 5));
        JButton btnNuovaLista = new JButton("Nuova Lista Spesa");
        JButton btnEliminaLista = new JButton("Elimina Lista");

        btnNuovaLista.addActionListener(e -> azioneCreaLista());
        btnEliminaLista.addActionListener(e -> azioneEliminaLista());

        pnlBottoniListe.add(btnNuovaLista);
        pnlBottoniListe.add(btnEliminaLista);
        pnlSinistra.add(pnlBottoniListe, BorderLayout.SOUTH);

        add(pnlSinistra, BorderLayout.WEST);

        // --- PANNELLO CENTRALE (Tabella e Header Navigazione) ---
        JPanel pnlCentro = new JPanel(new BorderLayout());
        
        // Header superiore con Navigazione
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Sotto-pannello per Titolo e Freccia Indietro (Allineato a sinistra)
        JPanel pnlTitoloNav = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        
        btnIndietro = new JButton("Indietro");
        btnIndietro.setFont(new Font("Arial", Font.BOLD, 12));
        btnIndietro.setVisible(false); // Nascosto di default
        btnIndietro.addActionListener(e -> {
            visualizzaCestino = false; // Torna alla lista normale
            aggiornaTabellaArticoli();
        });
        
        lblTitoloVista = new JLabel("Seleziona una lista o il catalogo");
        lblTitoloVista.setFont(new Font("Arial", Font.BOLD, 18));
        
        pnlTitoloNav.add(btnIndietro);
        pnlTitoloNav.add(lblTitoloVista);
        pnlHeader.add(pnlTitoloNav, BorderLayout.WEST);
        
        // Bottone per andare al Cestino (Allineato a destra)
        btnVediCestino = new JButton("Vedi Cestino");
        btnVediCestino.addActionListener(e -> {
            visualizzaCestino = true; // Entra nel cestino
            aggiornaTabellaArticoli();
        });
        pnlHeader.add(btnVediCestino, BorderLayout.EAST);
        
        pnlCentro.add(pnlHeader, BorderLayout.NORTH);

        // Tabella Articoli
        String[] colonne = {"Nome", "Categoria", "Prezzo (€)", "Reparto", "Nota"};
        tableModelArticoli = new DefaultTableModel(colonne, 0) {
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tabellaArticoli = new JTable(tableModelArticoli);
        tabellaArticoli.setRowHeight(25);
        pnlCentro.add(new JScrollPane(tabellaArticoli), BorderLayout.CENTER);

        add(pnlCentro, BorderLayout.CENTER);

        // --- PANNELLO INFERIORE (Azioni contestuali) ---
        JPanel pnlAzioni = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        pnlAzioni.setBorder(BorderFactory.createEtchedBorder());

        btnAggiungi = new JButton("Aggiungi Nuovo");
        btnAggiungiCatalogo = new JButton("Copia da Catalogo");
        btnRimuovi = new JButton("Rimuovi");
        btnRipristina = new JButton("Ripristina"); 
        btnSvuotaCestino = new JButton("Svuota Cestino");
        
        lblTotale = new JLabel("Totale: 0.00 €");
        lblTotale.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotale.setForeground(Color.BLUE);

        // Listeners Azioni
        btnAggiungi.addActionListener(e -> azioneAggiungiGenerica());
        btnAggiungiCatalogo.addActionListener(e -> azioneCopiaDaCatalogo());
        btnRimuovi.addActionListener(e -> {
			try {
				azioneRimuoviGenerica();
			} catch (GestioneListeException e1) {
				e1.printStackTrace();
			}
		});
        btnRipristina.addActionListener(e -> azioneRipristinaArticolo());
        btnSvuotaCestino.addActionListener(e -> azioneSvuotaCestino());

        pnlAzioni.add(btnAggiungi);
        pnlAzioni.add(btnAggiungiCatalogo);
        pnlAzioni.add(Box.createHorizontalStrut(20));
        pnlAzioni.add(btnRimuovi);
        pnlAzioni.add(btnRipristina);
        pnlAzioni.add(btnSvuotaCestino);
        pnlAzioni.add(Box.createHorizontalStrut(20));
        pnlAzioni.add(lblTotale);

        add(pnlAzioni, BorderLayout.SOUTH);
    }

    // --- LOGICA CAMBIO VISTA E STATO ---

    private void impostaStatoVuoto() {
        listaSelezionata = null;
        modalitaCatalogo = false;
        visualizzaCestino = false;
        lblTitoloVista.setText("Benvenuto! Seleziona una lista a sinistra.");
        tableModelArticoli.setRowCount(0);
        aggiornaBottoni();
    }

    private void attivaModalitaCatalogo() {
        jListListe.clearSelection();
        listaSelezionata = null;
        modalitaCatalogo = true;
        visualizzaCestino = false; // Reset
        
        lblTitoloVista.setText("VISUALIZZAZIONE CATALOGO GLOBALE");
        lblTitoloVista.setForeground(new Color(0, 100, 0));
        
        aggiornaTabellaArticoli();
        // Nota: aggiornaBottoni viene chiamato dentro aggiornaTabellaArticoli
    }

    private void caricaListaSelezionata() {
        String nomeLista = jListListe.getSelectedValue();
        if (nomeLista != null) {
            modalitaCatalogo = false;
            listaSelezionata = GestioneListe.getListeArticoli().get(nomeLista);
            visualizzaCestino = false; // Reset: si parte sempre dagli articoli attivi
            
            lblTitoloVista.setText("Lista: " + listaSelezionata.getListaNome());
            lblTitoloVista.setForeground(Color.BLACK);
            
            aggiornaTabellaArticoli();
        }
    }

    /**
     * Gestisce la visibilità e l'attivazione dei pulsanti in base al contesto
     * (Lista Normale vs Cestino vs Catalogo).
     */
    private void aggiornaBottoni() {
        // Default: bottoni visibili
        btnAggiungi.setVisible(true);
        btnAggiungiCatalogo.setVisible(true);
        btnRimuovi.setVisible(true);
        btnRipristina.setVisible(true);
        btnSvuotaCestino.setVisible(true);

        if (modalitaCatalogo) {
            // --- CONTESTO: CATALOGO GLOBALE ---
            btnVediCestino.setVisible(false);
            btnIndietro.setVisible(false);
            
            btnAggiungi.setText("Crea Nuovo Articolo Globale");
            btnAggiungi.setEnabled(true);
            
            btnAggiungiCatalogo.setVisible(false); 
            
            btnRimuovi.setText("Elimina da Catalogo");
            btnRimuovi.setEnabled(true);
            
            btnRipristina.setEnabled(false);
            btnSvuotaCestino.setEnabled(false);
            
            lblTotale.setVisible(false);
            
        } else if (listaSelezionata != null) {
            // --- CONTESTO: LISTA DELLA SPESA ---
            lblTotale.setVisible(true);
            
            if (visualizzaCestino) {
                // --- VISTA CESTINO ---
                btnIndietro.setVisible(true);     // Mostra freccia indietro
                btnVediCestino.setVisible(false); // Nascondi bottone "vai al cestino"
                
                lblTitoloVista.setText("Cestino di: " + listaSelezionata.getListaNome());
                lblTitoloVista.setForeground(Color.GRAY);

                // Azioni permesse nel cestino
                btnAggiungi.setEnabled(false);
                btnAggiungiCatalogo.setEnabled(false);
                btnRimuovi.setEnabled(false); 
                
                btnRipristina.setEnabled(true);    
                btnSvuotaCestino.setEnabled(true); 
                
            } else {
                // --- VISTA NORMALE ---
                btnIndietro.setVisible(false);    // Nascondi freccia
                btnVediCestino.setVisible(true);  // Mostra bottone "vai al cestino"
                
                lblTitoloVista.setText("Lista: " + listaSelezionata.getListaNome());
                lblTitoloVista.setForeground(Color.BLACK);

                // Azioni permesse nella lista
                btnAggiungi.setText("Aggiungi Manuale");
                btnAggiungi.setEnabled(true);
                
                btnAggiungiCatalogo.setText("Aggiungi da Catalogo");
                btnAggiungiCatalogo.setVisible(true);
                btnAggiungiCatalogo.setEnabled(true);
                
                btnRimuovi.setText("Sposta nel Cestino");
                btnRimuovi.setEnabled(true);
                
                btnRipristina.setEnabled(false);   
                btnSvuotaCestino.setEnabled(false); 
            }
        } else {
            // --- NESSUNA SELEZIONE ---
            btnVediCestino.setVisible(false);
            btnIndietro.setVisible(false);
            btnAggiungi.setEnabled(false);
            btnAggiungiCatalogo.setEnabled(false);
            btnRimuovi.setEnabled(false);
            btnRipristina.setEnabled(false);
            btnSvuotaCestino.setEnabled(false);
        }
    }

    private void aggiornaTabellaArticoli() {
        tableModelArticoli.setRowCount(0);
        
        List<Articolo> sorgente = null;
        
        if (modalitaCatalogo) {
            sorgente = GestioneListe.getArticoli();
        } else if (listaSelezionata != null) {
            if (visualizzaCestino) {
                sorgente = listaSelezionata.getCancellati();
                // Mostra totale cestino (richiede l'aggiornamento a ListaDiArticoli fatto prima)
                lblTotale.setText("Totale Cestino: " + String.format("%.2f", listaSelezionata.getPrezzoTotaleCestino()) + " €");
            } else {
                sorgente = listaSelezionata.getArticoli();
                lblTotale.setText("Totale: " + String.format("%.2f", listaSelezionata.getPrezzoTotale()) + " €");
            }
        }

        if (sorgente != null) {
            for (Articolo a : sorgente) {
                Object[] riga = {
                    a.getNome(),
                    a.getCategoria(),
                    String.format("%.2f", a.getPrezzo()),
                    a.getReparto().getDescrizione(),
                    a.getNota()
                };
                tableModelArticoli.addRow(riga);
            }
        }
        // Aggiorna lo stato dei bottoni ogni volta che si ridisegna la tabella
        aggiornaBottoni();
    }

    // --- AZIONI ---

    private void azioneAggiungiGenerica() {
        creaNuovoArticoloDialog(modalitaCatalogo);
    }

    private void azioneRimuoviGenerica() throws GestioneListeException {
        int riga = tabellaArticoli.getSelectedRow();
        if (riga == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona un articolo.");
            return;
        }

        if (modalitaCatalogo) {
            Articolo a = GestioneListe.getArticoli().get(riga);
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Eliminare definitivamente '" + a.getNome() + "' dal catalogo?", 
                "Elimina", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                GestioneListe.cancellaArticolo(a);
                aggiornaTabellaArticoli();
            }
        } else if (listaSelezionata != null) {
            Articolo a = listaSelezionata.getArticoli().get(riga);
            listaSelezionata.RimuoviArticolo(a);
            aggiornaTabellaArticoli();
        }
    }

    private void creaNuovoArticoloDialog(boolean isGlobale) {
        JTextField txtNome = new JTextField();
        JTextField txtCat = new JTextField();
        JTextField txtPrezzo = new JTextField();
        JTextField txtNota = new JTextField();
        JComboBox<Reparto> cmbReparto = new JComboBox<>(Reparto.values());

        String titolo = isGlobale ? "Nuovo Prodotto Globale" : "Nuovo Articolo in Lista";
        
        Object[] msg = {
            "Nome:", txtNome, "Categoria:", txtCat, 
            "Prezzo (€):", txtPrezzo, "Nota:", txtNota, "Reparto:", cmbReparto
        };

        if (JOptionPane.showConfirmDialog(this, msg, titolo, JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                double p = 0;
                try { p = Double.parseDouble(txtPrezzo.getText()); } catch(Exception e){}
                
                Articolo nuovo = new Articolo(
                    txtNome.getText(), txtCat.getText(), p, 
                    txtNota.getText(), (Reparto) cmbReparto.getSelectedItem()
                );
                
                if (isGlobale) {
                    GestioneListe.inserisciArticolo(nuovo);
                    aggiornaTabellaArticoli();
                    JOptionPane.showMessageDialog(this, "Aggiunto al Catalogo!");
                } else {
                    listaSelezionata.AggiungiArticolo(nuovo);
                    aggiornaTabellaArticoli();
                }
            } catch(Exception e) {
                JOptionPane.showMessageDialog(this, "Errore: " + e.getMessage());
            }
        }
    }

    private void azioneCopiaDaCatalogo() {
        if (listaSelezionata == null) return;
        List<Articolo> catalogo = GestioneListe.getArticoli();
        if (catalogo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Il catalogo è vuoto.");
            return;
        }
        
        Object[] scelte = catalogo.toArray();
        Articolo scelto = (Articolo) JOptionPane.showInputDialog(this, 
            "Scegli dal catalogo:", "Catalogo", JOptionPane.PLAIN_MESSAGE, null, scelte, scelte[0]);
            
        if (scelto != null) {
            try {
                Articolo copia = new Articolo(scelto.getNome(), scelto.getCategoria(), 
                                              scelto.getPrezzo(), scelto.getNota(), scelto.getReparto());
                listaSelezionata.AggiungiArticolo(copia);
                aggiornaTabellaArticoli();
            } catch(Exception e) { e.printStackTrace(); }
        }
    }
    
    private void azioneRipristinaArticolo() {
        int riga = tabellaArticoli.getSelectedRow();
        if (riga == -1) {
            JOptionPane.showMessageDialog(this, "Seleziona un articolo da ripristinare.");
            return;
        }
        
        if (listaSelezionata != null && visualizzaCestino) {
            Articolo a = listaSelezionata.getCancellati().get(riga);
            listaSelezionata.RipristinaArticolo(a);
            aggiornaTabellaArticoli();
            JOptionPane.showMessageDialog(this, "Articolo ripristinato!");
        }
    }
    
    private void azioneSvuotaCestino() {
        if (listaSelezionata != null && 
            JOptionPane.showConfirmDialog(this, "Svuotare definitivamente il cestino?", "Conferma", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            listaSelezionata.SvuotaCestino();
            aggiornaTabellaArticoli();
        }
    }

    private void azioneCreaLista() {
        String nome = JOptionPane.showInputDialog(this, "Nome lista:");
        if (nome != null && !nome.trim().isEmpty()) {
            try { GestioneListe.creaLista(nome); aggiornaElencoListe(); } 
            catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
        }
    }

    private void azioneEliminaLista() {
        String nome = jListListe.getSelectedValue();
        if (nome != null && JOptionPane.showConfirmDialog(this, "Eliminare " + nome + "?") == JOptionPane.YES_OPTION) {
            try { 
                GestioneListe.cancellaLista(nome); 
                aggiornaElencoListe(); 
                impostaStatoVuoto();
            } catch (Exception e) { JOptionPane.showMessageDialog(this, e.getMessage()); }
        }
    }

    private void aggiornaElencoListe() {
        listModelListe.clear();
        for(String s : GestioneListe.getListeArticoli().keySet()) listModelListe.addElement(s);
    }
}