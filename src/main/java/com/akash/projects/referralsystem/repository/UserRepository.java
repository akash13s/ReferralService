package com.akash.projects.referralsystem.repository;

import com.akash.projects.referralsystem.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);
    User findByUserReferralCode(String userReferralCode);
}
