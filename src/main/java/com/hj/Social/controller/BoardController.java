package com.hj.Social.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hj.Social.domain.PageRequestDTO;
import com.hj.Social.domain.PageResultDTO;
import com.hj.Social.entity.Board;
import com.hj.Social.service.BoardService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RequestMapping(value = "/board")
@Controller
public class BoardController {

	private BoardService boardService;
	

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
	public String getBoardDetail(HttpServletRequest request, Model model, @PathVariable("board_id") int id) {
		model.addAttribute("boardDetail", boardService.selectDetail(id));
		
		if ( "U".equals(request.getParameter("jCode")) ) {
			return "board/boardUpdate";
		}else {
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
	
	@GetMapping(value="/delete")
	public String deleteBoard(@RequestParam("board_id") int board_id) {
		
		System.out.println("board_id ************" + board_id);
		
		try {
			if(board_id > 0) {
				Board entity = boardService.selectDetail(board_id);
				entity.setBoard_delyn("Y");
				entity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				boardService.save(entity);
				
				return "redirect:/board/";
			}else {
				return "board/boardDetail";
			}
		} catch (Exception e) {
			System.out.println("Board Delete Exception => "+e.toString());
			return "redirect:/board/boardDetail";
		}
		
		
	}

	

	

}