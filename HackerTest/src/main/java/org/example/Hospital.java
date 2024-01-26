package org.example;

import jakarta.persistence.*;

@Entity
public class Hospital {
    @Id /*@GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private int id;
    @Column String name;
    @Column String city;
    @Column double rating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
