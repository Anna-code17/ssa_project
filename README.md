# ssa_project

## APPUNTI DA MODIFICARE SULLO STATO DEL PROGETTO 

- Finire la GUI
- Revisionare gli acceptance criteria/quello che c'è su jira
- revisionare i codici UML  ✔    ---> se li ricontrollaste sarebbe comunque meglio!
- aggiornare le immagini di tutti i grafici aggiunti ✔
- aggiungere il domain model ✔
- commentare un pochetto di più il codice ✔
- eliminare la ridondanza nelle classi sul livello di accesso di json ✔
- ✔ il javadoc temporaneo è pronto , prima della consegna va aggiornato per sicurezza. L'ho caricato in documenti
- creare doc di system test su gira (I report di unit test dovrebbero essere quelli che sono nella cartella proj/target/surefire-reports)✔
- sono finite le classi cu cui bisognava fare il testing giusto? ✔
- creare un documento sul come poter utilizzare/installare ✔

## DELIVERABLES DA CONSEGNARE:
1. un manuale" →2-3 pagine - github     ✔ --> è in documents in versione pdf, date un'occhiata
2. Definizione di user stories → jira   ✔ ---> ho messo il link a jira in documents
3. un documento di design → 4-5 pagine - github ✔ ---> è pronto, basta mettere insieme i pezzi
4. il codice → github ??? dovremmo aver finito, no?
5. un documento di system test → 1-2 pagine - github ---> ✔  è in documents in versione pdf, date un'occhiata
6. un report di unit test → automatico ✔
   + javadoc che Anna aveva ipotizzato di fare anche se non espressamente richiesto (ottima idea!) ✔ --> sempre in documents, 





# City Simulator - Panoramica della Repository

## Introduzione
Questa repository contiene l’implementazione e la documentazione del progetto **City Simulator**.  
Il sistema è organizzato in due sezioni principali: il progetto software (`proj`) e la documentazione (`documents`).

---

## Struttura della Repository

### proj
Questa directory contiene l’intera implementazione del sistema, strutturata come progetto Maven.

- **Codice sorgente**: `proj/src/main/java`  
  Contiene la logica principale dell’applicazione e l’implementazione del dominio.

- **Codice di test**: `proj/src/test/java`  
  Contiene i test unitari e di integrazione del sistema.

---

### documents
Questa directory contiene tutta la documentazione del progetto, inclusi design, test e materiali utente.

- **Documento di design**  
  [Documento di design](documents/Documento_di_design.md)

- **Manuale utente**  
  [Manuale utente](documents/Manuale_utente.md)

- **Documento di system test**  
  [System test](documents/System_test.md)

- **User stories (Jira)**  
  [User stories](documents/User_stories.md)

- **Report unit test**
  [Report unit test](documents/Report_unit_test)

---


## Note
- La repository segue la struttura standard di un progetto Maven.
- Il progetto può essere compilato tramite Maven con il comando:

```bash
mvn clean package




