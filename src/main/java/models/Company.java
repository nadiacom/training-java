package main.java.models;

/**
 * Created by ebiz on 14/03/17.
 */
public class Company {


    public Long id;
    public String name;

    public Company() {

    }

    public Company(String name) {
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
}
