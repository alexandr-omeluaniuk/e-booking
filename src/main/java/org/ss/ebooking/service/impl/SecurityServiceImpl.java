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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
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
class SecurityServiceImpl implements SecurityService {
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
        Reflections reflections = new Reflections(EntityService.ENTITY_PACKAGE);
        Set<Class<? extends DataModel>> allDataModels = reflections.getSubTypesOf(DataModel.class);
        for (Class<? extends DataModel> dataModelClass : allDataModels) {
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
                UserPermissions.EntityMetadata metadata = new UserPermissions.EntityMetadata();
                MaterialIcon materialIcon = dataModelClass.getAnnotation(MaterialIcon.class);
                if (materialIcon != null) {
                    metadata.setIcon(materialIcon.icon());
                }
                permissions.getEntityMetadata().add(metadata);
            }
        }
        return permissions;
    }
}
