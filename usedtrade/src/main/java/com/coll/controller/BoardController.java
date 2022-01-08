package com.coll.controller;



import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.coll.model.Board;
import com.coll.repository.BoardRepository;
import com.coll.repository.ReplyRepository;
import com.coll.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class BoardController {
	
	@Autowired
	private BoardService boardService;
	private final BoardRepository boardRepository;
	

	
	// 컨트롤로에서 세션을 어떻게 찾는지?
	// @AuthenticationPrincipal PrincipalDetail principal
	@GetMapping({"", "/"})
	public String index(Model model,
			@PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC)
	Pageable pageable,
	@RequestParam(required = false, defaultValue = "") String field,
	@RequestParam(required = false, defaultValue = "") String word){
		
		
		Page<Board> blist =boardRepository.findAll(pageable);
		
		if(field.equals("content")) {
			blist=boardRepository.findByContentContaining(word, pageable);
			
		}else if(field.equals("title")) {
			blist=boardRepository.findByTitleContaining(word, pageable);
		}
		

		
		int pageNumber=blist.getPageable().getPageNumber();
		//현재페이지 
		int totalPages=blist.getTotalPages(); //총 페이지 수 . 검색에따라 10개면 10개.. 
		int pageBlock = 5;
		//블럭의 수 1, 2, 3, 4, 5 
		int startBlockPage = ((pageNumber)/pageBlock)*pageBlock+1; 
		//현재 페이지가 7이라면 1*5+1=6 
		int endBlockPage = startBlockPage+pageBlock-1; 
		//6+5-1=10. 6,7,8,9,10해서 10. 
		endBlockPage= totalPages<endBlockPage? totalPages:endBlockPage;

		
		model.addAttribute("startBlockPage", startBlockPage);
		model.addAttribute("endBlockPage", endBlockPage);

		model.addAttribute("boards", boardService.글목록(pageable));
		return "index"; // viewResolver 작동!!
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.글상세보기(id));
		return "board/updateForm";
	}
	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
