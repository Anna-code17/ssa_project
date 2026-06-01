import java.util.Scanner;
public class es
{
   public static void main(String[] args) 
    {
//Creazione degli oggetti, controllo se genera errori di compilazione
    	Park a = new Park();
        PowerPlant b = new PowerPlant();
        Road c = new Road ();
        IndustrialBuilding d = new IndustrialBuilding();
	CommercialBuilding e = new CommercialBuilding();
	ResidentialBuilding f = new ResidentialBuilding();
//Controllo se effettivamente funziona il load per TUTTI GLI EFFETTI

System.out.println("\n\n\n\n\n\n-----------------------------------------------------INFRASTRUCTURES---------------------------------------------------\n\n\n\n");
//Park 	
	System.out.println("PARK");
        System.out.println("Stampo il nome: "+ a.getName());
        System.out.println("Stampo il tipo: "+ a.getType());
        System.out.println(a.toString() + "\n"); //non ho collegato il fatto di poter vedere che tipo di effetto può essere applicato
        System.out.println("VISUALIZZAZIONE SOLO DEGLI EFFETTI:");
        System.out.println(a.getEffects());//possibile solo grazie alla sovrascrizione del metodo toString()

//PP
	System.out.println("\n\n\n\nPP");
	System.out.println("VISUALIZZAZIONE PP");
        System.out.println(b + "\n\n\n"); 
//Road
	System.out.println("ROAD");
	System.out.println("VISUALIZZAZIONE Road");
        System.out.println(c + "\n");
//PROVA SE EFFETTIVAMENTE anche gli oggetti building si comportano in maniera corretta 

//IB
	System.out.println("\n\n\n\n\n\n-----------------------------------------------------BUILDINGS---------------------------------------------------\n\n\n\n");
	System.out.println("Stampo il nome: "+ d.getName());
        System.out.println("Stampo il tipo: "+ d.getType());
        System.out.println("\n\nTest di to string oggetto IB:" + d.toString() + "\n"); 
        System.out.println("\n\n\n\n\nVISUALIZZAZIONE SOLO DEGLI EFFETTI IB:");
        System.out.println(d.getEffects());//possibile solo grazie alla sovrascrizione del metodo toString()
//CB
	System.out.println("\n\n\n\n\n VISUALIZZAZIONE CB:");
        System.out.println(e + "\n\n\n\n");
//RB
	System.out.println("VISUALIZZAZIONE CB:");
        System.out.println(f + "\n\n\n\n");

//Questi test sono stati fatti per vedere se ho salvato male i file / fatto le estensioni sbagliate ai building 

//---------------------- TEST SULLA CLASSE CELL ----------------------------
System.out.println("\n\n-----------------------------------------------------CELL---------------------------------------------------\n\n");
	Cell test = new Cell(0 , 0);
	System.out.println("print oggetto all'interno cella: " + test.getEntity());
	
//INSERZIONE IN UNA CELLA DI UNA ENTITÀ
	boolean result = test.placeEntity(a);
	if(result)
		System.out.println("Park correttamente inserito all'interno della cella");
	else
	System.out.println("Park NON inserito. PROBLEMA.");

// PRINT DELLA ENTITA' CHE E' CONTENUTA ALL'INTERNO DELLA CELLA 
	System.out.println("\n Print della entita' contenuta all'interno cella: \n");
	System.out.println(test.getEntity());	
	
// Provo a mettere una seconda entità su questa cella gia' piena 
	boolean result2 = test.placeEntity(b);
	if(result2)
		System.out.println("PP correttamente inserito all'interno della cella.PROBLEMA");
	else
	System.out.println("PP NON inserito. GIUSTO.");
	
	
//---------------------- TEST SULLA CLASSE CITYGRID ----------------------------
System.out.println("\n\n-----------------------------------------------------CITYGRID---------------------------------------------------\n\n");
//creazione della city grid
 
CityGrid cityG = new CityGrid(4);


    
    
    }

}
