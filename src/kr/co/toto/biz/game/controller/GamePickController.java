/**
 * 토토게임경기 표시한다
 */
package kr.co.toto.biz.game.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.base.persistence.domain.GameMt;
import kr.co.toto.base.service.CommonService;
import kr.co.toto.biz.game.persistence.domain.GameDetailListDt;
import kr.co.toto.biz.game.service.GameService;
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.Paging;
import kr.co.toto.util.PagingList;
import kr.co.toto.util.ParamMap;

/**
 * @author seochan
 *
 */
@Controller
public class GamePickController extends AbstractController {
	
    /**
     * 게임픽 포스팅
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
    	
    	String gmCd = request.getParameter("gmCd");
    	String gmPostContent = request.getParameter("gmPostContent");
    	gmPostContent = gmPostContent.replace("\r\n","<br/>");
    	int gmPostNo = Integer.parseInt(gameService.selectMaxPostNo(new ParamMap(params)))+1;
    	
       	String expResult[] = request.getParameterValues("expResult");    	
    	String gmListNo[] = request.getParameterValues("gmListNo");
    	String mcCd[] = request.getParameterValues("mcCd");
    	    	
    	String hLtRecord[] = request.getParameterValues("hLtRecord");    	
    	String hLtAgRecord[] = request.getParameterValues("hLtAgRecord");
    	String aLtRecord[] = request.getParameterValues("aLtRecord");
    	String aLtAgRecord[] = request.getParameterValues("aLtAgRecord");
    	
    	String hLtGtPoint[] = request.getParameterValues("hLtGtPoint");
    	String hLtLsPoint[] = request.getParameterValues("hLtLsPoint");
    	String hLtGtAgPoint[] = request.getParameterValues("hLtGtAgPoint");
    	String hLtLsAgPoint[] = request.getParameterValues("hLtLsAgPoint");
    	
    	String aLtGtPoint[] = request.getParameterValues("aLtGtPoint");
    	String aLtLsPoint[] = request.getParameterValues("aLtLsPoint");
    	String aLtGtAgPoint[] = request.getParameterValues("aLtGtAgPoint");
    	String aLtLsAgPoint[] = request.getParameterValues("aLtLsAgPoint");    	
 
    	HashMap<String, String> gamePick = new HashMap<String, String>();
    	gamePick.put("gmCd", gmCd);
    	gamePick.put("gmPostNo", String.valueOf(gmPostNo));
    	gamePick.put("gmPostContent", gmPostContent);
    	
    	
    	List<HashMap<String, String>> gamePickList = new ArrayList<HashMap<String, String>>();
    	
    	for(int i=0;i<mcCd.length;i++) {
    		HashMap<String, String> tmp = new HashMap<String, String>();
    		tmp.put("hLtRecord", hLtRecord[i]);
    		tmp.put("hLtAgRecord", hLtAgRecord[i]);
    		tmp.put("aLtRecord", aLtRecord[i]);
    		tmp.put("aLtAgRecord", aLtAgRecord[i]);
    		tmp.put("hLtGtPoint", hLtGtPoint[i]);
    		tmp.put("hLtLsPoint", hLtLsPoint[i]);
    		tmp.put("hLtGtAgPoint", hLtGtAgPoint[i]);
    		tmp.put("hLtLsAgPoint", hLtLsAgPoint[i]);
    		tmp.put("aLtGtPoint", aLtGtPoint[i]);
    		tmp.put("aLtLsPoint", aLtLsPoint[i]);
    		tmp.put("aLtGtAgPoint", aLtGtAgPoint[i]);
    		tmp.put("aLtLsAgPoint", aLtLsAgPoint[i]);
    		
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
    	ParamMap formData = new ParamMap(params);
    	
    	HashMap<String, String> searchParam = service.selectLatestPick(formData);    	
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
    	ParamMap formData = new ParamMap(params);
    	String tmCdA = formData.getString("tmCdA");
    	int queryCnt = DomainConst.RECORD_LATEST_CNT;
    	formData.put("queryCnt", queryCnt);
    	
    	//상대전적
    	List<HashMap> againstList = service.selectLatestRecordList(formData);
    	
    	//홈팀 최근전적
    	formData.remove("tmCdA");
    	List<HashMap> homeList = service.selectLatestRecordList(formData);
    	
    	//어웨이팀 최근전적   	
    	formData.put("tmCd", tmCdA);
    	List<HashMap> awayList = service.selectLatestRecordList(formData);
    	
        
        model.addAttribute("agList", againstList);
        model.addAttribute("hList", homeList);
        model.addAttribute("aList", awayList);
        
        return getViewName(request);
    }
    
    
}