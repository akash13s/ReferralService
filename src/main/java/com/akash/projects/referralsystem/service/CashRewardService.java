package com.akash.projects.referralsystem.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CashRewardService {

    public long generateRandomCashReward() {
        Random r = new Random();
        int low = 1;
        int high = 30;
        int result = r.nextInt(high-low) + low;
        return (long) result;
    }
}
