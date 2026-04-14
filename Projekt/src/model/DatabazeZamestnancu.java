package model;

import java.io.*;
import java.util.*;

public class DatabazeZamestnancu {
    
    private Map<Integer, Zamestnanec> zamestnanci;
    private int dalsiId;
    
    public DatabazeZamestnancu() {
        this.zamestnanci = new HashMap<>();
        this.dalsiId = 1;
    }
    
    public Zamestnanec pridatZamestnance(String typ, String jmeno, String prijmeni, int rokNarozeni) {
        int id = dalsiId++;
        
        Zamestnanec novy;
        if ("analytik".equalsIgnoreCase(typ)) {
            novy = new DatovyAnalytik(id, jmeno, prijmeni, rokNarozeni);
        } else if ("specialista".equalsIgnoreCase(typ)) {
            novy = new BezpecnostniSpecialista(id, jmeno, prijmeni, rokNarozeni);
        } else {
            throw new IllegalArgumentException("Neznamy typ: " + typ);
        }
        
        zamestnanci.put(id, novy);
        return novy;
    }
    
    public boolean pridatSpolupraci(int idZamestnance, int idKolegy, UrovenSpoluprace uroven) {
        Zamestnanec zamestnanec = zamestnanci.get(idZamestnance);
        Zamestnanec kolega = zamestnanci.get(idKolegy);
        
        if (zamestnanec == null || kolega == null) {
            return false;
        }
        
        if (idZamestnance == idKolegy) {
            return false;
        }
        
        zamestnanec.pridatSpolupraci(idKolegy, uroven);
        kolega.pridatSpolupraci(idZamestnance, uroven);
        return true;
    }
    
    public boolean odebratZamestnance(int id) {
        Zamestnanec odebirany = zamestnanci.remove(id);
        if (odebirany == null) {
            return false;
        }
        
        for (Zamestnanec z : zamestnanci.values()) {
            z.odebratSpolupraci(id);
        }
        return true;
    }
    
    public Zamestnanec najdiPodleId(int id) {
        return zamestnanci.get(id);
    }
    
    public String spustitDovednost(int id) {
        Zamestnanec z = zamestnanci.get(id);
        if (z == null) {
            return "Zamestnanec s ID " + id + " nebyl nalezen.";
        }
        return z.spustitDovednost(zamestnanci);
    }
    
    public void vypisAbecednePodleSkupin() {
        List<Zamestnanec> analytici = new ArrayList<>();
        List<Zamestnanec> specialisti = new ArrayList<>();
        
        for (Zamestnanec z : zamestnanci.values()) {
            if (z instanceof DatovyAnalytik) {
                analytici.add(z);
            } else if (z instanceof BezpecnostniSpecialista) {
                specialisti.add(z);
            }
        }
        
        analytici.sort(Comparator.comparing(Zamestnanec::getPrijmeni));
        specialisti.sort(Comparator.comparing(Zamestnanec::getPrijmeni));
        
        System.out.println("\nDATOVI ANALYTICI:");
        if (analytici.isEmpty()) {
            System.out.println("  (zadni analytici)");
        }
        for (Zamestnanec z : analytici) {
            System.out.printf("  %s %s (ID: %d)%n", z.getPrijmeni(), z.getJmeno(), z.getId());
        }
        
        System.out.println("\nBEZPECNOSTNI SPECIALISTE:");
        if (specialisti.isEmpty()) {
            System.out.println("  (zadni specialisti)");
        }
        for (Zamestnanec z : specialisti) {
            System.out.printf("  %s %s (ID: %d)%n", z.getPrijmeni(), z.getJmeno(), z.getId());
        }
    }
    
    public void zobrazitStatistiky() {
        Map<UrovenSpoluprace, Integer> kvality = new HashMap<>();
        kvality.put(UrovenSpoluprace.SPATNA, 0);
        kvality.put(UrovenSpoluprace.PRUMERNA, 0);
        kvality.put(UrovenSpoluprace.DOBRA, 0);
        
        int celkemVazeb = 0;
        for (Zamestnanec z : zamestnanci.values()) {
            for (UrovenSpoluprace u : z.getSpolupracovnici().values()) {
                kvality.put(u, kvality.get(u) + 1);
                celkemVazeb++;
            }
        }
        
        System.out.println("\nPREVLAZUJICI KVALITA SPOLUPRACE:");
        if (celkemVazeb == 0) {
            System.out.println("  (zadne vazby)");
        } else {
            for (Map.Entry<UrovenSpoluprace, Integer> entry : kvality.entrySet()) {
                double procento = (entry.getValue() * 100.0) / celkemVazeb;
                System.out.printf("  %s: %d vazeb (%.1f%%)%n", 
                    entry.getKey().getPopis(), entry.getValue(), procento);
            }
        }
        
        Zamestnanec nejvice = null;
        int maxVazeb = -1;
        for (Zamestnanec z : zamestnanci.values()) {
            int pocet = z.getPocetSpolupracovniku();
            if (pocet > maxVazeb) {
                maxVazeb = pocet;
                nejvice = z;
            }
        }
        
        System.out.println("\nZAMESTNANEC S NEJVICE VAZBAMI:");
        if (nejvice == null) {
            System.out.println("  (zadni zamestnanci)");
        } else {
            System.out.printf("  %s %s (ID: %d) - %d spolupracovniku%n",
                nejvice.getJmeno(), nejvice.getPrijmeni(), nejvice.getId(), maxVazeb);
        }
    }
    
    public void vypisPocetPodleSkupin() {
        int analytici = 0;
        int specialisti = 0;
        
        for (Zamestnanec z : zamestnanci.values()) {
            if (z instanceof DatovyAnalytik) {
                analytici++;
            } else if (z instanceof BezpecnostniSpecialista) {
                specialisti++;
            }
        }
        
        System.out.println("\nPOCTY ZAMESTNANCU:");
        System.out.println("------------------------");
        System.out.printf("  Datovi analytici: %d%n", analytici);
        System.out.printf("  Bezpecnostni specialiste: %d%n", specialisti);
        System.out.println("------------------------");
        System.out.printf("  CELKEM: %d%n", analytici + specialisti);
    }
    
    public boolean ulozitDoSouboru(String nazevSouboru) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nazevSouboru))) {
            oos.writeObject(zamestnanci);
            oos.writeInt(dalsiId);
            System.out.println("Data ulozena do: " + nazevSouboru);
            return true;
        } catch (IOException e) {
            System.out.println("Chyba pri ukladani: " + e.getMessage());
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public boolean nacistZeSouboru(String nazevSouboru) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nazevSouboru))) {
            zamestnanci = (Map<Integer, Zamestnanec>) ois.readObject();
            dalsiId = ois.readInt();
            System.out.println("Data nactena ze souboru: " + nazevSouboru);
            return true;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Chyba pri nacitani: " + e.getMessage());
            return false;
        }
    }
    
    public Map<Integer, Zamestnanec> getVsechnyZamestnance() {
        return zamestnanci;
    }
}