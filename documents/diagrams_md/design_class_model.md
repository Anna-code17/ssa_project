# Design Class Model

![design_class_model.png](../img/diagrams/design_class_model.png)

```plantuml
@startuml

top to bottom direction
skinparam nodesep 10
skinparam ranksep 10

' ======================
' Classi
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
  + setPlacementRule(placementRule: String)
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
  + canPlaceBuilding(entity: Building, grid: CityGrid, x: int, y: int): boolean
  + canPlaceInfrastructure(infrastructure: Infrastructure, grid: CityGrid, x: int, y: int): boolean
}

class TickEngine {
  - applyPolicyPercent(value: int, percent: int): int
  + advanceTick(city: City): void 
}

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

class BuildSession {
  - {static, final} NO_SELECTION : int
  - mode: InteractionMode
  - waitingForCellSelection: boolean
  - selectedX: int
  - selectedY: int
  - selectedType: String
  + BuildSession()
  + clearSelection() : void
  + startBuild(): void
  + startRemove(): void
  + reset(): void
  + isActive(): boolean
  + isBuildMode(): boolean
  + isRemoveMode(): boolean
  + isWaitingForCellSelection(): boolean
  + selectCell(x: int, y: int): void
  + setSelectedType(type: String): void
  + getSelectedType(): String
  + getSelectedX(): int
  + getSelectedY(): int
  + canConfirm(): boolean
  + cancel(): void
}

enum InteractionMode {
  NORMAL
  BUILD
  REMOVE
}

class PolicyFactory {
  + {static} getAvailablePolicies(): List<Policy>
}

class EntityFactory {
  + {static} getAvailableEntityTypes() : List<String>
  + {static} getColor(type : String) : Color
  + {static} create(type: String): PlaceableEntity
}

' ==========================
' Ereditarietà e Relazioni
' =========================

City "1" *-- "1" CityGrid : contains
City "1" *-- "1" CityState : owns
City "0..1" o-- "0..1" Policy : activates

CityGrid "1" *-- "1..*" Cell : composed of
Cell "0..1" -- "0..1" PlaceableEntity : hosts
PlaceableEntity "1" --> "1" Effect : produces


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


City ..> PlacementRules : uses
CityGrid "1" *-- "1..*" Cell : composed of
Cell "0..1" -- "0..1" PlaceableEntity : hosts
PlaceableEntity "1" --> "1" Effect : produces
Controller --> City : manages
Controller o-- TickEngine : uses
Controller --> SaveManager : uses
TickEngine --> City : advances
TickEngine ..> CityGrid : uses
TickEngine ..> CityState : updates
PlacementRules ..> CityGrid : uses
PlacementRules ..> PlaceableEntity : validates
SaveManager --> JsonManager : uses
SaveManager --> City : saves
BuildSession --> InteractionMode
EntityFactory ..> PlaceableEntity : creates
PolicyFactory ..> Policy : creates

@enduml


```
