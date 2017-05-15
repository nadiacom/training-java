package utils;

import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import services.dtos.ComputerDTOServiceImpl;

import java.util.List;

public class ComputerUtils {

    private boolean isOrderAsC = false;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.ComputerUtils");
    @Autowired
    private ComputerDTOServiceImpl computerDTOService;

    /**
     * Get computers list for current pagination from request.
     *
     * @param request          http request.
     * @param currentPage      current pagination.
     * @param nbComputerByPage number of computers listed by page.
     * @return list of computers.
     */
    public List<ComputerDTO> getPageList(javax.servlet.http.HttpServletRequest request, int currentPage, int nbComputerByPage) {
        String order = "";
        List<ComputerDTO> listComputer = null;

        if (request.getParameter("click") != null) {
            isOrderAsC = !isOrderAsC;
            if (isOrderAsC) {
                order = "ASC";
            } else {
                order = "DESC";
            }
        }
        if (request.getParameter("search") != null && request.getParameter("order") != null && !(request.getParameter("order")).isEmpty()) {
            listComputer = computerDTOService.findByNameAndOrder(request.getParameter("search"), request.getParameter("order"), order, currentPage, nbComputerByPage);
        } else if (request.getParameter("search") != null) {
            listComputer = computerDTOService.findByName(request.getParameter("search"), currentPage, nbComputerByPage);
        } else if (request.getParameter("order") != null && !(request.getParameter("order").isEmpty())) {
            listComputer = computerDTOService.findByNameAndOrder("", request.getParameter("order"), order, currentPage, nbComputerByPage);
        } else {
            listComputer = computerDTOService.getPageList(currentPage, nbComputerByPage);
        }
        return listComputer;
    }


    /**
     * Get computers list for current pagination from request.
     *
     * @param request http request.
     * @return list of computers.
     */
    public int getNumberComputers(javax.servlet.http.HttpServletRequest request) {
        int nb = 0;
        if (request.getParameter("search") != null) {
            nb = computerDTOService.countByName(request.getParameter("search"));
        } else {
            nb = computerDTOService.count();
        }
        return nb;
    }
}
