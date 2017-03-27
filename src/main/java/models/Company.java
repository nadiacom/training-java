package models;

/**
 * Created by ebiz on 14/03/17.
 */
public class Company {


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
     * @param name (required) company name.
     */
    public Company(String name) {
        this.name = name;
    }

    /**
     * Overloaded constructor.
     *
     * @param id (required) company id.
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
