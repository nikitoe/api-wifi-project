package service;

import dao.SqliteDb;

public class IndexPage {
	
	public int checkApiWifi() {
		
		SqliteDb sqliteDb = new SqliteDb();
		
		// 테이블 존재 여부 확인
		int resultCheckTable = sqliteDb.checkTable("TB_PUBLIC_WIFI_INFO");
		
		// 해당 테이블의 데이터 존재 여부 확인
		int resultCheckSelectValue = sqliteDb.checkSelectValue("TB_PUBLIC_WIFI_INFO");
		
		// 모두 존재하면 1, 하나라도 존재 하지 않으면 0
		int result =  resultCheckTable * resultCheckSelectValue;
		
		return result;
		
	}

}
