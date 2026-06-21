# Sequence diagrams

## External sequence diagram

![external_sequence_diagram.png](../../img/diagrams/external_sequence_diagram.png)

```plantuml

@startuml
actor User
participant "City Simulation System" as System

User -> System : launchApplication()
System --> User : displayUI(cityState, cityGrid)

loop While simulation is running

    opt Place Entity
        User -> System : placeEntity(entityType, position)
        System -> System : validatePlacement()

        alt Placement allowed
            System -> System : addEntityToGrid()
            System --> User : updateGrid()
            System --> User : updateState()

        else Placement not allowed
            System --> User : showError("Invalid placement")
        end
    end

    opt Remove Entity
        User -> System : removeEntity(position)
        System -> System : removeEntityFromGrid()
        System --> User : updateGrid()
        System --> User : updateState()
    end

    opt Activate Policy
        User -> System : activatePolicy(policy)
        System -> System : applyPolicy()
        System --> User : updateUI()
    end

    opt Deactivate Policy
        User -> System : deactivatePolicy()
        System -> System : removePolicy()
        System --> User : updateUI()
    end

    opt Advance Tick
        User -> System : advanceTick()
        System -> System : scanGridAndApplyEffects()
        System -> System : updateCityState()
        System --> User : updateUI()
    end

    opt Save Simulation
        User -> System : saveCityState()
        System -> System : saveSimulationToFile()

        alt Save successful
            System --> User : saveConfirmed()
        else Save failed
            System --> User : showError("Save failed")
        end
    end

    opt Load Simulation
        User -> System : loadCityState()
        System -> System : loadSimulationFromFile()

        alt Load successful
            System --> User : updateUI()
        else Load failed
            System --> User : showError("Load failed")
        end

    end

    opt Reset Simulation
        User -> System : resetSimulation()
        System -> System : resetSimulationState()
        System --> User : updateUI()
    end

end

User -> System : closeApplication()
System --> User : applicationClosed()

@enduml

```
# Internal Sequence Diagrams


## Create entity
![create_entity.png](../../img/diagrams/create_entity.png)

```plantuml

@startuml
autonumber

actor User
boundary UI
control Controller
entity City
entity CityGrid
entity Cell
entity PlaceableEntity

User -> UI : selectEntity(entity)
User -> UI : selectCell(x, y)
User -> UI : confirmPlacement()

UI -> Controller : placeEntity(entity, x, y)

Controller -> City : placeEntity(entity, x, y)
City -> CityGrid : place(x, y, entity)
CityGrid -> Cell : placeEntity(entity)

alt Cell is free
    Cell --> CityGrid : success
    CityGrid --> City : success
    City --> Controller : success
    Controller -> UI : placementSuccess()
end

alt Cell is occupied
    Cell --> CityGrid : failed (occupied)
    CityGrid --> City : placement failed
    City --> Controller : failed
    Controller -> UI : showPlacementError()
end

@enduml 

```

## Activate Policy

![activate_policy.png](../../img/diagrams/activate_policy.png)

```plantuml

@startuml
autonumber

actor User

boundary UI
control Controller
entity City
collections Policy

User -> UI : selectPolicy(policy)
User -> UI : confirmActivation()

UI -> Controller : applyPolicy(policy)

Controller -> City : setActivePolicy(policy)

opt Policy successfully added
    Controller -> UI : policyApplied()
end

opt Policy already active
    Controller -> UI : showPolicyError()
end
@enduml

```
## Tick

![tick.png](../../img/diagrams/tick.png)

```plantuml

@startuml
autonumber

actor User

boundary UI
control Controller
entity TickEngine
entity City
entity CityGrid
entity Cell
entity PlaceableEntity
entity CityState
collections Policy

User -> UI : advanceTick()
UI -> Controller : nextTick()

Controller -> TickEngine : advanceTick(city)

TickEngine -> City : getGrid()
City --> TickEngine : grid

TickEngine -> City : getState()
City --> TickEngine : state

TickEngine -> City : getActivePolicy()
City --> TickEngine : policy

loop for each Cell

    TickEngine -> CityGrid : getCell(x, y)
    CityGrid --> TickEngine : cell

    TickEngine -> Cell : getEntity()
    Cell --> TickEngine : entity

    opt Cell has an entity

        TickEngine -> PlaceableEntity : getEffects()
        PlaceableEntity --> TickEngine : effect

        TickEngine -> TickEngine : sumEffects(budget, pollution, happiness, population)

    end

end

opt Active policy exists

    TickEngine -> TickEngine : applyPolicyPercent(effects, policy)

end

TickEngine -> CityState : applyEffects(totalEffect)
TickEngine -> CityState : setPopulation(currentPopulation)
TickEngine -> CityState : setBudget(newBudget)

TickEngine -> City : incrementTick()

TickEngine -> UI : refreshUI()
UI --> User : cityStateUpdated()

@enduml

```
## Save City State

![save_city_state.png](../../img/diagrams/save_city_state.png)

```plantuml

@startuml
autonumber

actor User

boundary UI
control Controller
entity SaveManager
entity City
database SaveRepository

User -> UI : saveGame()

UI -> Controller : saveGame()

Controller -> SaveManager : save(city, filepath)

SaveManager -> JsonManager : save(city, filepath)

JsonManager -> City : serialize()
City --> JsonManager : jsonData

JsonManager -> SaveRepository : write(jsonData)
SaveRepository --> JsonManager : written

JsonManager --> SaveManager : success

opt save successful
SaveManager --> Controller: true
Controller --> UI : showSaveSuccess()
UI --> User : save confirmed
end

opt save failed
SaveManager --> Controller: false
Controller --> UI : showSaveError()
UI --> User : show error
end
@enduml
```

```
