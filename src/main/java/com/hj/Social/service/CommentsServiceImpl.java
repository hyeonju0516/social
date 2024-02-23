package com.hj.Social.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Comments;
import com.hj.Social.entity.QComments;
import com.hj.Social.repository.CommentsRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
	
	private final QComments comments = QComments.comments;
	private final CommentsRepository repository;
	private final JPAQueryFactory queryFactory;
	
	@Override
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int id){
		QueryResults<Comments> result = queryFactory
	            .selectFrom(comments)
	            .where(comments.comment_delyn.eq("N").and(comments.board_id.eq(id)))
	            .offset(requestDTO.getPageable().getOffset())
	            .limit(requestDTO.getPageable().getPageSize())
	            .fetchResults();

	    return new PageResultDTO<>(result, requestDTO.getPageable());
	}
	
	@Override
	public Comments selectDetail(int id){
		Optional<Comments> comments = repository.findById(id);
		
		if(comments.isPresent()) {
			return comments.get();
		}else {
			return null;
		}
	}
	
	@Override
	public Comments save(Comments entity) {
		return repository.save(entity);
	}
	
	@Override
	public int delete(int id) {
		repository.deleteById(id);
		return id;
	}

}
