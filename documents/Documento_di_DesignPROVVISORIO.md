# Domain Class Model

![domain_class_model.png](img/diagrams/domain_class_model.png)

# Design Class Model

![design_class_model.png](img/diagrams/design_class_model.png)

# Sequence diagrams

## External sequence diagram
```
Questo diagramma mostra le principali interazioni tra l'utente e il sistema.
L'utente può gestire la simulazione attraverso operazioni quali posizionamento e rimozione di entità,
attivazione di policy, avanzamento dei tick, salvataggio, caricamento e reset della città.
```
![external_sequence_diagram.png](img/diagrams/external_sequence_diagram.png)

# Internal Sequence Diagrams

## Create entity

```
Questo diagramma descrive il flusso di creazione di una nuova entità nella griglia.
Il controller delega l'operazione alla città, che verifica la disponibilità della
cella e aggiorna lo stato del sistema in caso di successo oppure notifica un errore
in caso di posizionamento non valido.
```
![create_entity.png](img/diagrams/create_entity.png)

## Activate Policy
```
Questo diagramma rappresenta il processo di attivazione di una policy cittadina.
Dopo la selezione da parte dell'utente, la policy viene impostata come attiva
nella città e l'interfaccia viene aggiornata per riflettere il nuovo comportamento della simulazione.
```
![activate_policy.png](img/diagrams/activate_policy.png)

## Tick
```
Questo diagramma descrive il funzionamento del motore di simulazione.
Ad ogni tick vengono analizzate le entità presenti nella griglia, calcolati
gli effetti complessivi e applicate eventuali modifiche derivanti dalla policy
 attiva prima dell'aggiornamento dello stato della città.
```
![tick.png](img/diagrams/tick.png)

## Save City State
```
Questo diagramma mostra il processo di salvataggio della simulazione.
Lo stato della città viene serializzato in formato JSON e scritto su file;
l'utente riceve una conferma in caso di successo oppure un messaggio di errore
in caso di problemi durante il salvataggio.
```
![save_city_state.png](img/diagrams/save_city_state.png)
