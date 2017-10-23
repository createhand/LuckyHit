package kr.co.toto.biz.record.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.toto.base.exception.BizException;
import kr.co.toto.base.persistence.IBatisDAO;
import kr.co.toto.base.persistence.IBatisDAOImpl;
import kr.co.toto.base.persistence.domain.DomainConst;
import kr.co.toto.base.persistence.domain.MatchRecordMt;
import kr.co.toto.base.persistence.domain.TeamMt;
import kr.co.toto.base.service.CommonService;
import kr.co.toto.comn.model.TAData;
import kr.co.toto.util.BeanFinder;
import kr.co.toto.util.BizUtil;
import kr.co.toto.util.DateUtil;
import kr.co.toto.util.ParamMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


/**
 * <pre>
 * 경기기록, 일정관련 Service
 *
 * </pre>
 *
 * @title RecordService.java
 * @project TOTO
 * @date 2013. 4. 14. 오후 12:10:31
 * @version 1.0
 * @author seochan
 *
 */
@Service
@Transactional
public class RecordService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	

    /**
     * 시즌정보 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertSeason(HashMap<String, String> season) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return dao.update("SEASON.insertSeason",season);
    }
    
    /**
     * 시즌정보 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertTeamSeason(HashMap<String, String> teamSeason) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return dao.update("SEASON.insertTeamSeason",teamSeason);
    }
    
    
    //selectSeason
    /**
     * 적중결과(예상픽, 결과)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public HashMap selectSeason(HashMap<String, String> params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);    	
    	return (HashMap)dao.select("SEASON.selectSeason", params);   	
    }
    
	/**
     * 적중결과(예상픽, 결과)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<TAData> selectHitResult(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);    	
    	return (List<TAData>)dao.selectList("RECORD.selectHitResult", params);   	
    }
    
    
	/**
     * 팀의 최근 경기전적/상대전적(경기목록)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<HashMap> selectLatestRecordList(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);    	
    	return (List<HashMap>)dao.selectList("RECORD.latestRecordList", params);   	
    }
    
	
	/**
     * 팀의 최근 경기전적/상대전적(승,무,패 목록)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public List<String> selectLatestRecord(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);    	
    	return (List<String>)dao.selectList("RECORD.latestRecord", params);    	
    }
	
	/**
     * 팀의 최근 경기전적(승,무,패 횟수)
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public HashMap<String, String> selectLatestRecordCount(List<String> latestRecord) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	HashMap<String, String> recordCnt = new HashMap<String, String>();
    	int matchCnt = latestRecord.size();
    	int winCnt = 0; 
    	int drawCnt = 0;
    	
    	for(int i=0;i<latestRecord.size();i++) {
    		if(DomainConst.RECORD_WIN.equals(latestRecord.get(i).toString())) winCnt++;
    		else if(DomainConst.RECORD_DRAW.equals(latestRecord.get(i).toString())) drawCnt++;
    	}
    	
    	recordCnt.put("winCnt", String.valueOf(winCnt));
    	recordCnt.put("drawCnt", String.valueOf(drawCnt));
    	recordCnt.put("loseCnt", String.valueOf(matchCnt - drawCnt - winCnt));
    	
    	logger.info(recordCnt);
    	
    	return recordCnt;
    }
    
    /**
     * 최근경기 골득실
     * 상대경기 골득실
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public HashMap<String, BigDecimal> selectLatestScore(ParamMap params) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	return (HashMap<String, BigDecimal>)dao.select("RECORD.latestScore", params);
    }
    
    /**
     * 경기일정 및 결과 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int insertRecord(List<HashMap<String, String>> record) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	for(int i =0;i<record.size();i++) {
    			result += dao.update("RECORD.insertRecord", record.get(i));
    	}    	
    	return result;
    }
    
    /**
     * 경기일정 및 결과 입력
     * 
     * @param 
     * @return
     * @throws BizException
     * @throws Exception
     */ 
    public int updateRecord(List<HashMap<String, String>> record) throws Exception {
    	IBatisDAO dao = (IBatisDAO)BeanFinder.getBean(IBatisDAOImpl.class);
    	int result = 0;
    	for(int i =0;i<record.size();i++) {
    			result += dao.update("RECORD.updateRecord", record.get(i));
    	}    	
    	return result;
    }
    
    //paging 자동처리
    public HashMap<String, Object> getMatchRecordSum(ParamMap map) {
    	
    	//결과 합계
    	HashMap<String, Object> resultObj = new HashMap<String, Object>();
    	
		//경기기록 수집
    	HashMap<String, Object> result = getMatchRecord(map);
		
		//오류메세지
    	List<String> errMsg = new ArrayList<String>();
		errMsg.addAll((List<String>)result.get("errMsg"));
		
		//입력건수
		int iResult = 0;
		iResult += (Integer)result.get("iResult");
		//수정건수
		int uResult = 0;
		uResult += (Integer)result.get("uResult");
		
		//paging 정보
		JSONObject pagingInfo = (JSONObject)result.get("pagingInfo");
		
		//전체 건수
		int totalCount = 0;
		totalCount = Integer.parseInt(pagingInfo.getString("totalCount"));
		//전체 페이지 수
		int totalPage = (int)Math.ceil((double)totalCount/(double)map.getInt("pageSize"));
		//페이지번호(1페이지는 위에서 이미 조회)
		int page = 2;
		
		//마지막 페이지 데이터까지 조회
		while(page <= totalPage) {
			map.put("page", String.valueOf(page));
			result = getMatchRecord(map);
    		errMsg.addAll((List<String>)result.get("errMsg"));
    		iResult += (Integer)result.get("iResult");
    		uResult += (Integer)result.get("uResult");
    		page++;
		}
		
		resultObj.put("iResult", iResult);
    	resultObj.put("uResult", uResult);
    	resultObj.put("errMsg", errMsg);
    	return resultObj;
    }
    
    public HashMap<String, Object> getMatchRecord(ParamMap map) {
    	
    	HashMap<String, Object> resultObj = new HashMap<String, Object>();
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	List<String> errMsg = new ArrayList<String>();
    	int iResult = 0, uResult = 0;
    	JSONObject pagingInfo = new JSONObject();
    	
        try {
        	
        	String pLeague = map.getString("league");
        	String stDt = map.getString("stDt");
        	String enDt = map.getString("enDt");
        	int pageSize = Integer.parseInt(map.getString("pageSize"));
        	int page = Integer.parseInt(map.getString("page"));
        	String url = DomainConst.DAUM_GAME_URL;
        	        	
            List<HashMap<String, String>> insertRecordList = new ArrayList<HashMap<String, String>>();
            List<HashMap<String, String>> updateRecordList = new ArrayList<HashMap<String, String>>();
            
            url += "?leagueCode="+pLeague+"&detail=true&sort=recent&page="+page+"&pageSize="+pageSize+"&fromDate="+stDt+"&toDate="+enDt;
            
            String result = BizUtil.getUrlSource(url, "UTF-8");
            
//            logger.info(result);
            
            //조회결과
            JSONObject json = (JSONObject)JSONSerializer.toJSON(result);
            //페이징
            pagingInfo = json.getJSONObject("pagingInfo");
            //전체 경기정보
            JSONArray matchList = json.getJSONArray("list");
            
//        	if(matchList.size() == 0) {
        		logger.info("경기 수 : "+matchList.size());
        		logger.info("리그 : "+pLeague+" 조회일자 : "+stDt+" ~ "+enDt);
        		logger.info("URL :"+url);
//        		throw new Exception("조회결과가 없습니다 : 리그 : "+pLeague+" 조회일자 : "+stDt+" ~ "+enDt);
//        	}
        	
        	for(int i=0;i<matchList.size();i++) {
        		
        		int j = i + i;
        		
        		/* 경기전체정보 */
        		JSONObject matchInfo = matchList.getJSONObject(i);
        		
        		//경기일자
        		String date = matchInfo.getString("startDate");

        		//경기시간
        		String time = matchInfo.getString("startTime");

        		
        		
        		//홈팀 경기결과 및 팀정보
        		JSONObject homeObj = matchInfo.getJSONObject("home");
        		JSONObject homeTeamObj = homeObj.getJSONObject("team");
        		String homeTeamName = BizUtil.replaceAll(homeTeamObj.getString("shortName"), " ", "");
        		String homeTeamFullName = BizUtil.replaceAll(homeTeamObj.getString("name"), " ", "");
        		String homeTeamImgUrl = homeTeamObj.getString("imageUrl");
        		int homeScore = 0;
        		
        		//어웨팀 경기결과 및 팀정보
        		JSONObject awayObj = matchInfo.getJSONObject("away");
        		JSONObject awayTeamObj = awayObj.getJSONObject("team");
        		String awayTeamName = BizUtil.replaceAll(awayTeamObj.getString("shortName"), " ", "");
        		String awayTeamFullName = BizUtil.replaceAll(awayTeamObj.getString("name"), " ", "");
        		String awayTeamImgUrl = awayTeamObj.getString("imageUrl");
        		int awayScore = 0;
        		
        		//경기장정보
        		JSONObject stadiumObj = matchInfo.getJSONObject("field");
        		String stadium = (stadiumObj.isNullObject() ? "" : (String)BizUtil.isNull(stadiumObj.getString("shortName")));
        		
        		//경기종료여부
        		String state = "N"; 
        		if(StringUtils.equals(matchInfo.getString("gameStatus"), "END")) {
        			state = "Y";
        			homeScore = homeObj.getInt("result");
        			awayScore = awayObj.getInt("result");
        		}
        		
        		logger.info("===========경기정보==========");
        		logger.info("경기날짜 : "+date);
        		logger.info("경기종료 : "+state+"["+homeScore+" : "+awayScore+"]");
        		logger.info("경기시간 : "+time);
        		logger.info("경기장소 : "+stadium);
        		logger.info("홈팀 : "+homeTeamName+" 원정팀 : "+awayTeamName);
        		
        		String winner = DomainConst.RECORD_WIN;
    			if(homeScore < awayScore) winner = DomainConst.RECORD_LOSE;
    			else if(homeScore == awayScore) winner = "D";
    			
    			String lgCd = getLgCd(pLeague);
    			
        		HashMap<String, String> tmp = new HashMap<String, String>();
        		
//        		if(StringUtils.equals(homeTeamName, "밀란")) { homeTeamName = "AC밀란"; }
//        		if(StringUtils.equals(awayTeamName, "밀란")) { awayTeamName = "AC밀란"; }
        		
        		int intDay = new BizUtil().getDayNo(DateUtil.getDateDay(date,"yyyyMMdd"));
        		
        		String tmCdH = commService.selectTeamCodeByDaum(homeTeamName);
        		String tmCdA = commService.selectTeamCodeByDaum(awayTeamName);
        		
        		//홈팀 등록
        		if(tmCdH == null) {
        			errMsg.add("미검색팀[홈]:"+homeTeamName);
        			continue;
        		}
        		
        		//어웨이팀 등록
        		if(tmCdA == null) {
        			errMsg.add("미검색팀[어웨이]:"+awayTeamName);
        			continue;
        		}
        		 
        		String mcDate = date;
        		String mcCd = lgCd+tmCdH+mcDate;
        		
        		tmp.put("mcCd", mcCd);
        		tmp.put("mcDate", mcDate);
        		tmp.put("mcDay", String.valueOf(intDay));
        		tmp.put("mcTime", time);
        		tmp.put("tmCdH", tmCdH);
        		tmp.put("tmCdA", tmCdA);
        		tmp.put("scoreH", String.valueOf(homeScore));
        		tmp.put("scoreA", String.valueOf(awayScore));
        		tmp.put("mcResult", winner);
        		tmp.put("mcEnd", state);
        		tmp.put("stCd", stadium);
        		tmp.put("lgCd", lgCd);
        		
        		MatchRecordMt inserted = commService.selectRecord(mcCd);
        		
        		if(inserted != null) {
	        		if(inserted.getMcCd().equals(mcCd)) {
//	        			if(inserted.getMcEnd().equals("N")) {
	        				updateRecordList.add(tmp);
//	        			}
	        		}
        		} else {        		
        			insertRecordList.add(tmp);        		
        		}
        	}
        	iResult += recordService.insertRecord(insertRecordList);
        	uResult += recordService.updateRecord(updateRecordList);
        	
        	
        } catch(Exception e) {
        	logger.error(e.getLocalizedMessage());
        	logger.error(e.getMessage());
        	e.printStackTrace();
        	errMsg.add(e.toString());
        }
    	
        resultObj.put("pagingInfo", pagingInfo);
    	resultObj.put("iResult", iResult);
    	resultObj.put("uResult", uResult);
    	resultObj.put("errMsg", errMsg);
    	
    	return resultObj;
    }
    
    public HashMap<String, Object> getTeamInfo(ParamMap map) {
    	
    	HashMap<String, Object> resultObj = new HashMap<String, Object>();
    	CommonService commService = (CommonService) BeanFinder.getBean(CommonService.class);
    	RecordService recordService = (RecordService) BeanFinder.getBean(RecordService.class);
    	List<String> errMsg = new ArrayList<String>();
    	int iResult = 0, uResult = 0, sResult = 0;
    	JSONObject pagingInfo = new JSONObject();
    	
        try {
        	
        	String pLeague = map.getString("league");
        	String seasonKey = map.getString("seasonKey");
        	String url = DomainConst.DAUM_TEAM_URL;
        	        	
            List<HashMap<String, String>> insertRecordList = new ArrayList<HashMap<String, String>>();
            
            url += "?leagueCode="+pLeague+"&seasonKey="+seasonKey;
            
            String result = BizUtil.getUrlSource(url, "UTF-8");
            
//            logger.info(result);
            
            //조회결과
            JSONObject json = (JSONObject)JSONSerializer.toJSON(result);
            if(json.isNullObject()) {
            	String err = "해당 시즌이 존재하지 않습니다. 리그 : "+pLeague+" 시즌 : "+seasonKey; 
            	logger.error(err);
            	throw new Exception(err);
            }
            //페이징
            pagingInfo = json.getJSONObject("pagingInfo");
            //전체 팀 정보
            JSONArray teamList = json.getJSONArray("list");
            //시즌정보
            JSONObject seasonInfo = json.getJSONObject("season");
            //시즌코드
            String snCd = seasonInfo.getString("seasonId");
            //리그코드
            String lgCd = getLgCd(pLeague);
            //리그정보
            JSONObject leagueInfo = json.getJSONObject("league");
            
            //시즌정보 조회
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("lgCd", lgCd);
            params.put("snCd", snCd);
            HashMap season = recordService.selectSeason(params);
            
            if(season == null) {
            	//시즌등록
            	HashMap<String, String> info = new HashMap<String, String>();
            	info.put("lgCd", lgCd);
            	info.put("snCd", snCd);
            	info.put("snKey", seasonInfo.getString("seasonKey"));
            	info.put("snName", seasonInfo.getString("name"));
            	sResult += recordService.insertSeason(info);
            }
            
//        	if(teamList.size() == 0) {
        		logger.info("팀 수 : "+teamList.size());
        		logger.info("리그 : "+leagueInfo.getString("name")+" 시즌 : "+seasonInfo.getString("name"));
//        		throw new Exception("조회결과가 없습니다 : 리그 : "+pLeague+" 조회일자 : "+stDt+" ~ "+enDt);
//        	}
        		
        	for(int i=0;i<teamList.size();i++) {
        		
        		/* 팀 정보 */
        		JSONObject teamInfo = teamList.getJSONObject(i);
        		
        		/* 팀 순위정보 */
        		JSONObject rankInfo = teamInfo.getJSONObject("rank");
        		
        		//팀ID(팀코드)
        		String tmCd = StringUtils.leftPad(teamInfo.getString("teamId"), 6, "0");

        		//팀명(full name)
        		String tmName = teamInfo.getString("name");
        		
        		//팀 이미지 URL
        		String tmImgUrl = teamInfo.getString("imageUrl");
        		
        		//팀명(short name)
        		String tmNameDaum = teamInfo.getString("shortName");

        		//팀 등록여부 확인
        		TeamMt isTeam = commService.selectTeam(tmCd);
        		
        		if(isTeam == null) {
        			//팀등록
        			HashMap<String, String> team = new HashMap<String, String>();
        			team.put("tmCd", tmCd);
        			team.put("cpCd", StringUtils.leftPad(teamInfo.getString("cpTeamId"), 6, "0"));
        			team.put("tmName", tmName);
        			team.put("tmImgUrl", tmImgUrl);
        			team.put("stCd", "ST001");
        			team.put("lgCd", lgCd);
        			team.put("tmNameBet", "");
        			team.put("tmNameDaum", tmNameDaum);
        			team.put("tmNameAll", "");
        			iResult += commService.insertTeam(team);
        			
            		logger.info("===========등록한 팀==========");
            		logger.info("리그코드 : "+lgCd);
            		logger.info("팀코드 : "+tmCd);
            		logger.info("팀명 : "+tmName);
        		}
        		
        		if(!rankInfo.isNullObject()) {
	        		HashMap<String, String> teamSeason = new HashMap<String, String>();
	        		
	        		teamSeason.put("lgCd", lgCd);
	        		teamSeason.put("snCd", snCd);
	        		teamSeason.put("tmCd", tmCd);
	        		teamSeason.put("rank", BizUtil.isNullZero(rankInfo.getString("rank")));
	        		teamSeason.put("game", BizUtil.isNullZero(rankInfo.getString("game")));
	        		teamSeason.put("win", BizUtil.isNullZero(rankInfo.getString("win")));
	        		teamSeason.put("draw", BizUtil.isNullZero(rankInfo.getString("draw")));
	        		teamSeason.put("loss", BizUtil.isNullZero(rankInfo.getString("loss")));
	        		teamSeason.put("gf", BizUtil.isNullZero(rankInfo.getString("gf")));
	        		teamSeason.put("ga", BizUtil.isNullZero(rankInfo.getString("ga")));
	        		teamSeason.put("gd", BizUtil.isNullZero(rankInfo.getString("gd")));
	        		teamSeason.put("pts", BizUtil.isNullZero(rankInfo.getString("pts")));
	        		uResult += recordService.insertTeamSeason(teamSeason);
        		}
        	}
        	
        } catch(Exception e) {
        	e.printStackTrace();
        	logger.error(e.getLocalizedMessage());
        	logger.error(e.getMessage());
        	errMsg.add(e.toString());
        }
    	
        resultObj.put("pagingInfo", pagingInfo);
    	resultObj.put("iResult", iResult);
    	resultObj.put("uResult", uResult);
    	resultObj.put("sResult", sResult);
    	resultObj.put("errMsg", errMsg);
    	
    	return resultObj;
    }
    
    
    
    //코드변환
    public String getLgCd(String pLeague) {
    	
    	String lgCd = null;
    	
		if(StringUtils.equals(pLeague, "kl")) {
			lgCd = "KLG01";
		} else if(StringUtils.equals(pLeague, "kl2")) {
			lgCd = "KLC01";
		} else if(StringUtils.equals(pLeague, "epl")) {
			lgCd = "EPL01";
		} else if(StringUtils.equals(pLeague, "bundesliga")) {
			lgCd = "BDL01";
		}  else if(StringUtils.equals(pLeague, "primera")) {
			lgCd = "PRL01";
		}  else if(StringUtils.equals(pLeague, "seriea")) {
			lgCd = "SEA01";
		}  else if(StringUtils.equals(pLeague, "ligue1")) {
			lgCd = "LG101";
		}  else if(StringUtils.equals(pLeague, "eredivisie")) {
			lgCd = "ERD01";
		}  else if(StringUtils.equals(pLeague, "uefacl")) {
			lgCd = "UFC01";
		}  else if(StringUtils.equals(pLeague, "uefacup")) {
			lgCd = "UFP01";
		}
	return lgCd;
    }
}
