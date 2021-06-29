package com.akash.projects.referralsystem.repository;

import com.akash.projects.referralsystem.domain.User;
import com.akash.projects.referralsystem.domain.UserMilestone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface UserMilestoneRepository extends CrudRepository<UserMilestone, Long> {

    @Query(value = "select u from UserMilestone u where u.user=:user")
    Iterable<UserMilestone> findByUser(@Param("user") User user);
}
