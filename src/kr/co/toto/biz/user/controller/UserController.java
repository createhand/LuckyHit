/**
 * 토토게임경기 내용을 입력한다
 */
package kr.co.toto.biz.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.biz.user.service.UserService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;

/**
 * @author seochan
 *
 */
@Controller
public class UserController extends AbstractController {
	
    /**
     * 로그인
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    public String userLogin(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }
    
    /**
     * 로그인 처리
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userLoginProc", method = RequestMethod.POST)
    public String userLoginProc(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	UserService userService = (UserService) BeanFinder.getBean(UserService.class);
    	
    	boolean isLogin = true;
    	String errMsg = null;
    	TAData map = new TAData(params);
    	TAData userInfo = userService.selectUserInfo(map.getString("userId").toLowerCase());
    	
    	if(userInfo == null) {
    		errMsg = "아이디가 존재하지 않습니다.";
    		isLogin = false;
    	} else if(!StringUtils.equals(userInfo.getString("USER_PWD"), map.getString("userPwd"))) {
    		errMsg = "비밀번호가 일치하지 않습니다.";
    		isLogin = false;
    	}
    	
    	model.addAttribute("isLogin", isLogin);
    	model.addAttribute("userInfo", userInfo);
    	model.addAttribute("errMsg", errMsg);
        return getViewName(request);
    }

    /**
     * 가입
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userRegist", method = RequestMethod.GET)
    public String userRegist(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }
    
    /**
     * 가입처리
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/userRegistProc", method = RequestMethod.POST)
    public String userRegistProc(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	UserService userService = (UserService) BeanFinder.getBean(UserService.class);
    	
    	String errMsg = null;
    	TAData map = new TAData(params);
    	
    	TAData userInfo = userService.selectUserInfo(map.getString("userId").toLowerCase());
    	
    	if(userInfo != null) {
    		
    		errMsg = "이미 가입된 아이디입니다.";
    		
    	} else {
    		
	    	int result = userService.insertUser(map);
	    	
	    	if(result == 0) {
	    		errMsg = "시스템 오류로 등록되지 않았습니다.";
	    	} else {
	    		//등록된 사용자 정보 조회
	    		model.addAttribute("userInfo", userService.selectUserInfo(map.getString("userId")));
	    	}
    	}
    	model.addAttribute("errMsg", errMsg);
        return getViewName(request);
    }
    
    /**
     * 마이 페이지
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/myPage", method = RequestMethod.GET)
    public String myPage(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }
}
