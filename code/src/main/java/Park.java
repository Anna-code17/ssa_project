public class Park extends Infrastructure {

    public Park() {
        super();
        this.effects = JsonManager.loadFromResources(
        "effects/park.json",
        Effect.class
        );
    }
    //metodo per stampare l'entita' sulla griglia
    @Override
    public String getSymbol() {
        return "🌳";
    }
}
