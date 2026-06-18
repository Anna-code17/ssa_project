import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //input nome città
        System.out.print("Nome città: ");
        String cityName = scanner.nextLine();
        if (cityName.isEmpty()) cityName = "MyCity";

        //input valore dimensione griglia
        int gridSize;
        do {
            System.out.print("Dimensione griglia (2-20): ");
            gridSize = scanner.nextInt();
        } while (gridSize < 2 || gridSize > 20);
        
        scanner.close();
        
        // Crea il controller e avvia l'interfaccia grafica
        Controller controller = new Controller(cityName, gridSize);
        new UserInterface(controller);
    }
}
