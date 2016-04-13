package ua.kpi.nc.model;

import java.util.Date;

/**
 * Created by dima on 12.04.16.
 */
public class User {

    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Date interviewRangeStart;
    private Date interviewRangeEnd;

    public User() {
    }

    public User(long id, String username, String password, String firstName, String lastName, Date interviewRangeStart, Date interviewRangeEnd) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.interviewRangeStart = interviewRangeStart;
        this.interviewRangeEnd = interviewRangeEnd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getInterviewRangeStart() {
        return interviewRangeStart;
    }

    public void setInterviewRangeStart(Date interviewRangeStart) {
        this.interviewRangeStart = interviewRangeStart;
    }

    public Date getInterviewRangeEnd() {
        return interviewRangeEnd;
    }

    public void setInterviewRangeEnd(Date interviewRangeEnd) {
        this.interviewRangeEnd = interviewRangeEnd;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", interviewRangeStart=" + interviewRangeStart +
                ", interviewRangeEnd=" + interviewRangeEnd +
                '}';
    }
}
