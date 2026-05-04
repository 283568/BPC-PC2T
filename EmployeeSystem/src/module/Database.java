package module;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Database {
    private List<Employee> employees = new ArrayList<>();
    private int idCount = 0;
    private Scanner sc = new Scanner(System.in);

    private Employee findById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null; 
    }
    
    private void writeToFile(Employee emp){
	    	try {
	    	      File myFile = new File("employees.txt");
	    	      FileWriter myWriter = new FileWriter(myFile, true);
	    	      myWriter.write("id: "+emp.getId()+"\t meno priezvisko: "+emp.getName()+" "+emp.getSurname()+"\t rok: "+emp.getYear()+"\n");
	    	      myWriter.close(); 
	    	      System.out.println("Zamestnanec zapísaný do súboru.");
	    	    } catch (IOException e) {
	    	      System.out.println("An error occurred.");
	    	      e.printStackTrace();
	    	    }
	    	
	    }
    
    
    public void addEmployee() {
        try {
            System.out.println("Zadaj číslo skupiny: 1 - Analytik, 2 - Specialista ");
         
            if (!sc.hasNextInt()) {
                System.out.println("!!!Nezadal si číslo. Zadaj 1 alebo 2.");
                sc.nextLine();
                return; 
            }
            int option = sc.nextInt();
   
            if (option != 1 && option != 2) {
                System.out.println("!!!Možnosť " + option + " neexistuje. Zadaj 1 alebo 2.");
                return;
            }

            System.out.print("Jméno: "); 
            String name = sc.next();
            
            System.out.print("Příjmení: "); 
            String surname = sc.next();
            
            System.out.print("Rok narození: "); 
            if (!sc.hasNextInt()) {
                System.out.println("!!!Rok musí byť číslo nie string.");
                sc.nextLine(); 
                return;
            }
            int year = sc.nextInt();

            Employee newEmp;
            if (option == 1) {
                newEmp = new DataAnalyst(idCount++, name, surname, year);
            } else {
                newEmp = new SecuritySpecialist(idCount++, name, surname, year);
            }

            employees.add(newEmp);
            System.out.println("Zamestnanec pridaný.");

        } catch (Exception e) {
            System.out.println("!!!Chyba.");
            sc.nextLine(); 
        }
    }

    public void addCooperation() {
        try {
            System.out.print("Zadejte ID zamestnanca (0,1,2...): ");
            if (!sc.hasNextInt()) {
                System.out.println("!!!Nezadal si číslo. Zadaj id.");
                sc.nextLine();
                return; 
            }
            int id1 = sc.nextInt();
            
            System.out.print("Zadejte ID kolegy: ");
            if (!sc.hasNextInt()) {
                System.out.println("!!!Nezadal si číslo. Zadaj id.");
                sc.nextLine();
                return; 
            }
            int id2 = sc.nextInt();
            
            if (id1 == id2) {
                System.out.println("!!!Zadal si rovnakého zamestnanca.");
                return;
            }

            Employee emp1 = findById(id1);
            Employee emp2 = findById(id2);

            if (emp1 == null || emp2 == null) {
                System.out.println("!!!Zamestanec/i sa nenašiel/i");
                return;
            }

            System.out.println("Zadejte úroveň spolupráce: 1-špatná, 2-průměrná, 3-dobrá ");
            if (!sc.hasNextInt()) {
                System.out.println("!!!Nezadal si číslo. Zadaj 1/2/3.");
                sc.nextLine();
                return; 
            }
            int levelNum = sc.nextInt();
            if (levelNum != 1 && levelNum != 2 && levelNum != 3) {
                System.out.println("!!!Možnosť " + levelNum + " neexistuje. Zadaj 1/2/3.");
                return;
            }

            CooperationLevel level;
            switch (levelNum) {
                case 1: level = CooperationLevel.SPATNA; break;
                case 2: level = CooperationLevel.PRUMERNA; break;
                case 3: level = CooperationLevel.DOBRA; break;
                default: return;
            }

            emp1.getCooperations().put(id2, level);
            emp2.getCooperations().put(id1, level);
            System.out.println("Spolupráce byla úspěšně zaznamenána.");

        } catch (InputMismatchException e) {
            System.out.println("!!!Zadávejte pouze čísla!");
            sc.nextLine();
        }
    }


	public void removeEmployee() {
		try {
		System.out.println("Zadaj id zamestnanca, ktorého chceš odstrániť.");
		if (!sc.hasNextInt()) {
            System.out.println("!!!Nezadal si číslo. Zadaj id.");
            sc.nextLine();
            return; 
        }
		int idDelete =sc.nextInt();
		Employee emp = findById(idDelete);
		
		if (emp == null) {
            System.out.println("!!!Zamestanec sa nenašiel.");
            return;
        }
		for(Employee i: employees) {
			i.getCooperations().remove(idDelete);
		}
		employees.remove(emp);
		System.out.println("id:"+idDelete+"\nmeno, priezvisko: "+emp.getName()+" "+emp.getSurname()+"\nrok narodenia: "+emp.getYear()+"\nskupina: "+emp.getGroup());
		System.out.println("Zamestnanec úspešne odstránený.");

		}
		catch (Exception e) {
			System.out.println("!!!Chyba.");
			sc.nextLine(); 
		}
	}

	public void findEmployee() {
		try {
			System.out.println("Zadaj id zamestnanca, ktorého chceš nájsť.");
			if (!sc.hasNextInt()) {
	            System.out.println("!!!Nezadal si číslo. Zadaj id.");
	            sc.nextLine();
	            return; 
	        }
		int idEmp=sc.nextInt();
		Employee emp = findById(idEmp);
		
		if (emp == null) {
            System.out.println("!!!Zamestanec sa nenašiel.");
            return;
        }
		System.out.println("Základne informácie o zamestnancovi:");
		System.out.println("id: "+emp.getId());
		System.out.println("meno: "+emp.getName());
		System.out.println("priezvisko: "+emp.getSurname());
		System.out.println("rok narodenia: "+emp.getYear());
		System.out.println("skupina: "+emp.getGroup());
		
		System.out.println("počet spolupracovníkov: "+emp.getCooperations().size());
		//pridat statistiky spoluprace??
		}
		catch (Exception e) {
			System.out.println("!!!Chyba.");
			sc.nextLine(); 
			}
		}

	public void printSkills() {
		try {
			System.out.println("Zadaj id zamestnanca, ktorého skills chceš vidieť.");
			if (!sc.hasNextInt()) {
	            System.out.println("!!!Nezadal si číslo. Zadaj id.");
	            sc.nextLine();
	            return; 
	        }
		int idEmp=sc.nextInt();
		Employee emp = findById(idEmp);
		
		if (emp == null) {
            System.out.println("!!!Zamestanec sa nenašiel.");
            return;
        }
		System.out.println("zamestnanec patrí do skupiny: "+emp.getGroup());
		if (emp.getGroup().equals("Datový analytik")) {
			System.out.println("dokážou určit, s kterých spolupracovníkem mají nejvíce společných spolupracovníků.");
			analystSkills(emp);
		}
		else if (emp.getGroup().equals("Bezpečnostní specialista")) {
        		System.out.println(" dokážou vyhodnotit rizikovost spolupráce na základě počtu spolupracovníků a průměrné kvality spolupráce a vypočítat rizikové skóre ");
        		securitySkills(emp);
		}
    } catch (Exception e) {
        System.out.println("!!!Chyba: " + e.getMessage());
        sc.nextLine();
    }
}
	
	private void analystSkills(Employee employee) {
		int maxCount=0;
		String coworker=" ";
		for(Employee i:employees) {
			if(i.getId()==employee.getId()) 
				continue;
			int count=0;
			for(Integer coworkerId : employee.getCooperations().keySet()) {
				if(i.getCooperations().containsKey(coworkerId)) {
					count++;
					}
				}
			
		if(count>maxCount) {
			maxCount=count;
			coworker=i.getName()+" "+i.getSurname();
			}
		}
		System.out.println("najviac spoločných spolupracovníkov má s "+coworker);
		System.out.println("počet spoločných coworkers: "+maxCount);
		
	}
		
	
	private void securitySkills(Employee employee) {
		if(employee.getCooperations().isEmpty()) {
			System.out.println("Rizikové skóre: 0,0%... väzby neboli nájdené!");
		}
		double coopValue=0;
		for(CooperationLevel level : employee.getCooperations().values()) {
			coopValue+=level.getValue();
		}
		int count=employee.getCooperations().size();
		double average = coopValue/count;
		int maxLevel = CooperationLevel.getMaxPossibleValue();
		double riskScore = count * ((maxLevel + 1) - average);

	    System.out.println("Max level: " + maxLevel);
	    System.out.println("Rizikové skóre: " + String.format("%.2f", riskScore));
	}

	public void printAlphabetically() {
	    if (employees.isEmpty()) {
	        System.out.println("Databáze je prázdná.");
	        return;
	    }
	    
	    List<Employee> analytici = new ArrayList<>();
	    List<Employee> specialisti = new ArrayList<>();

	    for (Employee e : employees) {
	        if (e.getGroup().equals("Datový analytik")) {
	            analytici.add(e);
	        } else if (e.getGroup().equals("Bezpečnostní specialista")) {
	            specialisti.add(e);
	        }
	    }
	    
	    Comparator<Employee> podlaPriezviska = new Comparator<Employee>() {
	        @Override
	        public int compare(Employee e1, Employee e2) {
	            return e1.getSurname().compareToIgnoreCase(e2.getSurname());
	        }
	    };

	    Collections.sort(analytici, podlaPriezviska);
	    Collections.sort(specialisti, podlaPriezviska);

	    System.out.println("zoznam Datovych analytikov podla abecedy:");
	    if (analytici.isEmpty()) {
	        System.out.println("Žiadni analytici.");
	    } else {
	        for (Employee e : analytici) {
	            System.out.println(e.getSurname() + " " + e.getName() + " (ID: " + e.getId() + ")");
	        }
	    }

	    System.out.println("zoznam Bezpecnostnych specialistu podla abecedy:");
	    if (specialisti.isEmpty()) {
	        System.out.println("Žiadni špecialisti.");
	    } else {
	        for (Employee e : specialisti) {
	            System.out.println(e.getSurname() + " " + e.getName() + " (ID: " + e.getId() + ")");
	        }
	    }
	
	}

	public void printStats() {
		System.out.println("Štatistiky:");
		
		
	}
	
	public void groupNums() {
		if (employees.isEmpty()) {
	        System.out.println("Databáze je prázdná.");
	        return;
	    }
		List<Employee> analytici = new ArrayList<>();
	    List<Employee> specialisti = new ArrayList<>();

	    for (Employee e : employees) {
	        if (e.getGroup().equals("Datový analytik")) {
	            analytici.add(e);
	        } else if (e.getGroup().equals("Bezpečnostní specialista")) {
	            specialisti.add(e);
	        }
	    }
	    System.out.println("Počet Datovych analytikov: "+analytici.size());
	    System.out.println("Počet Bezpecnostnych specialistov: "+specialisti.size());
		
	}
	
	public void printEmp() {
		if (employees.isEmpty()) {
	        System.out.println("Databáze je prázdná.");
	        return;
	    }
	    for (Employee e : employees) {
	    		System.out.println(e.getSurname() + " " + e.getName() + " (ID: " + e.getId() + ")");
	    }	
	}

	public void writeEmp() {
		try {
			System.out.println("Zadaj id zamestnanca, ktorého chceš pridať do súboru.");
			if (!sc.hasNextInt()) {
	            System.out.println("!!!Nezadal si číslo. Zadaj id.");
	            sc.nextLine();
	            return; 
	        }
		int idEmp=sc.nextInt();
		sc.nextLine();
		Employee emp = findById(idEmp);
		
		if (emp == null) {
            System.out.println("!!!Zamestanec sa nenašiel.");
            return;
        }
		writeToFile(emp);
		}catch (Exception e) {
	        System.out.println("!!!Chyba: " + e.getMessage());
	        sc.nextLine();
	    }
		
		
	}
	
	//test ci viem citat cely subor
	public void readFile() {
		File myObj = new File("employees.txt");
		try (Scanner myReader = new Scanner(myObj)) {
		      while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        System.out.println(data);
		      }
		    } catch (FileNotFoundException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}

	
	public void readEmp() {
		try {
			System.out.println("Zadaj id zamestnanca, ktorého chceš načítať zo súboru.");
			if (!sc.hasNextInt()) {
	            System.out.println("!!!Nezadal si číslo. Zadaj id.");
	            sc.nextLine();
	            return; 
	        }
			readFile();
			}catch (Exception e) {
		        System.out.println("!!!Chyba: " + e.getMessage());
		        sc.nextLine();
	        }
		
		
	}
		
		
	

		
	

	
	
}
//testing commit to github ^._.^