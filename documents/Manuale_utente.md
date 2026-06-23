## Manuale Utente - City Simulator
---

> **Benvenuto in City Simulator!** Costruisci la tua città virtuale e guidala verso la prosperità. Costruisci edifici, gestisci risorse e affronta le sfide della crescita urbana.

---

## Indice

- [Descrizione ad alto livello del Progetto](#descrizione-ad-alto-livello-del-progetto)
- [Istruzioni per installare e lanciare il Software](#istruzioni-per-installare-e-lanciare-il-software)
  - [Requisiti](#requisiti)
  - [Download ed esecuzione del Programma](#download-ed-esecuzione-del-programma)
  - [Compilazione manuale](#download-e-compilazione-del-programma)
  - [Avvertenze particolari](#avvertenze-particolari)
- [Interfaccia principale](#interfaccia-principale)
  - [Tipi di entità e loro effetti](#tipi-di-entità-e-loro-effetti)
  - [Regole di posizionamento](#regole-di-posizionamento)
  - [Legenda simboli](#legenda-simboli)
- [Come giocare](#come-giocare)
  - [Costruire](#costruire)
  - [Rimuovere](#rimuovere)
  - [Fare avanzare il tempo](#fare-avanzare-il-tempo)
  - [Policy](#policy)
  - [Salvare/Caricare](#salvarecaricare)
  - [Resettare](#resettare)
- [Principali API esterne utilizzate](#principali-api-esterne-utilizzate)
- [Principali funzioni riutilizzate da librerie esterne](#principali-funzioni-riutilizzate-da-librerie-esterne)
- [Strumenti AI utilizzati](#strumenti-ai-utilizzati)

---

## Descrizione ad alto livello del Progetto

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

Se il file `.jar` non dovesse funzionare, è possibile ricompilare il progetto manualmente. Scaricare da GitHub la cartella `code`, che contiene il codice sorgente dell'applicazione:

1. Aprire il terminale nella cartella `code` (dove si trova il file `pom.xml`) e digitare:
```bash
mvn clean compile
```
2. Per avviare il programma, eseguire:
```bash
mvn exec:java
```
## Avvertenze particolari

- Il gioco è stato progettato per essere utilizzato a **schermo intero**. Se la finestra del gioco viene ridotta, questo potrebbe causare problemi di visualizzazione.

- Il software è stato sviluppato e testato esclusivamente su ambienti **Windows 10 e Windows 11**. L'esecuzione su altri sistemi operativi potrebbe causare comportamenti imprevisti o malfunzionamenti.

---

## Interfaccia principale

### Tipi di entità e loro effetti

Le entità sono di due tipologie: **Edifici** e **Infrastrutture**.
Ogni entità ha un effetto specifico sulle metriche della città. Gli effetti vengono applicati a ogni tick della simulazione, mentre il costo di costruzione viene applicato solo al momento del posizionamento dell'entità.

| Entità | Tipologia  | Budget | Popolazione | Inquinamento | Felicità | Costo di Costruzione |
|--------|-----------|--------|-------------|--------------|----------|---------------------|
| **Parco** | Infrastruttura | -50 | 0 | -15 | +20 | -200 |
| **Strada** | Infrastruttura | -5 | 0 | 0 | +2 | -50 |
| **Centrale Elettrica** | Infrastruttura | -50 | 0 | +15 | -5 | -400 |
| **Residenziale** | Edificio | -10 | +5 | +5 | +8 | -100 |
| **Commerciale** | Edificio | +60 | 0 | +9 | +5 | -200 |
| **Industriale** | Edificio | +110 | 0 | +20 | -10 | -250 |


### Regole di posizionamento
Gli edifici Residenziali devono essere posizionati entro 2 celle da una Centrale Elettrica.
Tutte le altre entità	sono posizionabili ovunque su cella vuota.

### Legenda simboli 

Sulla griglia di gioco, ogni entità è rappresentata da un simbolo e da un colore specifico che ne identificano il tipo e la funzione.

| Simbolo | Entità | Colore | 
|---------|--------|--------|
| 🌳 | **Parco** | Verdino chiaro | 
| ➖ | **Strada** | Grigio chiaro |
| 🏠 | **Edificio Residenziale** | Azzurrino chiaro |
| 🏪 | **Edificio Commerciale** | Arancione chiaro | 
| 🏭 | **Edificio Industriale** | Rosa chiaro| 
| ☢ | **Centrale Elettrica** | Violetto chiaro|

---
## Policy

Le policy sono strumenti strategici che il giocatore può attivare per influenzare le metriche della città. 

Le policy agiscono sulle statistiche della città applicando variazioni percentuali:

- Una **percentuale positiva** produce sempre un **aumento** della statistica su cui si applica.
- Una **percentuale negativa** produce sempre una **diminuzione** della statistica su cui si applica.

**Esempio:** Un Parco produce un -15 sull'inquinamento. Con la policy Tassa Ambientale attiva, che applica un -15% sull'inquinamento, il risultato finale dopo il primo tick sarà una riduzione di 17 punti di inquinamento (il calcolo esatto sarebbe -17.25, ma viene troncato alla parte intera).

**Attenzione agli arrotondamenti:** 
Tutti i calcoli vengono eseguiti utilizzando numeri interi. Quando una policy produce valori con decimali, la parte decimale viene eliminata tramite **troncamento alla parte intera**. 
### Policy disponibili

| Policy | Budget | Inquinamento | Felicità |
|--------|---------|-------------|----------|
| **Tassa Ambientale** | -15%    |     -15%| +10%    | 
| **Espansione Industriale** |+10%    |     +15%| -5%    |

---

## Come giocare

### Costruire

1. Clicca su **"Build"**
2. Scegli un'entità dalla lista
3. Clicca su una cella vuota della griglia
4. Clicca su **"Confirm"**

> **Nota:** Gli edifici residenziali vanno costruiti **vicino a una Centrale Elettrica** (entro 2 celle).

### Rimuovere

1. Clicca su **"Remove"**
2. Clicca su una cella occupata
3. L'entità viene rimossa

> **Nota**: è possibile effettuare il remove **solo se è presente almeno un'entità nella città.** 

### Fare avanzare il tempo

1. Clicca su **"Advance Tick"**
2. La città si evolve: gli effetti si applicano
3. Le metriche si aggiornano

### Policy

1. Clicca su **"Set policy"**
2. Scegli tra:
   - **Tassa Ambientale** (riduce budget, riduce inquinamento, aumenta felicità)
   - **Espansione Industriale** (aumenta budget, aumenta inquinamento, riduce felicità)

> **Nota:** Solo dopo aver scelto una policy, è possibile cliccare su **'View details'** per vedere nel dettaglio come la policy modifica i parametri.

### Salvare/Caricare

1. Clicca su **Save** e seleziona il file di destinazione.
2. Clicca su **Load** e seleziona un file precedentemente salvato.

### Resettare

1. Clicca su **"Reset"** 
2. Tutte le entità, le metriche e la policy attiva vengono riportate allo stato iniziale.
  
---

## Principali API esterne utilizzate

Il progetto utilizza le seguenti API esterne:

- **Java Swing**, versione Java 17 : Framework per l'interfaccia grafica (GUI). Utilizzato per creare la finestra principale, i pannelli, i bottoni e la griglia interattiva.
- **Java AWT**, versione Java 17 : Libreria per il disegno di componenti grafici e gestione degli eventi.
- **Java I/O**, versione Java 17 : Gestione dell'input/output per il salvataggio e caricamento dei file JSON.
- **Jackson Databind**, versione 2.17.0 : Gestione della serializzazione/deserializzazione JSON.
- **JUnit Jupiter**, versione 5.10.2: Framework per test unitari (solo in fase di sviluppo).

---

## Principali funzioni riutilizzate da librerie esterne

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

## Strumenti AI utilizzati

Durante lo sviluppo del progetto è stato utilizzato **ChatGPT** come supporto per attività di revisione del testo, documentazione e verifica del codice. Tutte le decisioni progettuali e implementative sono state prese dagli autori del progetto. 
