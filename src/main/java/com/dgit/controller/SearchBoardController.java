package com.dgit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dgit.domain.BoardVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	
	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model, @ModelAttribute("cri")SearchCriteria cri) throws Exception{
		logger.info("sboard listAll ....");
		logger.info(cri.toString());
		
		List<BoardVO> list = service.listSearch(cri);
		/*board/listPage?page=2&perPageNum=20*/
		model.addAttribute("list",list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalSearchCount(cri));
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void readPageGET(Model model, int bno, @ModelAttribute("cri")SearchCriteria cri, String mod) throws Exception{
		logger.info("sboard readPage.........");
		logger.info("bno : " + bno);
		logger.info(cri.toString());
	
		if(mod == null || mod.equals("mod") == false){
			service.boardviewcnt(bno);
		}
		
		
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO", vo);
	}
	
	/*@RequestMapping(value="/readPage", method=RequestMethod.POST)
	public void readPagePOST(Model model, int bno, @ModelAttribute("cri")SearchCriteria cri) throws Exception{
		logger.info("sboard readPage.........");
		logger.info("bno : " + bno);
		logger.info(cri.toString());
		
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO", vo);
	}*/
	
	@RequestMapping(value="/removePage", method=RequestMethod.GET)
	public String remove(Model model, int bno, SearchCriteria cri) throws Exception{
		logger.info("sboard removePage.........");
		
		service.remove(bno);
		model.addAttribute("page", cri.getPage());
		model.addAttribute("perPageNum", cri.getPerPageNum());
		model.addAttribute("keyword", cri.getKeyword());
		model.addAttribute("searchType", cri.getSearchType());
		
		return "redirect:/sboard/listPage?page="+cri.getPage();
	}
	
	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modifPageyget(Model model, @ModelAttribute("cri") SearchCriteria cri, int bno) throws Exception{
		logger.info("sboard modifyPage get.........");
		logger.info(cri.toString()+"ddddfafd");
		
		BoardVO vo = service.read(bno);		
		model.addAttribute("boardVO", vo);
		
	}
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPagepost(Model model, @ModelAttribute("cri")SearchCriteria cri, BoardVO vo) throws Exception{
		logger.info("sboard modifyPage post.........");
		logger.info(vo.toString());
		logger.info(cri.toString());
		
		service.modify(vo);
			
		
		model.addAttribute("bno", vo.getBno());//?bno=2 
		model.addAttribute("page", cri.getPage());	
		model.addAttribute("mod", "mod");
		
		/*return "redirect:/sboard/modifyPage?bno="+vo.getBno();*/
		return "redirect:/sboard/readPage";
	}
}
