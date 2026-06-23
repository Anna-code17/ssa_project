## DOCUMENTO DI SYSTEM TEST

Questo documento riporta i risultati della validazione degli Acceptance Criteria 
definiti per le User Story del progetto City Simulator.

Per ciascuna User Story vengono elencati i relativi Acceptance Criteria e l'esito 
della loro verifica (OK/KO), insieme alla data di validazione ed eventuali commenti.

---
## ELENCO USER STORIES:
---
### SCRUM-42 - Reset Simulation (Task)

**User Story:**

As a User I want to reset the current simulation, so that I can start a new city simulation without restarting the application or manually removing all existing buildings and data.

**Acceptance Criteria:**

1. The city grid must be cleared.
2. All entities must be removed.
3. All metrics must be reset to default values.
4. The active policy must be reset (or reselected).

**Esito:** OK

**Data:** 8/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-41 - Interact Through UI

**User Story:**  
As a user, I want to manage the city through a graphical interface, so that the simulation is easier to control.

**Acceptance Criteria:**

1. The system must provide a graphical interface to interact with the city simulation.
2. The interface must allow the user to: place and remove entities; advance the simulation by one tick.
3. The interface must display the current city grid and metrics.

**Esito:** OK

**Data:** 14/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-40 - City Metrics

**User Story:**  
As a user, I want to monitor city metrics such as population, pollution, happiness, and budget, so that I can evaluate the health of the city.

**Acceptance Criteria:**

1. Given the simulation is running, when the city state is displayed, then the system must show population, pollution, happiness and budget.
2. Given a simulation tick occurs, all city metrics must be updated.
3. Displayed metrics must always reflect the latest simulation state.

**Esito:** OK

**Data:** 8/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-38 - Entity State

**User Story:**  
As a user, I want to create and remove city entities, so that I can control the evolution of the simulation.

**Acceptance Criteria:**

1. Given a valid empty grid cell, when the user places an entity, then the entity is created in that cell.
2. Given an occupied cell, when the user tries to place another entity, then the operation is rejected.
3. Given an occupied cell, when the user removes an entity, then the entity must be removed and the cell must become empty.

**Esito:** OK

**Data:** 5/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-37 - System Check

**User Story:**  
As a user, I want the system to prevent invalid placements so that the city obeys the simulation rules.

**Acceptance Criteria:**

1. Entities can only be placed on empty cells within grid boundaries.
2. Placement must satisfy dependency rules.
3. Invalid placements must not modify the city state.

**Esito:** OK

**Data:** 5/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-36 - Save and Load

**User Story:**  
As a user, I want to be able to save the city state so that I can load it later and continue the simulation.

**Acceptance Criteria:**

1. Saving the grid configuration, entities, active policy and city metrics to a file.
2. Loading a file must restore exactly the previously saved state.
3. After loading, the simulation must continue correctly.

**Esito:** OK

**Data:** 15/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-35 - City Policies

**User Story:**  
As a user, I want to be able to activate different city policies (environmentally mindful or industrial oriented) so that I can choose how to evolve the city.

**Acceptance Criteria:**

1. Metrics must be calculated according to the active policy.
2. The system must provide at least: Environmental Policy and Industrial Expansion Policy.
3. The user can select the active policy.
4. Tick processing must not contain policy-specific logic directly.

**Esito:** OK

**Data:** 5/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-34 - Random Events (Optional)

**User Story:**  
As a user, I want random events such as fires, heatwaves, economic crashes to occur so that the simulation is more realistic.

**Acceptance Criteria:**

1. Each tick has a chance of generating a random event.
2. Supported events: Fire, Heatwave, Economic Crash.
3. Fire may destroy entities on a random cell.
4. Heatwave doubles energy consumption.
5. Economic Crash reduces happiness and budget by 40%.
6. The user receives a notification.

**Esito:** KO

**Data:** -

**Commento:** Questa user story non è stata implementata

---

### SCRUM-31 - Infrastructures

**User Story:**  
As a user, I want to build infrastructures such as roads, parks and power plants so that I can improve the city.

