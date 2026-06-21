public final class PolicyFormatter {

    private PolicyFormatter() {
    }

    /**
     * Restituisce una descrizione completa della policy.
     */
    public static String format(Policy policy) {

        if (policy == null) {
            return "No active policy";
        }

        StringBuilder sb = new StringBuilder();

        sb.append(policy.getName()).append("\n\n");

        sb.append("Budget: ")
                .append(policy.getPercentBudget())
                .append("%\n");

        sb.append("Population: ")
                .append(policy.getPercentPopulation())
                .append("%\n");

        sb.append("Pollution: ")
                .append(policy.getPercentPollution())
                .append("%\n");

        sb.append("Happiness: ")
                .append(policy.getPercentHappiness())
                .append("%");

        return sb.toString();
    }

    /**
     * Restituisce il nome della policy.
     */
    public static String getName(Policy policy) {

        if (policy == null) {
            return "No active policy";
        }

        return policy.getName();
    }

    /**
     * Restituisce il nome della policy attiva della città.
     */
    public static String getPolicyName(City city) {

        if (city == null) {
            return "No active policy";
        }

        return getName(city.getActivePolicy());
    }

    /**
     * Restituisce la descrizione della policy attiva della città.
     */
    public static String getPolicyDetails(City city) {

        if (city == null) {
            return "No active policy";
        }

        return format(city.getActivePolicy());
    }
}