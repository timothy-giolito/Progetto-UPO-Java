# 🛒 Progetto UPO Java - Gestore Liste della Spesa

**Corso di Programmazione Orientata agli Oggetti - Università del Piemonte Orientale**

Un'applicazione Java completa per la gestione intelligente di liste della spesa, cataloghi di prodotti e categorie merceologiche. Il progetto implementa un'architettura modulare che separa la logica di business (Modello) dall'interazione utente (Interfacce CLI e GUI).

## 👥 Autori
* **Timothy Giolito** (Matricola: 20054431)
* **Luca Franzon** (Matricola: 20054744)

---

## 🚀 Funzionalità Principali

### 1. Gestione Avanzata Liste
* **Creazione e Cancellazione:** Gestione dinamica di multiple liste della spesa (es. "Spesa Settimanale", "Bricolage", "Cena Natale").
* **Modifica Contenuto:** Aggiunta e rimozione di articoli specifici all'interno di ogni lista; Modifica di **Prezzo, Categoria, Nota**
* **Controlli di Integrità:** Prevenzione di duplicati e validazione dei nomi.
* **Agginta/Rimozione categoria:** Permette di gestire in modo dinamico le categorie da attribuire agli articoli da comprare.

### 2. Catalogo Globale Articoli
* Il sistema mantiene un **strutture dati in memoria** per mantenere categorie globali che possono essere riutilizzate in diverse liste.
* Gestione centralizzata per evitare di ricreare ogni volta lo stesso prodotto.

### 3. Articoli Dettagliati
Ogni articolo è un oggetto complesso dotato di:
* **Nome** personalizzabile.
* **Categoria** personalizzabile.
* **Prezzo** (con validazione valori negativi).
* **Note** aggiuntive.

### 4. Interfaccia Utente (Doppia Modalità)
All'avvio (`Main.java`), l'utente può scegliere tra due modalità:
* **🖥️ CLI (Command Line Interface):** Un'interfaccia testuale robusta con menu di navigazione, gestione degli errori e **inserimento intelligente** (ricerca reparti per parola chiave).
* **🖼️ GUI (Graphical User Interface):** Predisposizione per interfaccia Swing (struttura implementata, sviluppo grafico in corso).

---

## 🛠️ Architettura del Progetto

Il progetto segue il pattern architetturale che separa i dati dalla visualizzazione.

### 📦 Package `modello`
Il cuore dell'applicazione. Contiene la logica di business e le strutture dati.
* `GestioneListe`: Classe statica "Controller" che funge da database centrale. Gestisce le `Map` delle liste e le `List` di articoli e categorie.
* `ListaDiArticoli`: Rappresenta una singola lista contenente oggetti `Articolo`.
* `Articolo`: La classe base con override di `equals()` e `hashCode()` per garantire la corretta identificazione degli oggetti.

### 📦 Package `interfaccia`
* `interfaccia.cli.RigaDiComando`: Gestisce l'input/output su terminale. Include funzionalità di "Smart Search" per i reparti e menu nidificati.
* `interfaccia.mvc.VistaGUI e interfaccia.mvc.ControllerGUI`: Classi separate secondo pattern MVC per GUI.

### 3. Modello
Il modello (package `modello`) rimane indipendente dall'interfaccia grafica, venendo manipolato esclusivamente attraverso il Controller.

### 📦 Package `modello.eccezioni`
Gestione robusta degli errori tramite eccezioni personalizzate:
* `ArticoloException`: Errori di validazione dati prodotto.
* `ListaDiArticoliException`: Errori specifici di una lista.
* `GestioneListeException`: Errori globali (es. duplicati nel database).

### 📦 Package `modello.test`
Suite di test unitari sviluppata con **JUnit 5** per garantire la stabilità del codice:
* Copertura test per costruttori, setter/getter e logica di business.
* Test di regressione per le eccezioni e i casi limite (valori null, stringhe vuote, duplicati).
  
