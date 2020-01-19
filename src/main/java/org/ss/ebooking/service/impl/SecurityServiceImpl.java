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

import java.util.Optional;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.ss.ebooking.config.security.UserPrincipal;
import org.ss.ebooking.entity.SystemUser;
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
    public Optional<SystemUser> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(UserPrincipal.class::cast).filter(UserPrincipal::isAuthenticated)
                .map(UserPrincipal::getUser);
    }
}
