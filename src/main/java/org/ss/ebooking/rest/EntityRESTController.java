/*
 * The MIT License
 *
 * Copyright 2020 ss.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.ss.ebooking.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.ss.ebooking.constants.AppURLs;
import org.ss.ebooking.entity.DataModel;
import org.ss.ebooking.wrapper.EntityLayout;
import org.ss.ebooking.service.EntityService;
import org.ss.ebooking.wrapper.EntitySearchRequest;
import org.ss.ebooking.wrapper.EntitySearchResponse;

/**
 * Entity REST controller.
 * @author ss
 */
@RestController
@RequestMapping(AppURLs.APP_ADMIN_REST_API + "/entity")
public class EntityRESTController {
    /** Entity service. */
    @Autowired
    private EntityService entityService;
    /**
     * Get entity layout.
     * @param entityName entity name.
     * @return entity layout.
     * @throws Exception error.
     */
    @RequestMapping(value = "/layout/{entity}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityLayout getEntityLayout(@PathVariable("entity") String entityName) throws Exception {
        Class entityClass = (Class<? extends Serializable>) Class.forName(EntityService.ENTITY_PACKAGE + entityName);
        return entityService.getEntityLayout(entityClass);
    }
    /**
     * Search entities.
     * @param entityName entity name.
     * @param searchRequest search request.
     * @return search response.
     * @throws Exception error.
     */
    @RequestMapping(value = "/search/{entity}", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public EntitySearchResponse searchEntities(@PathVariable("entity") String entityName,
            @RequestBody EntitySearchRequest searchRequest) throws Exception {
        Class entityClass = (Class<? extends Serializable>) Class.forName(EntityService.ENTITY_PACKAGE + entityName);
        return entityService.searchEntities(entityClass, searchRequest);
    }
    /**
     * Create entity.
     * @param entityName entity name.
     * @param rawData raw data.
     * @return entity with ID.
     * @throws Exception error.
     */
    @RequestMapping(value = "/{entity}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object createEntity(@PathVariable("entity") String entityName, @RequestBody Object rawData) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        Class entityClass = (Class<? extends Serializable>) Class.forName(EntityService.ENTITY_PACKAGE + entityName);
        DataModel entity = (DataModel) mapper.convertValue(rawData, entityClass);
        return entityService.createEntity(entity);
    }
}
