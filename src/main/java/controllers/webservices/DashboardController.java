package controllers.webservices;

import models.PageRequest;
import models.dtos.ComputerDTO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.ComputerService;
import services.dtos.ComputerDTOService;
import utils.Pagination;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controllers.webservices..DashboardController");

    private final ComputerService computerService;
    private final ComputerDTOService computerDTOService;

    /**
     * Dashboard constructor.
     *
     * @param computerService autowired computerService
     * @param computerDTOService autowired computerDTOService
     */
    @Autowired
    public DashboardController(ComputerService computerService, ComputerDTOService computerDTOService) {
        this.computerService = computerService;
        this.computerDTOService = computerDTOService;
    }

    /**
     * Get the dashboard page.
     *
     * @param error       error msg.
     * @param order       order .
     * @param search      search
     * @param currentPage currentPage
     * @param click       click
     * @param limit       limit
     * @param session     session
     * @param model       model
     * @return dashboard view
     */
    @GetMapping()
    public String dashboard(@RequestParam(required = false) String error,
                            @RequestParam(required = false) String order,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) String currentPage,
                            @RequestParam(required = false) String click,
                            @RequestParam(required = false) String limit,
                            HttpSession session,
                            Model model) {

        //NUMBER OF COMPUTERS BY PAGINATION
        //Set user and get preference for number of computers by pagination in session
        //If user clicked on button, set number of computers by pagination's preference to session
        if (limit != null) {
            session.setAttribute(
                    "paginateLimit",
                    Pagination.Limit.getLimitNb(
                            Short.valueOf(limit)
                    )
            );
            //Else initiate to 10
        } else if (session.getAttribute("paginateLimit") == null) {
            session.setAttribute("paginateLimit", Pagination.Limit.getLimitNb(0));
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
        //Call Pagination method
        int[] values = Pagination.getPagination(nbComputerByPage, theCurrentPage, nbComputer);

        PageRequest pageRequest = new
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
                .islastPage(Pagination.isLastPage())
                .build();
        //PageRequest pageRequest = new PageRequestService().buildPage(session, currentPage, search, order, limit, click);

        //SET PARAMETERS TO VIEW
        model.addAttribute("pageRequest", pageRequest);

        if (error != null && !error.isEmpty()) {
            model.addAttribute("errorMsg", error);
        }
        return "dashboard";
    }

    /**
     * Delete computer selection.
     *
     * @param selection selection
     * @param model     model
     * @return dashboard view
     */
    @PostMapping()
    public String add(@RequestParam(value = "selection") String selection,
                      Model model
    ) {
        //Get computer selected
        String[] selected = selection.split(",");
        for (int i = 0; i < selected.length; i++) {
            //And delete computer from id
            computerService.delete(Integer.valueOf(selected[i]));
        }
        return "dashboard";
    }
}
