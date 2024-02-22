package com.hj.Social.service;

import com.hj.Social.domain.LikesId;
import com.hj.Social.entity.Likes;

public interface LikesService {
	
	Likes selectDetail(LikesId id);
	Likes save(Likes entity);
	void delete(LikesId id);
}
