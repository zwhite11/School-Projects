package com.jhuep.web_development.pizzapolis.entity;
// Generated May 9, 2017 4:30:58 PM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Toppings generated by hbm2java
 */
public class Toppings implements java.io.Serializable {

    private Integer id;
    private String name;
    private Double price;
    private String pizzaCoverage;
    private Set<Pizza> pizzas = new HashSet<Pizza>(0);

    public Toppings() {
    }

    public Toppings(String name, Double price, String pizzaCoverage, Set<Pizza> pizzas) {
        this.name = name;
        this.price = price;
        this.pizzaCoverage = pizzaCoverage;
        this.pizzas = pizzas;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPizzaCoverage() {
        return this.pizzaCoverage;
    }

    public void setPizzaCoverage(String pizzaCoverage) {
        this.pizzaCoverage = pizzaCoverage;
    }

    public Set<Pizza> getPizzas() {
        return this.pizzas;
    }

    public void setPizzas(Set<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.name);
        hash = 73 * hash + Objects.hashCode(this.price);
        hash = 73 * hash + Objects.hashCode(this.pizzaCoverage);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Toppings other = (Toppings) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.pizzaCoverage, other.pizzaCoverage)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Toppings{" + "id=" + id + ", name=" + name + ", price=" + price + ", pizzaCoverage=" + pizzaCoverage + '}';
    }

}