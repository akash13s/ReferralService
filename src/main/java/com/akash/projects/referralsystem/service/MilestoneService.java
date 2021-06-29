package com.akash.projects.referralsystem.service;

import com.akash.projects.referralsystem.domain.Milestone;
import com.akash.projects.referralsystem.repository.MilestoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MilestoneService {

    @Autowired
    private MilestoneRepository milestoneRepository;

    public Milestone addMilestone(Milestone milestone) {
        return milestoneRepository.save(milestone);
    }

    public List<Milestone> getAllMilestones() {
        List<Milestone> milestones = new ArrayList<>();
        milestoneRepository.findAll().forEach(milestones::add);
        return milestones;
    }

}
