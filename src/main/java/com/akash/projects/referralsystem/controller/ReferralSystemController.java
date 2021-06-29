package com.akash.projects.referralsystem.controller;

import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.payload.response.UserMilestones;
import com.akash.projects.referralsystem.payload.response.UserReferralHistory;
import com.akash.projects.referralsystem.service.ReferralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/referral-system")
public class ReferralSystemController {

    @Autowired
    private ReferralService referralService;

    @PostMapping("/enrol")
    public ResponseEntity<User> enrolInReferralSystem(Principal principal) {
        User user = referralService.enrolInReferralSystem(principal.getName());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping("/unroll")
    public ResponseEntity<User> unrollFromReferralSystem(Principal principal) {
        User user = referralService.unrollFromReferralSystem(principal.getName());
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<UserReferralHistory> getReferralHistory(Principal principal) {
        UserReferralHistory userReferralHistory = referralService.getReferralHistory(principal.getName());
        return new ResponseEntity<>(userReferralHistory, HttpStatus.OK);
    }

    @GetMapping("/milestones")
    public ResponseEntity<UserMilestones> getMilestones(Principal principal) {
        UserMilestones userMilestones = referralService.getUserMilestones(principal.getName());
        return new ResponseEntity<>(userMilestones, HttpStatus.OK);
    }
}
