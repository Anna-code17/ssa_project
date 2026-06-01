/*
Modifiche rispetto al design class model:
- sovrascrizione del metodo toString. 
 */

public class CityState 
{

    // Avrebbe senso rendere tutto questo una variabile effect? Da valutare 
    private int budget;
    private int population;
    private int pollution;
    private int happiness;

//--------------------------- COSTRUTTORE ------------------------------------------


    public CityState(int initialBudget) {
        this.budget = initialBudget;
        this.population = 0;
        this.pollution = 0;
        this.happiness = 0;
    }

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


}