package main.java.models;

import org.omg.CORBA.TIMEOUT;

import  java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by ebiz on 14/03/17.
 */
public class Computer {

    private Long id;
    private String name;
    private LocalDateTime introduced;
    private LocalDateTime discontinued;
    private Company company;

    private Computer(){

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

    public LocalDateTime getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDateTime introduced) {
        this.introduced = introduced;
    }

    public LocalDateTime getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDateTime discontinued) {
        this.discontinued = discontinued;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduced=" + introduced +
                ", discontinued=" + discontinued +
                ", company=" + company +
                '}';
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public static class ComputerBuilder {
        private Computer c;

        public ComputerBuilder(){
            c = new Computer();
        }

        public ComputerBuilder id(Long id){
            c.setId(id);
            return this;
        }
        public ComputerBuilder name(String name){
            c.setName(name);
            return this;
        }
        public ComputerBuilder introduced(LocalDateTime introduced){
            if(introduced!=null){ c.setIntroduced(introduced); }
            else { c.setIntroduced(null); }
            return this;
        }
        public ComputerBuilder discontinued(LocalDateTime discontinued){
            if(discontinued!=null){ c.setDiscontinued(discontinued); }
            else {c.setDiscontinued(null);}
            return this;
        }
        public ComputerBuilder company(Company company){
            c.setCompany(company);
            return this;
        }
        public Computer build() {
            return c;
        }
    }
}
