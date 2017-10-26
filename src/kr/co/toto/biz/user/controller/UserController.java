/**
 * 토토게임경기 내용을 입력한다
 */
package kr.co.toto.biz.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.biz.record.controller.RecordInputController;
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.biz.user.service.UserService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.DateUtil;
import kr.co.toto.util.ParamMap;
import kr.co.toto.util.crypto.EncryptSHA1;
import signgate.core.provider.md.MD5;

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
    	String userPwd = EncryptSHA1.getEncryptSHA1(map.getString("userPwd"));
    	
    	if(userInfo == null) {
    		errMsg = "아이디가 존재하지 않습니다.";
    		isLogin = false;
    	} else if(!StringUtils.equals(userInfo.getString("USER_PWD"), userPwd)) {
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
    	map.set("userPwd", EncryptSHA1.getEncryptSHA1(map.getString("userPwd")));
    	
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
    	
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	TAData map = new TAData(params);
    	
    	//내 픽만 조회
    	String userId = (String)request.getSession().getAttribute("userId");
    	map.put("userId", userId);
    	
    	String gmCd = map.getString("gmCd");
    	String gmPostNo = map.getString("gmPostNo");
    	
    	List<String> errMsg = new ArrayList<String>();    	
    	List<TAData> selectedGame = new ArrayList<TAData>();
    	List<TAData> gameList = new ArrayList<TAData>();
    	TAData selectGameInfo = new TAData();
    	
        try {
        	
        	TAData pickParams = new TAData();
        	pickParams.set("userId", userId);
        	gameList = gameService.selectPickGameList(pickParams);
        	
        	//페이지 최초 진입시 gmCd, gmPostNo 없는 경우
        	if(gmCd.equals("")) {
        		selectGameInfo = gameList.get(0);
        		gmCd = selectGameInfo.get("gmCd").toString();
	        	map.put("gmCd", gmCd);
        	}
        	
        	selectedGame = recordService.selectHitResult(map);
        	
        	if(StringUtils.isBlank(gmPostNo)) {
        		gmPostNo = String.valueOf((Integer)selectedGame.get(0).get("gmPostNo"));
        	}
        	
        	
        	if(StringUtils.isBlank((String)selectGameInfo.get("gmCd"))) {
	        	for(TAData pickInfo : gameList) {
	        		if(StringUtils.equals(gmPostNo, pickInfo.getString("gmPostNo")) &&
	        				StringUtils.equals(gmCd, pickInfo.getString("gmCd"))) {
	        			selectGameInfo = pickInfo;
	        			break;
	        		}
	        	}
        	}
        	
        	//종료된 경기 체크
        	int endCnt = 0;
        	
        	//종료경기 수집처리(현시각 기준으로 종료된 경기 카운팅 후 수집)
        	for(TAData matchInfo : selectedGame) {
        		//경기 종료여부 체크
        		if(StringUtils.equals(DomainConst.NO, matchInfo.get("matchEnd").toString())) {
        			
        			String mcDate = matchInfo.get("matchDate").toString();
        			String mcTime = matchInfo.get("matchTime").toString();
        			
        			if(StringUtils.isBlank(mcDate) || StringUtils.isBlank(mcTime)) {
        				continue;
        			}
        			
        			String nowTime = DateUtil.getCurrentDateTime().substring(0, 12);
        			int aftTime = DateUtil.getDayDiff(mcDate+mcTime, nowTime, "yyyyMMddHHmm");
        			
        			//경기시작 후 120분이상 경과된 경기
        			if(aftTime > 120) {
        				endCnt++;
        			}
        		}
        	}
        	
        	if(endCnt > 0) {
	        	//종료된 경기 수집일자 범위
	        	String endMatchStDt = selectGameInfo.get("gmEndDate").toString();
	        	
	        	//경기결과 수집
	        	params.put("league", "all");
	        	params.put("stDt", endMatchStDt);
	        	params.put("enDt", DateUtil.getCurrentDate());
	        	RecordInputController rc = new RecordInputController();
	        	rc.record002(request, response, model, params);
	        	
	        	//종료결과 수집 후 재조회
	        	selectedGame = recordService.selectHitResult(map);
        	}
        	
        } catch(Exception e) {
        	System.out.println(e);
        	errMsg.add(e.toString());
        }
    	
    	model.addAttribute("selectGameInfo", selectGameInfo);
        model.addAttribute("selectedGame", selectedGame);
        model.addAttribute("gmPostNo", gmPostNo);
    	model.addAttribute("gameList", gameList);
    	model.addAttribute("gmCd", gmCd);
        return getViewName(request);
    }
    
    
    /**
     * 댓글등록 처리
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/replyRegistProc", method = RequestMethod.POST)
    public String replyRegistProc(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	UserService userService = (UserService) BeanFinder.getBean(UserService.class);
    	TAData map = new TAData(params);
    	//내 픽만 조회
    	String userId = (String)request.getSession().getAttribute("userId");
    	if(StringUtils.isNotBlank(userId)) {
    		map.set("userId", userId);
    	}
    	
    	String errMsg = null;
    	int result = 0;
    	try {
    			result = userService.insertReply(map);
    	} catch(Exception e) {
    		errMsg = e.getMessage();
    	}
    	
    	model.addAttribute("result", result);
    	model.addAttribute("params", map);
    	model.addAttribute("errMsg", errMsg);
        return getViewName(request);
    }
}
