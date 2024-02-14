package com.hj.Social.service;

import static com.hj.Social.entity.QBoard.board;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hj.Social.entity.Board;
import com.hj.Social.repository.BoardRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	private final BoardRepository repository;
	private final JPAQueryFactory queryFactory;
	
	@Override
	public List<Board> selectList(){
		return queryFactory.selectFrom(board)
				.where(board.board_delyn.eq("N"))
				.orderBy(board.board_id.desc())
				.fetch();
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
