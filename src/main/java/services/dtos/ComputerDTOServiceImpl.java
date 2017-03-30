package services.dtos;

import mappers.ComputerMapper;
import models.Computer;
import models.dtos.ComputerDTO;
import persistence.daos.ComputerDaoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerDTOServiceImpl implements ComputerDTOService {

    /**
     * Default constructor.
     */
    private ComputerDTOServiceImpl() {
    }

    private static class SingletonHelper {
        private static final ComputerDTOServiceImpl INSTANCE = new ComputerDTOServiceImpl();
    }

    public static ComputerDTOServiceImpl getInstance() {
        return ComputerDTOServiceImpl.SingletonHelper.INSTANCE;
    }


    @Override
    public List<ComputerDTO> getAll() {
        //Get all companies (from DAO)
        List<Computer> computers = ComputerDaoImpl.getInstance().getAll();
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = ComputerMapper.getInstance().from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public List<ComputerDTO> getPageList(int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = ComputerDaoImpl.getInstance().getPageList(page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = ComputerMapper.getInstance().from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

    @Override
    public int count() {
        return ComputerDaoImpl.getInstance().count();
    }

    @Override
    public int countByName(String name) {
        return ComputerDaoImpl.getInstance().countByName(name);
    }

    @Override
    public ComputerDTO findById(int id) {
        Computer computer = ComputerDaoImpl.getInstance().findById(Long.valueOf(id));
        return ComputerMapper.getInstance().from(computer);
    }

    @Override
    public List<ComputerDTO> findByName(String name, int page, int nbComputerByPage) {
        //Get all companies (from DAO)
        List<Computer> computers = ComputerDaoImpl.getInstance().findByName(name, page, nbComputerByPage);
        List<ComputerDTO> companiesDTO = new ArrayList<>();
        for (int i = 0; i < computers.size(); i++) {
            //Map each Computer to ComputerDTO model
            ComputerDTO computerDTO = ComputerMapper.getInstance().from(computers.get(i));
            companiesDTO.add(computerDTO);
        }
        //Return Computer DTO list
        return companiesDTO;
    }

}
