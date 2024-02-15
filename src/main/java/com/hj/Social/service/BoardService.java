package com.hj.Social.service;

import java.util.List;

import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Board;

public interface BoardService {
	
	PageResultDTO<Board> selectList(PageRequestDTO requestDTO, String searchType, String keyword);
	Board selectDetail(int id);
	Board save(Board entity);
	int delete(int id);
}
