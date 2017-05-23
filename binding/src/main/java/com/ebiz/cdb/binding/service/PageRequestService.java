package com.ebiz.cdb.binding.service;

import com.ebiz.cdb.core.models.PageRequest;

import javax.servlet.http.HttpSession;

public interface PageRequestService {

    /**
     * Build page from request data.
     *
     * @param session     session.
     * @param currentPage currentPage
     * @param search      search
     * @param order       order
     * @param limit       limit
     * @param click       click
     * @return page Request.
     */
    PageRequest buildPage(HttpSession session, String currentPage, String search, String order, String limit, String click);
}
