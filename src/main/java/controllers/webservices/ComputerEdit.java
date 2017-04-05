package controllers.webservices;

import exceptions.validators.ValidatorException;
import exceptions.validators.models.computer.ComputerCompanyValidatorException;
import exceptions.validators.models.computer.ComputerDiscontinuedValidatorException;
import exceptions.validators.models.computer.ComputerIntroducedValidatorException;
import exceptions.validators.models.computer.ComputerNameValidatorException;
import exceptions.validators.urls.ComputerEditUrlValidatorException;
import models.dtos.CompanyDTO;
import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import services.ComputerService;
import services.dtos.CompanyDTOServiceImpl;
import services.dtos.ComputerDTOServiceImpl;
import services.validators.inputs.ComputerValidator;
import services.validators.inputs.Input;
import services.validators.urls.ComputerEditUrlValidator;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class ComputerEdit extends javax.servlet.http.HttpServlet {

    private static Input inputValidator = new Input();
    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.ComputerEdit");

    /**
     * doGet method.
     *
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException javax servlet exception.
     * @throws IOException                    IOException.
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        ComputerEditUrlValidator computerEditUrlValidator = new ComputerEditUrlValidator();
        String error = "";
        try {
            computerEditUrlValidator.isUrlValid(request.getParameter("id"));
        } catch (ComputerEditUrlValidatorException e) {
            error = e.getMessage();
        } finally {
            if (error.isEmpty()) {
                //Get computer id
                int id = Integer.valueOf(request.getParameter("id"));
                //Get computer from id
                ComputerDTO c = ComputerDTOServiceImpl.INSTANCE.findById(id);
                //Get companies ids and names
                List<CompanyDTO> companies = CompanyDTOServiceImpl.INSTANCE.getAll();
                //Set view parameters
                request.setAttribute("computer", c);
                request.setAttribute("companies", companies);
                //Dispatch view
                RequestDispatcher rd = request.getRequestDispatcher("views/editComputer.jsp");
                rd.include(request, response);
            } else {
                request.setAttribute("errorMsg", error);
                //Dispatch view
                RequestDispatcher rd = request.getRequestDispatcher("/dashboard");
                rd.include(request, response);
            }
        }
    }

    /**
     * doPost method.
     *
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException javax servlet exception.
     * @throws IOException                    IOException.
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String error = "";
        if (request.getParameter("id") != null) {
            ComputerValidator computerValidator = new ComputerValidator();

            try {
                computerValidator.isValidComputer(request);
            } catch (ComputerNameValidatorException e) {
                error = e.getMessage();
            } catch (ComputerIntroducedValidatorException e) {
                error = e.getMessage();
            } catch (ComputerDiscontinuedValidatorException e) {
                error = e.getMessage();
            } catch (ComputerCompanyValidatorException e) {
                error = e.getMessage();
            } catch (ValidatorException e) {
            } finally {
                if (error.isEmpty()) {
                    //Update computer
                    ComputerService.INSTANCE.update(Integer.valueOf(request.getParameter("id")), request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), computerValidator.getValidCompanyId(request.getParameter("companyId")));
                    //Redirect to dashboard
                    response.sendRedirect("/dashboard");
                } else {
                    request.setAttribute("errorMsg", error);
                    doGet(request, response);
                }
            }
        }
    }
}
