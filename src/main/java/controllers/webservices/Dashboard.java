package controllers.webservices;

import models.dtos.ComputerDTO;
import services.ComputerService;
import services.dtos.ComputerDTOServiceImpl;
import utils.Pagination;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class Dashboard extends javax.servlet.http.HttpServlet {

    private static final int NB_PAGINATION = 10;

    /**
     * @param request  request
     * @param response response
     * @throws javax.servlet.ServletException javax servlet exception
     * @throws IOException                    IOException
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //Variables
        int currentPage = 1;

        //NUMBER OF COMPUTERS BY PAGINATION
        //Set user preference for number of computers by pagination in session
        HttpSession session = request.getSession();
        //If user clicked on button, set number of computers by pagination's preference to session
        if (request.getParameter("limit") != null) {
            session.setAttribute(
                    "paginateLimit",
                    Pagination.Limit.getLimitNb(
                            Short.valueOf(request.getParameter("limit"))
                    )
            );
            //Else if session contains no number of computers by pagination's preference, initiate to 10
        } else if (session.getAttribute("paginateLimit") == null) {
            session.setAttribute("paginateLimit", Pagination.Limit.getLimitNb(0));
        }
        //Get number of computers by pagination from session
        int nbComputerByPage = (Integer) session.getAttribute("paginateLimit");

        //GET COMPUTERS LIST
        if (request.getParameter("currentPage") != null) {
            //Display computers for current pagination
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }

        List<ComputerDTO> listComputer1 = ComputerDTOServiceImpl.getInstance().getPageList(currentPage, nbComputerByPage);
        request.setAttribute("listComputer1", listComputer1);

        //CALCULATE NUMBER OF PAGINATION
        int totalPages = 1, nbComputer = 0, reste = 0, quotient = 0;
        nbComputer = ComputerDTOServiceImpl.getInstance().getNumberComputersDTO();
        if (nbComputer > nbComputerByPage) {
            //number of pagination to display employee list
            reste = nbComputer % nbComputerByPage;
            quotient = reste != 0
                    ? nbComputer / nbComputerByPage + 1
                    : nbComputer / nbComputerByPage;
            totalPages = quotient;
        }

        //DISPLAYED PAGINATIONS
        boolean lastPage = currentPage == totalPages;
        int pgStart = Math.max(currentPage - NB_PAGINATION / 2, 1);
        int pgEnd = pgStart + NB_PAGINATION;
        if (pgEnd > totalPages + 1) {
            int diff = pgEnd - totalPages;
            pgStart -= diff - 1;
            if (pgStart < 1) {
                pgStart = 1;
            }
            pgEnd = totalPages + 1;
        }

        //SET PARAMETERS TO VIEW
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("nbComputer", nbComputer);
        request.setAttribute("nbComputerByPage", nbComputerByPage);
        request.setAttribute("pgStart", pgStart);
        request.setAttribute("pgEnd", pgEnd);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("lastPage", lastPage);

        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/dashboard.jsp");
        rd.include(request, response);
    }

    /**
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException ServletException.
     * @throws IOException                    IOException.
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        if (request.getParameter("selection") != null) {
            //Get computer selected
            String[] selected = request.getParameter("selection").split(",");
            for (int i = 0; i < selected.length; i++) {
                //And delete computer from id
                Long computerId = ComputerService.getInstance().deleteComputer(Integer.valueOf(selected[i]));
                doGet(request, response);
            }
        }
    }
}
