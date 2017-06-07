package com.ebiz.cdb.api;

import com.ebiz.cdb.binding.dto.ComputerDTO;
import com.ebiz.cdb.binding.mapper.ComputerMapper;
import com.ebiz.cdb.binding.validator.ComputerDTOValidator;
import com.ebiz.cdb.core.models.Computer;
import com.ebiz.cdb.service.CompanyService;
import com.ebiz.cdb.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/computers")
public class ComputerRestController {

    public final String BaseLocation = "/api/computer";

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("com.ebiz.cdb.api.ComputerRestController");

    private final CompanyService companyService;
    private final ComputerService computerService;
    private final ComputerDTOValidator computerDTOValidator;
    private final ComputerMapper computerMapper;

    /**
     * ComputerRestController constructor.
     *
     * @param companyService       autowired companyService
     * @param computerService      autowired computerService
     * @param computerDTOValidator autowired computerDTOValidator
     * @param computerMapper       autowired computerMapper
     */
    @Autowired
    public ComputerRestController(CompanyService companyService, ComputerService computerService, ComputerDTOValidator computerDTOValidator, ComputerMapper computerMapper) {
        this.companyService = companyService;
        this.computerService = computerService;
        this.computerDTOValidator = computerDTOValidator;
        this.computerMapper = computerMapper;
    }


    /**
     * Get computer by id.
     *
     * @param id computer id
     * @return List of computer
     */
    @GetMapping("/{id}")
    public Computer getById(@PathVariable("id") Long id) {
        LOGGER.info("[GET rest] get computer by id");
        return computerService.findById(id);
    }

    /**
     * Get computer by id.
     *
     * @param name             computer name
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return List of computer
     */
    @GetMapping("/name/{name}")
    public List<Computer> getByName(@PathVariable("name") String name, @PathVariable("page") int page, @PathVariable("nbComputerByPage") int nbComputerByPage) {
        LOGGER.info("[GET rest] get computer by name or company name");
        return computerService.findByName(name, page, nbComputerByPage);
    }

    /**
     * Get all computers by page.
     *
     * @param page             page number.
     * @param nbComputerByPage number of computers displayed by page.
     * @return computer list by page.
     */
    @GetMapping
    public List<Computer> getByPage(@RequestParam("page") int page, @RequestParam("size") int nbComputerByPage) {
        LOGGER.info("[GET rest] get computers by page");
        return computerService.getByPage(page, nbComputerByPage);
    }

    /**
     * Count all computers.
     *
     * @return number of computers.
     */
    @GetMapping("/count")
    public int count() {
        LOGGER.info("[GET rest] count computers");
        return computerService.count();
    }

    /**
     * Count computers by name or company name.
     *
     * @param name computer or company name.
     * @return number of computers.
     */
    @GetMapping("/count/{name}")
    public int countByName(@PathVariable("name") String name) {
        LOGGER.info("[GET rest] count computers ny name");
        return computerService.countByName(name);
    }

    /**
     * Create computer.
     *
     * @param computer computerDTO.
     * @return computer.
     */
    @PostMapping
    public Computer create(@RequestBody ComputerDTO computer) {
        return computerService.create(computerMapper.to(computer));
    }

    /**
     * Update computer.
     *
     * @param computer computer.
     * @return computer.
     */
    @PutMapping
    public Computer update(@RequestBody ComputerDTO computer) {
        return computerService.update(computerMapper.to(computer));
    }

    /**
     * Delete computer.
     *
     * @param id computer id
     * @return computer id.
     */
    @DeleteMapping
    public Long delete(@RequestParam("id") int id) {
        LOGGER.info("[GET rest] delete computer");
        return computerService.delete(id);
    }
}
