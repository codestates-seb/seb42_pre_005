package com.group5.stackoverflow;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class UpdateDummyData {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

//        String url = "jdbc:mysql://localhost:3306/stackoverflow";
//        String id = "root"; // 데이터베이스 ID
//        String password = "pass!"; // 데이터베이스 비밀번호
        String id = System.getenv("RDS_MYSQL_ADMIN_ID"); //root
        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD"); //password!
        String url = "jdbc:mysql://"+System.getenv("AWS_RDS_ENDPOINT") + "/stackoverflow";

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // UPDATE 문 생성
            String sql = "UPDATE member SET password = ? WHERE name = ?";
            pstmt = conn.prepareStatement(sql);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 패스워드 인코더 생성

            for (int i = 1 ; i <= 100; i++){
                String rawPassword = "password" + i; // 패스워드
                String encodedPassword = "{bcrypt}"+passwordEncoder.encode(rawPassword);
                pstmt.setString(1, encodedPassword);

                String name = "name" + i;
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
