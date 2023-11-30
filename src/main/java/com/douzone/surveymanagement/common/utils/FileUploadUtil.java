package com.douzone.surveymanagement.common.utils;

import static com.douzone.surveymanagement.common.utils.FileExtensionConstant.*;

import com.douzone.surveymanagement.common.exception.FileUploadFailException;
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

    private FileUploadUtil() {

    }

    private static final String FILE_PATH = System.getProperty("user.home")
        + "/Documents/GitHub/survey-management-frontend/public/images";

    /**
     * 파일을 로컬 폴더에 업로드하기 위한 메서드 입니다.
     *
     * @param file 업로드할 파일(JPG, JPEG, PNG)
     * @return 파일 업로드된 경로
     * @author : 강명관
     */
    public static String uploadFile(MultipartFile file) {

        String fileExtension = getFileExtension(Objects.requireNonNull(file.getContentType()));

        String uuid = UUID.randomUUID().toString();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String formattedNow = LocalDateTime.now().format(formatter);

        Path uploadPath = Paths.get(FILE_PATH);

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = formattedNow + uuid + "." + fileExtension;
            Path dest = Paths.get(FILE_PATH, fileName);
            file.transferTo(dest);
            return dest.toString();

        } catch (IOException exception) {
            throw new FileUploadFailException();
        }
    }

    /**
     * 파일의 contentType 을 확인하기 하고 가져오기 위한 메서드 입니다.
     *
     * @param contentType 파일의 contentType
     * @return contentType String
     * @author : 강명관
     */
    private static String getFileExtension(String contentType) {
        switch (contentType) {
            case IMAGE_PNG:
                return PNG;
            case IMAGE_JPEG:
            case IMAGE_JPG:
                return JPG;
            default:
                throw new NotAcceptableFileException();
        }
    }
}
