/**
 * 
 */
package kr.co.toto.biz.record.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.toto.base.controller.AbstractController;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.biz.record.service.RecordService;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.ParamMap;
import net.sf.json.JSONObject;


/**
 * @author seochan
 *
 */
@Controller
public class RecordInputController extends AbstractController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
    /**
     * 경기일정 및 결과 입력화면
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/recordInput", method = RequestMethod.GET)
    public String record001(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
        return getViewName(request);
    }
    
    /**
     * 경기일정 및 결과 수집처리
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/recordCollecting", method = RequestMethod.POST)
    public String record002(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	
    	String pageSize = DomainConst.DAUM_API_PAGESIZE;
    	
    	ParamMap map = new ParamMap(params);
    	map.put("pageSize", pageSize);
    	map.put("page", "1");
    	
    	//리그선택
    	String pLeague = map.getString("league");
    	
    	HashMap<String, Object> result = new HashMap<String, Object>();
    	List<String> errMsg = new ArrayList<String>();
    	int iResult = 0, uResult = 0;
    	
    	if(StringUtils.equals(pLeague, "all")) {
    		
    		//K리그 클래식
    		map.put("league", "kl");
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    		//K리그 챌린지
    		map.put("league", "kl2");
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    		//EPL
    		map.put("league", "epl");
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    		//primera
    		map.put("league", "primera");
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    		//bundesliga
    		map.put("league", "bundesliga");
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    		//seriea
    		map.put("league", "seriea");
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    		//ligue1
//    		map.put("league", "ligue1");
//    		result = recordService.getMatchRecordSum(map);
//    		errMsg.addAll((List<String>)result.get("errMsg"));
//    		iResult += (Integer)result.get("iResult");
//    		uResult += (Integer)result.get("uResult");
    		
    	} else {
    		
    		result = recordService.getMatchRecordSum(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		
    	}
    	
    	
    	HashSet<String> listSet = new HashSet<String>(errMsg);
    	List<String> errMsgList = new ArrayList<String>(listSet);
    	
        model.addAttribute("errMsg", errMsgList);
    	model.addAttribute("iResult", iResult);
    	model.addAttribute("uResult", uResult);
        return getViewName(request);
    }    
    
    /**
     * 틈 등록처리
     * @param request
     * @param response
     * @param model
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/teamCollecting", method = RequestMethod.POST)
    public String record004(HttpServletRequest request, HttpServletResponse response,
            Model model, @RequestParam Map<String, Object> params) throws Exception {
    	
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	
    	String pageSize = DomainConst.DAUM_API_PAGESIZE;
    	
    	ParamMap map = new ParamMap(params);
    	map.put("pageSize", pageSize);
    	map.put("page", "1");
    	
    	//리그선택
    	String pLeague = map.getString("league");
    	
    	HashMap<String, Object> result = new HashMap<String, Object>();
    	List<String> errMsg = new ArrayList<String>();
    	int iResult = 0, uResult = 0, sResult = 0;
    	
    	if(StringUtils.equals(pLeague, "all")) {
    		
    		//K리그 클래식
    		map.put("league", "kl");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//K리그 챌린지
    		map.put("league", "kl2");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//EPL
    		map.put("league", "epl");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//primera
    		map.put("league", "primera");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//bundesliga
    		map.put("league", "bundesliga");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//seriea
    		map.put("league", "seriea");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//ligue1
    		map.put("league", "ligue1");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    		//eredivisie
    		map.put("league", "eredivisie");
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    	} else {
    		
    		result = recordService.getTeamInfo(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		sResult += (Integer)result.get("sResult");
    		
    	}
    	
    	
    	HashSet<String> listSet = new HashSet<String>(errMsg);
    	List<String> errMsgList = new ArrayList<String>(listSet);
        model.addAttribute("errMsg", errMsgList);
    	model.addAttribute("iResult", iResult);
    	model.addAttribute("uResult", uResult);
    	model.addAttribute("sResult", sResult);
        return getViewName(request);
    }
    
}