**Acceptance Criteria:**

1. Infrastructures decrease budget.
2. Parks increase happiness.
3. Power plants increase pollution.
4. Infrastructure can only be placed on empty cells.
5. Placement updates the city grid.
6. Statistics are updated after placement.
7. Construction is blocked if budget would become negative.

**Esito:** PARZIALE

**Data:** 6/06

**Commento:**
- Gli Acceptance Criteria 1, 2, 3, 4, 5 sono stati implementati.
- L'Acceptance Criteria 6 è stato implementato in modo diverso: solo il costo di costruzione dell'entity (buildCost) viene applicato al momento della costruzione, mentre le altre metriche vengono applicate al momento del tick. Quindi l'effettivo aggiornamento delle statistiche è conseguenza del tick e non del piazzamento.
- L'Acceptance Criteria 7 non è stato implementato.

---

### SCRUM-29 - Types of Buildings

**User Story:**  
As a user, I want to create residential, commercial and industrial buildings so that I can develop the city.

**Acceptance Criteria:**

1. Industrial buildings increase budget and pollution.
2. Residential buildings increase population and require a nearby power plant.
3. Commercial buildings decrease budget and increase happiness.
4. Buildings can only be placed on empty cells.
5. Placement updates the city grid.
6. Statistics are updated after placement.

**Esito:** PARZIALE

**Data:** 6/06

**Commento:**
- Gli Acceptance Criteria 1, 2, 4, 5 sono stati implementati.
- L'Acceptance Criteria 3 è stato implementato in modo diverso: i Commercial Buildings non diminuiscono ma aumentano il budget della città.
- L'Acceptance Criteria 6 è stato implementato in modo diverso: solo il costo di costruzione dell'entity (buildCost) viene applicato al momento della costruzione, mentre le altre metriche vengono applicate al momento del tick. Quindi l'effettivo aggiornamento delle statistiche è conseguenza del tick e non del piazzamento.

---

### SCRUM-27 - Simulation Rules

**User Story:**  
As a user, I want city entities to follow logic-based placement and dependency rules so that the city behaves realistically.

**Acceptance Criteria:**

1. Entities can only be placed on empty cells.
2. Some entities depend on other entities.
3. Entity effects may depend on nearby entities.

**Esito:** PARZIALE

**Data:** 5/06

**Commento:**
- Gli Acceptance Criteria 1 e 2 sono stati implementati.
- L'Acceptance Criteria 3 non è stato implementato: nel nostro progetto l'effetto generato da un'entità è indipendente da quello delle altre.

---

### SCRUM-25 - Advance the Simulation

**User Story:**  
As a user, I want to advance the simulation by one tick so that I can progress the city over time.

**Acceptance Criteria:**

1. Given the simulation is active, when a tick is executed, then the city progresses by one time unit.
2. Metrics are recalculated and updated.
3. All entities on the grid are processed.
4. The city state remains consistent after the tick.

**Esito:** OK

**Data:** 8/06

**Commento:** Tutti gli acceptance criteria sono stati implementati

---

### SCRUM-24 - City State

**User Story:**  
As a user, I want every entity placed in the city to affect the city state so that the buildings have consequences on the simulation.

**Acceptance Criteria:**

1. Entities affect one or more city metrics.
2. Effects are calculated according to the active policy.
3. Effects may depend on surrounding entities.

**Esito:** PARZIALE

**Data:** 7/06

**Commento:**
- Gli Acceptance Criteria 1 e 2 sono stati implementati.
- L'Acceptance Criteria 3 non è stato implementato: nel nostro progetto l'effetto generato da un'entità è indipendente da quello delle altre.

---

### SCRUM-22 - City Grid

**User Story:**  
As a user, I want to see the city grid so that I can understand the layout of the city.

**Acceptance Criteria:**

1. The city must be displayed as a grid.
2. Occupied cells must be distinguishable from empty cells.
3. Selecting a cell containing an entity must show its current state or values.

**Esito:** OK

**Data:** 5/06

**Commento:** Tutti gli acceptance criteria sono stati implementati
