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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.ss.ebooking.config.security.StandardRole;
import org.ss.ebooking.dao.CoreDAO;
import org.ss.ebooking.entity.Subscription;
import org.ss.ebooking.entity.SystemUser;
import org.ss.ebooking.service.SubscriptionService;
import org.ss.ebooking.service.SystemUserService;

/**
 * Subscription service implementation.
 * @author ss
 */
@Service
class SubscriptionServiceImpl implements SubscriptionService {
    /** Core DAO. */
    @Autowired
    private CoreDAO coreDAO;
    /** System user service. */
    @Autowired
    private SystemUserService systemUserService;
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Subscription createSubscription(Subscription subscription) throws Exception {
        SystemUser subscriptionAdmin = new SystemUser();
        subscriptionAdmin.setStandardRole(StandardRole.ROLE_SUBSCRIPTION_ADMINISTRATOR);
        subscriptionAdmin.setLastname(subscription.getOrganizationName());
        subscriptionAdmin.setEmail(subscription.getSubscriptionAdminEmail());
        systemUserService.startRegistration(subscriptionAdmin);
        return coreDAO.create(subscription);
    }
}
