package com.akash.projects.referralsystem.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserReferralHistory {
    String username;
    List<ReferralHistory> referralHistory;
}
