/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ss.ebooking.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application configuration.
 * @author ss
 */
@EnableAutoConfiguration
@ComponentScan("org.ss.ebooking")
@EntityScan("org.ss.ebooking.entity")
public class AppConfig {
}
