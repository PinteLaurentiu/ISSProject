package com.iss.enums;

public enum GrupaSange {
    O1, O1M, A2, A2M, B3, B3M, AB4, AB4M;

    @Override
    public String toString() {
        switch (this) {
            case O1:
                return "0I";
            case O1M:
                return "0I-";
            case A2:
                return "AII";
            case A2M:
                return "AII-";
            case B3:
                return "BIII";
            case B3M:
                return "BIII-";
            case AB4:
                return "ABIV";
            case AB4M:
                return "ABIV-";
        }
        return "";
    }

    public static GrupaSange fromString(String value){
        for (GrupaSange grupaSange : values())
            if (grupaSange.toString().equals(value)) return grupaSange;
        return null;
    }

}
