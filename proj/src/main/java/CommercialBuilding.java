public class CommercialBuilding extends Building 
{
        public CommercialBuilding() {
        super("CommercialBuilding");
        this.effects = EffectLoader.load("commercialBuilding.json");
    }

        @Override
        public String getSymbol() {
        return "C";
        }
}
