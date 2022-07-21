package service;

import java.util.ArrayList;
import java.util.List;

import dao.SqliteDb;
import dto.UserHistory;

public class HistoryPage {
	
	public boolean savedYn = false;
	public List<UserHistory> userHistoryList;
	
	public HistoryPage() {
		this.userHistoryList = new ArrayList<>();
	}
	
	public void saveUserHistory(double lat, double lnt) {
		
		SqliteDb sqliteDb = new SqliteDb();
		
		// TB_USER_HISTORY테이블에 데이터 삽입
		sqliteDb.InsertDbHistory(lat, lnt);
		
	}


	public List<UserHistory> searchUserHistory(){
		
		SqliteDb sqliteDb = new SqliteDb();
		
		// TB_USER_HISTORY테이블에서 데이터 검색
		userHistoryList = sqliteDb.selectDbHistory();
		System.out.println(userHistoryList.get(0).getId());
		
		this.savedYn = true;
		
		return userHistoryList;
		
	}
	
	public boolean isSavedYn() {
		return savedYn;
	}

}
