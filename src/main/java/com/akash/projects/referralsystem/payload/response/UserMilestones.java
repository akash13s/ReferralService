package com.akash.projects.referralsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserMilestones {

    private MilestoneList achievedMilestones;
    private MilestoneList incompleteMilestones;
}
