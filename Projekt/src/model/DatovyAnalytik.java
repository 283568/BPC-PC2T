package model;

import java.util.*;


public class DatovyAnalytik extends Zamestnanec {
    private static final long serialVersionUID = 1L;
    
    public DatovyAnalytik(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }
    
   
    @Override
    public String spustitDovednost(Map<Integer, Zamestnanec> vsichniZamestnanci) {
        if (getSpolupracovnici().isEmpty()) {
            return "Nemám žádné spolupracovníky, nelze provést analýzu.";
        }
        
        int nejlepsiKolega = -1;
        int maxSpolecnych = 0;
        
        for (Map.Entry<Integer, UrovenSpoluprace> entry : getSpolupracovnici().entrySet()) {
            int idKolegy = entry.getKey();
            Zamestnanec kolega = vsichniZamestnanci.get(idKolegy);
            
            if (kolega == null) continue;
            
            int spolecni = 0;
            for (int idMujSpol : getSpolupracovnici().keySet()) {
                if (kolega.getSpolupracovnici().containsKey(idMujSpol)) {
                    spolecni++;
                }
            }
            
            if (spolecni > maxSpolecnych) {
                maxSpolecnych = spolecni;
                nejlepsiKolega = idKolegy;
            }
        }
        
        if (nejlepsiKolega != -1) {
            Zamestnanec nejlepsi = vsichniZamestnanci.get(nejlepsiKolega);
            return String.format("Nejvíce společných spolupracovníků (%d) mám s: %s %s (ID: %d)",
                    maxSpolecnych, nejlepsi.getJmeno(), nejlepsi.getPrijmeni(), nejlepsi.getId());
        }
        
        return "Analýza nevrátila žádný výsledek.";
    }
    
    @Override
    public String toString() {
        return "[Datový analytik] " + super.toString();
    }
}