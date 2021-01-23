package com.codewithshihab.server.controller;

import com.codewithshihab.server.exception.ExecutionFailureException;
import com.codewithshihab.server.models.BloodRequest;
import com.codewithshihab.server.models.Feedback;
import com.codewithshihab.server.service.BloodRequestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = "*", exposedHeaders = {"httpStatus", "messageType", "messageTitle", "messageDescription", "servedAt"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping(value = "/blood-request/", produces = MediaType.APPLICATION_JSON_VALUE)
public class BloodRequestController {
    private final BloodRequestService bloodRequestService;

    public BloodRequestController(BloodRequestService bloodRequestService) {
        this.bloodRequestService = bloodRequestService;
    }

    @GetMapping("list")
    public ResponseEntity<?> findAll(HttpServletRequest httpServletRequest) {
        return new ResponseEntity<>(bloodRequestService.findAll(), HttpStatus.OK);
    }

    @GetMapping("view/{bloodRequestId}")
    public ResponseEntity<?> findById(@PathVariable String bloodRequestId, HttpServletRequest httpServletRequest) {
        try {
            return new ResponseEntity<>(bloodRequestService.findById(bloodRequestId), HttpStatus.OK);
        } catch (ExecutionFailureException e) {
            return new ResponseEntity<>(e.getError(), HttpStatus.valueOf(e.getError().getCode()));
        }
    }

    @GetMapping("add-donation-date/{bloodRequestId}/{donationDate}/{accessToken}")
    public ResponseEntity<?> addDonationDate(@PathVariable String bloodRequestId, @PathVariable String donationDate, @PathVariable String accessToken, HttpServletRequest httpServletRequest) {
        try {
            return new ResponseEntity<>(bloodRequestService.addBloodDonationDate(bloodRequestId, LocalDateTime.parse(donationDate), accessToken), HttpStatus.CREATED);
        } catch (ExecutionFailureException e) {
            return new ResponseEntity<>(e.getError(), HttpStatus.valueOf(e.getError().getCode()));
        }
    }

    @GetMapping("add-interested-donor/{bloodRequestId}/{accessToken}")
    public ResponseEntity<?> addInterestedDonorInList(@PathVariable String bloodRequestId, @PathVariable String accessToken, HttpServletRequest httpServletRequest) {
        try {
            return new ResponseEntity<>(bloodRequestService.addInterestedDonorInList(bloodRequestId, accessToken), HttpStatus.CREATED);
        } catch (ExecutionFailureException e) {
            return new ResponseEntity<>(e.getError(), HttpStatus.valueOf(e.getError().getCode()));
        }
    }

    @GetMapping("add-donor-information-after-giving-blood/{donorUsername}/{bloodRequestId}/{accessToken}")
    public ResponseEntity<?> addDonorInformationAfterGivingBlood(@PathVariable String donorUsername, @PathVariable String bloodRequestId, @PathVariable String accessToken, HttpServletRequest httpServletRequest) {
        try {
            return new ResponseEntity<>(bloodRequestService.addDonorInformationAfterGivingBlood(donorUsername, bloodRequestId, accessToken), HttpStatus.CREATED);
        } catch (ExecutionFailureException e) {
            return new ResponseEntity<>(e.getError(), HttpStatus.valueOf(e.getError().getCode()));
        }
    }

    @PostMapping("create/{accessToken}")
    public ResponseEntity<?> create(@PathVariable String accessToken, @RequestBody BloodRequest bloodRequest, HttpServletRequest httpServletRequest) {
        try {
            return new ResponseEntity<>(bloodRequestService.create(bloodRequest, accessToken), HttpStatus.CREATED);
        } catch (ExecutionFailureException e) {
            return new ResponseEntity<>(e.getError(), HttpStatus.valueOf(e.getError().getCode()));
        }
    }

    @PostMapping("add-feedback/{bloodRequestId}/{accessToken}")
    public ResponseEntity<?> addFeedback(@PathVariable String bloodRequestId, @PathVariable String accessToken, @RequestBody Feedback feedback, HttpServletRequest httpServletRequest) {
        try {
            return new ResponseEntity<>(bloodRequestService.addFeedback(feedback, bloodRequestId, accessToken), HttpStatus.CREATED);
        } catch (ExecutionFailureException e) {
            return new ResponseEntity<>(e.getError(), HttpStatus.valueOf(e.getError().getCode()));
        }
    }
}
