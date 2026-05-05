package module;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class Employee implements Serializable {
    private int id;           
    private String name;
    private String surname;
    private int year;
    public Map<Integer, CooperationLevel> cooperations = new HashMap<>();
    public Map<Integer, CooperationLevel> getCooperations(){
    		return cooperations;
    }

    public Employee(int id, String name, String surname, int year) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.year = year;
    }

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}
	
	public int getYear() {
		return year;
	}
	
	public abstract String getGroup();
	

}
