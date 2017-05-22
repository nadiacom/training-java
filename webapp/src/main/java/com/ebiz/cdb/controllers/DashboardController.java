package com.ebiz.cdb.controllers;

import com.ebiz.cdb.binding.service.ComputerDTOService;
import com.ebiz.cdb.binding.service.ComputerDTOServiceImpl;
import com.ebiz.cdb.core.models.PageRequest;
import com.ebiz.cdb.binding.service.PageRequestService;
import com.ebiz.cdb.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controllers.webservices..DashboardController");

    private final ComputerService computerService;
    private final ComputerDTOService computerDTOService;

    /**
     * Dashboard constructor.
     *
     * @param computerService    autowired computerService
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
    public String dashboard(@RequestParam(required = false) String order,
                            @RequestParam(required = false) String search,
                            @RequestParam(required = false) String currentPage,
                            @RequestParam(required = false) String click,
                            @RequestParam(required = false) String limit,
                            HttpSession session,
                            Model model) {

        PageRequest pageRequest = new PageRequestService((ComputerDTOServiceImpl) computerDTOService).buildPage(session, currentPage, search, order, limit, click);
        //SET PARAMETERS TO VIEW
        model.addAttribute("pageRequest", pageRequest);
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
