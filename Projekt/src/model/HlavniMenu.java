package model;

import java.util.*;

public class HlavniMenu {
    
    private DatabazeZamestnancu databaze;
    private Scanner scanner;
    
    public HlavniMenu() {
        this.databaze = new DatabazeZamestnancu();
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        boolean running = true;
        
        while (running) {
            System.out.println("\n========================================");
            System.out.println("     SPRAVA ZAMESTNANCU");
            System.out.println("========================================");
            System.out.println("1. Pridat zamestnance");
            System.out.println("2. Pridat spolupraci");
            System.out.println("3. Odebrat zamestnance");
            System.out.println("4. Vyhledat zamestnance dle ID");
            System.out.println("5. Spustit dovednost zamestnance");
            System.out.println("6. Abecedni vypis zamestnancu ve skupinach");
            System.out.println("7. Zobrazit statistiky");
            System.out.println("8. Vypis poctu zamestnancu ve skupinach");
            System.out.println("9. Ulozit zamestnance do souboru");
            System.out.println("10. Nacist zamestnance ze souboru");
            System.out.println("0. Konec programu");
            System.out.print("\nVase volba: ");
            
            int volba = scanner.nextInt();
            scanner.nextLine();
            
            switch (volba) {
                case 1:
                    pridatZamestnance();
                    break;
                case 2:
                    pridatSpolupraci();
                    break;
                case 3:
                    odebratZamestnance();
                    break;
                case 4:
                    vyhledatZamestnance();
                    break;
                case 5:
                    spustitDovednost();
                    break;
                case 6:
                    databaze.vypisAbecednePodleSkupin();
                    break;
                case 7:
                    databaze.zobrazitStatistiky();
                    break;
                case 8:
                    databaze.vypisPocetPodleSkupin();
                    break;
                case 9:
                    ulozitDoSouboru();
                    break;
                case 10:
                    nacistZeSouboru();
                    break;
                case 0:
                    running = false;
                    System.out.println("\nUkoncuji program... Nashledanou!");
                    break;
                default:
                    System.out.println("Neplatna volba!");
            }
        }
    }
    
    private void pridatZamestnance() {
        System.out.println("\n--- PRIDANI ZAMESTNANCE ---");
        System.out.print("Typ (analytik/specialista): ");
        String typ = scanner.nextLine();
        
        System.out.print("Jmeno: ");
        String jmeno = scanner.nextLine();
        
        System.out.print("Prijmeni: ");
        String prijmeni = scanner.nextLine();
        
        System.out.print("Rok narozeni: ");
        int rok = scanner.nextInt();
        scanner.nextLine();
        
        try {
            Zamestnanec novy = databaze.pridatZamestnance(typ, jmeno, prijmeni, rok);
            System.out.println("Zamestnanec byl pridan s ID: " + novy.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }
    
    private void pridatSpolupraci() {
        System.out.println("\n--- PRIDANI SPOLUPRACE ---");
        System.out.print("ID zamestnance: ");
        int id1 = scanner.nextInt();
        System.out.print("ID kolegy: ");
        int id2 = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Uroven (1=spatna, 2=prumerna, 3=dobra): ");
        int urovenHodnota = scanner.nextInt();
        scanner.nextLine();
        
        try {
            UrovenSpoluprace uroven = UrovenSpoluprace.zHodnoty(urovenHodnota);
            if (databaze.pridatSpolupraci(id1, id2, uroven)) {
                System.out.println("Spoluprace byla uspesne pridana.");
            } else {
                System.out.println("Chyba: Jeden ze zamestnancu neexistuje nebo se jedna o stejneho cloveka.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }
    
    private void odebratZamestnance() {
        System.out.println("\n--- ODEBRANI ZAMESTNANCE ---");
        System.out.print("ID zamestnance: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        if (databaze.odebratZamestnance(id)) {
            System.out.println("Zamestnanec byl uspesne odebran.");
        } else {
            System.out.println("Zamestnanec s ID " + id + " nebyl nalezen.");
        }
    }
    
    private void vyhledatZamestnance() {
        System.out.println("\n--- VYHLEDANI ZAMESTNANCE ---");
        System.out.print("ID zamestnance: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        Zamestnanec z = databaze.najdiPodleId(id);
        if (z == null) {
            System.out.println("Zamestnanec s ID " + id + " nebyl nalezen.");
            return;
        }
        
        System.out.println("\nZAKLADNI INFORMACE:");
        System.out.println(z);
        System.out.println("\nSTATISTIKY SPOLUPRACE:");
        System.out.println("  Pocet spolupracovniku: " + z.getPocetSpolupracovniku());
        System.out.printf("  Prumerna uroven spoluprace: %.2f\n", z.getPrumernaUrovenSpoluprace());
        
        if (!z.getSpolupracovnici().isEmpty()) {
            System.out.println("\nSEZNAM SPOLUPRACOVNIKU:");
            for (Map.Entry<Integer, UrovenSpoluprace> entry : z.getSpolupracovnici().entrySet()) {
                Zamestnanec kolega = databaze.najdiPodleId(entry.getKey());
                if (kolega != null) {
                    System.out.printf("  ID: %d | %s %s | Uroven: %s\n",
                            entry.getKey(), kolega.getJmeno(), kolega.getPrijmeni(), entry.getValue());
                }
            }
        }
    }
    
    private void spustitDovednost() {
        System.out.println("\n--- SPUSTENI DOVEDNOSTI ---");
        System.out.print("ID zamestnance: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        String vysledek = databaze.spustitDovednost(id);
        System.out.println("\n" + vysledek);
    }
    
    private void ulozitDoSouboru() {
        System.out.print("\nNazev souboru (napr. zamestnanci.dat): ");
        String nazev = scanner.nextLine();
        databaze.ulozitDoSouboru(nazev);
    }
    
    private void nacistZeSouboru() {
        System.out.print("\nNazev souboru: ");
        String nazev = scanner.nextLine();
        databaze.nacistZeSouboru(nazev);
    }
}