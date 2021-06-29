package com.akash.projects.referralsystem.payload.response;

import com.akash.projects.referralsystem.domain.Milestone;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MilestoneList {

    private List<Milestone> milestoneList;
}
