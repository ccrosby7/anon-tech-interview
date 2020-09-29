package com.crosby.anontech.service.impl;

import com.crosby.anontech.config.ApplicationProperties;
import com.crosby.anontech.service.MovingAverageService;
import com.crosby.anontech.util.MetricRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for interfacing with the MA Util.
 */
@Service
public class MovingAverageServiceImpl implements MovingAverageService {

    private final ApplicationProperties applicationProps;

    public MovingAverageServiceImpl(ApplicationProperties applicationProperties){
        this.applicationProps = applicationProperties;
    }

    @Override
    public double getCableDiameterMA() {
        return MetricRequestUtil.getMovingAverage();
    }

    @Scheduled(fixedDelayString = "${application.repeatInMillis}")
    public void requestCableDiameter(){
        MetricRequestUtil.requestCableDiameterAsync(applicationProps.getUrl());
    }
}
