package by.academy.it.task07individual.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
@MyTable(name="car")
public class Car {
    @MyColumn(name = "identifier")
    private Integer identifier;
    @MyColumn(name = "model")
    private String model;
    @MyColumn(name = "color")
    private String color;
    @MyColumn(name = "price")
    private BigDecimal price;
}
