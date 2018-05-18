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
import com.dgit.domain.Criteria;
import com.dgit.domain.PageMaker;
import com.dgit.service.BoardService;

@RequestMapping("/board/*")
@Controller
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService service;
	
	
	//board/register
	@RequestMapping(value="/register", method= RequestMethod.GET)
	public void registerGet(){
		logger.info("board register Get........");
		
		//return "/board/register"; 생략가능(return) 하고 string -> void 로 변경
	}
	
	@RequestMapping(value="/register", method= RequestMethod.POST)
	public String registerPost(BoardVO vo) throws Exception{ //title, content
		logger.info("board register Post ......");
		logger.info(vo.toString());
		
		service.regist(vo);
		
		return "redirect:/board/listAll";
	}
	
	@RequestMapping(value="/listAll", method=RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("board lsitAll ....");
		
		List<BoardVO> list = service.listAll();
		
		model.addAttribute("list",list);
	}
	
	@RequestMapping(value="/read", method=RequestMethod.GET)
	public void read(Model model, int bno) throws Exception{
		logger.info("board read.........");
		logger.info("bno : " + bno);
		
		
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO", vo);
	}
	@RequestMapping(value="/remove", method=RequestMethod.GET)
	public String remove(Model model, int bno) throws Exception{
		logger.info("board remove.........");
		
		service.remove(bno);
		
		return "redirect:/board/listAll";
	}
	
	//modify
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyget(Model model, int bno) throws Exception{
		logger.info("board modify get.........");
		
		BoardVO vo = service.read(bno);		
		model.addAttribute("boardVO", vo);
		
	}
	//modify
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modifypost(Model model, BoardVO vo) throws Exception{
		logger.info("board modify post.........");
		logger.info(vo.toString());
		service.modify(vo);
		
	/*	model.addAttribute("bno", vo.getBno());//?bno=2 */
		return "redirect:/board/read?bno="+vo.getBno();
	}
	
	
	//board/listAll
		//board/modify
	
	
	
	
	/*  -----------------------------------------  */

	@RequestMapping(value="/listPage", method=RequestMethod.GET)
	public void listPage(Model model, Criteria cri) throws Exception{
		logger.info("board listAll ....");
		
		List<BoardVO> list = service.listCriteria(cri);
		/*board/listPage?page=2&perPageNum=20*/
		model.addAttribute("list",list);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.totalCount());
		model.addAttribute("pageMaker", pageMaker);
	}
	
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void readPage(Model model, int bno, @ModelAttribute("cri")Criteria cri) throws Exception{
		logger.info("board readPage.........");
		logger.info("bno : " + bno);
		logger.info(cri.toString());
		
		
		
		BoardVO vo = service.read(bno);
		model.addAttribute("boardVO", vo);
	}
	
	@RequestMapping(value="/removePage", method=RequestMethod.GET)
	public String remove(Model model, int bno, Criteria cri) throws Exception{
		logger.info("board removePage.........");
		
		service.remove(bno);
		
		return "redirect:/board/listPage?page="+cri.getPage();
	}
	
	
	
/*	
	@RequestMapping(value="/modifyPage", method=RequestMethod.GET)
	public void modifPageyget(Model model, int bno) throws Exception{
		logger.info("board modifyPage get.........");
		
		BoardVO vo = service.read(bno);		
		model.addAttribute("boardVO", vo);
		
	}
	@RequestMapping(value="/modifyPage", method=RequestMethod.POST)
	public String modifyPagepost(Model model, Criteria cri, BoardVO vo) throws Exception{
		logger.info("board modifyPage post.........");
		logger.info(vo.toString());
		service.modify(vo);
		
		model.addAttribute("bno", vo.getBno());//?bno=2 
		return "redirect:/board/readPage?bno="+vo.getBno();
	}
	
	*/
	
}














