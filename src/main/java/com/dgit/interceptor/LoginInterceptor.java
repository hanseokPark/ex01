package com.dgit.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("pre Handle......................");
		
	
		
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("post Handle.....................");
		
		
		Object loginDTO = modelAndView.getModel().get("loginDTO");
		/*LoginDTO loginDTO = (LoginDTO)modelAndView.getModel().get("loginDTO");*/
		
		if(loginDTO != null){
			logger.info("new Login Successs");
			
			HttpSession session = request.getSession();
			session.setAttribute("login", loginDTO);
			
			Object dest = session.getAttribute("dest");
			String path = "";
			if(dest != null){
				path = (String)dest;
			}else{
				path = request.getContextPath();
			}
			
			response.sendRedirect(path);  //home화면으로 이동
		}
		
		
	}
	

}
































