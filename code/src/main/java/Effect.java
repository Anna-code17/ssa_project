public class Effect {

    private int budget; // effetto ricorrente
    private int population;
    private int pollution;
    private int happiness;
    private int buildCost;      // costo di costruzione
    private String placementRule;

    // costruttore con buildCost = 0
    public Effect(int budget, int population, int pollution, int happiness) {
        this.budget = budget;
        this.population = population;
        this.pollution = pollution;
        this.happiness = happiness;
        this.buildCost = 0;// default buildCost = 0
        this.placementRule = "Nessuna regola di posizionamento.";
    }

       // costruttore con buildCost diverso da 0
    public Effect(int budget, int population, int pollution, int happiness, int buildCost) {
        this.budget = budget;
        this.population = population;
        this.pollution = pollution;
        this.happiness = happiness;
        this.buildCost = buildCost;
        this.placementRule = "Nessuna regola di posizionamento.";
    }

    // Costruttore completo con placementRule e buildCost diverso da 0
    public Effect(int budget, int population, int pollution, int happiness, int buildCost, String placementRule) {
        this.budget = budget;
        this.population = population;
        this.pollution = pollution;
        this.happiness = happiness;
        this.buildCost = buildCost;
        this.placementRule = placementRule;
    }

    public Effect() { }

    //-------------------------------- METODI GETTER -----------------------------------

    public int getBudget() {
        return budget;
    }

    public int getPopulation() {
        return population;
    }

    public int getPollution() {
        return pollution;
    }

    public int getHappiness() {
        return happiness;
    }
     public int getBuildCost() { 
        return buildCost; 
    }  
    
    public String getPlacementRule() {  
        return placementRule;
    }
    //------------------------------- METODI SETTER -----------------------------------

    public void setBudget(int budget) {
        this.budget=budget;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public void setPollution(int pollution) {
        this.pollution = pollution;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }
    public void setBuildCost(int buildCost) { 
        this.buildCost = buildCost; 
    }  
    
    public void setPlacementRule(String placementRule) {  
        this.placementRule = placementRule;
    }

    //------------------------------- VISUALIZZAZIONE EFFETTI -------------------------

    @Override
    public String toString() {
        return "Effects: " +
                "\nBudget=" + budget +
                "\nNopulation=" + population +
                "\nPollution=" + pollution +
                "\nHappiness=" + happiness +
                "\nBuildCost=" + buildCost +
                "\nPlacement Rule=" + placementRule;
    }
}
