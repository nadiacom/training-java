package models.dtos;

/**
 * Created by ebiz on 22/03/17.
 */

public class CompanyDTO {

    public int id;
    public String name;

    /**
    * Default constructor.
    */
    public CompanyDTO() {

    }

    /**
     * Overloaded constructor.
     *
     * @param name (required) company name.
     */
    public CompanyDTO(String name) {
        this.name = name;
    }

    /**
     * Overloaded constructor.
     *
     * @param id (required) company id.
     * @param name (required) company name.
     */
    public CompanyDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

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
}