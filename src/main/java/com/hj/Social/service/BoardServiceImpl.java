package com.hj.Social.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Board;
import com.hj.Social.entity.Comments;
import com.hj.Social.entity.QBoard;
import com.hj.Social.repository.BoardRepository;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final QBoard board = QBoard.board;
	private final BoardRepository repository;
	private final JPAQueryFactory queryFactory;
	
	@Override
	public PageResultDTO<Board> selectList(PageRequestDTO requestDTO, String searchType, String keyword) {
	    BooleanExpression searchCondition = getSearchCondition(searchType, keyword);

	    QueryResults<Board> result = queryFactory
	            .selectFrom(board)
	            .where(board.board_delyn.eq("N").and(searchCondition))
	            .orderBy(board.board_id.desc())
	            .offset(requestDTO.getPageable().getOffset())
	            .limit(requestDTO.getPageable().getPageSize())
	            .fetchResults();

	    return new PageResultDTO<>(result, requestDTO.getPageable());
	}

    private BooleanExpression getSearchCondition(String searchType, String keyword) {
        if ("all".equals(searchType) || "".equals(keyword)) {
            return null;
        }

        switch (searchType) {
        	case "useremail":
        		return board.useremail.contains(keyword);
            case "board_title":
                return board.board_title.contains(keyword);
            case "board_content":
                return board.board_content.contains(keyword);
            default:
                return null;
        }
    }
    
    @Override
	public Board selectDetail(int id){
		Optional<Board> board = repository.findById(id);
		
		if(board.isPresent()) {
			return board.get();
		}else {
			return null;
		}
	}
	
	@Override
	public Board save(Board entity) {
		return repository.save(entity);
	}
	
	@Override
	public int delete(int id) {
		repository.deleteById(id);
		return id;
	}

}
