package ua.kpi.nc.util.scheduling;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by natalya on 14.05.2016.
 */
public class User {
    private String firstName;
    private String role;
    private ArrayList<Timestamp> timesAndDates = new ArrayList<>();

    public User(String firstName, String role, ArrayList<Timestamp> timesAndDates) {
        this.firstName = firstName;
        this.role = role;
        this.timesAndDates = timesAndDates;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public ArrayList<Timestamp> getTimesAndDates() {
        return timesAndDates;
    }

    public void setTimesAndDates(ArrayList<Timestamp> timesAndDates) {
        this.timesAndDates = timesAndDates;
    }

    @Override
    public String toString() {
        return firstName + ", role='" + role + '\'' +
                ", timesAndDates=" + timesAndDates +
                " --|||||-- ";
    }
}