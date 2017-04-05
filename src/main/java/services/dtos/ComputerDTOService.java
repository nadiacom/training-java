package services.dtos;

import models.dtos.ComputerDTO;

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
     * @param page             page number.
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
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return list of ComputerDTO by page.
     */
    List<ComputerDTO> getPageList(int page, int nbComputerByPage);

    /**
     * Get ComputerDTO by page, name and order by column.
     *
     * @param name             computer DTO name.
     * @param columnName       column name.
     * @param orderBy          order by: "ASC" or "DESC".
     * @param page             page number.
     * @param nbComputerByPage number of computers DTO displayed by page.
     * @return list of computers DTO.
     */
    List<ComputerDTO> findByNameAndOrder(String name, String columnName, String orderBy, int page, int nbComputerByPage);

    /**
     * Count number of ComputerDTO.
     *
     * @return number of ComputerDTO.
     */
    int count();

    /**
     * Count computers DTO with given name.
     *
     * @param name computer DTO name.
     * @return list of computers DTO.
     */
    int countByName(String name);
}
