package services.dtos;

import mappers.CompanyMapper;
import models.Company;
import models.dtos.CompanyDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.CompanyService;
import services.validators.inputs.Input;

import java.util.List;

@Service
public class CompanyDTOServiceImpl implements CompanyDTOService {

    private Input input = new Input();
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
