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
package org.ss.ebooking.config.security;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.ss.ebooking.dao.UserDAO;
import org.ss.ebooking.entity.User;

/**
 * Authentication provider.
 * @author Alexandr Omeluaniuk
 */
@Component
class AuthManager implements AuthenticationManager {
    private static final Logger LOG = LoggerFactory.getLogger(AuthManager.class);
    /** Password encoder. */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /** User DAO. */
    @Autowired
    private UserDAO userDAO;
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getPrincipal() + "";
        String password = auth.getCredentials() + "";
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        if (!user.isActive()) {
            throw new DisabledException("User is deactivated: " + username);
        }
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password: " + password);
        }
        GrantedAuthority ga = new SimpleGrantedAuthority(user.getRole().getName());
        List<GrantedAuthority> gaList = new ArrayList<>();
        gaList.add(ga);
        UserPrincipal authentication = new UserPrincipal(username, password, gaList);
        authentication.setUser(user);
        LOG.info("successfull authentication for [" + user.getFirstname() + " " + user.getLastname()
                + "], is authenticated [" + authentication.isAuthenticated() + "]");
        return authentication;
    }
}
