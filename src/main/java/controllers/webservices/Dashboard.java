package controllers.webservices;

import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import services.ComputerService;
import utils.ComputerUtils;
import utils.Pagination;
import utils.SessionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;


/**
 * Created by ebiz on 20/03/17.
 */
public class Dashboard extends javax.servlet.http.HttpServlet {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.Dashboard");


    /**
     * @param request  request
     * @param response response
     * @throws javax.servlet.ServletException javax servlet exception
     * @throws IOException                    IOException
     */
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        //NUMBER OF COMPUTERS BY PAGINATION
        //Set user and get preference for number of computers by pagination in session
        HttpSession session = request.getSession();
        SessionUtils.setPageLimit(request, session);
        int nbComputerByPage = (Integer) session.getAttribute("paginateLimit");

        //GET COMPUTERS LIST
        List<ComputerDTO> listComputer = ComputerUtils.getPageList(request, Pagination.getCurrentPage(request), nbComputerByPage);
        LOGGER.debug("computer list :" + listComputer.toString());
        for (ComputerDTO computer : listComputer) {
            LOGGER.debug("computer id : "+ computer.getId());
            LOGGER.debug("computer name : "+ computer.getName());
            LOGGER.debug("computer introduced : "+ computer.getIntroduced());
            LOGGER.debug("computer discon : "+ computer.getDiscontinued());
            LOGGER.debug("computer company : "+ computer.getCompanyDTO().getId() + " "  computer.getCompanyDTO().getName() );
            
        }
        //PAGINATION
        //Get total number of computers
        int nbComputer = ComputerUtils.getNumberComputers(request);
        //Call Pagination method
        int[] values = Pagination.getPagination(nbComputerByPage, Pagination.getCurrentPage(request), nbComputer);

        //SET PARAMETERS TO VIEW
        request.setAttribute("listComputer", listComputer);
        request.setAttribute("currentPage", Pagination.getCurrentPage(request));
        request.setAttribute("nbComputer", nbComputer);
        request.setAttribute("nbComputerByPage", nbComputerByPage);
        request.setAttribute("pgStart", values[1]);
        request.setAttribute("pgEnd", values[2]);
        request.setAttribute("totalPages", values[0]);
        request.setAttribute("lastPage", Pagination.isLastPage());

        if (request.getParameter("search") != null) {
            request.setAttribute("search", request.getParameter("search"));
        }
        if (request.getParameter("order") != null) {
            request.setAttribute("order", request.getParameter("order"));
        }

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
                ComputerService.INSTANCE.delete(Integer.valueOf(selected[i]));
                doGet(request, response);
            }
        }
    }
}
