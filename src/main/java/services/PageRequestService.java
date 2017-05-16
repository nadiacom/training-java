package services;

import models.PageRequest;
import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.dtos.ComputerDTOServiceImpl;
import utils.PaginationUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
@Service
public class PageRequestService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("services.PageRequestService");
    private PageRequest pageRequest;
    private final ComputerDTOServiceImpl computerDTOService;

    /**
     * PageRequest constructor.
     *
     * @param computerDTOService autowired computerDTOService
     */
    @Autowired
    public PageRequestService(ComputerDTOServiceImpl computerDTOService) {
        this.computerDTOService = computerDTOService;
    }

    /**
     * .
     *
     * @param session     session.
     * @param currentPage .
     * @param search      .
     * @param order       .
     * @param limit       .
     * @param click       .
     * @return page Request.
     */
    public PageRequest buildPage(HttpSession session, String currentPage, String search, String order, String limit, String click) {

        //NUMBER OF COMPUTERS BY PAGINATION
        //Set user and get preference for number of computers by pagination in session
        //If user clicked on button, set number of computers by pagination's preference to session
        if (limit != null) {
            session.setAttribute(
                    "paginateLimit",
                    PaginationUtils.Limit.getLimitNb(
                            Short.valueOf(limit)
                    )
            );
            //Else initiate to 10
        } else if (session.getAttribute("paginateLimit") == null) {
            session.setAttribute("paginateLimit", PaginationUtils.Limit.getLimitNb(0));
        }
        int nbComputerByPage = (Integer) session.getAttribute("paginateLimit");

        //GET COMPUTERS LIST
        List<ComputerDTO> listComputer = null;
        boolean isOrderAsC = false;
        String theOrder = "";

        if (click != null) {
            isOrderAsC = !isOrderAsC;
            if (isOrderAsC) {
                theOrder = "ASC";
            } else {
                theOrder = "DESC";
            }
        }
        int theCurrentPage = 1; //pagination 1 displayed by default
        if (currentPage != null) {
            //Get current page
            theCurrentPage = Integer.parseInt(currentPage);
        }
        if (search != null && order != null && !order.isEmpty()) {
            listComputer = computerDTOService.findByNameAndOrder(search, order, order, theCurrentPage, nbComputerByPage);
        } else if (search != null) {
            listComputer = computerDTOService.findByName(search, theCurrentPage, nbComputerByPage);
        } else if (order != null && !order.isEmpty()) {
            listComputer = computerDTOService.findByNameAndOrder("", order, theOrder, theCurrentPage, nbComputerByPage);
        } else {
            listComputer = computerDTOService.getPageList(theCurrentPage, nbComputerByPage);
        }
        //PAGINATION
        //Get total number of computers
        int nbComputer = 0;
        if (search != null) {
            nbComputer = computerDTOService.countByName(search);
        } else {
            nbComputer = computerDTOService.count();
        }
        //Call PaginationUtils method
        int[] values = PaginationUtils.getPagination(nbComputerByPage, theCurrentPage, nbComputer);

        pageRequest = new
                PageRequest.PageRequestBuilder()
                .nbComputerByPage(nbComputerByPage)
                .nbComputers(nbComputer)
                .currentPage(theCurrentPage)
                .listComputers(listComputer)
                .pgStart(values[1])
                .pgEnd(values[2])
                .totalPages(values[0])
                .order(order != null ? order : null)
                .search(search != null ? search : null)
                .islastPage(PaginationUtils.isLastPage())
                .build();

        return pageRequest;

    }
}
