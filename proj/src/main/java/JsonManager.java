import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

//Questa classe centralizza tutte le operazioni JSON del progetto

public class JsonManager {

    private static final ObjectMapper mapper = new ObjectMapper();
    
    static // Tale blocco viene eseguito una sola volta quando la classe viene caricata  
    {
        //configurazione della visibilita' dei campi per Jackson
        mapper.setVisibility(
        mapper.getSerializationConfig()
            .getDefaultVisibilityChecker()//fino a questo punto si recupera la configurazione di default della visibilita'
            .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
            .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
            .withCreatorVisibility(JsonAutoDetect.Visibility.NONE) //disabilitazione di costruttori/factory automatici. Quindi jackson va ad utilizzare i costruttori vuoti
        );

        //disattivazione errori su campi sconosciuto nel JSON
        mapper.configure(
        com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
        false
        );
    }

    // Caricamento da resources (per gli effects)
    public static <T> T loadFromResources(String path, Class<T> clazz) {
        //si apre uno stream verso un file dentro alla cartella resources 
        try (InputStream is = JsonManager.class
                .getClassLoader()
                .getResourceAsStream(path)) {

            if (is == null)
                throw new RuntimeException("File non trovato: " + path);

            return mapper.readValue(is, clazz);//jackson legge lo stream JSON e lo converte in oggetto di tipo clazz

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //Salva qualsiasi oggetto su file JSON 
    public static void save(Object object, String path) {
        //creazione di un writer con formattazione leggibile, si serializza l'oggetto e lo si scrive sul file 
        try {
            mapper.writerWithDefaultPrettyPrinter()
                  .writeValue(new File(path), object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*Carica un JSON da file del File System 
    clazz rappresenta il tipo runtime dell'oggetto da deserializzare.
    Viene utilizzato da Jackson per ricostruire l'istanza corretta dal JSON,
    poiché i generics Java non sono disponibili a runtime (type erasure).*/
    public static <T> T load(String path, Class<T> clazz) {

        try {
            return mapper.readValue(new File(path), clazz);//legge il file e lo converte in un oggetto di tipo clazz
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
