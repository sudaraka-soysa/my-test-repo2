/**
 * Copyrights 2020 Axiata Digital Labs Pvt Ltd.
 * All Rights Reserved.
 *
 * These material are unpublished, proprietary, confidential source
 * code of Axiata Digital Labs Pvt Ltd (ADL) and constitute a TRADE
 * SECRET of ADL.
 *
 * ADL retains all title to and intellectual property rights in these
 * materials.
 *
 */
package com.adl.et.telco.dte.template.baseapp.application.controller;

import ch.qos.logback.classic.Logger;
import com.adl.et.telco.dte.plugin.logging.services.LoggingUtils;
import com.adl.et.telco.dte.template.baseapp.application.config.YAMLConfig;
import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class BaseController {
    private static final Logger logger = LoggingUtils.getLogger(BaseController.class.getName());

    @Autowired
    private YAMLConfig yamlConfig;

    public void setLogIdentifier(HttpServletRequest request) {

        String logIdentifier = request.getHeader(yamlConfig.getLogIdentifierKey());
        if (logIdentifier != null) {
            MDC.put(yamlConfig.getLogIdentifierKey(), logIdentifier);
        } else {
            MDC.put(yamlConfig.getLogIdentifierKey(), UUID.randomUUID().toString());
        }
    }
}
