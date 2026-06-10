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
            System.out.print("Dimensione griglia (2-10): ");
            gridSize = scanner.nextInt();
        } while (gridSize < 2 || gridSize > 10);
        
        scanner.close();
        
        // Avvio interfaccia grafica
        Controller controller = new Controller(cityName, gridSize);
        new UserInterface(controller);
    }
}
