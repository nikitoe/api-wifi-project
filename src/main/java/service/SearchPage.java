package service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import dao.SqliteDb;
import dto.PublicWifiInfo;

public class SearchPage {
	
	public final String unit = "kilometer";	// 거리 단위
	
	public SearchPage() {}
	
	/**
	 * 데이터베이스에 있는 위도, 경도 와 현재 위도, 경도 거리 구하기
	 * 
	 * @param lat		현재 위도
	 * @param lnt		현재 경도
	 * @return			현재 위치에서 거리가 가장 가까운 순으로 와이파이 정보 리스트 출력
	 */
	public List<PublicWifiInfo> calDistance(double lat, double lnt) {
		 
		SqliteDb sqltieDb = new SqliteDb();
		List<PublicWifiInfo> pwiList = new ArrayList<>();
		
		// 데이터베이스에서 모든 와이파이정보 출력
		pwiList = sqltieDb.selectDb();
				
		for (int i = 0; i < pwiList.size(); i++) {
			double theta = pwiList.get(i).getLnt() - lnt;
	        double dist = Math.sin(deg2rad(pwiList.get(i).getLat())) * Math.sin(deg2rad(lat)) + Math.cos(deg2rad(pwiList.get(i).getLat())) * Math.cos(deg2rad(lat)) * Math.cos(deg2rad(theta));
	         
	        dist = Math.acos(dist);
	        dist = rad2deg(dist);
	        dist = dist * 60 * 1.1515;
	         
	        if (this.unit == "kilometer") {
	            dist = dist * 1.609344;
	        } else if(this.unit == "meter"){
	            dist = dist * 1609.344;
	        }
	        
	        //소수 4번째 자리까지만 표시
	        pwiList.get(i).setDistance(Math.floor(dist * 10000)/10000.0);
	 
		}
		
		Collections.sort(pwiList, cp);
		
        return pwiList;
    }
    // 10진수를 라디안으로 변환
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }
     
    // 라디안을 degree로 변환
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
    // 거리순으로 정렬정렬
    Comparator<PublicWifiInfo> cp = new Comparator<PublicWifiInfo>() {

		@Override
		public int compare(PublicWifiInfo data1, PublicWifiInfo data2) {
			double a = data1.getDistance();
			double b = data2.getDistance();
			
			// 오름차순
			if(a > b) {
				return 1;
			}else if (a < b){
				return -1;
			}return 0;
		} 	
	};
}
     
 
		

