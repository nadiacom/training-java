package services.dtos;

import mappers.CompanyMapper;
import models.Company;
import models.dtos.CompanyDTO;
import services.CompanyService;
import services.validators.inputs.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public class CompanyDTOServiceImpl implements CompanyDTOService {

    static Input input = new Input();
    private CompanyService companyService;
    private CompanyMapper companyMapper;

    /**
     * Default constructor.
     */
    CompanyDTOServiceImpl() {
    }

    @Override
    public List<CompanyDTO> getAll() {
        //Get all companies (from DAO)
        List<Company> companies = companyService.getAll();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            //Map each company to companyDTO model
            CompanyDTO companyDTO = companyMapper.from(companies.get(i));
            companiesDTO.add(companyDTO);
        }
        //Return company DTO list
        return companiesDTO;
    }

    @Override
    public List<CompanyDTO> getPageList(int page) {
        //Get all companies (from DAO)
        List<Company> companies = companyService.getPageList(page);
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            //Map each company to companyDTO model
            CompanyDTO companyDTO = companyMapper.from(companies.get(i));
            companiesDTO.add(companyDTO);
        }
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
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            //Map each company to companyDTO model
            CompanyDTO companyDTO = companyMapper.from(companies.get(i));
            companiesDTO.add(companyDTO);
        }
        //Return company DTO list
        return companiesDTO;
    }


    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyMapper(CompanyMapper companyMapper) {
        this.companyMapper = companyMapper;
    }

    public CompanyMapper getCompanyMapper() {
        return companyMapper;
    }
}
