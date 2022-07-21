package service;

import dao.SqliteDb;

public class HistoryPage {
	
	public boolean savedYn = false;
	
	public void saveUserHistory(double lat, double lnt) {
		
		SqliteDb sqliteDb = new SqliteDb();
		
		// TB_USER_HISTORY테이블에 데이터 삽입
		sqliteDb.InsertDbHistory(lat, lnt);
		this.savedYn = true;
	}

	public boolean isSavedYn() {
		return savedYn;
	}
}
