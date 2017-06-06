package com.ebiz.cdb.binding.utils;

public class PaginationUtils {

    private static final long NB_PAGINATION = 10L;
    private static boolean lastPage;

    public enum Limit {
        SHORT(10L),
        MEDIUM(50L),
        BIG(100L);
        private long nb;

        /**
         * Create a Limit.
         *
         * @param nb int
         */
        Limit(long nb) {
            this.nb = nb;
        }

        /**
         * Return the limit nb for a Limit.
         *
         * @return int
         */
        public long getNb() {
            return nb;
        }

        /**
         * Return the limit nb from index. (0, 1, 2 => 10, 50, 100)
         *
         * @param choice int
         * @return int
         */
        public static long getLimitNb(int choice) {
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
    public static long[] getPagination(int nbComputerByPage, int currentPage, long nbComputer) {

        long[] values = new long[3];
        //CALCULATE NUMBER OF PAGINATION
        long totalPages = 1L, reste = 0L, quotient = 0L;
        if (nbComputer > nbComputerByPage) {
            //number of pagination to display computer list
            reste = nbComputer % nbComputerByPage;
            quotient = reste != 0L
                    ? nbComputer / nbComputerByPage + 1L
                    : nbComputer / nbComputerByPage;
            totalPages = quotient;
        }

        //DISPLAYED PAGINATIONS
        lastPage = currentPage == totalPages;
        long pgStart = Math.max(currentPage - NB_PAGINATION / 2L, 1L);
        long pgEnd = pgStart + NB_PAGINATION;
        if (pgEnd > totalPages + 1L) {
            long diff = pgEnd - totalPages;
            pgStart -= diff - 1L;
            if (pgStart < 1L) {
                pgStart = 1L;
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
}
