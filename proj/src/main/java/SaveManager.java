public class SaveManager {

    public boolean save(City city, String filepath) {
        try {
            JsonManager.save(city, filepath);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public City load(String filepath) {
        return JsonManager.load(filepath, City.class);
    }
}
