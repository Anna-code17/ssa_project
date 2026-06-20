# Domain Class Model

![domain_class_model.png](img/diagrams/domain_class_model.png)

# Design Class Model

![design_class_model.png](img/diagrams/design_class_model.png)

# Sequence diagrams

## External sequence diagram
```
Questo diagramma mostra le principali interazioni tra l'utente e il sistema. L'utente può gestire la simulazione attraverso operazioni quali posizionamento e rimozione di entità, attivazione di policy, avanzamento dei tick, salvataggio, caricamento e reset della città.
```
![external_sequence_diagram.png](img/diagrams/external_sequence_diagram.png)

# Internal Sequence Diagrams

## Create entity

```
Questo diagramma descrive il flusso di creazione di una nuova entità nella griglia. Il controller delega l'operazione alla città, che verifica la disponibilità della cella e aggiorna lo stato del sistema in caso di successo oppure notifica un errore in caso di posizionamento non valido.
```
![create_entity.png](img/diagrams/create_entity.png)

## Activate Policy

![activate_policy.png](img/diagrams/activate_policy.png)

## Tick

![tick.png](img/diagrams/tick.png)

## Save City State

![save_city_state.png](img/diagrams/save_city_state.png)
