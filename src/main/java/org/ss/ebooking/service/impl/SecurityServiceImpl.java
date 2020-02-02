/*
 * Copyright (C) 2018 Wisent Media
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.ss.ebooking.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.ss.ebooking.anno.ListViewColumn;
import org.ss.ebooking.anno.MaterialIcon;
import org.ss.ebooking.anno.security.StandardRoleAccess;
import org.ss.ebooking.config.security.StandardRole;
import org.ss.ebooking.config.security.UserPermissions;
import org.ss.ebooking.config.security.UserPrincipal;
import org.ss.ebooking.entity.DataModel;
import org.ss.ebooking.entity.SystemUser;
import org.ss.ebooking.service.EntityService;
import org.ss.ebooking.service.SecurityService;

/**
 * Security service implementation.
 * @author Alexandr Omeluaniuk
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class SecurityServiceImpl implements SecurityService {
    /** Data model classes. */
    private static final Set<Class<? extends DataModel>> DATA_MODEL_CLASSES;
    /**
     * Static initialization.
     */
    static {
        Reflections reflections = new Reflections(EntityService.ENTITY_PACKAGE);
        DATA_MODEL_CLASSES = reflections.getSubTypesOf(DataModel.class);
    }
    @Override
    public SystemUser currentUser() {
        Object auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) auth;
        return userPrincipal.getUser();
    }
    @Override
    public UserPermissions getUserPermissions() throws Exception {
        SystemUser currentUser = this.currentUser();
        UserPermissions permissions = new UserPermissions();
        permissions.setEntityMetadata(new ArrayList<>());
        for (Class<? extends DataModel> dataModelClass : DATA_MODEL_CLASSES) {
            if (!Modifier.isAbstract(dataModelClass.getModifiers())) {
                boolean hasAccess = true;
                StandardRoleAccess roleAccess = dataModelClass.getAnnotation(StandardRoleAccess.class);
                if (roleAccess != null) {
                    Set<StandardRole> accessibleForRoles = new HashSet<>();
                    for (StandardRole sRole : roleAccess.roles()) {
                        accessibleForRoles.add(sRole);
                    }
                    hasAccess = accessibleForRoles.contains(currentUser.getStandardRole());
                }
                if (hasAccess) {
                    permissions.getEntityMetadata().add(createMetadata(dataModelClass));
                }
            }
        }
        return permissions;
    }
    // ========================================= PRIVATE ==============================================================
    /**
     * Create entity metadata.
     * @param dataModelClass data model class.
     * @return data model metadata.
     * @throws Exception error.
     */
    private UserPermissions.EntityMetadata createMetadata(Class<? extends DataModel> dataModelClass) throws Exception {
        UserPermissions.EntityMetadata metadata = new UserPermissions.EntityMetadata();
        metadata.setListViewColumns(new ArrayList());
        metadata.setClassName(dataModelClass.getSimpleName());
        MaterialIcon materialIcon = dataModelClass.getAnnotation(MaterialIcon.class);
        if (materialIcon != null) {
            metadata.setIcon(materialIcon.icon());
        }
        for (Field field : dataModelClass.getDeclaredFields()) {
            ListViewColumn listViewColumnAnno = field.getAnnotation(ListViewColumn.class);
            if (listViewColumnAnno != null) {
                UserPermissions.ListViewColumn listViewColumn = new UserPermissions.ListViewColumn();
                listViewColumn.setId(field.getName());
                listViewColumn.setAlign(listViewColumnAnno.align());
                metadata.getListViewColumns().add(listViewColumn);
            }
        }
        return metadata;
    }
}
