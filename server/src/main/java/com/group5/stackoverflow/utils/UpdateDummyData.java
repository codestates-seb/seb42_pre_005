package com.group5.stackoverflow.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class UpdateDummyData {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        String url = "jdbc:mysql://localhost:3306/stackoverflow";
        String id = "root"; // 데이터베이스 ID
        String password = "pass!"; // 데이터베이스 비밀번호

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // UPDATE 문 생성
            String sql = "UPDATE member SET member_status = ? WHERE name = ?";
            pstmt = conn.prepareStatement(sql);

            for (int i = 1 ; i < 100; i++){
                String name = "name" + i;
                String[] memberStatusList = {"MEMBER_NEW", "MEMBER_ACTIVE", "MEMBER_SLEEP", "MEMBER_QUIT"};
                String memberStatus = memberStatusList[new Random().nextInt(memberStatusList.length)];
                pstmt.setString(1, memberStatus);
                pstmt.setString(2, name);
                // UPDATE 문 실행
                int result = pstmt.executeUpdate();
                System.out.println(result + " rows updated successfully for name = " + name);

            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
