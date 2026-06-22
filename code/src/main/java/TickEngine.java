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

        int currentBudget = 0;
        int currentPopulation = 0;
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

                currentBudget += effect.getBudget();
                tpollution += effect.getPollution();
                thappiness += effect.getHappiness();

                currentPopulation += effect.getPopulation();
            }
        }

        // Applica la policy ai totali
        if (policy != null) {

            currentBudget = applyPolicyPercent(currentBudget, policy.getPercentBudget());
            currentPopulation = applyPolicyPercent(currentPopulation, policy.getPercentPopulation());
            tpollution  = applyPolicyPercent(tpollution, policy.getPercentPollution());
            thappiness  = applyPolicyPercent(thappiness, policy.getPercentHappiness());
        }

        //genera un nuovo effetto in base ai valori precedentemente calcolati
        Effect totalEffect = new Effect(
                currentBudget,
                0,
                tpollution,
                thappiness
        );

        state.applyEffects(totalEffect);
        state.setPopulation(currentPopulation);
        city.incrementTick();
    }

    //metodo per poter applicare le policy anche con valori negativi delle percentuali
    // Applica una variazione percentuale arrotondando al valore intero più vicino.
    private int applyPolicyPercent(int value, int percent) {

        double result;

        if (value >= 0) {
            result = value + value * percent / 100.0;
        } else {
            result = value + Math.abs(value) * percent / 100.0;
        }

        return (int) Math.round(result);
    }
}
