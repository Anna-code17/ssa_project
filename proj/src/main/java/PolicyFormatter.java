

public final class PolicyFormatter {

    private PolicyFormatter() {
    }

    public static String getPolicyName(City city) {

        Object policy = getPolicyObject(city);

        if (policy == null) {
            return null;
        }

        Object name =
                UIUtils.invokeNoArgMethod(
                        policy,
                        "getName"
                );

        if (name != null) {
            return String.valueOf(name);
        }

        return String.valueOf(policy);
    }

    public static String getPolicyDetails(City city) {

        Object policy = getPolicyObject(city);

        if (policy == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();

        Object name =
                UIUtils.invokeNoArgMethod(
                        policy,
                        "getName"
                );

        Object type =
                UIUtils.invokeNoArgMethod(
                        policy,
                        "getType"
                );

        Object effects =
                UIUtils.invokeNoArgMethod(
                        policy,
                        "getEffects"
                );

        if (name != null) {
            sb.append("Name: ")
                    .append(name)
                    .append("\n");
        }

        if (type != null) {
            sb.append("Type: ")
                    .append(type)
                    .append("\n");
        }

        if (effects != null) {
            sb.append("\nEffects:\n")
                    .append(effects);
        }

        if (sb.length() == 0) {
            sb.append(String.valueOf(policy));
        }

        return sb.toString();
    }

    private static Object getPolicyObject(City city) {

        if (city == null) {
            return null;
        }

        Object policy =
                UIUtils.invokeNoArgMethod(
                        city,
                        "getActivePolicy"
                );

        if (policy != null) {
            return policy;
        }

        policy =
                UIUtils.invokeNoArgMethod(
                        city,
                        "getPolicy"
                );

        return policy;
    }
}