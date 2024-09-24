package org.javaacademy.metro;

/**
 * enum цвет линии
 */
public enum Colors {
    RED("Красная", "red"),
    BLUE("Синяя", "blue"),
    GREEN("Зеленая", "green");

    private final String color;
    private final String translateColor;

    Colors(String color, String translateColor) {
        this.color = color;
        this.translateColor = translateColor;
    }

    public String getColor() {
        return color;
    }

    public String getTranslateColor() {
        return translateColor;
    }
}
