public class CommercialBuilding extends Building 
{
        public CommercialBuilding() {
        super();
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
