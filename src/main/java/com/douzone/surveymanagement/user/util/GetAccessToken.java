//package com.douzone.surveymanagement.user.util;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.Collections;
//import java.util.Map;
//
//
//public class GetAccessToken {
//
//    public static Map<String, String> getToken(String AccessUrl) {
//
//        StringBuilder response = new StringBuilder();
//
//        try {
//            // HTTP GET 요청 생성
//            URL url = new URL(AccessUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//
//            // 요청 보내기
//            int responseCode = connection.getResponseCode();
//
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                // 응답을 읽어오기
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                String inputLine;
//
//
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // 응답 내용 출력
//                System.out.println("OAuth2 Token Response:");
//                System.out.println(response.toString());
//
//            } else {
//                System.out.println("HTTP Request Failed with Error Code: " + responseCode);
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Map<String, String> responseAccessCode = parseJsonResponse(response.toString());
//
//        return responseAccessCode;
//    }
//
//
//    public static Map<String, String> parseJsonResponse(String jsonResponse) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            // JSON 문자열을 Map으로 변환
//            Map<String, String> responseMap = objectMapper.readValue(jsonResponse, new TypeReference<Map<String, String>>() {
//            });
//            return responseMap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Collections.emptyMap(); // 예외 발생 시 빈 Map 반환 또는 예외 처리
//        }
//
//
//    }
//}
