package models;

import models.dtos.ComputerDTO;
import utils.ComputerUtils;
import utils.Pagination;
import utils.SessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ebiz on 24/04/17.
 */
public class Dashboard {

    public int nbComputerByPage; // number of computers displayed by page
    public int currentPage; // current page number
    public String order; // order by parameter (column name)
    public String search; // filter by parameter (search box input)

    /**
     * Default constructor.
     */
    public Dashboard() {

    }

    public HttpServletRequest setRequest(HttpServletRequest request){

        //NUMBER OF COMPUTERS BY PAGINATION
        //Set user and get preference for number of computers by pagination in session
        HttpSession session = request.getSession();
        SessionUtils.setPageLimit(request, session);
        nbComputerByPage = (Integer) session.getAttribute("paginateLimit");

        //GET COMPUTERS LIST
        List<ComputerDTO> listComputer = ComputerUtils.getPageList(request, Pagination.getCurrentPage(request), nbComputerByPage);

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
        return request;
    }


}