---

### 👁️ Interfaccia grafica

L'interfaccia grafica dell'applicazione è stata sviluppata seguendo il pattern architetturale **MVC (Model-View-Controller)**. 
Questa scelta progettuale garantisce una netta separazione tra la logica di presentazione, la gestione degli eventi e la logica di business.

La struttura dei package GUI è suddivisa come segue:

### 1. Vista (`interfaccia.vista`)
* **Classe Principale:** `VistaGUI`
* **Responsabilità:** Si occupa esclusivamente della costruzione del layout e della visualizzazione dei dati all'utente.
* **Dettagli Implementativi:**
    * Estende `JFrame` e inizializza tutti i componenti Swing (pannelli, bottoni, tabelle).
    * È **passiva**: non contiene logica di controllo o gestione degli eventi.
    * Espone metodi pubblici (es. `registraAscoltatori`, `aggiornaTabella`, `mostraMessaggio`) che vengono invocati dal Controller per manipolare l'interfaccia.

### 2. Controller (`interfaccia.controller`)
* **Classe Principale:** `ControllerGUI`
* **Responsabilità:** Gestisce l'interazione tra l'utente, la vista e il modello dati.
* **Dettagli Implementativi:**
    * Implementa le interfacce `ActionListener` e `ListSelectionListener` per catturare gli input dell'utente.
    * Mantiene lo stato della sessione (es. lista correntemente selezionata, modalità catalogo).
    * Riceve le notifiche dalla Vista, elabora le richieste invocando i metodi del Modello (`GestioneListe`) e aggiorna la Vista di conseguenza.

---
### 📂 Struttura delle Cartelle
```
Progetto-UPO-Java
│
├── 📂 src
│   ├── 📦 interfaccia
│   │   ├── 📂 cli
│   │   │   └── 📄 RigaDiComando.java       # Gestione input/output su terminale
│   │   └── 📂 mvc
│   │       └── 📄 ControllerGUI.java
|   |       └── 📄 VistaGUI.java
│   │
│   ├── 📦 main
│   │   └── 📄 Main.java                    # Entry point: scelta tra CLI e GUI
│   │
│   └── 📦 modello
│       ├── 📂 eccezioni
│       │   ├── 📄 ArticoloException.java
│       │   ├── 📄 GestioneListeException.java
│       │   └── 📄 ListaDiArticoliException.java
│       │
│       ├── 📂 test
│       │   ├── 📄 testArticolo.java
│       │   ├── 📄 testGestioneListe.java
│       │   └── 📄 testListaDiArticoli.java
│       │
│       ├── 📄 Articolo.java                # Classe base per i prodotti
│       ├── 📄 GestioneListe.java           # Controller statico 
│       ├── 📄 ListaDiArticoli.java         # Oggetto lista della spesa
│
└── 📂 bin                                  # File compilati (.class)
```

## 💻 Requisiti e Installazione

* **Java JDK:** Versione 21 o superiore.
* **IDE Consigliato:** Eclipse o IntelliJ IDEA.
* **Librerie:** JUnit 5 (per l'esecuzione dei test).

### Come avviare il progetto
1. Clonare la repository.
  
2. Entrare nella cartella di root ed eseguire:
   **javac -d bin -sourcepath src src/main/Main.java**
   
3. Eseguire:
    **java -cp bin main.Main**
   
4. Seguire le istruzioni a schermo per avviare una delle interfacce o uscire.

---

## 🧪 Esempio di Utilizzo (CLI)

```text
Benvenuto in Progetto UPO Java - CLI
1. Gestione Liste della Spesa
2. Gestione Catalogo Globale Articoli
3. Gestione Categorie
...
> Scelta: 1
> Nome nuova lista: Spesa Lunedì
> ...
> Aggiunta articolo: "Pane", Reparto: "panetteria" (Rilevato automaticamente Reparto.PANETTERIA)
