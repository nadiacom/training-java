package utils;

import models.dtos.ComputerDTO;
import services.dtos.ComputerDTOServiceImpl;

import java.util.List;

/**
 * Created by ebiz on 22/03/17.
 */
public class ComputerUtils {

    /**
     * Get computers list for current pagination from request.
     *
     * @param request          http request.
     * @param currentPage      current pagination.
     * @param nbComputerByPage number of computers listed by page.
     * @return list of computers.
     */
    public static List<ComputerDTO> getPageList(javax.servlet.http.HttpServletRequest request, int currentPage, int nbComputerByPage) {

        List<ComputerDTO> listComputer1;
        if (request.getParameter("search") != null) {
            listComputer1 = ComputerDTOServiceImpl.getInstance().findByName(request.getParameter("search"), currentPage, nbComputerByPage);
        } else {
            listComputer1 = ComputerDTOServiceImpl.getInstance().getPageList(currentPage, nbComputerByPage);
        }
        return listComputer1;
    }
}
