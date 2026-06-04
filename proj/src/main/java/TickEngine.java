public class TickEngine {

    public void advanceTick(City city) {

        if (city == null) {
            throw new IllegalArgumentException("City cannot be null");
        }

        CityGrid grid = city.getGrid();
        CityState state = city.getState();
        Policy policy = city.getActivePolicy();

        if (grid == null || state == null) {
            throw new IllegalStateException("City must have a valid grid and state");
        }

        int tbudget = 0;
        int tpopulation = 0;
        int tpollution = 0;
        int thappiness = 0;

        // Somma gli effetti di tutte le entità presenti nella griglia
        for (int x = 0; x < grid.getSize(); x++) {

            for (int y = 0; y < grid.getSize(); y++) {

                Cell cell = grid.getCell(x, y);

                if (cell.isEmpty()) {
                    continue;
                }

                PlaceableEntity entity = cell.getEntity();
                Effect effect = entity.getEffects();

                tbudget += effect.getBudget();
                tpopulation += effect.getPopulation();
                tpollution += effect.getPollution();
                thappiness += effect.getHappiness();
            }
        }

        // Applica la policy ai totali
        if (policy != null) {

            tbudget += tbudget * policy.getPercentBudget() / 100;

            tpopulation +=
                    tpopulation * policy.getPercentPopulation() / 100;

            tpollution +=
                    tpollution * policy.getPercentPollution() / 100;

            thappiness +=
                    thappiness * policy.getPercentHappiness() / 100;
        }

        Effect totalEffect = new Effect(
                tbudget,
                tpopulation,
                tpollution,
                thappiness
        );

        state.applyEffects(totalEffect);

        city.incrementTick();
    }
}
