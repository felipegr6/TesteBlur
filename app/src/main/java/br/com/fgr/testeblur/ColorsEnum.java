package br.com.fgr.testeblur;

public enum ColorsEnum {

    LIGHT_GRAY("#D3D3D3"),
    SAPPHIRE("#0062B6"),
    ALPHA_SAPPHIRE("#660062B6"),
    DIAMOND("#1E3B60"),
    ALPHA_DIAMOND("#661E3B60"),
    TOPAZ("#06B1EE"),
    ALPHA_TOPAZ("#6606B1EE");

    private String color;

    ColorsEnum(String color) {
        this.color = color;
    }

    public String getColors() {
        return this.color;
    }

}