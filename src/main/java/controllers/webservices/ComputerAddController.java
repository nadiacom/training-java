package controllers.webservices;

import mappers.ComputerMapper;
import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.CompanyService;
import services.ComputerService;
import services.validators.ComputerDTOValidator;

import javax.servlet.http.HttpServlet;

@Controller
@RequestMapping("/add")
public class ComputerAddController extends HttpServlet {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controllers.webservices.ComputerAddController");
    private final CompanyService companyService;
    private final ComputerService computerService;
    private final ComputerDTOValidator computerDTOValidator;
    private final ComputerMapper computerMapper;

    /**
     * ComputerAddController constructor.
     *
     * @param companyService       autowired companyService
     * @param computerService      autowired computerService
     * @param computerDTOValidator autowired computerDTOValidator
     * @param computerMapper       autowired computerMapper
     */
    @Autowired
    public ComputerAddController(CompanyService companyService, ComputerService computerService, ComputerDTOValidator computerDTOValidator, ComputerMapper computerMapper) {
        this.companyService = companyService;
        this.computerService = computerService;
        this.computerDTOValidator = computerDTOValidator;
        this.computerMapper = computerMapper;
    }

    /**
     * Get the add computer page.
     *
     * @param model model.
     * @return addComputer page.
     */
    @GetMapping()
    public String get(Model model) {
        model.addAttribute("computerDTO", new ComputerDTO.ComputerDTOBuilder().build());
        model.addAttribute("companies", companyService.getAll());
        return "addComputer";
    }

    /**
     * Add computer post method.
     *
     * @param computerDTO        computerDTO
     * @param result             result
     * @param model              model
     * @param redirectAttributes redirectAttributes
     * @return view redirection
     */
    @PostMapping()
    public String add(Model model, @ModelAttribute("computerDTO") @Validated ComputerDTO computerDTO, BindingResult result, final RedirectAttributes redirectAttributes) {
        //TODO validators
        computerDTOValidator.validate(computerDTO, result);
        if (result.hasErrors()) {
            return "addComputer";
        } else {
            computerService.create(computerMapper.to(computerDTO));
            return "redirect:/dashboard";
        }
    }
}
