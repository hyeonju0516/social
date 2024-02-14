package com.hj.Social.service;

import java.util.List;

import com.hj.Social.entity.Comments;

public interface CommentsService {
	
	List<Comments> selectList();
	Comments selectDetail(int id);
	Comments save(Comments entity);
	int delete(int id);
}
