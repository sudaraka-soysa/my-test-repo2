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

import com.adl.et.telco.dte.template.baseapp.application.transport.request.entities.SampleRequestEntity;
import com.adl.et.telco.dte.template.baseapp.application.validator.RequestEntityValidator;
import com.adl.et.telco.dte.template.baseapp.domain.service.SampleManageService;
import com.adl.et.telco.dte.template.baseapp.application.transformer.ResponseEntityTransformer;
import com.adl.et.telco.dte.template.baseapp.application.transport.response.transformers.SampleResponseTransformer;
import com.adl.et.telco.dte.template.baseapp.domain.entities.dto.SampleDomainRequestEntity;
import com.adl.et.telco.dte.template.baseapp.domain.entities.dto.SampleDomainResponseEntity;
import com.adl.et.telco.dte.plugin.logging.services.LoggingUtils;
import io.prometheus.client.CollectorRegistry;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("${base-url.context}")
public class IntegratorController extends BaseController {
    private static final Logger logger = LoggingUtils.getLogger(IntegratorController.class.getName());

    @Autowired
    SampleManageService sampleManageService;

    @Autowired
    ResponseEntityTransformer responseEntityTransformer;

    @Autowired
    SampleResponseTransformer sampleResponseTransformer;

    @Autowired
    private RequestEntityValidator validator;

    @PostMapping(value="/action", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> integration(@Validated @RequestBody(required = true) SampleRequestEntity sampleRequestEntity, HttpServletRequest request)throws Exception{

//        TODO: set UUID
        setLogIdentifier(request);
//        TODO: validate the request
        validator.validate(sampleRequestEntity);
//        logger.info("Request validation success");

//        TODO: request object map to domain entity object
        SampleDomainRequestEntity sampleDomainRequestEntity = new ModelMapper().map(sampleRequestEntity, SampleDomainRequestEntity.class);

//        TODO: call domain business logic
        SampleDomainResponseEntity sampleDomainResponseEntity = sampleManageService.process(sampleDomainRequestEntity);

//        TODO: transform domain response
        Map trResponse = responseEntityTransformer.transform(sampleDomainResponseEntity,sampleResponseTransformer);
//        logger.info("Transformed response : "+trResponse.toString());

//        TODO: return response
        switch (sampleDomainResponseEntity.getResCode()){
            case "200" : return ResponseEntity.status(HttpStatus.OK).body(trResponse);
            case "202" : return ResponseEntity.status(HttpStatus.ACCEPTED).body(trResponse);
            case "400" : return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(trResponse);
            case "404" : return ResponseEntity.status(HttpStatus.NOT_FOUND).body(trResponse);
            default: return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(trResponse);
        }
    }
}
