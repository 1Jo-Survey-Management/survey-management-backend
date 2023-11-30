package com.douzone.surveymanagement.user.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetAccessToken {
    
    public static Map<String, String> getToken(String accessUrl) {

        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(accessUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                }
            } else {
                log.error("HTTP Request Failed with Error Code: {}", responseCode);
            }

        } catch (IOException e) {
            log.error("IOException occurred while getting the access token: {}", e.getMessage());
            e.printStackTrace();
        }

        Map<String, String> responseAccessCode = parseJsonResponse(response.toString());

        return responseAccessCode;
    }

    public static Map<String, String> parseJsonResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (jsonResponse != null && !jsonResponse.isEmpty()) {
                // JSON 문자열이 비어 있지 않은 경우에만 변환
                return objectMapper.readValue(jsonResponse, new TypeReference<>() {
                });
            } else {
                log.warn("Empty or null JSON response. Returning an empty map.");
                return Collections.emptyMap();
            }
        } catch (IOException e) {
            log.error("Error occurred while parsing JSON response: {}", e.getMessage());
            e.printStackTrace();
            return Collections.emptyMap(); // 예외 발생 시 빈 Map 반환 또는 예외 처리
        }
    }
}
