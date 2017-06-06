package com.ebiz.cdb.api;

import com.ebiz.cdb.binding.dto.ComputerDTO;
import com.ebiz.cdb.binding.mapper.ComputerMapper;
import com.ebiz.cdb.binding.validator.ComputerDTOValidator;
import com.ebiz.cdb.core.models.Computer;
import com.ebiz.cdb.core.models.PageRequest;
import com.ebiz.cdb.service.CompanyService;
import com.ebiz.cdb.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
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
     * @param computerDTO computerDTO.
     * @param result      result.
     * @return computer id.
     * @throws MalformedURLException MalformedURLException
     * @throws URISyntaxException    URISyntaxException
     */
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody @Validated ComputerDTO computerDTO, BindingResult result) throws MalformedURLException, URISyntaxException {
        LOGGER.info("[GET rest] create computer");
        String location = BaseLocation + "/create";
        URL url = new URL(location);
        URI uri = url.toURI();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uri);
        if (!result.hasErrors()) {
            return new ResponseEntity<Computer>(computerService.create(computerMapper.to(computerDTO)), responseHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update computer.
     *
     * @param computerDTO computerDTO.
     * @param result      result.
     * @return computer id.
     * @throws MalformedURLException MalformedURLException
     * @throws URISyntaxException    URISyntaxException
     */
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody @Validated ComputerDTO computerDTO, BindingResult result) throws MalformedURLException, URISyntaxException {
        LOGGER.info("[GET rest] update computer");
        String location = BaseLocation + "/update";
        URL url = new URL(location);
        URI uri = url.toURI();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(uri);
        if (!result.hasErrors()) {
            return new ResponseEntity<Computer>(computerService.update(computerMapper.to(computerDTO)), responseHeaders, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete computer.
     *
     * @param id computer id
     * @return computer id.
     */
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") int id) {
        LOGGER.info("[GET rest] delete computer");
        return computerService.delete(id);
    }
}
