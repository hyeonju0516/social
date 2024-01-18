package com.hj.Social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hj.Social.domain.OauthId;
import com.hj.Social.entity.User;

public interface UserRepository extends JpaRepository<User, OauthId> {

}
