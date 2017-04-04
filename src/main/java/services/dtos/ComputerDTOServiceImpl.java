package services.dtos;

import mappers.ComputerMapper;
import models.Computer;
import models.dtos.ComputerDTO;
import persistence.daos.ComputerDaoImpl;
import services.ComputerService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public enum ComputerDTOServiceImpl implements ComputerDTOService {

    INSTANCE;

    /**
     * Default constructor.
     */
    ComputerDTOServiceImpl() {
    }


    @Override
    public List<ComputerDTO> getAll() {
        //Get all companies (from DAO)
        List<Computer> computers = ComputerService.INSTANCE.getAll();
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = ComputerMapper.INSTANCE.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> getPageList(int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = ComputerService.INSTANCE.getByPage(page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = ComputerMapper.INSTANCE.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public int count() {
        return ComputerService.INSTANCE.count();
    }

    @Override
    public int countByName(String name) {
        return ComputerService.INSTANCE.countByName(name);
    }

    @Override
    public ComputerDTO findById(int id) {
        Computer computer = ComputerService.INSTANCE.findById(Long.valueOf(id));
        return ComputerMapper.INSTANCE.from(computer);
    }

    @Override
    public List<ComputerDTO> findByName(String name, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = ComputerService.INSTANCE.findByName(name, page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = ComputerMapper.INSTANCE.from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

}
