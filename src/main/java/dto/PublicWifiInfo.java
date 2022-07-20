package dto;

public class PublicWifiInfo {
	private double distance;
	private String mgr_no;
	private String wrdofc;
	private String main_nm;
	private String adres1;
	private String adres2;
	private String instl_floor;
	private String instl_ty;
	private String instl_mby;
	private String svc_se;
	private String cmcwr;
	private String cnstc_year;
	private String inout_door;
	private String remars3;
	private double lat;
	private double lnt;
	private String work_dttm;
	
	public PublicWifiInfo() {
		this.distance = 0;
	}
	
	
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}



	public String getMgr_no() {
		return mgr_no;
	}

	public void setMgr_no(String mgr_no) {
		this.mgr_no = mgr_no.replace("\"","");
	}

	public String getWrdofc() {
		return wrdofc;
	}

	public void setWrdofc(String wrdofc) {
		this.wrdofc = wrdofc.replace("\"","");
	}

	public String getMain_nm() {
		return main_nm;
	}

	public void setMain_nm(String main_nm) {
		this.main_nm = main_nm.replace("\"","");
	}

	public String getAdres1() {
		return adres1;
	}

	public void setAdres1(String adres1) {
		this.adres1 = adres1.replace("\"","");
	}

	public String getAdres2() {
		return adres2;
	}

	public void setAdres2(String adres2) {
		this.adres2 = adres2.replace("\"","");
	}

	public String getInstl_floor() {
		return instl_floor;
	}

	public void setInstl_floor(String instl_floor) {
		this.instl_floor = instl_floor.replace("\"","");
	}

	public String getInstl_ty() {
		return instl_ty;
	}

	public void setInstl_ty(String instl_ty) {
		this.instl_ty = instl_ty.replace("\"","");
	}

	public String getInstl_mby() {
		return instl_mby;
	}

	public void setInstl_mby(String instl_mby) {
		this.instl_mby = instl_mby.replace("\"","");
	}

	public String getSvc_se() {
		return svc_se;
	}

	public void setSvc_se(String svc_se) {
		this.svc_se = svc_se.replace("\"","");
	}

	public String getCmcwr() {
		return cmcwr;
	}

	public void setCmcwr(String cmcwr) {
		this.cmcwr = cmcwr.replace("\"","");
	}

	

	public String getCnstc_year() {
		return cnstc_year;
	}



	public void setCnstc_year(String cnstc_year) {
		this.cnstc_year = cnstc_year.replace("\"","");
	}


	public String getInout_door() {
		return inout_door;
	}

	public void setInout_door(String inout_door) {
		this.inout_door = inout_door.replace("\"","");
	}

	public String getRemars3() {
		return remars3;
	}

	public void setRemars3(String remars3) {
		this.remars3 = remars3.replace("\"","");
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLnt() {
		return lnt;
	}

	public void setLnt(double lnt) {
		this.lnt = lnt;
	}

	public String getWork_dttm() {
		return work_dttm;
	}

	public void setWork_dttm(String work_dttm) {
		this.work_dttm = work_dttm.replace("\"","");
	}

	

	
	
}
