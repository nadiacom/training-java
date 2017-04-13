package controllers.webservices;

import exceptions.validators.ValidatorException;
import exceptions.validators.models.computer.ComputerCompanyValidatorException;
import exceptions.validators.models.computer.ComputerDiscontinuedValidatorException;
import exceptions.validators.models.computer.ComputerIntroducedValidatorException;
import exceptions.validators.models.computer.ComputerNameValidatorException;
import models.Company;
import services.CompanyService;
import services.ComputerService;
import services.validators.inputs.ComputerValidator;
import services.validators.inputs.Input;

import javax.servlet.RequestDispatcher;
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

    /**
     * @param request  request.
     * @param response response.
     * @throws ServletException ServletException.
     * @throws IOException      IOException.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get companies ids and names
        List<Company> companies = CompanyService.INSTANCE.getAll();
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
        } catch (ComputerNameValidatorException e) {
            error = e.getMessage();
        } catch (ComputerIntroducedValidatorException e) {
            error = e.getMessage();
        } catch (ComputerDiscontinuedValidatorException e) {
            error = e.getMessage();
        } catch (ComputerCompanyValidatorException e) {
            error = e.getMessage();
        } catch (ValidatorException e) {
            //Ignore company id exceptions for now.
        } finally {
            if (error.isEmpty()) {
                //Create computer
                ComputerService.INSTANCE.create(request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), computerValidator.getValidCompanyId(request.getParameter("companyId")));
                //Redirect to dashboard
                response.sendRedirect(request.getContextPath() + "/dashboard");
            } else {
                request.setAttribute("errorMsg", error);
                doGet(request, response);
            }
        }
    }
}
