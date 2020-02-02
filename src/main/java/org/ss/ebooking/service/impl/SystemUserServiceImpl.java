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
package org.ss.ebooking.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.ss.ebooking.config.EBookingConfig;
import org.ss.ebooking.config.security.StandardRole;
import org.ss.ebooking.config.security.SystemUserStatus;
import org.ss.ebooking.dao.CoreDAO;
import org.ss.ebooking.dao.UserDAO;
import org.ss.ebooking.entity.Subscription;
import org.ss.ebooking.entity.SystemUser;
import org.ss.ebooking.service.SystemUserService;

/**
 * System user service implementation.
 * @author ss
 */
@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
class SystemUserServiceImpl implements SystemUserService {
    /** Logger. */
    private static final Logger LOG = LoggerFactory.getLogger(SystemUserService.class);
    /** Core DAO. */
    @Autowired
    private CoreDAO coreDAO;
    /** User DAO. */
    @Autowired
    private UserDAO userDAO;
    /** Email service. */
    @Autowired
    private JavaMailSender emailService;
    /** Password encoder. */
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /** E-Booking configuration. */
    @Autowired
    private EBookingConfig config;
// =================================================== PUBLIC =========================================================
    @Override
    public void startRegistration(SystemUser systemUser) throws Exception {
        String validationString = UUID.randomUUID().toString();
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(systemUser.getEmail());
        msg.setSubject("Регистрация нового пользователя");
        msg.setText("My test: " + validationString);
        systemUser.setStatus(SystemUserStatus.REGISTRATION);
        systemUser.setValidationString(validationString);
        emailService.send(msg);
        coreDAO.create(systemUser);
    }
    @Override
    public void superUserCheck() {
        SystemUser superAdmin = userDAO.getSuperUser();
        if (superAdmin == null && config.getSuperAdminEmail() != null
                && config.getSuperAdminLastName() != null && config.getSuperAdminPassword() != null) {
            LOG.info("super user is not exist, create it...");
            try {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(new Date());
                Subscription superAdminSubscription = new Subscription();
                superAdminSubscription.setStarted(calendar.getTime());
                calendar.add(Calendar.YEAR, 33);
                superAdminSubscription.setExpirationDate(calendar.getTime());
                superAdminSubscription.setOrganizationName("Super Admin subscription");
                superAdminSubscription = coreDAO.create(superAdminSubscription);
                superAdmin = new SystemUser();
                superAdmin.setSubscription(superAdminSubscription);
                superAdmin.setEmail(config.getSuperAdminEmail());
                superAdmin.setFirstname(config.getSuperAdminFirstName());
                superAdmin.setLastname(config.getSuperAdminLastName());
                superAdmin.setPassword(bCryptPasswordEncoder.encode(config.getSuperAdminPassword()));
                superAdmin.setStandardRole(StandardRole.ROLE_SUPER_ADMIN);
                superAdmin.setStatus(SystemUserStatus.ACTIVE);
                coreDAO.create(superAdmin);
            } catch (Exception e) {
                LOG.warn("Unexpected error occurred during super user creation.", e);
            }
        }
    }
}
