package com.ebiz.cdb.core.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "company")
@Table(name = "company")
public class Company implements Serializable {

    @Id
    @GeneratedValue
    public Long id;
    public String name;

    /**
     * Default constructor.
     */
    public Company() {

    }

    /**
     * Overloaded constructor.
     *
     * @param id (required) company id.
     */
    public Company(Long id) {
        this.id = id;
    }

    /**
     * Overloaded constructor.
     *
     * @param name (required) company name.
     */
    public Company(String name) {
        this.name = name;
    }

    /**
     * Overloaded constructor.
     *
     * @param id   (required) company id.
     * @param name (required) company name.
     */
    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
