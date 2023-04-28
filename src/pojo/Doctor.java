package pojo;

import java.io.Serializable;

public class Doctor implements Serializable {
    private int iD=0;
    private String name=null;
    private boolean[] timeslots;
    private String[]patients;

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean[] getTimeslots() {
        return timeslots;
    }

    public void setTimeslots(boolean[] timeslots) {
        this.timeslots = timeslots;
    }

    public String[] getPatients() {
        return patients;
    }

    public void setPatients(String[] patients) {
        this.patients = patients;
    }
}
