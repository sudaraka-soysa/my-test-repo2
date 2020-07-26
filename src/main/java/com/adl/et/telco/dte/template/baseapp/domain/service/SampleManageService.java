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
package com.adl.et.telco.dte.template.baseapp.domain.service;

import ch.qos.logback.classic.Logger;
import com.adl.et.telco.dte.template.baseapp.domain.entities.dto.SampleDomainRequestEntity;
import com.adl.et.telco.dte.template.baseapp.domain.entities.dto.SampleDomainResponseEntity;
import com.adl.et.telco.dte.plugin.logging.services.LoggingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SampleManageService {
    private static final Logger logger = LoggingUtils.getLogger(SampleManageService.class.getName());

    @Autowired
    SampleDomainResponseEntity sampleDomainResponseEntity;
    /**
     * handle business logic
     * @param
     * @return
     */
    public SampleDomainResponseEntity process(SampleDomainRequestEntity sampleDomainRequestEntity) throws Exception{
        logger.info("INFO|START use case...| request : " );
//        TODO: Execute business logic

        sampleDomainResponseEntity.setResCode("200");
        sampleDomainResponseEntity.setResDesc("Operation Success");

        return sampleDomainResponseEntity;
    }

}
