package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ebiz on 14/03/17.
 */
public class Computer {

    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * Default constructor.
     */
    private Computer() {

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

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
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
        public ComputerBuilder introduced(LocalDate introduced) {
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
        public ComputerBuilder discontinued(LocalDate discontinued) {
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
