/*
 * #%L
 * React API Starter
 * %%
 * Copyright (C) 2009 - 2017 Broadleaf Commerce
 * %%
 * Broadleaf Commerce React Starter
 * 
 * Written in 2017 by Broadleaf Commerce info@broadleafcommerce.com
 * 
 * To the extent possible under law, the author(s) have dedicated all copyright and related and neighboring rights to this software to the public domain worldwide. This software is distributed without any warranty.
 * You should have received a copy of the CC0 Public Domain Dedication along with this software. If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 * 
 * Please Note - The scope of CC0 Public Domain Dedication extends to Broadleaf Commerce React Starter demo application alone. Linked libraries (including all Broadleaf Commerce Framework libraries) are subject to their respective licenses, including the requirements and restrictions specified therein.
 * #L%
 */
package com.mycompany.api.endpoint.seo;

import org.broadleafcommerce.common.page.dto.PageDTO;
import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.broadleafcommerce.rest.api.service.PageRequestService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Nick Crum ncrum
 */
@RestController
@RequestMapping(value = "/seo")
public class SeoEndpoint {

    protected final CatalogService catalogService;
    protected final PageRequestService pageRequestService;

    @Autowired
    public SeoEndpoint(PageRequestService pageRequestService, CatalogService catalogService) {
        this.pageRequestService = pageRequestService;
        this.catalogService = catalogService;
    }

    @RequestMapping
    public Map<String, String> getSeoProperties(HttpServletRequest request,
                                                @RequestParam("entityType") String entityType,
                                                @RequestParam("entityURI") String entityURI) {
        Map<String, String> seoProperties = new HashMap<>();
        switch(EntityType.valueOf(entityType)) {
            case PRODUCT:
                Product product = catalogService.findProductByURI(entityURI);
                if (product != null) {
                    seoProperties.put("title", product.getMetaTitle() != null ? product.getMetaTitle() : product.getName());
                    seoProperties.put("description", product.getMetaDescription() != null ? product.getMetaDescription() : product.getLongDescription());
                    seoProperties.put("canonicalUrl", product.getCanonicalUrl() != null ? product.getCanonicalUrl() : product.getUrl());
                }
                break;
            case CATEGORY:
                Category category = catalogService.findCategoryByURI(entityURI);
                if (category != null) {
                    seoProperties.put("title", category.getMetaTitle() != null ? category.getMetaTitle() : category.getName());
                    seoProperties.put("description", category.getMetaDescription() != null ? category.getMetaDescription() : category.getLongDescription());
                }
                break;
            case PAGE:
                PageDTO pageDTO = pageRequestService.findPageByURI(request, entityURI);
                if (pageDTO != null) {
                    Map<String, String> pageAttributes = pageDTO.getPageAttributes();
                    seoProperties.put("title", pageAttributes.get("title"));
                    seoProperties.put("description", pageAttributes.get("metaDescription"));
                }
                break;
            default:
        }

        return seoProperties;
    }

    private enum EntityType {
        PRODUCT,
        CATEGORY,
        PAGE
    }
}
