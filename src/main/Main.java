package main;

import java.util.Scanner;
import interfaccia.cli.RigaDiComando;
import interfaccia.grafica.InterfacciaGrafica;

public class Main {

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