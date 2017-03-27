package utils;

import javax.servlet.http.HttpSession;

/**
 * Created by ebiz on 27/03/17.
 */
public class SessionUtils {

    /**
     * Set user preference for number of computers by pagination in session.
     *
     * @param request HttpServletRequest request.
     * @param session HttpSession session.
     */
    public static void setPageLimit(javax.servlet.http.HttpServletRequest request, HttpSession session) {

        //If user clicked on button, set number of computers by pagination's preference to session
        if (request.getParameter("limit") != null) {
            session.setAttribute(
                    "paginateLimit",
                    Pagination.Limit.getLimitNb(
                            Short.valueOf(request.getParameter("limit"))
                    )
            );
            //Else initiate to 10
        } else if (session.getAttribute("paginateLimit") == null) {
            session.setAttribute("paginateLimit", Pagination.Limit.getLimitNb(0));
        }
    }
}
