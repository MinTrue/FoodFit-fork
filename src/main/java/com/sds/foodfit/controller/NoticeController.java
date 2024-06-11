package com.sds.foodfit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sds.foodfit.common.Pager;
import com.sds.foodfit.domain.Notice;
import com.sds.foodfit.exception.NoticeException;
import com.sds.foodfit.model.notice.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class NoticeController {

	@Autowired
    private NoticeService noticeService;

    @GetMapping("/notice")
    public String getList(Model model, @RequestParam(value="currentPage", defaultValue="1") int currentPage) {
        // 총 레코드 수를 가져오기
        int totalRecord = noticeService.getTotalCount();

        Pager pager = new Pager();
        pager.init(totalRecord, currentPage);

        // 레코드를 가져오기 위한 매개변수 맵
        Map<String, Integer> map = new HashMap<>();
        map.put("startIndex", pager.getStartIndex());
        map.put("rowCount", pager.getPageSize());
        // 레코드 가져오기
        List<Notice> noticeList = noticeService.selectAll(map); //selectAll은 db용어여서 다른걸로

        // 모델에 데이터 추가
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pager", pager);

        return "notice/list";
    }
    
    
    //검색기능 요청 처리
    @GetMapping("/notice/search")
    public String searchNotices(@RequestParam("query") String query, Model model,
                                @RequestParam(value = "currentPage", defaultValue = "1") int currentPage) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", query);

        int totalRecord = noticeService.getTotalCountByQuery(query); //쿼리말고 키워드나 이런용어로.
        Pager pager = new Pager();
        pager.initSearch(totalRecord, currentPage);

        paramMap.put("startIndex", pager.getStartIndex());
        paramMap.put("rowCount", pager.getPageSize());

        List<Notice> noticeList = noticeService.searchNoticesByTitle(paramMap);

        model.addAttribute("noticeList", noticeList);
        model.addAttribute("pager", pager);
        model.addAttribute("query", query);

        return "notice/searchList";
    }
    
    //게시판 글쓰기폼 요청처리
	@GetMapping("/notice/writeform")
	public String getRegistForm(Model model) {
		model.addAttribute("notice", new Notice());
		return "notice/write";
	}
	
	// 게시판 글쓰기 요청 처리 
//	@PostMapping("/notice/regist")
//	public String regist(Notice notice, RedirectAttributes redirectAttributes) {
//	    try {
//	    	noticeService.insert(notice); 
//	        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 등록되었습니다.");
//	    } catch (Exception e) {
//	        redirectAttributes.addFlashAttribute("message", "게시글 등록 중 오류가 발생하였습니다.");
//	    }
//	    return "redirect:/notice";
//	}
	
	// 상세보기 요청 처리
	@GetMapping("/notice/detail")
	public String getDetail(Model model, @RequestParam(value="noticeIdx", defaultValue="0") int noticeIdx) {
	    Notice notice = noticeService.select(noticeIdx);
	    
	    model.addAttribute("notice", notice);
	    return "notice/content";
	}
	
	@GetMapping("/notice/editform")
	public String getEditForm(Model model, @RequestParam(value="noticeIdx", defaultValue="0") int noticeIdx) {
		Notice notice = noticeService.select(noticeIdx);
		model.addAttribute("notice", notice);
		return "notice/editForm";
	}
	
	// 게시글 수정 요청 처리
	@PostMapping("/notice/edit")
	public String edit(Notice notice) {
		noticeService.update(notice);
		return "redirect:/notice/detail?noticeIdx="+notice.getNoticeIdx();
	}
	
	// 게시글 삭제 요청 처리
	@PostMapping("/notice/del")
	public String del(Notice notice, RedirectAttributes redirectAttributes) {
	    try {
	        noticeService.delete(notice);
	        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("message", "게시글 삭제 중 오류가 발생하였습니다.");
	    }
	    return "redirect:/notice";
	}
	
	@ExceptionHandler(NoticeException.class)
    public String handle(NoticeException e, Model model) {
        model.addAttribute("e", e);
        return "error/result";
    }
}