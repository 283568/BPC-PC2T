package module;

public class DataAnalyst extends Employee{
	public DataAnalyst(int id, String name, String surname, int year) {
        super(id, name, surname, year);
    }
	
    @Override
    public String getGroup() {
        return "Datový analytik";
    }
    
}
