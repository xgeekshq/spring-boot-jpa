package io.xgeeks.examples.spring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String city;
    @Column
    private String model;
    @Column
    private String color;

    /**
     * @Deprecated use the {@link Car#builder()} instead.
     */
    @Deprecated
    public Car() {
    }

    Car(Long id, String name,
        String city, String model,
        String color) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.model = model;
        this.color = color;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getModel() {
        return model;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Car car = (Car) o;
        return Objects.equals(id, car.id);
    }

    public void update(Car car) {
        this.name = car.name;
        this.city = car.city;
        this.model = car.model;
        this.color = car.color;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public static CarBuilder builder() {
        return new CarBuilder();
    }
}
