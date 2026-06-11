public class ResidentialBuilding extends Building {

    public ResidentialBuilding() {
        super("ResidentialBuilding");
        this.effects = JsonManager.loadFromResources(
        "effects/residentialBuilding.json",
        Effect.class
         );
    }

    //metodo per ottenere il simbolo della entita' sulla griglia
    @Override
    public String getSymbol() {
        return "R";
    }
}
