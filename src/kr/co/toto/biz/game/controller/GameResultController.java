/**
 * 토토게임경기 내용을 입력한다
 */
package kr.co.toto.biz.game.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
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
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.DateUtil;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GameResultController extends AbstractController {
	
    /**
     * 공개픽 목록
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameResult", method = RequestMethod.GET)
    public String gameResult(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	ParamMap map = new ParamMap(params);
    	
    	String gmCd = map.getString("gmCd");
    	String gmPostNo = map.getString("gmPostNo");
    	int pageNo = map.getString("pageNo") == null ? 0 : map.getInt("pageNo");
    	
    	int totalCount = 0;
    	List<String> errMsg = new ArrayList<String>();    	
    	List<TAData> pickList = new ArrayList<TAData>();
    	TAData pagingInfo = new TAData();
    	
        try {
        	
        	//공개픽만 조회
        	map.put("pubYn", "0");
        	
        	//전체 목록 수  
        	totalCount = gameService.selectPickGameListCount(map);
        	
        	pagingInfo.set("pageNo", pageNo);
        	pagingInfo.set("countPerPage", DomainConst.countPerPage);
        	
        	int totalPage = (int)Math.ceil((double)totalCount/10);
        	pagingInfo.set("totalCount", totalCount);
        	pagingInfo.set("totalPageCount", totalPage);
        	
        	//페이징처리
        	map.put("paging", "Y");
        	map.put("pageIndex", (pageNo -1) * DomainConst.countPerPage);
        	map.put("countPerPage", DomainConst.countPerPage);
        	
        	//페이징 목록 수
        	pickList = gameService.selectPickGameList(map);
        	
        } catch(Exception e) {
        	System.out.println(e);
        	errMsg.add(e.toString());
        }
    	
        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("totalCount", totalCount);
    	model.addAttribute("pickList", pickList);
        return getViewName(request);
    }
    
    
    /**
     * 게임정보수집
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gameResultDetail", method = RequestMethod.GET)
    public String gameResultDetail(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	ParamMap map = new ParamMap(params);
    	
    	String gmCd = map.getString("gmCd");
    	String gmPostNo = map.getString("gmPostNo");
    	int pageNo = map.getString("pageNo") == null ? 0 : map.getInt("pageNo");
    	
    	int totalCount = 0;
    	List<String> errMsg = new ArrayList<String>();    	
    	List<HashMap> selectedGame = new ArrayList<HashMap>();
    	List<HashMap> pickList = new ArrayList<HashMap>();
    	HashMap selectGameInfo = new HashMap();
    	TAData pagingInfo = new TAData();
    	
        try {
        	
        	//공개픽만 조회
        	map.put("pubYn", "0");
        	
        	//전체 목록 수  
        	totalCount = gameService.selectPickGameListCount(map);
        	
        	pagingInfo.set("pageNo", pageNo);
        	pagingInfo.set("countPerPage", DomainConst.countPerPage);
        	
        	int totalPage = (int)Math.ceil((double)totalCount/10);
        	pagingInfo.set("totalCount", totalCount);
        	pagingInfo.set("totalPageCount", totalPage);
        	
        	//페이징처리
        	map.put("paging", "Y");
        	map.put("pageIndex", (pageNo -1) * DomainConst.countPerPage);
        	map.put("countPerPage", DomainConst.countPerPage);
        	
        	//페이징 목록 수
//        	pickList = gameService.selectPickGameList(map);
        	
        	if(gmCd.equals("")) {
	        	HashMap latest = gameService.selectLatestPick(map);
	        	gmCd = latest.get("gmCd").toString();
	        	map.put("gmCd", gmCd);
        	}
        	
        	selectedGame = recordService.selectHitResult(map);
        	
        	if(StringUtils.isBlank(gmPostNo)) {
        		gmPostNo = String.valueOf((Integer)selectedGame.get(0).get("gmPostNo"));
        	}
        	
        	for(HashMap pickInfo : pickList) {
        		if(StringUtils.equals(gmPostNo, String.valueOf((Integer)pickInfo.get("gmPostNo"))) &&
        				StringUtils.equals(gmCd, String.valueOf((String)pickInfo.get("gmCd")))) {
        			selectGameInfo = pickInfo;
        			break;
        		}
        	}
        	
        	//종료된 경기 체크
        	int endCnt = 0;
        	
        	//종료경기 수집처리(현시각 기준으로 종료된 경기 카운팅 후 수집)
        	for(HashMap matchInfo : selectedGame) {
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
        model.addAttribute("pagingInfo", pagingInfo);
        model.addAttribute("totalCount", totalCount);
    	model.addAttribute("pickList", pickList);
    	model.addAttribute("gmCd", gmCd);
        return getViewName(request);
    }

}
