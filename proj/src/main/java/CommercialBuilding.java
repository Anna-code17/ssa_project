public class CommercialBuilding extends Building 
{
        public CommercialBuilding() {
        super("CommercialBuilding");
        this.effects = JsonManager.loadFromResources(
        "effects/commercialBuilding.json",
        Effect.class
        );
    }

        @Override
        public String getSymbol() {
        return "C";
        }
}
