package services.dtos;

import models.dtos.CompanyDTO;

import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public interface CompanyDTOService {

    /**
     * Get all companies DTO.
     *
     * @return list of companyDTO.
     */
    List<CompanyDTO> getAll();

    /**
     * Get companies DTO by pagination.
     *
     * @param page pagination number.
     * @return list of companyDTO by pagination.
     */
    List<CompanyDTO> getPageList(int page);

    /**
     * Find companyDTO by id.
     *
     * @param id companyDTO id.
     * @return companyDTO.
     */
    CompanyDTO findById(int id);

}
