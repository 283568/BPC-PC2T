package module;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	public static void main(String[] args) {
		Database db=new Database();
		Scanner sc=new Scanner(System.in);
		boolean run=true;
		while(run) {
			System.out.println("-------------------------------------");
	        System.out.println("HLAVNÉ MENU:");
	        System.out.println(" 1. Přidat zaměstnance");
	        System.out.println(" 2. Přidat spolupráci");
	        System.out.println(" 3. Odebrat zaměstnance");
	        System.out.println(" 4. Vyhledat zaměstnance dle ID");
	        System.out.println(" 5. Spustit dovednost zaměstnance");
	        System.out.println(" 6. Abecední výpis podle skupin");
	        System.out.println(" 7. Statistiky");
	        System.out.println(" 8. Počty ve skupinách");
	        System.out.println(" 9. Uložit zaměstnance do souboru");
	        System.out.println("10. Načíst zaměstnance ze souboru");
	        System.out.println(" 0. Ukončit program");
	        System.out.println(" -1. Výpis zamestnancov (iba na check)");
	        System.out.println("-------------------------------------");
	        
	        try {
	            System.out.print("Možnosť: ");
	            int choice = sc.nextInt();
	            
			switch(choice) {
				case 1:
					db.addEmployee(); 
					break;
					
			    case 2: 
			    		db.addCooperation(); 
			    		break;
			    
			    case 3: 
			    		db.removeEmployee(); 
			    		break;
			    case 4: 
			    		db.findEmployee(); 
			    		break;
			    case 5:
			    		db.printSkills();
			    		break;
			    case 6:
				    	db.printAlphabetically();
				    	break;
			    case 7:
			    		db.printStats();
			    		break;
			    case 8:
			    		db.groupNums();
			    		break;
			    case 9:
			    		db.writeEmp();
			    		break;
			    case 10:
			    		db.readEmp();
			    		break;
			    case 0:
			    		//db.konec();
			    		run=false;
			    		break;
			    case -1:
			    		db.printEmp();
			    		break;
			    	default:
			    		System.out.println("nespravne!");
			    		break;
			    		}
	        }
	        catch (InputMismatchException e) {
	        		System.out.println("!!!Musíte zadať číslo!");
				sc.nextLine();
			}
		}
	}
}