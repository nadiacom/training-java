package com.ebiz.cdb.binding.service.impl;

import com.ebiz.cdb.binding.dto.ComputerDTO;
import com.ebiz.cdb.binding.service.ComputerDTOService;
import com.ebiz.cdb.binding.service.PageRequestService;
import com.ebiz.cdb.binding.utils.PaginationUtils;
import com.ebiz.cdb.core.models.PageRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class PageRequestServiceImpl implements PageRequestService {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("service.PageRequestService");
    private PageRequest pageRequest;
    private final ComputerDTOService computerDTOService;

    /**
     * PageRequest constructor.
     *
     * @param computerDTOService autowired computerDTOService
     */
    @Autowired
    public PageRequestServiceImpl(ComputerDTOService computerDTOService) {
        this.computerDTOService = computerDTOService;
    }

    @Override
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
        long nbComputerByPage = (long) session.getAttribute("paginateLimit");

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
        if (search != null && !search.isEmpty() && order != null && !order.isEmpty()) {
            listComputer = computerDTOService.findByNameAndOrder(search, order, theOrder, theCurrentPage, (int) nbComputerByPage);
        } else if (search != null && !search.isEmpty()) {
            listComputer = computerDTOService.findByName(search, theCurrentPage, (int) nbComputerByPage);
        } else if (order != null && !order.isEmpty()) {
            listComputer = computerDTOService.findByOrder(order, theOrder, theCurrentPage, (int) nbComputerByPage);
        } else {
            listComputer = computerDTOService.getPageList(theCurrentPage, (int) nbComputerByPage);
        }
        //PAGINATION
        //Get total number of computers
        int nbComputer = 0;
        if (search != null && !search.isEmpty()) {
            nbComputer = computerDTOService.countByName(search);
        } else {
            nbComputer = computerDTOService.count();
        }
        //Call PaginationUtils method
        long[] values = PaginationUtils.getPagination((int) nbComputerByPage, theCurrentPage, nbComputer);

        pageRequest = new
                PageRequest.PageRequestBuilder()
                .nbComputerByPage((int) nbComputerByPage)
                .nbComputers(nbComputer)
                .currentPage(theCurrentPage)
                .listComputers(listComputer)
                .pgStart((int) values[1])
                .pgEnd((int) values[2])
                .totalPages((int) values[0])
                .order(order != null ? order : null)
                .search(search != null ? search : null)
                .islastPage(PaginationUtils.isLastPage())
                .build();

        return pageRequest;

    }
}
