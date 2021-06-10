package com.vehicle.app.enums;

public enum Roles {
    SUPERADMIN( "SA"), ADMIN( "AD"), CLIENT( "CL"), TRANSPORTER("TP");

    private String alisa;

    Roles(String alisa) {
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

    public String getAlisa() {
        return alisa;
    }

    public void setAlisa(String alisa) {
        this.alisa = alisa;
    }
}