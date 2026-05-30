import java.util.Scanner;
public class es
{
   public static void main(String[] args) 
    {
            Park a = new Park();
            System.out.println("Stampo il nome: "+ a.getName());
            System.out.println("Stampo il tipo: "+ a.getType());
            System.out.println(a.toString() + "\n"); //non ho collegato il fatto di poter vedere che tipo di effetto può essere applicato
            System.out.println("VISUALIZZAZIONE SOLO DEGLI EFFETTI:");
            System.out.println(a.getEffects());

    }

}