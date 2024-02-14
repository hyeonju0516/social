package com.hj.Social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hj.Social.entity.Comments;
import com.hj.Social.repository.CommentsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
	
	private final CommentsRepository repository;
	
	@Override
	public List<Comments> selectList(){
		return repository.findAll();
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
