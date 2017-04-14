package utils;

import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import services.dtos.ComputerDTOServiceImpl;

import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerUtils {

    private static boolean isOrderAsC = false;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.ComputerUtils");
    private static ComputerDTOServiceImpl computerDTOService = ComputerDTOServiceImpl.INSTANCE;


    /**
     * Get computers list for current pagination from request.
     *
     * @param request          http request.
     * @param currentPage      current pagination.
     * @param nbComputerByPage number of computers listed by page.
     * @return list of computers.
     */
    public static List<ComputerDTO> getPageList(javax.servlet.http.HttpServletRequest request, int currentPage, int nbComputerByPage) {
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
        if (request.getParameter("search") != null && request.getParameter("order") != null) {
            LOGGER.debug("Order parameter: " + request.getParameter("order"));
            LOGGER.debug("List computer search and order: " + listComputer);
            listComputer = computerDTOService.findByNameAndOrder(request.getParameter("search"), request.getParameter("order"), order, currentPage, nbComputerByPage);
        } else if (request.getParameter("search") != null) {
            LOGGER.debug("Order parameter: " + request.getParameter("order"));
            LOGGER.debug("List computer search: " + listComputer);
            listComputer = computerDTOService.findByName(request.getParameter("search"), currentPage, nbComputerByPage);
        } else if (request.getParameter("order") != null) {
            listComputer = computerDTOService.findByNameAndOrder("", request.getParameter("order"), order, currentPage, nbComputerByPage);
            LOGGER.debug("Order parameter: " + request.getParameter("order"));
            LOGGER.debug("List computer order: " + listComputer);
        } else {
            listComputer = computerDTOService.getPageList(currentPage, nbComputerByPage);
            LOGGER.debug("List computer : " + listComputer);
        }
        return listComputer;
    }


    /**
     * Get computers list for current pagination from request.
     *
     * @param request http request.
     * @return list of computers.
     */
    public static int getNumberComputers(javax.servlet.http.HttpServletRequest request) {
        int nb = 0;
        if (request.getParameter("search") != null) {
            nb = computerDTOService.countByName(request.getParameter("search"));
        } else {
            nb = computerDTOService.count();
        }
        return nb;
    }
}
