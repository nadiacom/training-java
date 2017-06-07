package com.ebiz.cdb.binding.mapper;

import com.ebiz.cdb.binding.dto.CompanyDTO;
import com.ebiz.cdb.core.models.Company;
import org.springframework.stereotype.Service;

@Service("ComputerMapper")
public class CompanyMapper implements GenericMapper<Company, CompanyDTO> {

    /**
     * Default constructor.
     */
    public CompanyMapper() {
    }

    /**
     * Map companyDTO to company.
     *
     * @param companyDTO companyDTO.
     * @return mapped company from companyDTO.
     */
    public Company to(CompanyDTO companyDTO) {
        Company company = new Company(Long.valueOf(companyDTO.getId() != null ? Long.valueOf(companyDTO.getId()) : null), companyDTO.getName());
        return company;
    }

    /**
     * Map company to companyDTO.
     *
     * @param company company.
     * @return mapped companyDTO from company.
     */
    public CompanyDTO from(Company company) {
        CompanyDTO companyDTO = new CompanyDTO(company.getId(), company.getName());
        return companyDTO;
    }
}