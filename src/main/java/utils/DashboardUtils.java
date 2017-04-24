package utils;

import models.PageRequest;
import models.dtos.ComputerDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by ebiz on 24/04/17.
 */
public class DashboardUtils {

    public int nbComputerByPage; // number of computers displayed by page
    public int currentPage; // current page number
    public String order; // order by parameter (column name)
    public String search; // filter by parameter (search box input)

    /**
     * Default constructor.
     */
    public DashboardUtils() {

    }

    /**
     * Set Dashboard request.
     *
     * @param request Dashboard http request.
     * @return request.
     */
    public HttpServletRequest setRequest(HttpServletRequest request) {

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

        PageRequest pageRequest = new
                PageRequest.PageRequestBuilder()
                .nbComputerByPage(nbComputerByPage)
                .nbComputers(nbComputer)
                .currentPage(Pagination.getCurrentPage(request))
                .listComputers(listComputer)
                .pgStart(values[1])
                .pgEnd(values[2])
                .totalPages(values[0])
                .order(request.getParameter("order") != null ? request.getParameter("order") : null)
                .search(request.getParameter("search") != null ? request.getParameter("search") : null)
                .islastPage(Pagination.isLastPage())
                .build();

        //SET PARAMETERS TO VIEW
        request.setAttribute("pageRequest", pageRequest);

        return request;
    }


}
