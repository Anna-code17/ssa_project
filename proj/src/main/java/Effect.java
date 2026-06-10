import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Effect {

    private int budget;
    private int population;
    private int pollution;
    private int happiness;

    public Effect(int budget, int population, int pollution, int happiness) 
    {
        this.budget = budget;
        this.population = population;
        this.pollution = pollution;
        this.happiness = happiness;
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

    //------------------------------- VISUALIZZAZIONE EFFETTI -------------------------

    @Override
    public String toString() {
        return "Effects: " +
                "\nBudget=" + budget +
                "\nNopulation=" + population +
                "\nPollution=" + pollution +
                "\nHappiness=" + happiness
                ;
    }
}
