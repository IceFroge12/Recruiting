package ua.kpi.nc.domain.model;

/**
 * Created by Chalienko on 14.04.2016.
 */
public interface Student extends Model{
    User getUser();

    void setUser(User user);

    SocialAuth getSocialAuth();

    void setSocialAuth(SocialAuth socialAuth);

    String getFeedback();

    void setFeedback(String feedback);

    String getPhotoPath();

    void setPhotoPath(String photoPath);
}
