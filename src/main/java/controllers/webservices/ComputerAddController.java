package controllers.webservices;

import models.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.CompanyService;
import services.ComputerService;
import services.validators.inputs.ComputerValidator;
import services.validators.inputs.Input;

import javax.servlet.http.HttpServlet;
import java.util.List;

@Controller
@RequestMapping("/add")
public class ComputerAddController extends HttpServlet {

    private static Input inputValidator = new Input();
    private final CompanyService companyService;
    private final ComputerService computerService;

    /**
     * ComputerAddController constructor.
     *
     * @param companyService  autowired companyService
     * @param computerService autowired computerService
     */
    @Autowired
    public ComputerAddController(CompanyService companyService, ComputerService computerService) {
        this.companyService = companyService;
        this.computerService = computerService;
    }

    /**
     * Get the add computer page.
     *
     * @param model model.
     * @return addComputer page.
     */
    @GetMapping()
    public String get(Model model) {
        //Get companies ids and names
        List<Company> companies = companyService.getAll();
        model.addAttribute("companies", companies);
        return "addComputer";

    }

    /**
     * Add computer post method.
     *
     * @param name         computer name
     * @param introduced   date when computer was introduced
     * @param discontinued date when computer was discontinued
     * @param companyId    company id of computer
     * @param model        model
     * @return dashboard view redirection
     */
    @PostMapping()
    public String add(@RequestParam(value = "name") String name,
                      @RequestParam(value = "introduced") String introduced,
                      @RequestParam(value = "discontinued") String discontinued,
                      @RequestParam(value = "companyId") String companyId,
                      Model model
    ) {
        ComputerValidator computerValidator = new ComputerValidator();
        computerService.create(name, inputValidator.getLocalDate(introduced), inputValidator.getLocalDate(discontinued), computerValidator.getValidCompanyId(companyId));
        return "redirect:/";
    }
}
