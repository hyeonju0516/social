package com.hj.Social.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hj.Social.domain.LikesId;
import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Board;
import com.hj.Social.entity.Likes;
import com.hj.Social.entity.User;
import com.hj.Social.service.BoardService;
import com.hj.Social.service.LikesService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@AllArgsConstructor
@Log4j2
@RequestMapping(value = "/board")
@Controller
public class BoardController {

	private BoardService boardService;
	private LikesService likesService;
	

	@GetMapping("/")
	public String getBoardList(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchType", defaultValue = "") String searchType,
			@RequestParam(value = "keyword", defaultValue = "") String keyword) {
		PageRequestDTO requestDTO = PageRequestDTO.builder().page(page).size(5).build();
		PageResultDTO<Board> resultDTO = boardService.selectList(requestDTO, searchType, keyword);

		model.addAttribute("boardList", resultDTO.getEntityList());
		model.addAttribute("resultDTO", resultDTO);
		model.addAttribute("searchType", searchType);
		model.addAttribute("keyword", keyword);
		
		return "board/boardList";
	}
	
	@GetMapping(value ="/{board_id}")
	public String getBoardDetail(HttpServletRequest request, Model model, @PathVariable("board_id") int id, HttpSession session) {
		
		Board entity = boardService.selectDetail(id);
		
		User loginUser = (User) session.getAttribute("loginUser");
		LikesId likeId = new LikesId(loginUser.getUseremail(),id);
		
		if(likesService.selectDetail(likeId) != null) {
			model.addAttribute("likeStatus","좋아요 취소");
		}else {
			model.addAttribute("likeStatus","좋아요");
		}
		
		if ("U".equals(request.getParameter("jCode")) ) {
			
			model.addAttribute("boardDetail", entity);
			
			return "board/boardUpdate";
		}else {
			entity.setBoard_views(entity.getBoard_views()+1);
			boardService.save(entity);
			model.addAttribute("boardDetail", entity);
			
			return "board/boardDetail";
		}
	}
	
	@GetMapping(value = "/boardInsert")
	public void getBoardInsert() {
		
	}
	
	@PostMapping(value = "/boardInsert")
	public String postBoardInsert(RedirectAttributes rttr, Board entity) {
		
		String uri="redirect:boardInsert";
		
		try {
			entity.setBoard_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setBoard_delyn("N");
			if(boardService.save(entity) != null) {
				uri = "redirect:";
			}else {
				rttr.addFlashAttribute("message","글 등록 실패");
			}
		} catch (Exception e) {
			rttr.addFlashAttribute("message","글 등록 실패");
			System.out.println("boardInsert Exception" + e.toString());			
		}
		
		return uri;
		
	}
	
	@PostMapping(value = "/{board_id}")
	public String postBoardUpdate(Board entity, Model model) {
		
		String uri="board/boardUpdate";
		
		try {
			entity.setBoard_moddate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			if(boardService.save(entity) != null) {
				model.addAttribute("boardDetail", entity);
				uri = "board/boardDetail";
			}else {
				model.addAttribute("message","글 수정 실패");
			}
		} catch (Exception e) {
			model.addAttribute("message","글 수정 실패");
			System.out.println("boardInsert Exception" + e.toString());			
		}
		
		return uri;
		
	}
	
	@PostMapping(value="/delete")
	@ResponseBody
	public String deleteBoard(@RequestBody int id) {
		
		System.out.println(id);
		
		try {
			
			Board entity = boardService.selectDetail(id);
			if(entity != null) {
				entity.setBoard_delyn("Y");
				entity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				boardService.save(entity);
				
				return "성공";
			}else {
				return "실패";
			}
		} catch (Exception e) {
			System.out.println("Board Delete Exception => "+e.toString());
			return "실패";
		}
		
		
	}

	
    // LIKES
	
	@PostMapping("/like")
	@ResponseBody
    public Map<String, Object> toggleLike(@RequestBody Likes entity, Model model) {
		
		System.out.println("*******************"+entity);
		LikesId id = new LikesId(entity.getUseremail(),entity.getBoard_id());
		
		Board boardEntity = boardService.selectDetail(entity.getBoard_id());

		Map<String,Object> result = new HashMap<String,Object>();
		
		if(likesService.selectDetail(id) == null) {
			boardEntity.setBoard_likes(boardEntity.getBoard_likes()+1);
			likesService.save(entity);
			
			result.put("likeStatus", true);
			
		}else {
			boardEntity.setBoard_likes(boardEntity.getBoard_likes()-1);
			likesService.delete(id);
			
			result.put("likeState", false);
		}

		boardService.save(boardEntity);
		
		result.put("board_likes",boardEntity.getBoard_likes());
		
		return result;
	
    }

}