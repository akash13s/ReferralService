package com.akash.projects.referralsystem.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Transient
    private String confirmPassword;

    private String signupReferralCode;

    private String userReferralCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<UserReferral> userReferralList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<UserMilestone> userMilestones = new ArrayList<>();

    private Long cashRewardOnSignup;

    public List<UserReferral> getUserReferralList() {
        return userReferralList;
    }

    public void setUserReferralList(List<UserReferral> userReferralList) {
        this.userReferralList = userReferralList;
    }

    public List<UserMilestone> getUserMilestones() {
        return userMilestones;
    }

    public void setUserMilestones(List<UserMilestone> userMilestones) {
        this.userMilestones = userMilestones;
    }

    public Long getCashRewardOnSignup() {
        return cashRewardOnSignup;
    }

    public void setCashRewardOnSignup(Long cashRewardOnSignup) {
        this.cashRewardOnSignup = cashRewardOnSignup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSignupReferralCode() {
        return signupReferralCode;
    }

    public void setSignupReferralCode(String signupReferralCode) {
        this.signupReferralCode = signupReferralCode;
    }

    public String getUserReferralCode() {
        return userReferralCode;
    }

    public void setUserReferralCode(String userReferralCode) {
        this.userReferralCode = userReferralCode;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
