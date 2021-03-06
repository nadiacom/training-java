package controllers.webservices;

import exceptions.validators.FormException;
import models.Company;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import services.CompanyService;
import services.ComputerService;
import services.validators.inputs.ComputerValidator;
import services.validators.inputs.Input;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by ebiz on 21/03/17.
 */
public class ComputerAdd extends HttpServlet {

    private static Input inputValidator = new Input();
    private CompanyService companyService;
    private ComputerService computerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ApplicationContext ac = (ApplicationContext) config.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
        computerService = (ComputerService) ac.getBean("computerService");
        companyService = (CompanyService) ac.getBean("companyService");
    }

    /**
     * @param request  request.
     * @param response response.
     * @throws ServletException ServletException.
     * @throws IOException      IOException.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get companies ids and names
        List<Company> companies = companyService.getAll();
        //Set view parameters
        request.setAttribute("companies", companies);
        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/addComputer.jsp");
        rd.include(request, response);
    }

    /**
     * @param request  request.
     * @param response response.
     * @throws ServletException ServletException.
     * @throws IOException      IOException.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String error = "";
        ComputerValidator computerValidator = new ComputerValidator();

        try {
            computerValidator.isValidComputer(request);
            error = computerValidator.getError();
        } catch (FormException e) {
            error = e.getMessage();
        } finally {
            if (error.length() == 0) {
                //Create computer
                computerService.create(request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), computerValidator.getValidCompanyId(request.getParameter("companyId")));
                //Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                request.setAttribute("errorMsg", error);
                doGet(request, response);
            }
        }
    }
}
