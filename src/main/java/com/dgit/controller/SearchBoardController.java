package com.dgit.controller;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgit.domain.BoardVO;
import com.dgit.domain.PageMaker;
import com.dgit.domain.SearchCriteria;
import com.dgit.service.BoardService;
import com.dgit.util.MediaUtils;
import com.dgit.util.UploadFileUtils;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	private static final Logger logger = LoggerFactory.getLogger(SearchBoardController.class);
	
	@Autowired
	private BoardService service;
	
	@Resource(name="uploadPath")
	private String uploadPath;
	
	
		//board/register
		@RequestMapping(value="/register", method= RequestMethod.GET)
		public void registerGet(){
			logger.info("sboard register Get........");
			
			//return "/board/register"; 생략가능(return) 하고 string -> void 로 변경
		}
		
		@RequestMapping(value="/register", method= RequestMethod.POST)
		public String registerPost(BoardVO vo, List<MultipartFile> imageFiles) throws Exception{ //title, content
			logger.info("sboard register Post ......");
			logger.info(vo.toString());
			
			
			if(imageFiles.get(0).isEmpty()){
				logger.info("=========================== 업로드 없음 =========================== " );
				
				service.regist(vo);				
				
			}else{							
				
				logger.info("=========================== 업로드 있음 =========================== " );
				
				ArrayList<String> list = new ArrayList<>();
			
			
				for(MultipartFile file : imageFiles){
					logger.info("filename : " + file.getOriginalFilename());
				
					String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		
					list.add(thumb);
				}
			
				vo.setFiles(list.toArray(new String[list.size()]));			
				service.regist(vo);
			}
			
			
			return "redirect:/sboard/listPage";
		}
	
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
	public String remove(Model model, int bno, String[] fileName,SearchCriteria cri, BoardVO vo) throws Exception{
		logger.info("sboard removePage.........");
		
		if(fileName != null ){
			logger.info("===========================삭제 게시물에 사진이 있음 =========================== " );
			for(String file : fileName){
				logger.info(file+"======================================");
				UploadFileUtils.deleteFile(uploadPath, file);
				service.deleteAttach(vo.getBno(), file);				
			}
			service.remove(bno);
			
		}else{
			logger.info("===========================삭제 게시물에 사진이 없음 =========================== " );
			
			service.remove(bno);
		}
		
		
		
		
		
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
	public String modifyPagepost(Model model, @ModelAttribute("cri")SearchCriteria cri, BoardVO vo, String[] fileName, List<MultipartFile> imageFiles) throws Exception{
		logger.info("sboard modifyPage post.........");
		logger.info(vo.toString());
		logger.info(cri.toString());
		
		
		
		if(imageFiles != null && fileName == null ){
			logger.info("=========================== 삭제 없고 업로드 있고 =========================== " );
			
			ArrayList<String> list = new ArrayList<>();			
			
			for(MultipartFile file : imageFiles){
				logger.info("filename : " + file.getOriginalFilename());
				
				String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		
				list.add(thumb);
			}
			
			vo.setFiles(list.toArray(new String[list.size()]));					
			service.modify(vo);
			
		}else if(imageFiles.get(0).isEmpty() && fileName != null){	
			logger.info("=========================== 삭제 있고 업로드 없고 =========================== " );
			
			for(String file : fileName){
				logger.info(file+"======================================");
				UploadFileUtils.deleteFile(uploadPath, file);
				service.deleteAttach(vo.getBno(), file);		
			
				
			}
			service.modify(vo);
			
			
		}else{ 
			logger.info("=========================== 삭제 있고 업로드 있고 =========================== " );
			ArrayList<String> list = new ArrayList<>();
			
			
			for(MultipartFile file : imageFiles){
				logger.info("filename : " + file.getOriginalFilename());
				
				String thumb = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
		
				list.add(thumb);
				service.modify(vo);
			}
			
			for(String file : fileName){
				logger.info(file+"======================================");
				UploadFileUtils.deleteFile(uploadPath, file);
				service.deleteAttach(vo.getBno(), file);				
			}
			vo.setFiles(list.toArray(new String[list.size()]));
			service.modify(vo);
		}
		
		
		
		model.addAttribute("bno", vo.getBno());//?bno=2 
		model.addAttribute("page", cri.getPage());	
		model.addAttribute("mod", "mod");
		/*model.addAttribute("BoardVO", "BoardVO");*/
		
		/*return "redirect:/sboard/modifyPage?bno="+vo.getBno();*/
		return "redirect:/sboard/readPage";
	}
	
	
	//@ResponseBody 데이터만 가는경우
		@ResponseBody
		@RequestMapping("/displayFile")
		public ResponseEntity<byte[]> displyFile(String filename) throws Exception{
			ResponseEntity<byte[]> entity = null;
			
			InputStream in = null;
			
			logger.info("[displayFile] filename : " + filename);
			
			try{
				
				String format = filename.substring(filename.lastIndexOf(".") +1);  //확장자만
				MediaType mType = MediaUtils.getMediaType(format);
				HttpHeaders headers = new  HttpHeaders();
				headers.setContentType(mType);
				
				in = new FileInputStream(uploadPath + "/" + filename);
				// IOUtils 바이트 배열로 뽑아줌
				entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);			
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
			}finally {
				in.close();
			}
			
			return entity;
		}
}
