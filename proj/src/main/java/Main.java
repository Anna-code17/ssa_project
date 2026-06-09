import java.util.Scanner;

public class CitySimulator {
    
    private static Controller controller;
    private static Scanner scanner;
    
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        
        // Input nome città
        System.out.print("Inserisci il nome della città: ");
        String cityName = scanner.nextLine();
        if (cityName.isEmpty()) {
            cityName = "MyCity";
        }
        
        // Input dimensione griglia
        int gridSize;
        do {
            System.out.print("Inserisci la dimensione della griglia (3-10): ");
            gridSize = scanner.nextInt();
            scanner.nextLine();
        } while (gridSize < 3 || gridSize > 10);
        
        // Creazione controller
        controller = new Controller(cityName, gridSize);
