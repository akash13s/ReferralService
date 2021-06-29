package com.akash.projects.referralsystem.controller;

import com.akash.projects.referralsystem.domain.Milestone;
import com.akash.projects.referralsystem.payload.response.MilestoneList;
import com.akash.projects.referralsystem.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/milestone")
public class MilestoneController {

    @Autowired
    private MilestoneService milestoneService;

    @PostMapping("/add")
    public ResponseEntity<Milestone> addMilestone(@Valid @RequestBody Milestone milestone) {
        Milestone newMilestone = milestoneService.addMilestone(milestone);
        return new ResponseEntity<Milestone>(newMilestone, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<MilestoneList> getAllMilestones() {
        List<Milestone> milestones = milestoneService.getAllMilestones();
        MilestoneList milestoneList = new MilestoneList();
        milestoneList.setMilestoneList(milestones);
        return new ResponseEntity<MilestoneList>(milestoneList, HttpStatus.OK);
    }
}
