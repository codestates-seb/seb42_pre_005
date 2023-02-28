package com.group5.stackoverflow;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class InsertAnswerDummyData {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

//        String url = "jdbc:mysql://localhost:3306/stackoverflow?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8&useSSL=true"; // 데이터베이스 URL
//        String url = "jdbc:mysql://localhost:3306/stackoverflow";
//        String id = "root"; // 데이터베이스 ID
//        String password = ""; // 데이터베이스 비밀번호

        String url = "jdbc:mysql://"+System.getenv("AWS_RDS_ENDPOINT")+"/stackoverflow";
        String id = System.getenv("RDS_MYSQL_ADMIN_ID");
        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD");

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i = 1; i <= 100; i++) {
                String content = "내용입니다" + i; // 내용
                int voteCount = (int) (Math.random() * 20 + 1) + 30;// 투표 수 (평균 30, 표준편차 5인 정규분포)


                // INSERT 문 생성
                String sql = "INSERT INTO answer (content, vote_count, created_at, modified_at) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                // INSERT 문 파라미터 설정
                pstmt.setString(1, content);
                pstmt.setInt(2, voteCount);
                pstmt.setObject(3, LocalDateTime.now()); // created_at
                pstmt.setObject(4, LocalDateTime.now()); // modified_at

                // INSERT 문 실행
                pstmt.executeUpdate();
            }

            System.out.println("100 rows inserted successfully.");

        } catch (SQLException | ClassNotFoundException e) {
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
