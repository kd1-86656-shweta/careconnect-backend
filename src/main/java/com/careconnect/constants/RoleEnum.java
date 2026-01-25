package com.careconnect.constants;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum RoleEnum {
    SUPER_ADMIN(1, "Super Admin", "Overall view of application"),
    DOCTOR(2, "Task Basic User", "Action is Task Basic Update"),
    PATIENT(3, "BU Admin", "Backup BU Admin Role + BU Remove Action");


    private final int roleId;
    private final String roleName;
    private final String roleDesc;

    RoleEnum(int roleId, String roleName, String roleDesc) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public static RoleEnum valueOf(int roleId) {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (roleEnum.getRoleId() == roleId) {
                return roleEnum;
            }
        }
        throw new IllegalArgumentException("No role found for id: " + roleId);
    }


    public static List<RoleEnum> getAllRoles() {
        return Arrays.asList(RoleEnum.values());
    }

}


