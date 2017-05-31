package com.ebiz.cdb.core.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity(name = "Computer")
@Table(name = "computer")
public class Computer implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    private LocalDateTime introduced;
    private LocalDateTime discontinued;
    @ManyToOne
    private Company company;

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

    /**
     * Nested class ComputerBuilder.
     */
    public static class ComputerBuilder {
        private Computer c;

        /**
         * Constructor.
         */
        public ComputerBuilder() {
            c = new Computer();
        }

        /**
         * Set computer id to builder.
         *
         * @param id (required) computer id.
         * @return computer builder.
         */
        public ComputerBuilder id(Long id) {
            c.setId(id);
            return this;
        }

        /**
         * Set computer name to builder.
         *
         * @param name (required) computer name.
         * @return computer builder.
         */
        public ComputerBuilder name(String name) {
            c.setName(name);
            return this;
        }

        /**
         * Set computer introduced date to builder.
         *
         * @param introduced (required) date when computer was introduced.
         * @return computer builder.
         */
        public ComputerBuilder introduced(LocalDateTime introduced) {
            if (introduced != null) {
                c.setIntroduced(introduced);
            } else {
                c.setIntroduced(null);
            }
            return this;
        }

        /**
         * Set computer discontinued date to builder.
         *
         * @param discontinued (required) date when computer was discontinued.
         * @return computer builder.
         */
        public ComputerBuilder discontinued(LocalDateTime discontinued) {
            if (discontinued != null) {
                c.setDiscontinued(discontinued);
            } else {
                c.setDiscontinued(null);
            }
            return this;
        }

        /**
         * Set computer company to builder.
         *
         * @param company (required) company computer belongs to.
         * @return computer builder.
         */
        public ComputerBuilder company(Company company) {
            c.setCompany(company);
            return this;
        }

        /**
         * Build computer.
         *
         * @return computer.
         */
        public Computer build() {
            return c;
        }
    }
}
