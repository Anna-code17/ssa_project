# Design Class Model

![design_class_model.png](../img/diagrams/design_class_model.png)

```plantuml
@startuml

top to bottom direction
skinparam nodesep 10
skinparam ranksep 10

' ======================
' Classi del Dominio
' ======================

class City {
  - MAX_BUDGET_20X20 : int
  - name: String
  - grid: CityGrid
  - state: CityState
  - activePolicy: Policy
  - currentTick: int
  + City(name: String, size: int)
  + City()
  + placeEntity(x: int, y: int, entity: PlaceableEntity): boolean
  + placeBuilding(x: int, y: int, building: Building): boolean
  + placeInfrastructure(x: int, y: int, infrastructure: Infrastructure): boolean
  + removeEntity(x: int, y: int): void
  + getName(): String
  + getGrid(): CityGrid
  + getState(): CityState
  + getActivePolicy(): Policy
  + getMaxBudget(): int
  + incrementTick(): void
  + getCurrentTick(): int
  + setActivePolicy(policy: Policy): void
  + reset(): void
}

class CityGrid {
  - size: int
  - cells: Cell[][]
  - occupiedCount: int
  + CityGrid(size: int)
  + CityGrid()
  + place(x: int, y: int, entity: PlaceableEntity): boolean
  + remove(x: int, y: int): void
  + clearGrid(): void
  + getCell(x: int, y: int): Cell
  + getSize(): int
  + getOccupiedCount(): int
  + isValidPosition(x: int, y: int): boolean
  + isEmpty(x: int, y: int): boolean
  + toString(): String
}

class Cell {
  - x: int
  - y: int
  - entity: PlaceableEntity
  + Cell(x: int, y: int)
  + Cell()
  + isEmpty(): boolean
  + placeEntity(entity: PlaceableEntity): boolean
  + clear(): void
  + getEntity(): PlaceableEntity
  + getX(): int
  + getY(): int
}

class CityState {
  - budget: int
  - population: int
  - pollution: int
  - happiness: int
  - initialBudget: int
  + CityState(initialBudget: int)
  + CityState()
  + applyEffects(effects: Effect): void
  + clear(): void
  + getBudget(): int
  + getPopulation(): int
  + getPollution(): int
  + getHappiness(): int
  + setPopulation(population: int): void
  + setBudget(budget: int): void
  + toString(): String
}

class Effect {
  - budget: int
  - population: int
  - pollution: int
  - happiness: int
  - buildCost: int
  - placementRule: String
  + Effect(budget: int, population: int, pollution: int, happiness: int)
  + Effect(budget: int, population: int, pollution: int, happiness: int, buildCost: int)
  + Effect(budget: int, population: int, pollution: int, happiness: int, buildCost: int, placementRule: String)
  + Effect()
  + getBudget(): int
  + getPopulation(): int
  + getPollution(): int
  + getHappiness(): int
  + getBuildCost(): int
  + getPlacementRule(): String
  + setBudget(budget: int): void
  + setPopulation(population: int): void
  + setPollution(pollution: int): void
  + setHappiness(happiness: int): void
  + setBuildCost(buildCost: int): void
  + setPlacementRule(placementRule: String): void
  + toString(): String
}

abstract class PlaceableEntity {
  # effects: Effect
  + PlaceableEntity()
  + getName(): String
  + getEffects(): Effect
  + getType(): String
  + {abstract} getSymbol(): String
  + setEffects(effects: Effect): void
  + toString(): String
}

abstract class Building {
  + Building()
}

abstract class Infrastructure {
  + Infrastructure()
}

class ResidentialBuilding {
  + ResidentialBuilding()
  + getSymbol(): String
}

class IndustrialBuilding {
  + IndustrialBuilding()
  + getSymbol(): String
}

class CommercialBuilding {
  + CommercialBuilding()
  + getSymbol(): String
}

class Park {
  + Park()
  + getSymbol(): String
}

class Road {
  + Road()
  + getSymbol(): String
}

class PowerPlant {
  + PowerPlant()
  + getSymbol(): String
}

interface Policy {
  + getName(): String
  + getPercentBudget(): int
  + getPercentPopulation(): int
  + getPercentPollution(): int
  + getPercentHappiness(): int
}

class EnvironmentalTaxPolicy {
  + getName(): String
  + getPercentBudget(): int
  + getPercentPollution(): int
  + getPercentHappiness(): int
}

class IndustrialExpansionPolicy {
  + getName(): String
  + getPercentBudget(): int
  + getPercentPollution(): int
  + getPercentHappiness(): int
}

' ======================
' Controller e Motore
' ======================

class Controller {
  - city: City
  - tickEngine: TickEngine
  - SaveLoad: SaveManager
  + Controller(cityName: String, gridSize: int)
  + placeEntity(x: int, y: int, entity: PlaceableEntity): boolean
  + removeEntity(x: int, y: int): void
  + applyPolicy(policy: Policy): void
  + deactivatePolicy(): void
  + nextTick(): void
  + getCurrentTick(): int
  + resetCity(): void
  + getCity(): City
  + getCityState(): CityState
  + getGrid(): CityGrid
  + getActivePolicy(): Policy
  + getCityName(): String
  + saveCity(filepath: String): boolean
  + loadCity(filepath: String): boolean
}

class PlacementRules {
  - hasNearby(grid: CityGrid, x: int, y: int, entityType: Class<? extends PlaceableEntity>, distance: int): boolean
  + canPlaceBuilding(entity: Building, grid: CityGrid, x: int, y: int): boolean
  + canPlaceInfrastructure(infrastructure: Infrastructure, grid: CityGrid, x: int, y: int): boolean
}

class TickEngine {
  + advanceTick(city: City): void
  - applyPolicyPercent(value: int, percent: int): int
}

' ======================
' Persistenza
' ======================

class SaveManager {
  + save(city: City, filepath: String): boolean
  + load(filepath: String): City
}

class JsonManager {
  - mapper: ObjectMapper
  + {static} loadFromResources(path: String, clazz: Class<T>): T
  + {static} save(object: Object, path: String): void
  + {static} load(path: String, clazz: Class<T>): T
}

' ======================
' Relazioni
' ======================

' ---- Domain ----
City "1" *-- "1" CityGrid : contains
City "1" *-- "1" CityState : owns
City "0..1" --> "0..1" Policy : activates

CityGrid "1" *-- "*" Cell : composed of
Cell "0..1" -- "0..1" PlaceableEntity : hosts
PlaceableEntity "1" --> "1" Effect : produces

' ---- Inheritance ----
PlaceableEntity <|-- Building
PlaceableEntity <|-- Infrastructure

Building <|-- ResidentialBuilding
Building <|-- IndustrialBuilding
Building <|-- CommercialBuilding

Infrastructure <|-- PowerPlant
Infrastructure <|-- Park
Infrastructure <|-- Road

Policy <|.. EnvironmentalTaxPolicy
Policy <|.. IndustrialExpansionPolicy

' ---- Controller ----
Controller "1" --> "1" City : manages
Controller --> TickEngine : uses
Controller --> SaveManager : uses

' ---- TickEngine ----
TickEngine --> City : advances
TickEngine ..> CityGrid : uses
TickEngine ..> CityState : updates

' ---- PlacementRules ----
PlacementRules ..> CityGrid : uses
City ..> PlacementRules : uses
PlacementRules ..> Building
PlacementRules ..> Infrastructure

' ---- SaveManager ----
SaveManager --> JsonManager : uses
SaveManager --> City : saves


' ======================
' Note
' ======================

note right of Effect
  Modifiche applicate allo stato della città.
  buildCost applicato una sola volta
  durante il posizionamento.
  placementRule descrive la regola di
  posizionamento dell'entità.
end note

note bottom of Policy
  Le policy modificano il modo in cui
  gli effetti vengono applicati durante un tick.
end note

@enduml

```
 
  
