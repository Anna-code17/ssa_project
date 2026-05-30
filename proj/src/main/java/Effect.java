/*

Ho aggiunto di nuovo a questa classe, rispetto al domain model , tutti i corrispettivi metodi setter E l'override del metodo toString della classe string. 
Idea, mi piacerebbe che invece di print effects, si potesse utilizzare il toString. e' puramente per scelta stilistica perchè tutti sanno che cosa ci si deve aspettare da un toString se applicato ad un oggetto, ovvero una descrizione testuale. 

*/


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

    //effetto di default. nullo

    public Effect()
    {
        this(0, 0, 0, 0);
    }

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