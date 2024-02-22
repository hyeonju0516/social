package com.hj.Social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hj.Social.domain.LikesId;
import com.hj.Social.entity.Likes;

public interface LikesRepository extends JpaRepository<Likes, LikesId> {

}
