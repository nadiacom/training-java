package controllers.webservices;

import exceptions.validators.ValidatorException;
import exceptions.validators.computer.ComputerDiscontinuedValidatorException;
import exceptions.validators.computer.ComputerIntroducedValidatorException;
import exceptions.validators.computer.ComputerNameValidatorException;
import models.dtos.CompanyDTO;
import models.dtos.ComputerDTO;
import services.ComputerService;
import services.dtos.CompanyDTOServiceImpl;
import services.dtos.ComputerDTOServiceImpl;
import services.validators.ComputerValidator;
import services.validators.Input;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class ComputerEdit extends javax.servlet.http.HttpServlet {

    private int NB_COMPUTER_PAGE = 50;
    private static Input inputValidator = new Input();

    /**
     * doGet method.
     *
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException javax servlet exception.
     * @throws IOException                    IOException.
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        if (request.getParameter("computer") != null) {
            //Get computer id
            int id = Integer.valueOf(request.getParameter("computer"));
            //Get computer from id
            ComputerDTO c = ComputerDTOServiceImpl.getInstance().findById(id);
            //Get companies ids and names
            List<CompanyDTO> companies = CompanyDTOServiceImpl.getInstance().getAll();
            //Set view parameters
            request.setAttribute("computer", c);
            request.setAttribute("companies", companies);
        }
        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/editComputer.jsp");
        rd.include(request, response);
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
        if (request.getParameter("computer") != null) {
            ComputerValidator computerValidator = new ComputerValidator();

            try {
                computerValidator.isValidComputer(request);
            } catch (ComputerNameValidatorException e) {
                error = e.getMessage();
            } catch (ComputerIntroducedValidatorException e) {
                error = e.getMessage();
            } catch (ComputerDiscontinuedValidatorException e) {
                error = e.getMessage();
            } catch (ValidatorException e) {
                //Ignore company id exceptions for now.
            } finally {
                if (error.isEmpty()) {
                    //Update computer
                    ComputerService.getInstance().updateComputer(Integer.valueOf(request.getParameter("id")), request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), Integer.valueOf(request.getParameter("companyId")));
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
