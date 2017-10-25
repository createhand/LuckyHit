package kr.co.toto.biz.game.persistence.domain;

import java.util.List;

import kr.co.toto.base.persistence.domain.TeamMt;
import kr.co.toto.comn.model.TAData;

public class GameDetailListDt {

	private String gameCode;
	private String gameName;
	private String gameTurn;
	private int gameListNo;	
	private String matchCode;
	private String lgCd;
	private String homeTeamCode;
	private String awayTeamCode;
	private String homeTeamName;
	private String awayTeamName;	
	private String matchDate;
	private String matchTime;
	private String matchDay;
	private String matchEnd;
	private String matchResult;
	private String stadiumCode;
	private String stadiumName;
	
	//홈, 어웨이 최근 경기 전체
	private List<TAData> homeTeamlatestRecordList;
	private List<TAData> awayTeamlatestRecordList;
	
	//상대 경기
	private List<TAData> againstRecordList;
	
	//홈 최근 경기 요약
	private TAData homeTeamlatestInfo;
	
	//어웨이 최근 경기 요약
	private TAData awayTeamlatestInfo;
	
	//예상결과
	private String expectMatchResult;
	private String expectMatchResultCode;
	
	private int scoreHome;
	private int scoreAway;
	
	//베팅현황
	private double winBetCnt;
	private double drawBetCnt;
	private double loseBetCnt;
	
	//팀정보
	private TeamMt homeTeamInfo;
	private TeamMt awayTeamInfo;
	
	//시즌성적 
	private TAData homeTeamSeaonInfo;
	private TAData awayTeamSeaonInfo;
	
