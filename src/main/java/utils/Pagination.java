package utils;

/**
 * Created by ebiz on 22/03/17.
 */
public class Pagination {

    private static final int NB_PAGINATION = 10;
    private static boolean lastPage;

    public enum Limit {
        SHORT(10),
        MEDIUM(50),
        BIG(100);
        private int nb;

        /**
         * Create a Limit.
         *
         * @param nb int
         */
        Limit(int nb) {
            this.nb = nb;
        }

        /**
         * Return the limit nb for a Limit.
         *
         * @return int
         */
        public int getNb() {
            return nb;
        }

        /**
         * Return the limit nb from index. (0, 1, 2 => 10, 50, 100)
         *
         * @param choice int
         * @return int
         */
        public static int getLimitNb(int choice) {
            return values()[choice] != null ? values()[choice].getNb() : values()[0].getNb();
        }
    }

    /**
     * Calcul total number of pagination and where displayed pagination begins and ends for current page (pgStart, pgEnd).
     *
     * @param nbComputerByPage number of computers displayed by pagination.
     * @param currentPage      curent page number.
     * @param nbComputer total number of computers.
     * @return array : total number of pagination, pgStart and pgEnd for current pagination.
     */
    public static int[] getPagination(int nbComputerByPage, int currentPage, int nbComputer) {

        int[] values = new int[3];
        //CALCULATE NUMBER OF PAGINATION
        int totalPages = 1, reste = 0, quotient = 0;
        if (nbComputer > nbComputerByPage) {
            //number of pagination to display employee list
            reste = nbComputer % nbComputerByPage;
            quotient = reste != 0
                    ? nbComputer / nbComputerByPage + 1
                    : nbComputer / nbComputerByPage;
            totalPages = quotient;
        }

        //DISPLAYED PAGINATIONS
        lastPage = currentPage == totalPages;
        int pgStart = Math.max(currentPage - NB_PAGINATION / 2, 1);
        int pgEnd = pgStart + NB_PAGINATION;
        if (pgEnd > totalPages + 1) {
            int diff = pgEnd - totalPages;
            pgStart -= diff - 1;
            if (pgStart < 1) {
                pgStart = 1;
            }
            pgEnd = totalPages + 1;
        }
        values[0] = totalPages;
        values[1] = pgStart;
        values[2] = pgEnd;
        return values;
    }

    public static boolean isLastPage() {
        return lastPage;
    }

    /**
     * Get current page.
     *
     * @param request HttpServletRequest request.
     * @return current page.
     */
    public static int getCurrentPage(javax.servlet.http.HttpServletRequest request) {
        int currentPage = 1; //pagination 1 displayed by default
        if (request.getParameter("currentPage") != null) {
            //Get current page
            currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        return currentPage;
    }
}
