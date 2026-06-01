public class IndustrialBuilding extends Building {

    public IndustrialBuilding() {
        super("IndustrialBuilding");
        this.effects = EffectLoader.load("industrialBuilding.json");
    }

    @Override
    public String getSymbol() {
        return "I";
    }
}