	//이변 경기
	private List<TAData> homeTeamAmzaingList;
	private List<TAData> awayTeamAmzaingList;
	
	
	public TAData getHomeTeamlatestInfo() {
		return homeTeamlatestInfo;
	}
	public void setHomeTeamlatestInfo(TAData homeTeamlatestInfo) {
		this.homeTeamlatestInfo = homeTeamlatestInfo;
	}
	public TAData getAwayTeamlatestInfo() {
		return awayTeamlatestInfo;
	}
	public void setAwayTeamlatestInfo(TAData awayTeamlatestInfo) {
		this.awayTeamlatestInfo = awayTeamlatestInfo;
	}
	public String getGameCode() {
		return gameCode;
	}
	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getGameTurn() {
		return gameTurn;
	}
	public void setGameTurn(String gameTurn) {
		this.gameTurn = gameTurn;
	}
	public int getGameListNo() {
		return gameListNo;
	}
	public void setGameListNo(int gameListNo) {
		this.gameListNo = gameListNo;
	}
	public String getMatchCode() {
		return matchCode;
	}
	public void setMatchCode(String matchCode) {
		this.matchCode = matchCode;
	}
	public String getLgCd() {
		return lgCd;
	}
	public void setLgCd(String lgCd) {
		this.lgCd = lgCd;
	}
	public String getHomeTeamCode() {
		return homeTeamCode;
	}
	public void setHomeTeamCode(String homeTeamCode) {
		this.homeTeamCode = homeTeamCode;
	}
	public String getAwayTeamCode() {
		return awayTeamCode;
	}
	public void setAwayTeamCode(String awayTeamCode) {
		this.awayTeamCode = awayTeamCode;
	}
	public String getHomeTeamName() {
		return homeTeamName;
	}
	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}
	public String getAwayTeamName() {
		return awayTeamName;
	}
	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}
	public String getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(String matchDate) {
		this.matchDate = matchDate;
	}
	public String getMatchTime() {
		return matchTime;
	}
	public void setMatchTime(String matchTime) {
		this.matchTime = matchTime;
	}
	public String getMatchDay() {
		return matchDay;
	}
	public void setMatchDay(String matchDay) {
		this.matchDay = matchDay;
	}
	public String getMatchEnd() {
		return matchEnd;
	}
	public void setMatchEnd(String matchEnd) {
		this.matchEnd = matchEnd;
	}
	public String getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(String matchResult) {
		this.matchResult = matchResult;
	}
	public String getStadiumCode() {
		return stadiumCode;
	}
	public void setStadiumCode(String stadiumCode) {
		this.stadiumCode = stadiumCode;
	}
	public String getStadiumName() {
		return stadiumName;
	}
	public void setStadiumName(String stadiumName) {
		this.stadiumName = stadiumName;
	}
	public List<TAData> getHomeTeamlatestRecordList() {
		return homeTeamlatestRecordList;
	}
	public void setHomeTeamlatestRecordList(List<TAData> homeTeamlatestRecordList) {
		this.homeTeamlatestRecordList = homeTeamlatestRecordList;
	}
	public List<TAData> getAwayTeamlatestRecordList() {
		return awayTeamlatestRecordList;
	}
	public void setAwayTeamlatestRecordList(List<TAData> awayTeamlatestRecordList) {
		this.awayTeamlatestRecordList = awayTeamlatestRecordList;
	}
	public List<TAData> getAgainstRecordList() {
		return againstRecordList;
	}
	public void setAgainstRecordList(List<TAData> againstRecordList) {
		this.againstRecordList = againstRecordList;
	}
	public String getExpectMatchResult() {
		return expectMatchResult;
	}
	public void setExpectMatchResult(String expectMatchResult) {
		this.expectMatchResult = expectMatchResult;
	}
	public String getExpectMatchResultCode() {
		return expectMatchResultCode;
	}
	public void setExpectMatchResultCode(String expectMatchResultCode) {
		this.expectMatchResultCode = expectMatchResultCode;
	}
	public int getScoreHome() {
		return scoreHome;
	}
	public void setScoreHome(int scoreHome) {
		this.scoreHome = scoreHome;
	}
	public int getScoreAway() {
		return scoreAway;
	}
	public void setScoreAway(int scoreAway) {
		this.scoreAway = scoreAway;
	}
	public double getWinBetCnt() {
		return winBetCnt;
	}
	public void setWinBetCnt(double winBetCnt) {
		this.winBetCnt = winBetCnt;
	}
	public double getDrawBetCnt() {
		return drawBetCnt;
	}
	public void setDrawBetCnt(double drawBetCnt) {
		this.drawBetCnt = drawBetCnt;
	}
	public double getLoseBetCnt() {
		return loseBetCnt;
	}
	public void setLoseBetCnt(double loseBetCnt) {
		this.loseBetCnt = loseBetCnt;
	}
	public TeamMt getHomeTeamInfo() {
		return homeTeamInfo;
	}
	public void setHomeTeamInfo(TeamMt homeTeamInfo) {
		this.homeTeamInfo = homeTeamInfo;
	}
	public TeamMt getAwayTeamInfo() {
		return awayTeamInfo;
	}
	public void setAwayTeamInfo(TeamMt awayTeamInfo) {
		this.awayTeamInfo = awayTeamInfo;
	}
	public TAData getHomeTeamSeaonInfo() {
		return homeTeamSeaonInfo;
	}
	public void setHomeTeamSeaonInfo(TAData homeTeamSeaonInfo) {
		this.homeTeamSeaonInfo = homeTeamSeaonInfo;
	}
	public TAData getAwayTeamSeaonInfo() {
		return awayTeamSeaonInfo;
	}
	public void setAwayTeamSeaonInfo(TAData awayTeamSeaonInfo) {
		this.awayTeamSeaonInfo = awayTeamSeaonInfo;
	}
	public List<TAData> getHomeTeamAmzaingList() {
		return homeTeamAmzaingList;
	}
	public void setHomeTeamAmzaingList(List<TAData> homeTeamAmzaingList) {
		this.homeTeamAmzaingList = homeTeamAmzaingList;
	}
	public List<TAData> getAwayTeamAmzaingList() {
		return awayTeamAmzaingList;
	}
	public void setAwayTeamAmzaingList(List<TAData> awayTeamAmzaingList) {
		this.awayTeamAmzaingList = awayTeamAmzaingList;
	}

}
