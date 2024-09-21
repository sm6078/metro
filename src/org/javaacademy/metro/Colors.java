package org.javaacademy.metro;

/**
 * enum цвет линии
 */
public enum Colors {
    RED(1, "Красная"),
    BLUE(2, "Синяя"),
    GREEN(3, "Зеленая");

    private final int id;
    private final String color;

    Colors(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
}
