package com.crosby.anontech.web.rest;

import com.crosby.anontech.service.MovingAverageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the application.
 */
@RestController
@RequestMapping("/api")
public class MovingAverageResource {

    private final Logger log = LoggerFactory.getLogger(MovingAverageResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovingAverageService movingAverageService;

    public MovingAverageResource(MovingAverageService movingAverageService) {
        this.movingAverageService = movingAverageService;
    }

    /**
     * {@code GET  /api/cable-diameter} : get the current moving average of the cable.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the quote, or with status {@code 400 (Bad Request)}.
     */
    @GetMapping("/cable-diameter")
    public ResponseEntity<Double> getCableDiameter() {
        log.debug("REST request to get moving average of the cable diameter");
        var response = movingAverageService.getCableDiameterMA();
        if(response == -1){ //magic number, move to optional?
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(response);
    }

}
