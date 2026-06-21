## Manuale Utente - City Simulator

---

## Descrizione ad Alto Livello del Progetto

City Simulator è un gioco di strategia in cui il giocatore costruisce e gestisce una città virtuale. 

Il giocatore può posizionare edifici, gestire le risorse disponibili e far crescere la propria città 
attraverso scelte strategiche.

Le principali funzionalità includono:

- Posizionamento di edifici e infrastrutture su una griglia
- Gestione di Budget, Popolazione, Inquinamento e Felicità
- Attivazione di politiche (Tassa Ambientale, Espansione Industriale)
- Simulazione temporale tramite tick
- Salvataggio e caricamento su file JSON
- Interfaccia grafica interattiva
  
---

## Istruzioni per installare e lanciare il Software

### Requisiti

| Requisito | Versione | Note |
|-----------|----------|------|
| **Sistema Operativo** | Windows 10 o 11 | Ambiente testato |
| **Java** | 17 o superiore | Necessario per eseguire il programma |
| **Maven** | 3.8 o superiore | Necessario per compilare ed eseguire il progetto senza utilizzare il file JAR |

---

### Download ed esecuzione del Programma

Per scaricare il programma in formato `.jar`, scaricare il file **CitySimulator.zip**.
Una volta completato il download del file `.zip`, estrarne il contenuto in una posizione a scelta sul dispositivo. Verrà creata una directory contenente il file:

- `city-simulator-1.0-SNAPSHOT.jar` , l'applicazione

Per avviare l'applicazione, aprire il terminale nella cartella contenente `city-simulator-1.0-SNAPSHOT.jar` e digitare:

```bash
java -jar city-simulator-1.0-SNAPSHOT.jar
```
**Compilazione manuale**

Se il file `.jar` non dovesse funzionare, è possibile ricompilarlo da terminale nella cartella `proj` (dove si trova `pom.xml`):

1. Assicurarsi di trovarsi nella cartella proj (quella contenente il file pom.xml)
2. Compilare il progetto:

```bash
mvn clean compile
```
3. Eseguire il programma:

```bash
mvn exec:java
```
## Avvertenze particolari

- Il gioco è stato progettato per essere utilizzato a **schermo intero**. Se la finestra del gioco viene ridotta, questo potrebbe causare problemi di visualizzazione.

- Il software è stato sviluppato e testato esclusivamente su ambienti **Windows 10 e Windows 11**. L'esecuzione su altri sistemi operativi potrebbe causare comportamenti imprevisti o malfunzionamenti.

---

## Interfaccia Principale

### Legenda Simboli

| Simbolo | Entità |
|---------|--------|
| 🌳 | Parco |
| ➖ | Strada |
| 🏡 | Edificio Residenziale |
| 🏪 | Edificio Commerciale |
| 🏭 | Edificio Industriale |
| ☢ | Centrale Elettrica |

---

## Come Giocare

### Costruire

1. Clicca su **"Build"**
2. Scegli un'entità dalla lista
3. Clicca su una cella vuota della griglia
4. Clicca su **"Confirm"**

> **Nota:** Gli edifici residenziali vanno costruiti **vicino a una Centrale Elettrica** (entro 2 celle)

### Rimuovere

1. Clicca su **"Remove"**
2. Clicca su una cella occupata
3. L'entità viene rimossa

### Fare avanzare il Tempo

1. Clicca su **"Advance Tick"**
2. La città si evolve: gli effetti si applicano
3. Le metriche si aggiornano

### Policy

1. Clicca su **"Set policy"**
2. Scegli tra:
   - **Tassa Ambientale** (riduce inquinamento, aumenta felicità)
   - **Espansione Industriale** (aumenta budget, aumenta inquinamento)

### Salvare/Caricare

1. Clicca su **Save** e seleziona il file di destinazione.
2. Clicca su **Load** e seleziona un file precedentemente salvato.

### Resettare

1. Clicca su **"Reset"** 
2. Tutte le entità, le metriche e la policy attiva vengono riportate allo stato iniziale.
  
---

## Principali API Esterne Utilizzate

Il progetto utilizza le seguenti API esterne:

- **Java Swing**, versione Java 17 : Framework per l'interfaccia grafica (GUI). Utilizzato per creare la finestra principale, i pannelli, i bottoni e la griglia interattiva.
- **Java AWT**, versione Java 17 : Libreria per il disegno di componenti grafici e gestione degli eventi.
- **Java I/O**, versione Java 17 : Gestione dell'input/output per il salvataggio e caricamento dei file JSON.
- **Java Reflection**, versione Java 17 : Utilizzato per l'invocazione dinamica di metodi nelle classi UIUtils e PolicyFormatter.
- **Jackson Databind**, versione 2.17.0 : Gestione della serializzazione/deserializzazione JSON.
- **JUnit Jupiter**, versione 5.10.2: Framework per test unitari (solo in fase di sviluppo).

---

## Principali Funzioni Riutilizzate da Librerie Esterne

Il progetto utilizza le funzioni principali:

- **Jackson Databind** versione 2.17.0:
  - `readValue()`
  - `writeValue()`
  - `@JsonAutoDetect`
  - `@JsonTypeInfo`

- **JUnit Jupiter** 5.10.2:
  - `@Test`
  - `assertEquals()`
  - `assertTrue()`
  - `@BeforeEach`
  - `@TempDir`

---

## Strumenti AI Utilizzati

Durante lo sviluppo del progetto è stato utilizzato **ChatGPT** come supporto.
