package ua.kpi.nc.persistence.dto;

/**
 * Created by IO on 05.05.2016.
 */
public class CurrentAuthUser {
    private Long id;
    private String firstName;

    public CurrentAuthUser(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
