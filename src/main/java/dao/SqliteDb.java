package dao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dto.PublicWifiInfo;
import dto.UserHistory;


public class SqliteDb {
	
	public String apiUrl;	// 사용할 API url
	public String authKey;	// 인증키
	public String requestType = "json";	// 요청파일타입(xml,xmlf,xls,json)
	public String service = "TbPublicWifiInfo";	// 서비스명(대소문자 구분 필수)
	public boolean searchYn;	// 근처 정보 보기 이벤트 발생할때
	public int totalCount;	// 총 데이터 건수
	public JsonArray row;	// 총 출력 값들의 Json배열
	public int cnt = 0;
	public List<PublicWifiInfo> PublicWifiInfoList;	// 총 출력 값들의 리스트
	
	public SqliteDb() {
		this.apiUrl = "http://openapi.seoul.go.kr:8088";
		this.authKey = "6f5777694973696d34356a4a51706d";
		this.requestType = "json";
		this.service = "TbPublicWifiInfo";
		this.PublicWifiInfoList = new ArrayList<>();
		this.searchYn = false;
	}

	public boolean isSearchYn() {
		return searchYn;
	}

	public void setSearchYn(boolean searchYn) {
		this.searchYn = searchYn;
	}

	/**
	 * Open API 와이파이 정보 가져오기 링크를 클릭시,
	 * API를 통해 총 와이파이 정보의 수를 얻는다.
	 * 
	 */
	public void startWifiAPI() {

		try {
			String startIndex = "1"; // 요청시작위치
			String endIndex = "5"; // 요청종료위치

			StringBuilder urlBuilder = new StringBuilder(this.apiUrl);
			urlBuilder.append("/" + URLEncoder.encode(this.authKey, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(this.requestType, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(this.service, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(startIndex, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(endIndex, "UTF-8"));

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/xml");
			System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다. */
			BufferedReader rd;

			// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			String result = sb.toString();

			JsonObject jObject = new Gson().fromJson(result, JsonObject.class);
			JsonObject TbPublicWifiInfo = (JsonObject) jObject.get("TbPublicWifiInfo");
			this.totalCount = TbPublicWifiInfo.get("list_total_count").getAsInt();

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.searchYn = false;
	}
	
	/**
	 * 요청시작위치와 요청종료위치 값을 입력 받아 API를 통해 요청을 보낸다.
	 * (예: http://openapi.seoul.go.kr:8088/(인증키)/(요청파일타입)/(서비스명)/(요청시작위치)/(요청종료위치)/
	 * 
	 * @param start 요청시작위치
	 * @param end 요청종료위치
	 */
	public void getWifiAPI(String start, String end) {
		try {
			String startIndex = start; // 요청시작위치
			String endIndex = end; // 요청종료위치
			System.out.println(startIndex);
			System.out.println(endIndex);
			
			StringBuilder urlBuilder = new StringBuilder(this.apiUrl);
			urlBuilder.append("/" + URLEncoder.encode(this.authKey, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(this.requestType, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(this.service, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(startIndex, "UTF-8"));
			urlBuilder.append("/" + URLEncoder.encode(endIndex, "UTF-8"));

			URL url = new URL(urlBuilder.toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/xml");
			System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다. */
			BufferedReader rd;

			// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
			if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			String result = sb.toString();

			JsonObject jObject = new Gson().fromJson(result, JsonObject.class);
			JsonObject TbPublicWifiInfo = (JsonObject) jObject.get("TbPublicWifiInfo");
			this.row = (JsonArray) TbPublicWifiInfo.get("row");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 총 데이터 정보들을 가져오고, 총 데이터 건수를 출력
	 * @return total 총 데이터 건수 
	 */
	public int getData() {
		/*
		 *  데이터를 한번에 최대 1000개까지만 가져올 수 있기때문에,
		 *  처음 받아온 totalCount(총 데이터 건수)를 이용해 1000단위로 계산
		 */
		int total = this.totalCount;	// 총 데이터 건수
		System.out.println(total);
		int onePer = 1000; // 한번에 가져올 가져올 단위
		int divideCount = total / onePer; // 실행 할 총 횟수(몫)
		int lastPer = total % onePer;// 1000단위가 아닌 나머지 데이터 수
		
		String start;	// 요청시작위치
		String end;	// 요청종료위치
		createDbHistory();
		createDb();
		try {
			for (int i = 0; i <= divideCount; i++) {
				if (i == divideCount) {
					start = (1 + i * onePer) + "";
					end = ((1 + i * onePer) + lastPer - 1) + "";
					
					// 요청시작위치와 요청종료위치에 따른 데이터 건수들 가져오기
					getWifiAPI(start, end);
					// 가져온 데이터들의 정보들을 데이터베이스에 삽입
					insertDb();
					System.out.println("완료");
					System.out.println(cnt);
					break;
				}
				start = (1 + i * onePer) + "";
				end = (onePer *(i+1)) + "";
				
				// 요청시작위치와 요청종료위치에 따른 데이터 건수들 가져오기
				getWifiAPI(start, end);
				// 가져온 데이터들의 정보들을 데이터베이스에 삽입
				insertDb();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.totalCount;
	}
	
	/**
	 * TB_PUBLIC_WIFI_INFO 테이블에 삽입
	 */
	public void insertDb() {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String url = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			connection = DriverManager.getConnection(url);
			System.out.println("SQLite DB connected");
			
			for (int i = 0; i < this.row.size(); i++) {
				PublicWifiInfo publicWifiInfo = new PublicWifiInfo();
				JsonObject rowobj = (JsonObject) this.row.get(i);
				
				publicWifiInfo.setMgr_no(rowobj.get("X_SWIFI_MGR_NO") + "");
				publicWifiInfo.setWrdofc(rowobj.get("X_SWIFI_WRDOFC") + "");
				publicWifiInfo.setMain_nm(rowobj.get("X_SWIFI_MAIN_NM") + "");
				publicWifiInfo.setAdres1(rowobj.get("X_SWIFI_ADRES1") + "");
				publicWifiInfo.setAdres2(rowobj.get("X_SWIFI_ADRES2") + "");
				publicWifiInfo.setInstl_floor(rowobj.get("X_SWIFI_INSTL_FLOOR") + "");
				publicWifiInfo.setInstl_ty(rowobj.get("X_SWIFI_INSTL_TY") + "");
				publicWifiInfo.setInstl_mby(rowobj.get("X_SWIFI_INSTL_MBY") + "");
				publicWifiInfo.setSvc_se(rowobj.get("X_SWIFI_SVC_SE") + "");
				publicWifiInfo.setCmcwr(rowobj.get("X_SWIFI_CMCWR") + "");
				publicWifiInfo.setCnstc_year(rowobj.get("X_SWIFI_CNSTC_YEAR") + "");
				publicWifiInfo.setInout_door(rowobj.get("X_SWIFI_INOUT_DOOR") + "");
				publicWifiInfo.setRemars3(rowobj.get("X_SWIFI_REMARS3") + "");
				publicWifiInfo.setLat((rowobj.get("LAT")).getAsDouble());
				publicWifiInfo.setLnt(rowobj.get("LNT").getAsDouble());
				publicWifiInfo.setWork_dttm(rowobj.get("WORK_DTTM") + "");

				String sql = " INSERT INTO TB_PUBLIC_WIFI_INFO " + " ( X_SWIFI_MGR_NO " + " , X_SWIFI_WRDOFC "
						+ " , X_SWIFI_MAIN_NM " + " , X_SWIFI_ADRES1 " + " , X_SWIFI_ADRES2 "
						+ " , X_SWIFI_INSTL_FLOOR " + " , X_SWIFI_INSTL_TY " + " , X_SWIFI_INSTL_MBY "
						+ " , X_SWIFI_SVC_SE " + " , X_SWIFI_CMCWR " + " , X_SWIFI_CNSTC_YEAR "
						+ " , X_SWIFI_INOUT_DOOR " + " , X_SWIFI_REMARS3 " + " , LAT " + " , LNT " + " , WORK_DTTM ) "
						+ " VALUES " + " ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ); ";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1,publicWifiInfo.getMgr_no());
				preparedStatement.setString(2,publicWifiInfo.getWrdofc());
				preparedStatement.setString(3,publicWifiInfo.getMain_nm());
				preparedStatement.setString(4,publicWifiInfo.getAdres1());
				preparedStatement.setString(5,publicWifiInfo.getAdres2());
				preparedStatement.setString(6,publicWifiInfo.getInstl_floor());
				preparedStatement.setString(7,publicWifiInfo.getInstl_ty());
				preparedStatement.setString(8,publicWifiInfo.getInstl_mby());
				preparedStatement.setString(9,publicWifiInfo.getSvc_se());
				preparedStatement.setString(10,publicWifiInfo.getCmcwr());
				preparedStatement.setString(11,publicWifiInfo.getCnstc_year());
				preparedStatement.setString(12,publicWifiInfo.getInout_door());
				preparedStatement.setString(13,publicWifiInfo.getRemars3());
				preparedStatement.setDouble(14,publicWifiInfo.getLnt());
				preparedStatement.setDouble(15,publicWifiInfo.getLat());
				preparedStatement.setString(16,publicWifiInfo.getWork_dttm());
				int affected = preparedStatement.executeUpdate();
				cnt++;
	            if (affected <= 0) {
	            	System.out.println("실패");
	            } 
			}Thread.sleep(600);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * TB_PUBLIC_WIFI_INFO 테이블 생성
	 */
	public void createDb() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String urlDb = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			
			connection = DriverManager.getConnection(urlDb);
			System.out.println("SQLite DB connected(create database)");

			String sql = " CREATE TABLE IF NOT EXISTS TB_PUBLIC_WIFI_INFO "
					+ " ( "
					+ " DISTANCE				REAL NULL DEFAULT 0 "
					+ " , X_SWIFI_MGR_NO		TEXT PRIMARY KEY NOT NULL "
					+ " , X_SWIFI_WRDOFC		TEXT NULL "
					+ " , X_SWIFI_MAIN_NM		TEXT NULL "
					+ " , X_SWIFI_ADRES1		TEXT NULL "
					+ " , X_SWIFI_ADRES2		TEXT NULL "
					+ " , X_SWIFI_INSTL_FLOOR	TEXT NULL "
					+ " , X_SWIFI_INSTL_TY		TEXT NULL "
					+ " , X_SWIFI_INSTL_MBY		TEXT NULL "
					+ " , X_SWIFI_SVC_SE		TEXT NULL "
					+ " , X_SWIFI_CMCWR			TEXT NULL "
					+ " , X_SWIFI_CNSTC_YEAR	TEXT NULL "
					+ " , X_SWIFI_INOUT_DOOR	TEXT NULL "
					+ " , X_SWIFI_REMARS3		TEXT NULL "
					+ " , LAT					REAL NULL "
					+ " , LNT					REAL NULL "
					+ " , WORK_DTTM				TEXT NULL "
					+ " ); ";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.executeUpdate();
				Thread.sleep(500);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * TB_PUBLIC_WIFI_INFO에서 모든 와이파이 정보 검색
	 * @return 모든 와이파이 정보 리스트
	 */
	public List<PublicWifiInfo> selectDb() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		

		String urlDb = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			
			connection = DriverManager.getConnection(urlDb);
			System.out.println("SQLite DB connected(select database)");

			String sql = " SELECT "
					+ " DISTANCE "
					+ " , X_SWIFI_MGR_NO "
					+ " , X_SWIFI_WRDOFC "
					+ " , X_SWIFI_MAIN_NM "
					+ " , X_SWIFI_ADRES1 "
					+ " , X_SWIFI_ADRES2 "
					+ " , X_SWIFI_INSTL_FLOOR "
					+ " , X_SWIFI_INSTL_TY "
					+ " , X_SWIFI_INSTL_MBY "
					+ " , X_SWIFI_SVC_SE "
					+ " , X_SWIFI_CMCWR	 "
					+ " , X_SWIFI_CNSTC_YEAR "
					+ " , X_SWIFI_INOUT_DOOR "
					+ " , X_SWIFI_REMARS3 "
					+ " , LAT "
					+ " , LNT "
					+ " , WORK_DTTM	"
					+ " FROM TB_PUBLIC_WIFI_INFO "
					+ "; ";


				preparedStatement = connection.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				
				while(rs.next()) {
					
					PublicWifiInfo publicWifiInfo = new PublicWifiInfo();
					
					publicWifiInfo.setDistance(rs.getDouble("DISTANCE"));
					publicWifiInfo.setMgr_no(rs.getString("X_SWIFI_MGR_NO"));
					publicWifiInfo.setWrdofc(rs.getString("X_SWIFI_WRDOFC"));
					publicWifiInfo.setMain_nm(rs.getString("X_SWIFI_MAIN_NM"));
					publicWifiInfo.setAdres1(rs.getString("X_SWIFI_ADRES1"));
					publicWifiInfo.setAdres2(rs.getString("X_SWIFI_ADRES2"));
					publicWifiInfo.setInstl_floor(rs.getString("X_SWIFI_INSTL_FLOOR"));
					publicWifiInfo.setInstl_ty(rs.getString("X_SWIFI_INSTL_TY"));
					publicWifiInfo.setInstl_mby(rs.getString("X_SWIFI_INSTL_MBY"));
					publicWifiInfo.setSvc_se(rs.getString("X_SWIFI_SVC_SE"));
					publicWifiInfo.setCmcwr(rs.getString("X_SWIFI_CMCWR"));
					publicWifiInfo.setCnstc_year(rs.getString("X_SWIFI_CNSTC_YEAR"));
					publicWifiInfo.setInout_door(rs.getString("X_SWIFI_INOUT_DOOR"));
					publicWifiInfo.setRemars3(rs.getString("X_SWIFI_REMARS3"));
					publicWifiInfo.setLat(rs.getDouble("LAT"));
					publicWifiInfo.setLnt(rs.getDouble("LNT"));
					publicWifiInfo.setWork_dttm(rs.getString("WORK_DTTM"));
					
					PublicWifiInfoList.add(publicWifiInfo);
				}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return PublicWifiInfoList;
	}
	
	/**
	 * TB_USER_HISTORY 테이블 생성
	 */
	public void createDbHistory() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String urlDb = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			
			connection = DriverManager.getConnection(urlDb);
			System.out.println("SQLite DB connected(create database)");

			String sql = " CREATE TABLE IF NOT EXISTS TB_USER_HISTORY "
					+ " ( "
					+ " ID				INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL "
					+ " ,LAT			REAL    NULL "
					+ " ,LNT			REAL    NULL "   
					+ " ,SEARCH_DTTM	TEXT    NULL  DEFAULT CURRENT_TIMESTAMP" 
					+ " ); ";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.executeUpdate();
				Thread.sleep(500);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * TB_USER_HISTORY 테이블에 데이터 삭제
	 * 
	 * @param id TB_USER_HISTORY의 id 컬럼
	 */
	public void deleteDbHistory(int id) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String url = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			connection = DriverManager.getConnection(url);
			System.out.println("SQLite DB connected");
							

				String sql = " DELETE FROM TB_USER_HISTORY " 
						+ " WHERE"
						+ " ID = ? ; ";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setInt(1, id);
				int affected = preparedStatement.executeUpdate();

	            if (affected > 0) {
	                System.out.println("성공");
	            } else {
	                System.out.println("실패");
	            }


		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * TB_USER_HISTORY에서 모든 히스토리 정보 검색
	 * @return 모든 히스토리 정보 리스트
	 */
	public List<UserHistory> selectDbHistory() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<UserHistory> uhList = new ArrayList<>();
		
		String urlDb = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			connection = DriverManager.getConnection(urlDb);
			System.out.println("SQLite DB connected(SELECT TB_USER_HISTORY)");

			String sql = " SELECT "
					+ " ID "
					+ " , LAT "
					+ " , LNT "
					+ " , SEARCH_DTTM	"
					+ " FROM TB_USER_HISTORY "
					+ "; ";

				preparedStatement = connection.prepareStatement(sql);
				rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					
					UserHistory userHistory = new UserHistory();
					
					userHistory.setId(rs.getInt("ID"));
					userHistory.setLat(rs.getDouble("LAT"));
					userHistory.setLnt(rs.getDouble("LNT"));
					userHistory.setSearchDttm(rs.getString("SEARCH_DTTM"));
					
					uhList.add(userHistory);
				}
	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return uhList;
	}
	
	/**
	 * TB_USER_HISTORY 테이블에 삽입
	 * 
	 * @param lat 위도
	 * @param lnt 경도
	 */
	public void InsertDbHistory(double lat, double lnt) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String url = "jdbc:sqlite:public_wifi.db";

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {
			connection = DriverManager.getConnection(url);
			System.out.println("SQLite DB connected");
			
				UserHistory userHistory = new UserHistory();
				
				userHistory.setLat(lat);
				userHistory.setLnt(lnt);

				String sql = " INSERT INTO TB_USER_HISTORY " 
						+ " ( LAT , LNT )"
						+ " VALUES " 
						+ " ( ?, ? ); ";

				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setDouble(1, lat);
				preparedStatement.setDouble(2, lnt);
				int affected = preparedStatement.executeUpdate();

	            if (affected > 0) {
	                System.out.println("성공");
	            } else {
	                System.out.println("실패");
	            }


		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (preparedStatement != null && !preparedStatement.isClosed()) {
					preparedStatement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (connection != null && !connection.isClosed()) {
					connection.isClosed();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	}

	
}
