package com.akash.projects.referralsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class UserReferral {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    private Long referredUserId;
    private String referredUserEmail;
    private String signupReferralCode;
    private Long cashRewardForReferrer;
    private Long cashRewardForReferee;
}
