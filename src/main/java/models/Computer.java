package main.java.models;

import  java.sql.Timestamp;

/**
 * Created by ebiz on 14/03/17.
 */
public class Computer {

    public Long id;
    public String name;
    public Timestamp introduced;
    public Timestamp discontinued;
    public Long company_id;

    public Computer() {
    }

    public Computer(String name, Timestamp introduced, Timestamp discontinued, Long company_id) {
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company_id = company_id;
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

    public Timestamp getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Timestamp introduced) {
        this.introduced = introduced;
    }

    public Timestamp getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Timestamp discontinued) {
        this.discontinued = discontinued;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }
}
