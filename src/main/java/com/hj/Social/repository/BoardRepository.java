package com.hj.Social.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hj.Social.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
		
}
