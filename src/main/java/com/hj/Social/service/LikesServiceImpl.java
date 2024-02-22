package com.hj.Social.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hj.Social.domain.LikesId;
import com.hj.Social.entity.Likes;
import com.hj.Social.repository.LikesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService{
	
	private final LikesRepository repository;
	
	@Override
	public Likes selectDetail(LikesId id) {
		
		Optional<Likes> result = repository.findById(id);
		
		if(result.isPresent()) {
			return result.get();
		}else {
			return null;
		}
	};
	
	@Override
	public Likes save(Likes entity) {
		return repository.save(entity);
	};
	
	@Override
	public void delete(LikesId id) {
		repository.deleteById(id);		
	};
	

}
