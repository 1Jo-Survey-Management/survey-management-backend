package com.douzone.surveymanagement.common.utils;

import static com.douzone.surveymanagement.common.utils.FileExtensionConstant.*;

import com.douzone.surveymanagement.common.exception.NotAcceptableFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.springframework.http.MediaType;

/**
 * 이미지를 보여주기 위해 필요한 메서드를 정의해놓은 유틸 클래스 입니다.
 *
 * @author : 강명관
 * @since : 1.0
 **/
public class ImageUtil {

    private ImageUtil() {

    }

    /**
     * 이미지 저장 경로에 대해 해당 이미지를 byte[] 반환해주는 역할을 하는 메서드 입니다.
     *
     * @param imageSavePath 이미지가 저장되어있는 경로
     * @return image byte[]
     * @author : 강명관
     */
    public static byte[] getImageByteArray(String imageSavePath) {
        Path imagePath = Paths.get(imageSavePath);
        try {
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new NotAcceptableFileException();
        }
    }

    /**
     * 파일의 타입에 맞는 MediaType을 반환하는 메서드 입니다. (JPEG, JPG, PNG)
     *
     * @param filename 파일 이름
     * @return 파일 타입 MediaType
     * @author : 강명관
     */
    public static MediaType getExtensionMediaTypeByFileName(String filename) {
        String fileExtension = Optional.ofNullable(filename)
            .filter(f -> f.contains("."))
            .map(f -> f.substring(filename.lastIndexOf(".") + 1))
            .orElseThrow(NotAcceptableFileException::new);

        return getMediaType(fileExtension);
    }

    /**
     * JPEG, JPG, PNG 에 따라 맞는 MediaType을 반환하는 메서드 입니다.
     * 그 외의 타입의 경우 NotAcceptableFileException 을 발생 시킵니다.
     *
     * @param fileExtension 파일 확장자
     * @return 파일 MediaType
     * @author : 강명관
     */
    private static MediaType getMediaType(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case JPEG:
            case JPG:
                return MediaType.IMAGE_JPEG;
            case PNG:
                return MediaType.IMAGE_PNG;
            default:
                throw new NotAcceptableFileException();
        }
    }
}
