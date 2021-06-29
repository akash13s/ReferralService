package com.akash.projects.referralsystem.repository;

import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.domain.UserReferral;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserReferralRepository extends CrudRepository<UserReferral, Long> {

    @Query(value = "select count(u) from UserReferral u where u.user=:user and u.signupReferralCode=:referralCode")
    Long findCountOfReferredUsers(@Param("user")User user, @Param("referralCode") String referralCode);

}
