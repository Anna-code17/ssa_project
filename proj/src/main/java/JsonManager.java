import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

public class JsonManager {

    private static final ObjectMapper mapper = new ObjectMapper();
    static 
    {
        mapper.setVisibility(
        mapper.getSerializationConfig()
            .getDefaultVisibilityChecker()
            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
        );

        mapper.configure(
        com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        false
        );
    }

    // Caricamento da resources (per gli effects)
    public static <T> T loadFromResources(String path, Class<T> clazz) {

        try (InputStream is = JsonManager.class
                .getClassLoader()
                .getResourceAsStream(path)) {

            if (is == null)
                throw new RuntimeException("File non trovato: " + path);

            return mapper.readValue(is, clazz);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void save(Object object, String path) {

        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(path), object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T load(String path, Class<T> clazz) {

        try {
            return mapper.readValue(new File(path), clazz);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
