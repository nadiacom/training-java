package com.ebiz.cdb.binding.service;

import com.ebiz.cdb.binding.dto.CompanyDTO;
import com.ebiz.cdb.binding.mapper.CompanyMapper;
import com.ebiz.cdb.binding.utils.DatePatternUtils;
import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyDTOServiceImpl implements CompanyDTOService {

    private DatePatternUtils datePatternUtils = new DatePatternUtils();
    private final CompanyService companyService;
    private CompanyMapper companyMapper = new CompanyMapper();

    /**
     * CompanyDTOServiceImpl constructor.
     *
     * @param companyService autowired companyService
     */
    @Autowired
    CompanyDTOServiceImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @Override
    public List<CompanyDTO> getAll() {
        //Get all companies (from DAO)
        List<Company> companies = companyService.getAll();
        List<CompanyDTO> companiesDTO = companyMapper.fromList(companies);
        //Return company DTO list
        return companiesDTO;
    }

    @Override
    public List<CompanyDTO> getPageList(int page) {
        //Get all companies (from DAO)
        List<Company> companies = companyService.getPageList(page);
        List<CompanyDTO> companiesDTO = companyMapper.fromList(companies);
        //Return company DTO list
        return companiesDTO;
    }

    @Override
    public CompanyDTO findById(int id) {
        Company company = companyService.findById(Long.valueOf(id));
        return companyMapper.from(company);
    }

    @Override
    public List<CompanyDTO> findByName(String name, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Company> companies = companyService.findByName(name, page, nbComputerByPage);
        List<CompanyDTO> companiesDTO = companyMapper.fromList(companies);
        //Return company DTO list
        return companiesDTO;
    }
}
