package com.douzone.surveymanagement.common.utils;

import com.douzone.surveymanagement.common.exception.NotAcceptableFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 업로드를 처리 하기위한 유틸 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class FileUploadUtil {

    private static final String FILE_PATH = System.getProperty("user.home") + "/survey-management/image";

    public static String uploadFile(MultipartFile file) throws IOException {

        String fileExtension = getFileExtension(Objects.requireNonNull(file.getContentType()));

        String uuid = UUID.randomUUID().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedNow = LocalDateTime.now().format(formatter);

        Path uploadPath = Paths.get(FILE_PATH);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = formattedNow + uuid + "." + fileExtension;
        Path dest = Paths.get(FILE_PATH, fileName);
        file.transferTo(dest);

        return dest.toString();
    }

    private static String getFileExtension(String contentType) {
        switch (contentType) {
            case "image/png":
                return "png";
            case "image/jpeg":
                return "jpg";
            case "image/jpg":
                return "jpg";
            default:
                throw new NotAcceptableFileException();
        }
    }


}
