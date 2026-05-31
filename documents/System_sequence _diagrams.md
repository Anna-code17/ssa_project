@startuml
actor User
participant "City Simulation System" as System

User -> System : launchApplication()
System --> User : displayUI(cityState, cityGrid)

loop while simulation is running

  opt Create new entity
    User -> System : createEntity(entityType, position)
    System -> System : validatePlacement(position, entityType)

    opt placement allowed
      System -> System : addEntityToGrid()
      System --> User : entityCreated()
      System --> User : updateGrid()

    else placement not allowed
      System --> User : showError("Invalid placement")
    end
  end

  opt Remove entity
    User -> System : removeEntity(position)
    System -> System : removeEntityFromGrid()
    System --> User : entityRemoved()
    System --> User : updateGrid()
  end

  opt Activate policy
    User -> System : activatePolicy(policy)
    System -> System : applyPolicy(policy)
    System --> User : policyActivated()
    System --> User : updateUI()
  end

  opt Advance time
    User -> System : advanceTick()
    System -> System : scanGridAndApplyEffects()
    System -> System : updateCityState()
    System --> User : cityStateUpdated()
    System --> User : updateUI()
  end

  opt Save simulation
    User -> System : saveCityState()
    System -> System : saveSimulation()
    System --> User : saveConfirmed()
  end

  opt Reset simulation
    User -> System : resetSimulation()
    System -> System : resetSimulationState()
    System --> User : resetConfirmed()
    System --> User : updateUI()
  end

end
@enduml


@startuml
autonumber

actor User

boundary UI
control PlacementController
entity City
entity CityGrid
entity Cell
entity PlaceableEntity

User -> UI : selectEntity(entity)
User -> UI : selectCell(x, y)
User -> UI : confirmPlacement()

UI -> PlacementController : placeEntity(entity, x, y)

PlacementController -> City : getGrid()
City --> PlacementController : CityGrid

PlacementController -> CityGrid : getCell(x, y)
CityGrid --> PlacementController : Cell

PlacementController -> Cell : isEmpty()

opt Cell is free
    PlacementController -> Cell : place(entity)
    PlacementController -> UI : placementSuccess()
end

opt Cell is occupied
    PlacementController -> UI : showPlacementError()
end
@enduml 

@startuml
autonumber

actor User

boundary UI
control PolicyController
entity City
collections Policy

User -> UI : selectPolicy(policy)
User -> UI : confirmActivation()

UI -> PolicyController : applyPolicy(policy)

PolicyController -> City : addPolicy(policy)

opt Policy successfully added
    PolicyController -> UI : policyApplied()
end

opt Policy already active
    PolicyController -> UI : showPolicyError()
end
@enduml

@startuml
autonumber

actor User

boundary UI
control TickController
entity TickEngine
entity RuleEngine
entity City
entity CityGrid
entity Cell
entity PlaceableEntity
entity CityState
collections Policy

User -> UI : advanceTick()
UI -> TickController : advanceTick()

TickController -> TickEngine : advanceTick(city)

TickEngine -> City : getGrid()
City --> TickEngine : grid

TickEngine -> City : getState()
City --> TickEngine : state

TickEngine -> City : getActivePolicy()
City --> TickEngine : policy

TickEngine -> RuleEngine : processTick(grid, state, policy)

RuleEngine -> CityGrid : getCells()
CityGrid --> RuleEngine : cells

loop for each Cell

    RuleEngine -> Cell : getEntity()
    Cell --> RuleEngine : entity

    opt Cell has an entity

        RuleEngine -> PlaceableEntity : getEffect()
        PlaceableEntity --> RuleEngine : effect

        opt Active policy exists

            RuleEngine -> Policy : modify(effect)
            Policy --> RuleEngine : modifiedEffect

            RuleEngine -> CityState : apply(modifiedEffect)

        end

        opt No active policy

            RuleEngine -> CityState : apply(effect)

        end

    end

end

RuleEngine --> TickEngine : tickProcessed()

TickEngine -> UI : refreshCityView()
@enduml

@startuml
autonumber

actor User

boundary UI
control SaveController
entity SaveManager
entity City
database SaveRepository

User -> UI : saveGame()

UI -> SaveController : saveGame()

SaveController -> SaveManager : save(city)

SaveManager -> City : getSaveData()
City --> SaveManager : saveData

SaveManager -> SaveRepository : write(saveData)
SaveRepository --> SaveManager : saved

SaveManager --> SaveController : saveCompleted()

SaveController --> UI : showSaveSuccess()

UI --> User : save confirmed

@enduml
