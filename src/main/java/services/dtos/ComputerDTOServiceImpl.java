package services.dtos;

import mappers.ComputerMapper;
import models.Computer;
import models.dtos.ComputerDTO;
import services.ComputerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerDTOServiceImpl implements ComputerDTOService {

    private ComputerService computerService;
    private ComputerMapper computerMapper;

    /**
     * Default constructor.
     */
    ComputerDTOServiceImpl() {
    }


    @Override
    public List<ComputerDTO> getAll() {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.getAll();
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = computerMapper.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> getPageList(int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.getByPage(page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = computerMapper.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
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
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = computerMapper.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> findByNameAndOrder(String name, String columnName, String orderBy, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = computerService.findByNameAndOrder(name, columnName, orderBy, page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = computerMapper.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    public void setComputerMapper(ComputerMapper computerMapper) {
        this.computerMapper = computerMapper;
    }

    public ComputerMapper getComputerMapper() {
        return computerMapper;
    }

    public void setComputerService(ComputerService computerService) {
        this.computerService = computerService;
    }

    public ComputerService getComputerService() {
        return computerService;
    }
}
