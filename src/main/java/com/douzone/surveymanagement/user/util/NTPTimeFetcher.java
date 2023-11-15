//package com.douzone.surveymanagement.user.util;
//import org.apache.commons.net.ntp.NTPUDPClient;
//import org.apache.commons.net.ntp.TimeInfo;
//
//import java.net.InetAddress;
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class NTPTimeFetcher {
//
//    public String getFormattedKoreaTimeWithExpiration(int minutesToAdd) {
//        try {
//            String ntpServer = "kr.pool.ntp.org"; // NTP 서버 주소
//            NTPUDPClient client = new NTPUDPClient();
//            InetAddress address = InetAddress.getByName(ntpServer);
//            TimeInfo timeInfo = client.getTime(address);
//            long ntpTime = timeInfo.getReturnTime();
//            Instant instant = Instant.ofEpochMilli(ntpTime);
//            ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 적절한 시간대 선택
//            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
//            System.out.println("NTP 시간: " + zonedDateTime);
//
//            // 만료 시간을 50분 후로 설정
//            ZonedDateTime expirationTime = zonedDateTime.plusMinutes(minutesToAdd);
//
//            // ZonedDateTime을 "yyyy-MM-dd HH:mm:ss" 형식으로 포맷
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String formattedTime = expirationTime.format(formatter);
//
//            System.out.println("만료 시간: " + formattedTime);
//
//            return formattedTime;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public String getFormattedKoreaTime() {
//        try {
//            String ntpServer = "kr.pool.ntp.org"; // NTP 서버 주소
//            NTPUDPClient client = new NTPUDPClient();
//            InetAddress address = InetAddress.getByName(ntpServer);
//            TimeInfo timeInfo = client.getTime(address);
//            long ntpTime = timeInfo.getReturnTime();
//            Instant instant = Instant.ofEpochMilli(ntpTime);
//            ZoneId zoneId = ZoneId.of("Asia/Seoul"); // 적절한 시간대 선택
//            ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
//            System.out.println("NTP 시간: " + zonedDateTime);
//
//            // ZonedDateTime을 "yyyy-MM-dd HH:mm:ss" 형식으로 포맷
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
//            String formattedTime = zonedDateTime.format(formatter);
//
//            System.out.println("현재 시간: " + formattedTime);
//
//            return formattedTime;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
