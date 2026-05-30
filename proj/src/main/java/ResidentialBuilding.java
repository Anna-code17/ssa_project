public class ResidentialBuilding extends Building {

    public ResidentialBuilding() {
        super("ResidentialBuilding");
        this.effects = EffectLoader.load("residentialBuilding.json");
    }
}