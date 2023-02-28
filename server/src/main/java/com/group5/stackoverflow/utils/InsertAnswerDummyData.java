package com.group5.stackoverflow.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;

public class InsertAnswerDummyData {

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

//        String url = "jdbc:mysql://localhost:3306/stackoverflow?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&characterSetResults=UTF-8&useSSL=true"; // 데이터베이스 URL
        String url = "jdbc:mysql://localhost:3306/stackoverflow";
        String id = "root"; // 데이터베이스 ID
        String password = "dnseo4904!"; // 데이터베이스 비밀번호

//        String url = "jdbc:mysql://" + System.getenv("AWS_RDS_ENDPOINT") + "/stackoverflow";
//        String id = System.getenv("RDS_MYSQL_ADMIN_ID");
//        String password = System.getenv("RDS_MYSQL_ADMIN_PASSWORD");

//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 패스워드 인코더 생성

        try {
            // MySQL 데이터베이스에 연결
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, id, password);

            // 100개의 더미 데이터 생성
            for (int i = 1; i <= 100; i++) {
                String content = "내용입니다" + i; // 내용
//                String email = "email" + i + "@example.com"; // 이메일
//                int age = (int) (Math.random() * 30 + 1) + 20; // 나이 (평균 20, 표준편차 10인 정규분포)
//                String rawPassword = "password" + i; // 패스워드
                int voteCount = (int) (Math.random() * 20 + 1) + 30;// 투표 수 (평균 30, 표준편차 5인 정규분포)
                Long memberId = 0L + i;
//                Long questionId = 0L + i;


                // 패스워드 인코딩
//                String encodedPassword = passwordEncoder.encode(rawPassword);

                // INSERT 문 생성
                String sql = "INSERT INTO answer (content, vote_count, created_at, modified_at, member_id) VALUES (?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);

                // INSERT 문 파라미터 설정
                pstmt.setString(1, content);
                pstmt.setInt(2, voteCount);
                pstmt.setObject(3, LocalDateTime.now()); // created_at
                pstmt.setObject(4, LocalDateTime.now()); // modified_at
                pstmt.setLong(5, memberId);
//                pstmt.setLong(6, questionId);

//                String[] memberStatusList = {"MEMBER_NEW", "MEMBER_ACTIVE", "MEMBER_SLEEP", "MEMBER_QUIT"};
//                String memberStatus = memberStatusList[new Random().nextInt(memberStatusList.length)];
//                pstmt.setString(7, memberStatus); // member_status
//                pstmt.setInt(8, voteCount); // vote_count

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
