/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.ebooking.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Core DAO API.
 * @author Alexandr Omeluaniuk
 */
public interface CoreDAO {
    /**
     * Create entity.
     * @param <T>    entity class.
     * @param entity entity.
     * @return created entity.
     */
    <T> T create(T entity);
    /**
     * Update entity.
     * @param <T>    entity class.
     * @param entity entity.
     * @return updated entity.
     */
    <T> T update(T entity);
    /**
     * Find entity by ID.
     * @param <T> entity type.
     * @param id  entity ID.
     * @param cl  entity class.
     * @return entity.
     */
    <T> T findById(Serializable id, Class<T> cl);
    /**
     * Delete entity.
     * @param <T> entity type.
     * @param id  entity ID.
     * @param cl  entity class.
     */
    <T> void delete(Serializable id, Class<T> cl);
    /**
     * Get all entities.
     * @param <T> entity type.
     * @param cl  entity class.
     * @return all entities.
     */
    <T> List<T> getAll(Class<T> cl);
    /**
     * Get portion of entities.
     * @param <T> entity type.
     * @param cl entity class.
     * @param page page.
     * @param pageSize page size.
     * @return all entities.
     */
    <T> List<T> getPortion(Class<T> cl, int page, int pageSize);
    /**
     * Get count of records.
     * @param <T> record type.
     * @param cl  record class.
     * @return count of records.
     */
    <T> Integer count(Class<T> cl);
}
