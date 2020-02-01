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
package org.ss.ebooking.service;

import org.ss.ebooking.config.security.UserPermissions;
import org.ss.ebooking.entity.SystemUser;

/**
 * Security service.
 * @author Alexandr Omeluaniuk
 */
public interface SecurityService {
    /**
     * Get current user.
     * @return current user.
     */
    SystemUser currentUser();
    /**
     * Get user permissions.
     * @return user permissions.
     * @throws Exception error.
     */
    UserPermissions getUserPermissions() throws Exception;
}
