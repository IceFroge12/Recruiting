package ua.kpi.nc.persistence.dto;

/**
 * Created by Admin on 13.05.2016.
 */
public class StudentAppFormDto {

    private Long id;
    private Long appFormId;
    private String firstName;
    private String lastName;
    private String status;

    public StudentAppFormDto() {
    }

    public StudentAppFormDto(Long id, Long appFormId, String firstName,
                             String lastName, String status) {
        this.id = id;
        this.appFormId = appFormId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppFormId() {
        return appFormId;
    }

    public void setAppFormId(Long appFormId) {
        this.appFormId = appFormId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "StudentAppFormDto{" +
                "id=" + id +
                ", appFormId=" + appFormId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

}
