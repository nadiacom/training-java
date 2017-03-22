package models.dtos;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerDTO {

    private int id;
    private String name;
    private String introduced;
    private String discontinued;
    private CompanyDTO companyDTO;

    /**
     * Default constructor.
     */
    private ComputerDTO() {

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

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public CompanyDTO getCompanyDTO() {
        return companyDTO;
    }

    public void setCompanyDTO(CompanyDTO companyDTO) {
        this.companyDTO = companyDTO;
    }

    /**
     * Nested class ComputerDTOBuilder.
     */
    public static class ComputerDTOBuilder {
        private ComputerDTO c;

        /**
         * Constructor.
         */
        public ComputerDTOBuilder() {
            c = new ComputerDTO();
        }

        /**
         * Set computerDTO id to builder.
         *
         * @param id (required) computerDTO id.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder id(int id) {
            c.setId(id);
            return this;
        }

        /**
         * Set computerDTO name to builder.
         *
         * @param name (required) computerDTO name.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder name(String name) {
            c.setName(name);
            return this;
        }

        /**
         * Set computerDTO introduced date to builder.
         *
         * @param introduced (required) date when computerDTO was introduced.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder introduced(String introduced) {
            if (introduced != null) {
                c.setIntroduced(introduced);
            } else {
                c.setIntroduced(null);
            }
            return this;
        }

        /**
         * Set computerDTO discontinued date to builder.
         *
         * @param discontinued (required) date when computerDTO was discontinued.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder discontinued(String discontinued) {
            if (discontinued != null) {
                c.setDiscontinued(discontinued);
            } else {
                c.setDiscontinued(null);
            }
            return this;
        }

        /**
         * Set computerDTO company to builder.
         *
         * @param company (required) companyDTO computerDTO belongs to.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder company(CompanyDTO company) {
            c.setCompanyDTO(company);
            return this;
        }

        /**
         * Build computerDTO.
         *
         * @return computerDTO.
         */
        public ComputerDTO build() {
            return c;
        }
    }
}
