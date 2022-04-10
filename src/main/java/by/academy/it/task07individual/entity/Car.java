package by.academy.it.task07individual.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 */
@MyTable(name = "car")
public class Car {
    /**
     *
     */
    @MyColumn(name = "identifier")
    private final Integer identifier;
    /**
     *
     */
    @MyColumn(name = "model")
    private final String model;
    /**
     *
     */
    @MyColumn(name = "color")
    private final String color;
    /**
     *
     */
    @MyColumn(name = "price")
    private final BigDecimal price;

    /**
     * @param ident
     * @param mod
     * @param col
     * @param pr
     */
    public Car(final Integer ident,
               final String mod,
               final String col,
               final BigDecimal pr) {
        this.identifier = ident;
        this.model = mod;
        this.color = col;
        this.price = pr;
    }

    /**
     * @param o
     * @return -
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(identifier, car.identifier)
                && Objects.equals(model, car.model)
                && Objects.equals(color, car.color)
                && Objects.equals(price, car.price);
    }

    /**
     * @return - hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(identifier, model, color, price);
    }

    /**
     * @return -
     */
    @Override
    public String toString() {
        return "Car{"
                + "identifier=" + identifier
                + ", model='" + model + '\''
                + ", color='" + color + '\''
                + ", price=" + price
                + '}';
    }
}
