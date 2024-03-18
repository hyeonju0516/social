package com.hj.Social.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.hj.Social.entity.Comments;
import com.hj.Social.entity.Likes;
import com.hj.Social.entity.User;
import com.hj.Social.service.BoardService;
import com.hj.Social.service.CommentsService;
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
	private CommentsService commService;
	
	
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
	public String getBoardDetail(HttpServletRequest request, Model model, @PathVariable("board_id") int id,
			HttpSession session) {
		
		Board entity = boardService.selectDetail(id);
		
		System.out.println("***************BoardEntity" + entity);
		
		User loginUser = (User) session.getAttribute("loginUser");
		LikesId likeId = new LikesId(loginUser.getUseremail(),id);
		
		PageResultDTO<Comments> resultDTO = getCommentList(id,1);
		
		model.addAttribute("commentList", resultDTO.getEntityList());
		model.addAttribute("resultDTO", resultDTO);
		
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
	
	@ResponseBody
	@GetMapping(value ="/commentList")
	public PageResultDTO<Comments> getCommentList(@RequestParam("board_id") int id, 
									@RequestParam(value = "page", defaultValue = "1") int page) {
		
		PageRequestDTO requestDTO = PageRequestDTO.builder().page(page).size(10).build();
		PageResultDTO<Comments> resultDTO = commService.selectList(requestDTO,id);
		
		return resultDTO;

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
				uri = "redirect:/board/";
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
				uri = "redirect:/board/"+entity.getBoard_id();
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
		
		try {
			
			Board entity = boardService.selectDetail(id);
			if(entity != null) {
				entity.setBoard_delyn("Y");
				entity.setBoard_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
				boardService.save(entity);
				
				commService.boardDelete(id);
				
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
	
	
	// 댓글 기능
	@PostMapping(value = "/commentInsert")
	public String postCommentInsert(RedirectAttributes rttr, Comments entity) {
		
		try {
			entity.setComment_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setComment_delyn("N");
			entity = commService.save(entity);
			
			entity.setComment_root(entity.getComment_id());
			commService.save(entity);
		} catch (Exception e) {
			System.out.println("comment Insert Exception" + e.toString());			
		}
		
		return "redirect:/board/"+entity.getBoard_id();
	}
	
	@PostMapping(value = "/ReplyInsert")
	public String postReplyInsert(RedirectAttributes rttr, Comments entity) {
		
		try {
			entity.setComment_regdate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setComment_delyn("N");
			
			Comments parents = commService.selectDetail(entity.getComment_root());
			entity.setComment_root(parents.getComment_root());
			entity.setComment_steps(entity.getComment_steps()+1);
			entity.setComment_indent(entity.getComment_indent()+1);
			commService.save(entity);
			System.out.println("****************"+entity);
			commService.stepUpdate(entity);
			
		} catch (Exception e) {
			System.out.println("comment Insert Exception" + e.toString());			
		}
		
		return "redirect:/board/"+entity.getBoard_id();
	}
	
	
	@DeleteMapping("/deleteComment")
	public ResponseEntity<?> deleteComment(@RequestParam("comment_id")int comment_id ) {
		
		Comments entity = commService.selectDetail(comment_id);
		try {
			
			entity.setComment_deldate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			entity.setComment_delyn("Y");
			commService.save(entity);
			
			return ResponseEntity.ok().build();
			
		} catch (Exception e) {
			System.out.println("comment delete Exception" + e.toString());			
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
		}
		
		
	}
	
	
	@PostMapping(value = "/updateComments")
	@ResponseBody
    public String updateComments(@RequestParam("comment_id") int comment_id, @RequestParam("comment_content") String comment_content, HttpSession session, Model model) {
        try {

            Comments comment = commService.selectDetail(comment_id);
            if (comment != null) {
            	 comment.setComment_content(comment_content);
                 comment.setComment_moddate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                 commService.save(comment); 
                return "Success";
            } else {
                return "Fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("댓글 업데이트 중에 오류 발생: " + e.getMessage());
            model.addAttribute("error", "댓글 업데이트에 실패했습니다.");
            return "Error";
        }
    }
	    
}