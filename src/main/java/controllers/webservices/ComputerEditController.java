package controllers.webservices;

import models.dtos.CompanyDTO;
import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import services.ComputerService;
import services.dtos.CompanyDTOServiceImpl;
import services.dtos.ComputerDTOServiceImpl;
import services.validators.inputs.ComputerValidator;
import services.validators.inputs.Input;
import services.validators.urls.ComputerEditUrlValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.List;


@Controller
@RequestMapping("/edit")
public class ComputerEditController extends HttpServlet {

    private static Input inputValidator = new Input();
    @Autowired
    private ComputerService computerService;
    @Autowired
    private CompanyDTOServiceImpl companyDTOService;
    @Autowired
    private ComputerDTOServiceImpl computerDTOService;
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.webservices.ComputerEditController");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        computerService = (ComputerService) ac.getBean("computerService");
        companyDTOService = (CompanyDTOServiceImpl) ac.getBean("companyDTOService");
        computerDTOService = (ComputerDTOServiceImpl) ac.getBean("computerDTOService");
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
        ComputerEditUrlValidator computerEditUrlValidator = new ComputerEditUrlValidator();
        computerEditUrlValidator.isUrlValid(id);
        String error = computerEditUrlValidator.getError();

        if (error.length() == 0) {
            //Get computer id
            int theId = Integer.valueOf(id);
            //Get computer from id
            ComputerDTO c = computerDTOService.findById(theId);
            //Get companies ids and names
            List<CompanyDTO> companies = companyDTOService.getAll();
            //Set view parameters
            model.addAttribute("computer", c);
            model.addAttribute("companies", companies);
            //Dispatch view
            return "editComputer";
        } else {
            model.addAttribute("errorMsg", error);
            //Dispatch view
            return "redirect:/";
        }
    }

    /**
     * Edit computer page.
     *
     * @param id           id
     * @param name         name
     * @param introduced   introduced
     * @param discontinued discontinued
     * @param companyId    companyId
     * @param model        model
     * @return edit page.
     */
    @PostMapping()
    public String edit(@RequestParam(value = "id") String id,
                       @RequestParam(value = "name") String name,
                       @RequestParam(value = "introduced") String introduced,
                       @RequestParam(value = "discontinued") String discontinued,
                       @RequestParam(value = "companyId") String companyId,
                       Model model
    ) {
        ComputerValidator computerValidator = new ComputerValidator();
        computerService.update(Integer.valueOf(id), name, inputValidator.getLocalDate(introduced), inputValidator.getLocalDate(discontinued), computerValidator.getValidCompanyId(companyId));
        return "redirect:/";
    }
}
