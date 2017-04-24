package controllers.webservices;

import exceptions.validators.FormException;
import models.dtos.CompanyDTO;
import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import services.ComputerService;
import services.dtos.CompanyDTOServiceImpl;
import services.dtos.ComputerDTOServiceImpl;
import services.validators.inputs.ComputerValidator;
import services.validators.inputs.Input;
import services.validators.urls.ComputerEditUrlValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class ComputerEdit extends HttpServlet {

    private static Input inputValidator = new Input();
    private ComputerService computerService;
    private CompanyDTOServiceImpl companyDTOService;
    private ComputerDTOServiceImpl computerDTOService;
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.ComputerEdit");

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        computerService = (ComputerService) ac.getBean("computerService");
        companyDTOService = (CompanyDTOServiceImpl) ac.getBean("companyDTOService");
        computerDTOService = (ComputerDTOServiceImpl) ac.getBean("computerDTOService");
    }

    /**
     * doGet method.
     *
     * @param request  request.
     * @param response response.
     * @throws ServletException javax servlet exception.
     * @throws IOException      IOException.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ComputerEditUrlValidator computerEditUrlValidator = new ComputerEditUrlValidator();
        computerEditUrlValidator.isUrlValid(request.getParameter("id"));
        String error = computerEditUrlValidator.getError();

        if (error.length() == 0) {
            //Get computer id
            int id = Integer.valueOf(request.getParameter("id"));
            //Get computer from id
            ComputerDTO c = computerDTOService.findById(id);
            //Get companies ids and names
            List<CompanyDTO> companies = companyDTOService.getAll();
            //Set view parameters
            request.setAttribute("computer", c);
            request.setAttribute("companies", companies);
            //Dispatch view
            RequestDispatcher rd = request.getRequestDispatcher("views/editComputer.jsp");
            rd.include(request, response);
        } else {
            request.setAttribute("errorMsg", error);
            //Dispatch view
            RequestDispatcher rd = request.getRequestDispatcher(request.getContextPath() + "/dashboard");
            rd.include(request, response);
        }
    }

    /**
     * doPost method.
     *
     * @param request  request.
     * @param response response.
     * @throws ServletException javax servlet exception.
     * @throws IOException      IOException.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = "";
        if (request.getParameter("id") != null) {
            ComputerValidator computerValidator = new ComputerValidator();

            try {
                computerValidator.isValidComputer(request);
                error = computerValidator.getError();
            } catch (FormException e) {
                error = e.getMessage();
            } finally {
                if (error.length() == 0) {
                    //Update computer
                    computerService.update(Integer.valueOf(request.getParameter("id")), request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), computerValidator.getValidCompanyId(request.getParameter("companyId")));
                    //Redirect to dashboard
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                } else {
                    request.setAttribute("errorMsg", error);
                    doGet(request, response);
                }
            }
        }
    }
}
