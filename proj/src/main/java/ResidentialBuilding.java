public class ResidentialBuilding extends Building {

    public ResidentialBuilding() {
        super("ResidentialBuilding");
        this.effects = EffectLoader.load("residentialBuilding.json");
    }

    //metodo per ottenere il simbolo della entita' sulla griglia
    @Override
    public String getSymbol() {
        return "R";
    }
}
