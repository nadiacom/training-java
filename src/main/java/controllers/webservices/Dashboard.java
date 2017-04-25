package controllers.webservices;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import services.ComputerService;
import utils.DashboardUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by ebiz on 20/03/17.
 */
public class Dashboard extends javax.servlet.http.HttpServlet {

    private org.slf4j.Logger LOGGER = LoggerFactory.getLogger("controller.Dashboard");

    @Autowired
    private ComputerService computerService;


    /**
     * @param request  request
     * @param response response
     * @throws javax.servlet.ServletException javax servlet exception
     * @throws IOException                    IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DashboardUtils dashboard = new DashboardUtils();
        request = dashboard.setRequest(request);

        //Dispatch view
        RequestDispatcher rd = request.getRequestDispatcher("views/dashboard.jsp");
        rd.include(request, response);
    }


    /**
     * @param request  request.
     * @param response response.
     * @throws javax.servlet.ServletException ServletException.
     * @throws IOException                    IOException.
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        if (request.getParameter("selection") != null) {
            //Get computer selected
            String[] selected = request.getParameter("selection").split(",");
            for (int i = 0; i < selected.length; i++) {
                //And delete computer from id
                computerService.delete(Integer.valueOf(selected[i]));
                doGet(request, response);
            }
        }
    }
}
