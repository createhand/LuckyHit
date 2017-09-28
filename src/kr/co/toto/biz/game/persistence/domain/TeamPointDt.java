package kr.co.toto.biz.game.persistence.domain;

import java.util.List;

public class TeamPointDt {

	List<String> latestRecord;
	List<String> latestRecordAtHome;
	List<String> againstRecord;	
	List<String> againstRecordAtHome;
	
	public List<String> getLatestRecord() {
		return latestRecord;
	}
	public void setLatestRecord(List<String> latestRecord) {
		this.latestRecord = latestRecord;
	}
	public List<String> getLatestRecordAtHome() {
		return latestRecordAtHome;
	}
	public void setLatestRecordAtHome(List<String> latestRecordAtHome) {
		this.latestRecordAtHome = latestRecordAtHome;
	}
	public List<String> getAgainstRecord() {
		return againstRecord;
	}
	public void setAgainstRecord(List<String> againstRecord) {
		this.againstRecord = againstRecord;
	}
	public List<String> getAgainstRecordAtHome() {
		return againstRecordAtHome;
	}
	public void setAgainstRecordAtHome(List<String> againstRecordAtHome) {
		this.againstRecordAtHome = againstRecordAtHome;
	}
	
}

