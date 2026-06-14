
import java.lang.reflect.Method;

public final class UIUtils {

    private UIUtils() {
    }

    public static String safeString(String value) {
        return (value == null || value.isBlank())
                ? "N/A"
                : value;
    }

    public static Object invokeNoArgMethod(
            Object target,
            String methodName
    ) {

        if (target == null ||
                methodName == null ||
                methodName.isBlank()) {
            return null;
        }

        try {
            Method method =
                    target.getClass().getMethod(methodName);

            return method.invoke(target);

        } catch (Exception ignored) {
            return null;
        }
    }

    public static String readEffects(
            PlaceableEntity entity
    ) {

        if (entity == null) {
            return "No entity selected.";
        }

        Object effects =
                invokeNoArgMethod(entity, "getEffects");

        if (effects != null) {
            return String.valueOf(effects);
        }

        return "No effects information available.";
    }
}