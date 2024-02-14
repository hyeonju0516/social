package com.hj.Social.service;

import java.util.List;

import com.hj.Social.entity.Board;

public interface BoardService {
	
	List<Board> selectList();
	Board selectDetail(int id);
	Board save(Board entity);
	int delete(int id);
}
