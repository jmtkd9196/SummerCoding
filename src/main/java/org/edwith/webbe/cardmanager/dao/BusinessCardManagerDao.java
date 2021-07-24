package org.edwith.webbe.cardmanager.dao;

import org.edwith.webbe.cardmanager.dto.BusinessCard;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BusinessCardManagerDao {
	private static String dburl = "jdbc:mysql://localhost:3306/projectdb";
	private static String dbUser = "projectuser";
	private static String dbpasswd = "project123!@#";
	
    public List<BusinessCard> searchBusinessCard(String keyword){
    	List<BusinessCard> list = new ArrayList<>();

		try {
			Class.forName("com.mysql.jdbc.Driver");	//드라이버 불러옴
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		String sql = "SELECT name, phone, company_name, create_date FROM business_card";
		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd);
				PreparedStatement ps = conn.prepareStatement(sql)) {

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					String name = rs.getString(1); //하나씩 꺼냄
					String phone = rs.getString("phone");
					String companyName = rs.getString("company_name");
//					String createDate = rs.getString("craete_date");
					BusinessCard businessCard = new BusinessCard(name, phone, companyName);
					list.add(businessCard); // list에 반복할때마다 Role인스턴스를 생성하여 list에 추가한다.
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	// 구현하세요.
    }

    public BusinessCard addBusinessCard(BusinessCard businessCard){//insert문이기 때문에 rs와 같은 결과 값을 받는 변수는 필요X
		int insertCount = 0; //insert/update/delete의 결과를 담는 변수

		try {
			Class.forName("com.mysql.jdbc.Driver");	//드라이버 불러옴
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		String sql = "INSERT INTO business_card (name, phone, company_name, create_date) VALUES ( ?, ? )"; //query문.

		try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbpasswd); //try with resource.
				PreparedStatement ps = conn.prepareStatement(sql)) { //conn객체로 ps객체 얻어옴.

			ps.setString(1, businessCard.getName()); //'?'에 대한 값을 바인딩.
			ps.setString(2, businessCard.getPhone()); //'?'에 대한 값을 바인딩.
			ps.setString(3, businessCard.getCompanyName());
			ps.setDate(4, (Date)businessCard.getCreateDate());

			
			insertCount = ps.executeUpdate(); //select는 excuteQeury() / insert, update, delete는 excuteUpadate().

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return businessCard; //query가 실행되면 받아오는 값을 return.
	// 구현하세요.
    	
    }
}
