package com.vehicle.app.enums;

public enum Roles {
    SUPERADMIN(1L, "SA"), ADMIN(2L, "AD"), CLIENT(3L, "CL"), TRANSPORTER(4L, "TP");

    private Long id;
    private String alisa;

    Roles(Long id, String alisa) {
        this.id = id;
        this.alisa = alisa;
    }

    public static Roles getChildRole(String roleAlisa) {
        switch (roleAlisa) {
            case "SA":
                return ADMIN;
            case "AD":
                return CLIENT;
            case "CL":
                return TRANSPORTER;
            default:
                return null;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlisa() {
        return alisa;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
    }
}
