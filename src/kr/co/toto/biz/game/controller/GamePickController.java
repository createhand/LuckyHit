/**
 * 토토게임경기 표시한다
 */
package kr.co.toto.biz.game.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GamePickController extends AbstractController {
	
    /**
     * 게임픽 등록
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gamePicking", method = RequestMethod.POST)
    public String post001(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	int mResult = 0, sResult = 0;
    	List<String> errMsg = new ArrayList<String>();
    	
    	GameService gameService = (GameService) BeanFinder.getBean(GameService.class);
    	
    	//게임CD
    	String gmCd = request.getParameter("gmCd");
    	//픽제목
    	String gmPostTitle = request.getParameter("gmPostTitle");
    	//픽내용
    	String gmPostContent = request.getParameter("gmPostContent");
    	gmPostContent = gmPostContent.replace("\r\n","<br/>");
    	//픽번호
    	int gmPostNo = Integer.parseInt(gameService.selectMaxPostNo(new TAData()))+1;
    	//공개여부
    	String pubYn = request.getParameter("pubYn");
    	if(StringUtils.isBlank(pubYn)) pubYn = "0";
    	//사용자ID
    	String userId = request.getParameter("userId");
    	
       	String expResult[] = request.getParameterValues("expResult");    	
    	String gmListNo[] = request.getParameterValues("gmListNo");
    	String mcCd[] = request.getParameterValues("mcCd");
    	    	
    	HashMap<String, String> gamePick = new HashMap<String, String>();
    	gamePick.put("gmCd", gmCd);
    	gamePick.put("gmPostNo", String.valueOf(gmPostNo));
    	gamePick.put("gmPostTitle", gmPostTitle);
    	gamePick.put("gmPostContent", gmPostContent);
    	gamePick.put("pubYn", pubYn);
    	if(StringUtils.isNotBlank(userId)) {
    		gamePick.put("userId", userId);
    	}
    	
    	List<HashMap<String, String>> gamePickList = new ArrayList<HashMap<String, String>>();
    	
    	for(int i=0;i<mcCd.length;i++) {
    		HashMap<String, String> tmp = new HashMap<String, String>();
    		tmp.put("gmCd", gmCd);
    		tmp.put("mcCd", mcCd[i]);
    		tmp.put("gmListNo", gmListNo[i]);
    		tmp.put("gmPostNo", String.valueOf(gmPostNo));
    		tmp.put("expResult", expResult[i]);
    		gamePickList.add(tmp);
    	}
    	
    	sResult = gameService.insertPick(gamePick);
    	mResult = gameService.insertPickList(gamePickList);
    	
    	
    	model.addAttribute("errMsg", errMsg);
    	model.addAttribute("mResult", mResult);
    	model.addAttribute("sResult", sResult);
        return getViewName(request);
    }
    
    
    /**
     * 게임픽목록 화면(이용자)
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gamePickList", method = RequestMethod.GET)
    public String game001(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	GameService service = (GameService) BeanFinder.getBean(GameService.class);
    	TAData formData = new TAData(params);
    	
    	TAData searchParam = service.selectLatestPick(formData);    	
    	formData.put("gmCd", searchParam.get("gmCd"));
    	formData.put("gmPostNo", service.selectMaxPostNo(formData));
    	List<HashMap> pickList = service.selectGamePickList(formData);
    	HashMap pick = service.selectGamePick(formData);
        
        model.addAttribute("list", pickList);
        model.addAttribute("pick", pick);
        model.addAttribute("searchParam", searchParam);
    	
        return getViewName(request);
    }
    
    /**
     * 게임픽 상세화면(상대전적, 최근전적)
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/gamePickDetail", method = RequestMethod.POST)
    public String game002(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	RecordService service = (RecordService) BeanFinder.getBean(RecordService.class);
    	TAData formData = new TAData(params);
    	String tmCdA = formData.getString("tmCdA");
    	int queryCnt = DomainConst.RECORD_LATEST_CNT;
    	formData.put("queryCnt", queryCnt);
    	
    	//상대전적
    	List<TAData> againstList = service.selectLatestRecordList(formData);
    	
    	//홈팀 최근전적
    	formData.remove("tmCdA");
    	List<TAData> homeList = service.selectLatestRecordList(formData);
    	
    	//어웨이팀 최근전적   	
    	formData.put("tmCd", tmCdA);
    	List<TAData> awayList = service.selectLatestRecordList(formData);
    	
        
        model.addAttribute("agList", againstList);
        model.addAttribute("hList", homeList);
        model.addAttribute("aList", awayList);
        
        return getViewName(request);
    }
    
    
}