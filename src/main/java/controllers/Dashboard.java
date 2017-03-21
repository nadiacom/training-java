package controllers;

import models.Computer;
import services.ComputerService;

import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class Dashboard extends javax.servlet.http.HttpServlet {

    private int NB_COMPUTER_PAGE = 50;

    /**
     * @param request  request
     * @param response response
     * @throws javax.servlet.ServletException javax servlet exception
     * @throws IOException                    IOException
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        if (request.getParameter("page") != null) {
            //If pagination parameter, display computers for current pagination
            int pagination = Integer.valueOf(request.getParameter("page"));
            //companyService = CompanyService.getInstance();
            List<Computer> listComputer1 = ComputerService.getInstance().getComputersByPage(pagination);
            request.setAttribute("listComputer1", listComputer1);
        } else {
            //Pagination 1 displayed if by default
            List<Computer> listComputer1 = ComputerService.getInstance().getComputersByPage(1);
            request.setAttribute("listComputer1", listComputer1);
        }

        //Set number pagination
        int nbPagination = 1, nbComputer = 0, reste = 0, quotient = 0;
        try {
            nbComputer = ComputerService.getInstance().getNumberComputers();
            //Calcul pagination
            if (nbComputer > NB_COMPUTER_PAGE) {
                //number of pagination to display employee list
                reste = nbComputer % NB_COMPUTER_PAGE;
                quotient = reste != 0
                        ? nbComputer / NB_COMPUTER_PAGE + 1
                        : nbComputer / NB_COMPUTER_PAGE;
                nbPagination = quotient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("nbComputer", nbComputer);
        request.setAttribute("pagination", nbPagination);

        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/dashboard.jsp");
        rd.include(request, response);
    }
}
