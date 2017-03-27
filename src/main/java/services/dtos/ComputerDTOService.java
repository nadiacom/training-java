package services.dtos;

import exceptions.daos.DAOException;
import models.Computer;
import models.dtos.ComputerDTO;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public interface ComputerDTOService {

    /**
     * Find ComputerDTO by id.
     *
     * @param id (required) ComputerDTO id.
     * @return ComputerDTO.
     */
    ComputerDTO findById(int id);

    /**
     * Find computers DTO by name.
     *
     * @param name             computer DTO name.
     * @param page             (required) page number.
     * @param nbComputerByPage number of computers DTO displayed by page.
     * @return list of computers DTO.
     */
    List<ComputerDTO> findByName(String name, int page, int nbComputerByPage);

    /**
     * Get all ComputerDTO.
     *
     * @return list of all ComputerDTO.
     */
    List<ComputerDTO> getAll();

    /**
     * Get all ComputerDTO by page.
     *
     * @param page (required) page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of ComputerDTO by page.
     */
    List<ComputerDTO> getPageList(int page, int nbComputerByPage);

    /**
     * Get number of ComputerDTO.
     *
     * @return number of ComputerDTO.
     */
    int getNumberComputersDTO();
}
