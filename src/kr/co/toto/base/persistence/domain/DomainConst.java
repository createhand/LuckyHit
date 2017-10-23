package kr.co.toto.base.persistence.domain;

/**
 * <pre>
 * 도메인에서 사용되는 상수를 정의한 클래스
 * </pre>
 *
 * @title DomainConst.java
 * @project Default
 * @date 2012. 9. 21. 오후 7:37:55
 * @version 1.0
 * @author hjlee
 *
 */
public class DomainConst {
    /* 여부 : [Y:예, N:아니오] */
    /** 여부 : 예 */
    public static final String YES = "Y";
    /** 여부 : 아니오 */
    public static final String NO = "N";

    /* 사용자 상태 [0:등록,1:정상,2:분실,3:비밀번호5회오류,4:정지,5:해지] */
    /** 사용자상태 : 등록 */
    public static final String STATUS_USER_REGISTRATION = "0"; //사용자 등록
    /** 사용자상태 : 정상 */
    public static final String USER_STTS_NORMAL = "1"; //사용자  정상
    /** 사용자상태 : 분실 */
    public static final String USER_STTS_LOSE = "2"; //사용자  분실
    /** 사용자상태 : 비밀번호5회오류 */
    public static final String USER_STTS_PWD_5TIMES_MISTAKEN = "3"; //사용자 비밀번호 5회오류
    /** 사용자상태 : 정지 */
    public static final String USER_STTS_SUSPENSION = "4"; //사용자 정지
    /** 사용자상태 : 해지 */
    public static final String USER_STTS_CANCEL = "5"; //사용자 해지
    
    /* 로그인 상태 [1:로그인 성공,2:로그인 실패] */
    /** 로그인 상태 : 로그인 성공 */
    public static final String LGIN_STTS_SUCCESS = "1"; // 로그인 성공
    /** 로그인 상태 : 로그인 실패 */
    public static final String LGIN_STTS_FAIL = "2"; // 로그인 실패

    
    /* 사용자 구분 [1:사용자,2:관리자] */ 
    /** 사용자 구분 : 사용자 */ 
    public static final String USER_DS_USER = "1";
    /** 사용자 구분 : 관리자 */ 
    public static final String USER_DS_ADMIN = "2";
    
    /* 경기결과 */
    public static final String RECORD_DRAW = "D";
    
    public static final String RECORD_WIN = "W";
    
    public static final String RECORD_LOSE = "L";
    
    /* 조회조건 */
    public static final int RECORD_LATEST_CNT = 5;	// 최근경기 조회수
    
    
    /*
    /* 경기결과 리턴유형 *
    public static final int MC_RESULT_TYPE_ENG_TXT = 1; // 영문
    public static final int MC_RESULT_TYPE_HAN_TXT = 2; // 국글
    public static final int MC_RESULT_TYPE_IMG = 3; // 이미지
    */
    
    /* 예상 경기결과 적용값 */
    /** 상대전적 계산값 **/
    public static final double AGAINST_WIN_POINT = 3;
    public static final double AGAINST_DRAW_POINT = 0;
    public static final double AGAINST_LOSE_POINT = -3;
    //public static final double AGAINST_WEIGHTED_VALUE = 0.5;
    public static final double AGAINST_WEIGHTED_VALUE = 0.65;
    
    /** 최근전적 계산값 **/
    public static final double LATEST_WIN_POINT = 3;
    public static final double LATEST_DRAW_POINT = 0;
    public static final double LATEST_LOSE_POINT = -3;
    //public static final double LATEST_WEIGHTED_VALUE = 0.5;
    public static final double LATEST_WEIGHTED_VALUE = 0.35;
    
    /** 최종점수 무승부 구간 **/
    public static final double EXPECTED_DRAW_RANGE_FROM = 5;
    public static final double EXPECTED_DRAW_RANGE_TO = -5;
    
    /** 단순점수 절대 구간 **/
    public static final double SIMPLE_ABS_RANGE_FROM = 7;
    public static final double SIMPLE_ABS_RANGE_TO = -7;
    
    /* 경기결과 수집 사이트 Base url */
    public static final String DAUM_GAME_URL = "http://score.sports.media.daum.net/hermes/api/game/list.json";
    //game info
    //http://score.sports.media.daum.net/hermes/api/game/list.json?leagueCode=bundesliga&detail=true&sort=recent&page=1&pageSize=200&fromDate=20090101&toDate=20171231
    public static final String DAUM_TEAM_URL = "http://score.sports.media.daum.net/hermes/api/team/rank.json";
    //team info
    //http://score.sports.media.daum.net/hermes/api/team/list.json?leagueCode=wvl&seasonKey=20162017
    //http://score.sports.media.daum.net/hermes/api/team/rank.json?leagueCode=wvl&seasonKey=20162017
    public static final String BASE_SOCCER_URL = "http://m.sports.media.daum.net";
    public static final String EPL_SOCCER_URL = "/schedule/soccer/epl/main.daum?";
    public static final String BUNDESLIGA_SOCCER_URL = "/schedule/soccer/bundesliga/main.daum?";    
    public static final String KLEAGUE_SOCCER_URL = "/schedule/soccer/kl/main.daum?";
    public static final String JLEAGUE_SOCCER_URL = "http://sdb.spojoy.com/team/index.spo?";
    public static final String DAUM_COLLECTION_DIV_ID = "scheduleTbl";
    
    public static final String DAUM_API_PAGESIZE = "200";
    
    /* 게임 Base url */
    public static final String GAME_BASE_URL = "http://www.betman.co.kr/gameSchedule.so?method=basic&gameId=G011&gameRound=";
    
    /* 요일 */
    public static final String MON = "1";
    public static final String TUE = "2";
    public static final String WED = "3";
    public static final String THU = "4";
    public static final String FRI = "5";
    public static final String SAT = "6";
    public static final String SUN = "7";
    
    
    /* paging */
    //페이지당 게시물수
    public static final int countPerPage = 5;
    //블럭당 페이지수
    public static final int countPerBlock = 10;
    
}
