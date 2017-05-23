package com.ebiz.cdb.controllers;

import com.ebiz.cdb.binding.service.ComputerDTOService;
import com.ebiz.cdb.binding.service.impl.ComputerDTOServiceImpl;
import com.ebiz.cdb.binding.service.impl.PageRequestServiceImpl;
import com.ebiz.cdb.core.models.PageRequest;
import com.ebiz.cdb.service.ComputerService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("com.ebiz.cdb.controllers.DashboardController");

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

        PageRequest pageRequest = new PageRequestServiceImpl((ComputerDTOServiceImpl) computerDTOService).buildPage(session, currentPage, search, order, limit, click);
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

    //TODO : Add logout button
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/dashboard"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
