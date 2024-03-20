package com.hj.Social.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Comments;
import com.hj.Social.entity.QComments;
import com.hj.Social.repository.CommentsRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.dml.UpdateClause;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
	
	private final QComments comments = QComments.comments;
	private final CommentsRepository repository;
	private final JPAQueryFactory queryFactory;
	private final EntityManager entityManager;
	
	@Override
	public PageResultDTO<Comments> selectList(PageRequestDTO requestDTO, int id){
		QueryResults<Comments> result = queryFactory
	            .selectFrom(comments)
	            .where(comments.comment_delyn.eq("N").and(comments.board_id.eq(id)))
	            .orderBy(comments.comment_root.asc(),comments.comment_steps.asc())
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
	@Transactional
	public void stepUpdate(Comments entity) {
		UpdateClause update = queryFactory.update(comments)
			    .set(comments.comment_steps, comments.comment_steps.add(1))
			    .where(
			        comments.comment_root.eq(entity.getComment_root())
			            .and(comments.comment_steps.goe(entity.getComment_steps()))
			            .and(comments.comment_id.ne(entity.getComment_id()))
			    );

		update.execute();
	}
	
	@Override
	@Transactional
	public int boardDelete(int id) {
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	    
		long updatedCount = queryFactory.update(comments)
	            .set(comments.comment_delyn, "Y")
	            .set(comments.comment_deldate, now) 
	            .where(comments.board_id.eq(id))
	            .execute();
		
	    return (int)updatedCount;
	}

	
	@Override
	@Transactional
	public int delete(Comments entity) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        long updatedCount = queryFactory.update(comments)
                .set(comments.comment_delyn, "Y")
                .set(comments.comment_deldate, now)
                .where(comments.comment_root.eq(entity.getComment_id())
                        .or(comments.comment_root.eq(entity.getComment_root())
                            .and(comments.comment_steps.between(entity.getComment_steps(), entity.getComment_steps() + entity.getComment_steps()))))
                .execute();

        return (int) updatedCount;
    }

}
