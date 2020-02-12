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
package ss.martin.platform.service.impl;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import ss.martin.platform.anno.ui.SideBarNavigationItem;
import ss.martin.platform.entity.DataModel;
import ss.martin.platform.entity.SystemUser;
import ss.martin.platform.service.EntityMetadataService;
import ss.martin.platform.service.EntityService;
import ss.martin.platform.service.SecurityService;
import ss.martin.platform.spring.security.SecurityContext;
import ss.martin.platform.spring.security.StandardRole;
import ss.martin.platform.wrapper.UserPermissions;

/**
 * Security service implementation.
 * @author Alexandr Omeluaniuk
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class SecurityServiceImpl implements SecurityService {
    /** Data model classes. */
    private static final Set<Class<? extends DataModel>> DATA_MODEL_CLASSES;
    /** Security context. */
    @Autowired
    private SecurityContext securityContext;
    /** Entity metadata service. */
    @Autowired
    private EntityMetadataService entityMetadataService;
    /**
     * Static initialization.
     */
    static {
        Reflections reflections = new Reflections(EntityService.ENTITY_PACKAGE);
        DATA_MODEL_CLASSES = reflections.getSubTypesOf(DataModel.class);
    }
    @Override
    public UserPermissions getUserPermissions() throws Exception {
        SystemUser currentUser = securityContext.currentUser();
        UserPermissions permissions = new UserPermissions();
        permissions.setFullname((currentUser.getFirstname() == null ? "" : currentUser.getFirstname() + " ")
                + currentUser.getLastname());
        permissions.setSideBarNavItems(new ArrayList<>());
        permissions.setDashboardTabItems(new ArrayList<>());
        for (Class<? extends DataModel> dataModelClass : DATA_MODEL_CLASSES) {
            if (!Modifier.isAbstract(dataModelClass.getModifiers())) {
                SideBarNavigationItem sideBarNavItem = dataModelClass.getAnnotation(SideBarNavigationItem.class);
                if (sideBarNavItem != null) {
                    Set<StandardRole> accessibleForRoles = new HashSet<>();
                    for (StandardRole sRole : sideBarNavItem.roles()) {
                        accessibleForRoles.add(sRole);
                    }
                    if (accessibleForRoles.contains(currentUser.getStandardRole())) {
                        permissions.getSideBarNavItems().add(entityMetadataService.getEntityListView(dataModelClass));
                    }
                }
            }
        }
        return permissions;
    }
}