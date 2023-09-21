package com.douzone.surveymanagement.user.service.impl;

import com.douzone.surveymanagement.user.dto.request.ImageModifyDTO;
import com.douzone.surveymanagement.user.mapper.MyPageMapper;
import com.douzone.surveymanagement.user.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    private final MyPageMapper myPageMapper;
    private final Environment environment;

    @Override
    @Transactional
    public String uploadImageAndUpdateUser(Long userNo, MultipartFile file) throws IOException {
        // 파일을 업로드하고 저장된 파일의 경로를 가져옴
        String imagePath = uploadFile(file);

        // 이미지 정보를 업데이트하는 DTO 생성
        ImageModifyDTO imageModifyDTO = new ImageModifyDTO();
        imageModifyDTO.setUserNo(userNo);
        imageModifyDTO.setUserImage(imagePath);

        // 이미지 정보 업데이트
        myPageMapper.updateUserImage(imageModifyDTO);

        return imagePath;
    }

    private String uploadFile(MultipartFile file) throws IOException {
        // 업로드할 파일명 생성 (원하는 방식으로 파일명을 생성하면 됩니다)
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename = UUID.randomUUID().toString() + fileExtension;

        // 파일을 업로드 디렉토리에 저장
        Path uploadPath = Paths.get(getUploadDir());
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(newFilename);

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            // 예외 처리 코드 추가
            e.printStackTrace();
            throw e;
        }

        // 저장된 파일의 경로를 반환
        return filePath.toString();
    }

    private String getUploadDir() {
        // 업로드 디렉토리 경로 가져오기
        return environment.getProperty("file.upload-dir");
    }
}
