package models;

import models.dtos.ComputerDTO;

import java.util.List;

/**
 * Created by ebiz on 24/04/17.
 */
public class PageRequest {

    public int nbComputerByPage; // number of computers displayed by page
    public int nbComputers; // total number of computers for current search
    public int currentPage; // current page number
    public List<ComputerDTO> listComputers; // computers DTO list
    public int pgStart; // pagination first visible number
    public int pgEnd; // pagination last visible number
    public int totalPages; // total number of pagination (visible and hidden)
    public String order; // order by parameter (column name)
    public String search; // filter by parameter (search box input)
    public boolean islastPage; // boolean : current page is last page or not

    public boolean isLastPage() {
        return this.islastPage;
    }

    public void setLastPage(boolean lastPage) {
        this.islastPage = lastPage;
    }

    /**
     * Default constructor.
     */
    public PageRequest() {
    }

    public int getNbComputerByPage() {
        return this.nbComputerByPage;
    }

    public void setNbComputerByPage(int nbComputerByPage) {
        this.nbComputerByPage = nbComputerByPage;
    }

    public int getNbComputers() {
        return this.nbComputers;
    }

    public void setNbComputers(int nbComputers) {
        this.nbComputers = nbComputers;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<ComputerDTO> getListComputers() {
        return this.listComputers;
    }

    public void setListComputers(List<ComputerDTO> listComputers) {
        this.listComputers = listComputers;
    }

    public int getPgStart() {
        return this.pgStart;
    }

    public void setPgStart(int pgStart) {
        this.pgStart = pgStart;
    }

    public int getPgEnd() {
        return this.pgEnd;
    }

    public void setPgEnd(int pgEnd) {
        this.pgEnd = pgEnd;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getSearch() {
        return this.search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


    /**
     * Nested class PageRequestBuilder.
     */
    public static class PageRequestBuilder {
        private PageRequest pageRequest;

        /**
         * Constructor.
         */
        public PageRequestBuilder() {
            pageRequest = new PageRequest();
        }

        /**
         * Set nbComputerByPage to builder.
         *
         * @param nbComputerByPage (required) PageRequest number of computers by page.
         * @return PageRequest builder.
         */
        public PageRequestBuilder nbComputerByPage(int nbComputerByPage) {
            pageRequest.setNbComputerByPage(nbComputerByPage);
            return this;
        }

        /**
         * Set nbComputers to builder.
         *
         * @param nbComputers (required) PageRequest total number of computers by page.
         * @return PageRequest builder.
         */
        public PageRequestBuilder nbComputers(int nbComputers) {
            pageRequest.setNbComputers(nbComputers);
            return this;
        }

        /**
         * Set currentPage to builder.
         *
         * @param currentPage (required) PageRequest current page.
         * @return PageRequest builder.
         */
        public PageRequestBuilder currentPage(int currentPage) {
            pageRequest.setCurrentPage(currentPage);
            return this;
        }

        /**
         * Set listComputers to builder.
         *
         * @param listComputers (required) PageRequest list of computers for current page.
         * @return PageRequest builder.
         */
        public PageRequestBuilder listComputers(List<ComputerDTO> listComputers) {
            pageRequest.setListComputers(listComputers);
            return this;
        }

        /**
         * Set pgStart to builder.
         *
         * @param pgStart (required) PageRequest pagination first visible number.
         * @return PageRequest builder.
         */
        public PageRequestBuilder pgStart(int pgStart) {
            pageRequest.setPgStart(pgStart);
            return this;
        }

        /**
         * Set pgStart to builder.
         *
         * @param pgEnd (required) PageRequest pagination last visible number.
         * @return PageRequest builder.
         */
        public PageRequestBuilder pgEnd(int pgEnd) {
            pageRequest.setPgEnd(pgEnd);
            return this;
        }

        /**
         * Set pgStart to builder.
         *
         * @param totalPages (required) PageRequest total number of pagination (visible and hidden).
         * @return PageRequest builder.
         */
        public PageRequestBuilder totalPages(int totalPages) {
            pageRequest.setTotalPages(totalPages);
            return this;
        }

        /**
         * Set order to builder.
         *
         * @param order (required) order by parameter (column name).
         * @return PageRequest builder.
         */
        public PageRequestBuilder order(String order) {
            pageRequest.setOrder(order);
            return this;
        }

        /**
         * Set search to builder.
         *
         * @param search (required) filter by parameter (search box input).
         * @return PageRequest builder.
         */
        public PageRequestBuilder search(String search) {
            pageRequest.setSearch(search);
            return this;
        }

        /**
         * Set isLastPage to builder.
         *
         * @param islastPage (required) boolean : current page is last page or not.
         * @return PageRequest builder.
         */
        public PageRequestBuilder islastPage(boolean islastPage) {
            pageRequest.setLastPage(islastPage);
            return this;
        }


        /**
         * Build PageRequest.
         *
         * @return PageRequest.
         */
        public PageRequest build() {
            return pageRequest;
        }
    }
}
