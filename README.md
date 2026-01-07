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
* **Modifica Contenuto:** Aggiunta e rimozione di articoli specifici all'interno di ogni lista.
* **Controlli di Integrità:** Prevenzione di duplicati e validazione dei nomi.

### 2. Catalogo Globale Articoli
* Il sistema mantiene un **database in memoria** di articoli preferiti/globali che possono essere riutilizzati in diverse liste.
* Gestione centralizzata per evitare di ricreare ogni volta lo stesso prodotto.

### 3. Articoli Dettagliati
Ogni articolo è un oggetto complesso dotato di:
* **Nome** univoco.
* **Categoria** personalizzabile.
* **Prezzo** (con validazione valori negativi).
* **Note** aggiuntive.
* **Reparto (Enum):** Classificazione automatica o manuale basata su una vasta enumerazione (`Reparto.java`) che copre dai generi alimentari (Ortofrutta, Macelleria) al non-food (Elettronica, Fai-da-te).

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
* `Reparto`: Enumerazione (`Enum`) che definisce le corsie del supermercato.

### 📦 Package `interfaccia`
* `cli.RigaDiComando`: Gestisce l'input/output su terminale. Include funzionalità di "Smart Search" per i reparti e menu nidificati.
* `grafica.InterfacciaGrafica`: Classe entry point per la futura implementazione Swing.

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

```
### 📂 Struttura delle Cartelle

```text
Progetto-UPO-Java
│
├── 📂 src
│   ├── 📦 interfaccia
│   │   ├── 📂 cli
│   │   │   └── 📄 RigaDiComando.java       # Gestione input/output su terminale
│   │   └── 📂 grafica
│   │       └── 📄 InterfacciaGrafica.java  # Predisposizione per GUI Swing
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
│       ├── 📄 GestioneListe.java           # Controller statico (Database in memoria)
│       ├── 📄 ListaDiArticoli.java         # Oggetto lista della spesa
│       └── 📄 Reparto.java                 # Enum per le corsie (Ortofrutta, ecc.)
│
└── 📂 bin                                  # File compilati (.class)
```

## 💻 Requisiti e Installazione

* **Java JDK:** Versione 21 o superiore.
* **IDE Consigliato:** Eclipse o IntelliJ IDEA.
* **Librerie:** JUnit 5 (per l'esecuzione dei test).

### Come avviare il progetto
1.  Clonare la repository.
2.  Importare il progetto nell'IDE.
3.  Eseguire la classe `main.Main`.
4.  Seguire le istruzioni a schermo per scegliere l'interfaccia desiderata.

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
