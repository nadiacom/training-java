package com.ebiz.cdb.controllers;

import com.ebiz.cdb.binding.dto.ComputerDTO;
import com.ebiz.cdb.binding.mapper.ComputerMapper;
import com.ebiz.cdb.binding.service.CompanyDTOService;
import com.ebiz.cdb.binding.service.ComputerDTOService;
import com.ebiz.cdb.binding.validator.ComputerDTOValidator;
import com.ebiz.cdb.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/edit")
public class ComputerEditController {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.webservices.ComputerEditController");
    private final CompanyDTOService companyDTOService;
    private final ComputerDTOService computerDTOService;
    private final ComputerDTOValidator computerDTOValidator;
    private final ComputerService computerService;
    private final ComputerMapper computerMapper;

    /**
     * ComputerEditController constructor.
     *
     * @param companyDTOService    autowired companyDTOService
     * @param computerDTOService   autowired computerDTOService
     * @param computerService      autowired computerService
     * @param computerDTOValidator autowired computerDTOValidator
     * @param computerMapper       autowired computerMapper
     */
    @Autowired
    public ComputerEditController(CompanyDTOService companyDTOService, ComputerDTOService computerDTOService, ComputerService computerService, ComputerDTOValidator computerDTOValidator, ComputerMapper computerMapper) {
        this.companyDTOService = companyDTOService;
        this.computerDTOService = computerDTOService;
        this.computerService = computerService;
        this.computerDTOValidator = computerDTOValidator;
        this.computerMapper = computerMapper;
    }

    /**
     * Get edit page.
     *
     * @param id    id
     * @param model model
     * @return edit page.
     */
    @GetMapping()
    public String get(@RequestParam String id,
                      Model model) {
        //TODO check computer with given id exists
        model.addAttribute("computerDTO", computerDTOService.findById(Integer.valueOf(id)));
        model.addAttribute("companies", companyDTOService.getAll());
        return "editComputer";
    }

    /**
     * Edit computer page.
     *
     * @param computerDTO        computerDTO
     * @param result             result
     * @param model              model
     * @param redirectAttributes redirectAttributes
     * @return page redirection.
     */
    @PostMapping()
    public String edit(Model model, @ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult result, final RedirectAttributes redirectAttributes) {
        //TODO validator
        computerDTOValidator.validate(computerDTO, result);
        if (result.hasErrors()) {
            return "editComputer";
        } else {
            redirectAttributes.addFlashAttribute("message", "success.computer.updated");
            computerService.update(computerMapper.to(computerDTO));
            return "redirect:/dashboard";
        }
    }
}
