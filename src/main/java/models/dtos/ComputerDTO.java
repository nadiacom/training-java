package models.dtos;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerDTO {

    private Long id;
    private String name;
    private String introduced;
    private String discontinued;
    private Long companyId;
    private String companyName;

    /**
     * Default constructor.
     */
    private ComputerDTO() {

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

    public Long getCompanyId() {
        return this.companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
        public ComputerDTOBuilder id(Long id) {
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
         * Set computerDTO company id to builder.
         *
         * @param companyId (required) companyId.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder companyId(Long companyId) {
            c.setCompanyId(companyId);
            return this;
        }

        /**
         * Set computerDTO company name to builder.
         *
         * @param companyName (required) companyName.
         * @return computerDTO builder.
         */
        public ComputerDTOBuilder companyName(String companyName) {
            c.setCompanyName(companyName);
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
