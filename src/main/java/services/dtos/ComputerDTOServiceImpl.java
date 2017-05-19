package services.dtos;

import mappers.ComputerMapper;
import models.Computer;
import models.dtos.ComputerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.ComputerService;

import java.util.List;

@Service
public class ComputerDTOServiceImpl implements ComputerDTOService {

    private final ComputerService computerService;
    private ComputerMapper computerMapper = new ComputerMapper();

    /**
     * ComputerDTOServiceImpl constructor.
     *
     * @param computerService autowired computerService
     */
    @Autowired
    public ComputerDTOServiceImpl(ComputerService computerService) {
        this.computerService = computerService;
    }

    @Override
    public List<ComputerDTO> getAll() {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.getAll();
        List<ComputerDTO> companiesDTO = computerMapper.fromList(computers);
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> getPageList(int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.getByPage(page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = computerMapper.fromList(computers);
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public int count() {
        return computerService.count();
    }

    @Override
    public int countByName(String name) {
        return computerService.countByName(name);
    }

    @Override
    public ComputerDTO findById(int id) {
        Computer computer = computerService.findById(Long.valueOf(id));
        return computerMapper.from(computer);
    }

    @Override
    public List<ComputerDTO> findByName(String name, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.findByName(name, page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = computerMapper.fromList(computers);
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> findByNameAndOrder(String name, String columnName, String orderBy, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.findByNameAndOrder(name, columnName, orderBy, page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = computerMapper.fromList(computers);
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> findByOrder(String columnName, String orderBy, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.findByOrder(columnName, orderBy, page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = computerMapper.fromList(computers);
        //Return Computer DTO list
        return companiesDTO;
    }
}
