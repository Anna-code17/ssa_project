public final class PolicyDescriptionFormatter {

    private PolicyDescriptionFormatter() {
    }

    public static String format(Policy policy) {

        StringBuilder sb = new StringBuilder();

        sb.append(policy.getName())
                .append("\n\n");

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
}