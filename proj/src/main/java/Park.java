public class Park extends Infrastructure {

    public Park() {
        super("Park");
        this.effects = EffectLoader.load("park.json");
    }
    //metodo per stampare l'entita' sulla griglia
    @Override
    public String getSymbol() {
        return "P";
    }
}
