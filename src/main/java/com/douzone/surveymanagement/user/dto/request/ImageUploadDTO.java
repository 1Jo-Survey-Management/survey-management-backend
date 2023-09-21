package com.douzone.surveymanagement.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ImageUploadDTO {
    private Long userNo;
//    private MultipartFile imageFile;
    private byte[] imageData; // Add this variable to hold image data
}
