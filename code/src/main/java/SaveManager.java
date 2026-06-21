//classe che gestisce il salvataggio della citta'
public class SaveManager {

    //salva lo stato della citta'
    public boolean save(City city, String filepath) {
        try {
            JsonManager.save(city, filepath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //carica uno stato della citta' precedentemente salvato
    public City load(String filepath) {
        return JsonManager.load(filepath, City.class);
    }
}
