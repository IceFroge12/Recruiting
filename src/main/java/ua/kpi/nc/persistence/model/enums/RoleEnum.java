package ua.kpi.nc.persistence.model.enums;

import ua.kpi.nc.controller.auth.UserAuthority;
import ua.kpi.nc.persistence.model.User;

/**
 * Created by dima on 26.04.16.
 */
public enum RoleEnum {
    ROLE_ADMIN, ROLE_STUDENT, ROLE_SOFT, ROLE_TECH;


    public UserAuthority asAuthorityFor(final User user) {
        final UserAuthority authority = new UserAuthority();
        authority.setAuthority("ROLE_" + toString());
        authority.setUser(user);
        return authority;
    }

    public static RoleEnum valueOf(final UserAuthority authority) {
        switch (authority.getAuthority()) {
            case "ROLE_ADMIN":
                return ROLE_ADMIN;
            case "ROLE_STUDENT":
                return ROLE_STUDENT;
        }
        throw new IllegalArgumentException("No role defined for user: " + authority.getAuthority());
    }

    public static String valueOf(RoleEnum role){
        switch (role) {
            case ROLE_ADMIN:
                return "ROLE_ADMIN";
            case ROLE_STUDENT:
                return "ROLE_STUDENT";
        }
        throw new IllegalArgumentException("No role defined for");
    }


}
