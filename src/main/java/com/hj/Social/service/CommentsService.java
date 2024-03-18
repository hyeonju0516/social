package com.hj.Social.service;

import java.util.List;

import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Comments;

public interface CommentsService {
	
	PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int id);
	Comments selectDetail(int id);
	Comments save(Comments entity);
	void stepUpdate(Comments entity);
	int boardDelete(int id);
	int delete(int id);
}
