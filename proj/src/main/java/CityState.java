import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)


public class CityState 
{
    private int budget;
    private int population;
    private int pollution;
    private int happiness;
    private int initialBudget; 

//--------------------------- COSTRUTTORE ------------------------------------------


    public CityState(int initialBudget) {
        this.budget = initialBudget;
        this.initialBudget = initialBudget;
        this.population = 0;
        this.pollution = 0;
        this.happiness = 0;
    }

    public CityState() { }
// -------------------------- METODI PER EFFETTUARE OPERAZIONI SULLE METRICHE CITTA'------------------------

    //applica un effetto dato in input andando a modificare i valori delle variabili 
    public void applyEffects(Effect effects) 
    {
        if (effects == null) 
        {
            return;
        }

        budget += effects.getBudget();
        population += effects.getPopulation();
        pollution += effects.getPollution();
        happiness += effects.getHappiness();
    }

    //permette di visualizzare le metriche che appartengono ad una citta' in un determinato momento
    @Override
    public String toString() {
        return "CityState{" +
                "budget=" + budget +
                ", population=" + population +
                ", pollution=" + pollution +
                ", happiness=" + happiness +
                '}';
    }
    //resetta i parametri alle condizioni iniziali
    public void clear() {
    this.budget = this.initialBudget;
    this.population = 0;
    this.pollution = 0;
    this.happiness = 0;
}

// ------------------------------- METODI GETTER -----------------------------------

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

    // ------------------------------- METODI SETTER ----------------------------------

    public void setPopulation(int population)
    {
        this.population = population;
    }
    
    public void setBudget(int budget)
    {
        this.budget = budget;
    }
    
    


}
