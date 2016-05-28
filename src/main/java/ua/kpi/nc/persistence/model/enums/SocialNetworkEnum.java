package ua.kpi.nc.persistence.model.enums;

import ua.kpi.nc.persistence.model.SocialNetwork;

/**
 * Created by IO on 27.05.2016.
 */
public enum SocialNetworkEnum {
    FaceBook(1L);

    private final Long id;

    SocialNetworkEnum(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static SocialNetwork getSocialNetwork(Long id){
        switch (Math.toIntExact(id)){
            case 1:
                return new SocialNetwork(FaceBook.getId(), "FaceBook");
            default:
                throw new IllegalStateException("Social network not found");
        }
    }

    public static SocialNetwork getSocialNetwork(String title){
        switch (title){
            case "facebookAuth":
                return new SocialNetwork(FaceBook.getId(), "FaceBook");
            default:
                throw new IllegalStateException("Social network not found");
        }
    }
}
