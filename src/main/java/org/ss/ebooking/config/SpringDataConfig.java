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
package org.ss.ebooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.ss.ebooking.entity.SystemUser;
import org.ss.ebooking.service.SecurityService;

/**
 * Spring Data configuration.
 * @author Alexandr Omeluaniuk
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SpringDataConfig {
    @Autowired
    private SecurityService securityService;
    @Bean
    public AuditorAware<SystemUser> auditorAware() {
        return securityService;
    }
}
