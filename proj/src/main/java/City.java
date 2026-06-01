/*
Modifiche apportate al Domain Model:
-Aggiunta di setPolicy

ATTENZIONE PER FARE LA PROVA HO DOVUTO SETTARE TUTTO CHE PUNTAVA A POLICY COME COMMENTO. DA SISTEMARE
 */

public class City {

    private String name;
    private CityGrid grid;
    private CityState state;
    //private Policy activePolicy;

    public City(String name, int size) {
        this.name = name;
        this.grid = new CityGrid(size);
        //Ho impostato il budget iniziale a 1000
        this.state = new CityState(1000);
        //this.activePolicy = null;
    }

// --------------------------- METODI GETTER ------------------------------
    
    public String getName() {
        return name;
    }

    public CityGrid getGrid() {
        return grid;
    }

    public CityState getState() {
        return state;
    }
/*
    public Policy getPolicy() {
        return activePolicy;
    }

// --------------------------- METODI SETTER -----------------------------

    public void setPolicy(Policy policy) {
        this.activePolicy = policy;
    }
*/

}
