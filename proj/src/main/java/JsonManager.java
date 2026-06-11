import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;


public class JsonManager {

    private static final ObjectMapper mapper = new ObjectMapper();


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
