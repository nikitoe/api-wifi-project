package dto;

public class UserHistory {

	private int id;	// 번호
	private double lat;	// 위도
	private double lnt;	// 경도
	private String searchDttm; // 조회일자
	
	public UserHistory() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = (Math.floor(lat*10000000) / 10000000.0);
	}

	public double getLnt() {
		return lnt;
	}

	public void setLnt(double lnt) {
		this.lnt = (Math.floor(lnt*10000000) / 10000000.0);
	}

	public String getSearchDttm() {
		return searchDttm;
	}

	public void setSearchDttm(String searchDttm) {
		this.searchDttm = searchDttm;
	}
	
}
