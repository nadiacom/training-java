package controllers;

import models.Company;
import models.Computer;
import services.CompanyService;
import services.ComputerService;
import services.validators.Input;

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
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Get companies ids and names
        List<Company> companies = CompanyService.getInstance().getAllCompanies();
        //Set view parameters
        request.setAttribute("companies", companies);
        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/addComputer.jsp");
        rd.include(request, response);

    }

    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Update computer
        Computer computer = ComputerService.getInstance().createComputer(request.getParameter("name"), inputValidator.getLocalDate(request.getParameter("introduced")), inputValidator.getLocalDate(request.getParameter("discontinued")), Integer.valueOf(request.getParameter("companyId")));
        //Redirect to dashboard
        response.sendRedirect("/dashboard");
    }
}
