package services.dtos;

import mappers.CompanyMapper;
import models.Company;
import models.dtos.CompanyDTO;
import persistence.daos.CompanyDaoImpl;
import services.validators.inputs.Input;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public class CompanyDTOServiceImpl implements CompanyDTOService {

    static Input input = new Input();

    /**
     * Default constructor.
     */
    private CompanyDTOServiceImpl() {
    }

    private static class SingletonHelper {
        private static final CompanyDTOServiceImpl INSTANCE = new CompanyDTOServiceImpl();
    }

    public static CompanyDTOServiceImpl getInstance() {
        return CompanyDTOServiceImpl.SingletonHelper.INSTANCE;
    }

    @Override
    public List<CompanyDTO> getAll() {
        //Get all companies (from DAO)
        List<Company> companies = CompanyDaoImpl.getInstance().getAll();
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            //Map each company to companyDTO model
            CompanyDTO companyDTO = CompanyMapper.getInstance().from(companies.get(i));
            companiesDTO.add(companyDTO);
        }
        //Return company DTO list
        return companiesDTO;
    }

    @Override
    public List<CompanyDTO> getPageList(int page) {
        //Get all companies (from DAO)
        List<Company> companies = CompanyDaoImpl.getInstance().getPageList(page);
        List<CompanyDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            //Map each company to companyDTO model
            CompanyDTO companyDTO = CompanyMapper.getInstance().from(companies.get(i));
            companiesDTO.add(companyDTO);
        }
        //Return company DTO list
        return companiesDTO;
    }

    @Override
    public CompanyDTO findById(int id) {
        Company company = CompanyDaoImpl.getInstance().findById(Long.valueOf(id));
        return CompanyMapper.getInstance().from(company);
    }
}
