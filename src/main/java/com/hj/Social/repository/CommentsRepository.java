package com.hj.Social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hj.Social.entity.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Integer> {

}
