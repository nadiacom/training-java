package com.ebiz.cdb.binding.service;

import com.ebiz.cdb.binding.dto.ComputerDTO;

import java.util.List;

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
     * Get ComputerDTO by page and order by column.
     *
     * @param columnName       column name.
     * @param orderBy          order by: "ASC" or "DESC".
     * @param page             page number.
     * @param nbComputerByPage number of computers DTO displayed by page.
     * @return list of computers DTO.
     */
    List<ComputerDTO> findByOrder(String columnName, String orderBy, int page, int nbComputerByPage);

    /**
     * Count number of ComputerDTO.
     *
     * @return number of ComputerDTO.
     */
    Long count();

    /**
     * Count computers DTO with given name.
     *
     * @param name computer DTO name.
     * @return list of computers DTO.
     */
    Long countByName(String name);
}
