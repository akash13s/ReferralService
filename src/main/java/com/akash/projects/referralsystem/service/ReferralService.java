package com.akash.projects.referralsystem.service;

import com.akash.projects.referralsystem.domain.Milestone;
import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.domain.UserMilestone;
import com.akash.projects.referralsystem.domain.UserReferral;
import com.akash.projects.referralsystem.exceptions.user.UserAlreadyEnrolledInReferralSystemException;
import com.akash.projects.referralsystem.exceptions.user.UserNotEnrolledInReferralSystemException;
import com.akash.projects.referralsystem.payload.response.MilestoneList;
import com.akash.projects.referralsystem.payload.response.ReferralHistory;
import com.akash.projects.referralsystem.payload.response.UserMilestones;
import com.akash.projects.referralsystem.payload.response.UserReferralHistory;
import com.akash.projects.referralsystem.repository.MilestoneRepository;
import com.akash.projects.referralsystem.repository.UserMilestoneRepository;
import com.akash.projects.referralsystem.repository.UserReferralRepository;
import com.akash.projects.referralsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ReferralService {

    @Autowired
    private CashRewardService cashRewardService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MilestoneRepository milestoneRepository;

    @Autowired
    private UserMilestoneRepository userMilestoneRepository;

    @Autowired
    private UserReferralRepository userReferralRepository;

    public User processSignup(User referee) {

        // generate cash rewards for both referee and referrer
        Long cashRewardForReferee = cashRewardService.generateRandomCashReward();
        Long cashRewardForReferrer = cashRewardService.generateRandomCashReward();
        referee.setCashRewardOnSignup(cashRewardForReferee);
        User newUser = userRepository.save(referee);

        // add transaction to USER_REFERRAL table
        String userReferralCode = newUser.getSignupReferralCode();
        User referrer = userRepository.findByUserReferralCode(userReferralCode);
        UserReferral userReferral = new UserReferral();
        userReferral.setUser(referrer);
        userReferral.setReferredUserId(newUser.getId());
        userReferral.setReferredUserEmail(newUser.getEmail());
        userReferral.setSignupReferralCode(userReferralCode);
        userReferral.setCashRewardForReferee(cashRewardForReferee);
        userReferral.setCashRewardForReferrer(cashRewardForReferrer);

        referrer.getUserReferralList().add(userReferral);
        userRepository.save(referrer);

        // update USER_MILESTONE table after performing checks
        Iterable<Milestone> milestones = milestoneRepository.findAll();
        Iterable<UserMilestone> userMilestones = userMilestoneRepository.findByUser(referrer);
        milestones.forEach(milestone -> {
            if (isMilestoneAchieved(milestone, referrer) &&
                    !containsMilestone(milestone, userMilestones)) {
                UserMilestone userMilestone = new UserMilestone();
                userMilestone.setMilestone(milestone);
                userMilestone.setUser(referrer);
                referrer.getUserMilestones().add(userMilestone);
            }
        });
        userRepository.save(referrer);
        return newUser;
    }

    private boolean isMilestoneAchieved(Milestone milestone, User user) {
        Long countOfReferredUsers = userReferralRepository.findCountOfReferredUsers(user, user.getUserReferralCode());
        return countOfReferredUsers>=milestone.getNumberOfPeopleReferred();
    }

    private boolean containsMilestone(Milestone milestone, Iterable<UserMilestone> userMilestones) {
        AtomicBoolean flag = new AtomicBoolean(false);
        userMilestones.forEach(userMilestone -> {
            if (userMilestone.getMilestone().getId().equals(milestone.getId())) {
                flag.set(true);
            }
        });
        return flag.get();
    }

    public User enrolInReferralSystem(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.nonNull(user.getUserReferralCode())) {
            throw new UserAlreadyEnrolledInReferralSystemException(username + " is already enrolled in referral-system");
        }
        user.setUserReferralCode(generateReferralCode());
        return userRepository.save(user);
    }

    private String generateReferralCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public User unrollFromReferralSystem(String username) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user.getUserReferralCode())) {
            throw new UserNotEnrolledInReferralSystemException(username + " is not enrolled in referral-system");
        }
        // make the user referral code NULL
        user.setUserReferralCode(null);

        // remove user referral list
        user.getUserReferralList().clear();

        // remove user milestones
        user.getUserMilestones().clear();
        return userRepository.save(user);
    }

    public UserReferralHistory getReferralHistory(String username) {
        User user = userRepository.findByUsername(username);
        UserReferralHistory userReferralHistory = mapToUserReferralHistory(user);
        return userReferralHistory;
    }

    private UserReferralHistory mapToUserReferralHistory(User user) {
        UserReferralHistory userReferralHistory = new UserReferralHistory();
        List<ReferralHistory> referralHistoryList = new ArrayList<>();
        user.getUserReferralList().forEach(userReferral -> {
            ReferralHistory referralHistory = new ReferralHistory();
            referralHistory.setRefereeEmail(userReferral.getReferredUserEmail());
            referralHistory.setCashReward(userReferral.getCashRewardForReferrer());
            referralHistoryList.add(referralHistory);
        });
        userReferralHistory.setReferralHistory(referralHistoryList);
        userReferralHistory.setUsername(user.getUsername());
        return userReferralHistory;
    }

    public UserMilestones getUserMilestones(String username) {
        User user = userRepository.findByUsername(username);
        return mapToUserMilestones(user);
    }

    private UserMilestones mapToUserMilestones(User user) {
        UserMilestones userMilestones = new UserMilestones();

        // set achieved milestones
        MilestoneList achievedMilestones = new MilestoneList();
        List<Milestone> milestonesDone = new ArrayList<>();
        user.getUserMilestones().forEach(userMilestone -> milestonesDone.add(userMilestone.getMilestone()));
        achievedMilestones.setMilestoneList(milestonesDone);
        userMilestones.setAchievedMilestones(achievedMilestones);

        // set incomplete milestones
        MilestoneList incompleteMilestones = new MilestoneList();
        List<Milestone> milestonesNotDone = new ArrayList<>();
        Iterable<Milestone> milestones = milestoneRepository.findAll();
        milestones.forEach(milestone -> {
            if (!containsMilestone(milestone, user.getUserMilestones())) {
                milestonesNotDone.add(milestone);
            }
        });
        incompleteMilestones.setMilestoneList(milestonesNotDone);
        userMilestones.setIncompleteMilestones(incompleteMilestones);

        return userMilestones;
    }

}
