import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

/*
 * EffectLoader è una classe di utilità responsabile del caricamento degli oggetti Effect da file JSON.
 *
 * L'obiettivo è separare i dati (definiti nei JSON) dalla logica del programma.
 */
public class EffectLoader {

    /*
     * ObjectMapper è la classe principale di Jackson usata per convertire JSON <-> oggetti Java. È dichiarato static così viene riutilizzato
     * senza crearne uno nuovo ogni volta (più efficiente).
     */
    private static final ObjectMapper mapper = new ObjectMapper();

    /*
     * Carica un Effect da un file JSON presente nelle resources.
     * Esempio di utilizzo:
     * EffectLoader.load("park.json")
     */
    public static Effect load(String fileName) {

        try (
            /**
             * Recupera il file dalle resources del progetto.
             *
             * Il path "effects/" indica che i JSON sono
             * nella cartella resources/effects.
             *
             * getResourceAsStream ritorna uno stream del file,
             * che può essere letto come input.
             */
            InputStream is = EffectLoader.class
                    .getClassLoader()
                    .getResourceAsStream("effects/" + fileName)
        ) {

            /**
             * Controllo di sicurezza:
             * se il file non esiste, lo stream è null.
             */
            if (is == null) {
                throw new RuntimeException(
                        "FILE NON TROVATO: " + fileName
                );
            }

            /**
             * Jackson legge il JSON e lo converte
             * in un EffectDTO (classe temporanea).
             */
            EffectDTO dto = mapper.readValue(is, EffectDTO.class);

            /**
             * Conversione da DTO a Effect reale del dominio.
             * Qui creiamo l'oggetto usato dal gioco.
             */
            return new Effect(
                    dto.budget,
                    dto.population,
                    dto.pollution,
                    dto.happiness
            );

        } catch (Exception e) {

            /**
             * In caso di errore (file mancante, JSON errato, ecc.)
             * lanciamo un RuntimeException.
             *
             * In un progetto più avanzato potresti usare
             * un'eccezione custom.
             */
            throw new RuntimeException(
                    "Errore durante il caricamento dell'effect: " + fileName,
                    e
            );
        }
    }
}