package mappers;

import models.Company;
import models.dtos.CompanyDTO;

/**
 * Created by ebiz on 22/03/17.
 */
public class CompanyMapper {

    /**
     * Default constructor.
     */
    CompanyMapper() {
    }

    /**
     * Map companyDTO to company.
     *
     * @param companyDTO companyDTO.
     * @return mapped company from companyDTO.
     */
    public static Company from(CompanyDTO companyDTO) {
        Company company = new Company(Long.valueOf(companyDTO.getId()), companyDTO.getName());
        return company;
    }

    /**
     * Map company to companyDTO.
     *
     * @param company company.
     * @return mapped companyDTO from company.
     */
    public static CompanyDTO from(Company company) {
        CompanyDTO companyDTO = new CompanyDTO(company.getId().intValue(), company.getName());
        return companyDTO;
    }
}
