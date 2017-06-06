package com.ebiz.cdb.api;

import com.ebiz.cdb.core.models.Company;
import com.ebiz.cdb.service.CompanyService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

    public final String BaseLocation = "/api/computer";

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("com.ebiz.cdb.api.ComputerRestController");

    private final CompanyService companyService;

    /**
     * ComputerRestController constructor.
     *
     * @param companyService autowired companyService
     */
    @Autowired
    public CompanyRestController(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Get all companies.
     *
     * @return List of companies
     */
    @GetMapping
    public List<Company> getAll() {
        LOGGER.info("[GET rest] get computer by id");
        return companyService.getAll();
    }
}
