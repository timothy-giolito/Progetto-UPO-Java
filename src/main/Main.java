package main;

import java.util.Scanner;
import interfaccia.cli.RigaDiComando;
import interfaccia.grafica.InterfacciaGrafica;

/**
 * Classe principale (Entry Point) dell'applicazione "Progetto UPO Java".
 * <p>
 * Questa classe gestisce l'avvio del programma permettendo all'utente di scegliere
 * la modalità di interazione desiderata:
 * <ul>
 * <li><b>Interfaccia a Riga di Comando (CLI):</b> per un utilizzo testuale nel terminale.</li>
 * <li><b>Interfaccia Grafica (GUI):</b> per un utilizzo tramite finestre e pulsanti.</li>
 * </ul>
 * </p>
 * @author Timothy Giolito 20054431
 * @author Luca Franzon 20054744
 * @version 1.0
 */

public class Main {

	/**
     * Metodo principale che avvia l'esecuzione del programma.
     * <p>
     * Richiede all'utente di selezionare la modalità di avvio tramite input da tastiera
     * e istanzia la classe di interfaccia corrispondente ({@link RigaDiComando} o {@link InterfacciaGrafica}).
     * </p>
     * @param args Argomenti da riga di comando (non utilizzati in questa versione).
     */
    public static void main(String[] args) {

        // Scelta dell'interfaccia utente
        Scanner scanner = new Scanner(System.in);
        int scelta = -1;

        while (scelta != 1 && scelta != 2 && scelta != 3) {
        	
            System.out.println("Seleziona la modalità di avvio:\n");
            System.out.println("1. Interfaccia a Riga di Comando (CLI)");
            System.out.println("2. Interfaccia Grafica (GUI)");
            System.out.println("3. Esci\n");
            System.out.print("Scelta: ");

            try {
            	
                String input = scanner.nextLine();
                scelta = Integer.parseInt(input);
                
            } catch (NumberFormatException e) {
            	
                System.out.println("Inserire un numero valido.");
            }

            if (scelta == 1) {
                System.out.println("Avvio interfaccia testuale...");
                
                // Istanza per CLI
                new RigaDiComando().start(); 
                
            } else if (scelta == 2) {
                System.out.println("Avvio interfaccia grafica...");
                
                // Istanza per GUI
                javax.swing.SwingUtilities.invokeLater(() -> {
                    new InterfacciaGrafica(); 
                });
                
            } else if (scelta == 3) {
            	
                System.out.println("Uscita dal programma.");
                
            } else {
            	
                System.out.println("Opzione non valida. Riprovare con un numero tra 1 e 3.\n");
            }
        }
        
        scanner.close();
    }
}