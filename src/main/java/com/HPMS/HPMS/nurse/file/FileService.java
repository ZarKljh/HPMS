package com.HPMS.HPMS.nurse.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileService {

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Value("${file.upload.base-url}")
    private String baseUrl;

    public String saveUploadedFile(MultipartFile file) throws IOException {
        // 절대 경로 가져오기
        Path uploadPath = Paths.get(uploadDirectory).toAbsolutePath();

        // 폴더 없으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일 이름
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + extension;

        // 실제 저장
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        // 디버그용 출력
        System.out.println("📂 Upload absolute path = " + uploadPath);
        System.out.println("✅ Saved file: " + filePath.toAbsolutePath());

        // URL 리턴 (웹에서 접근할 경로)
        return baseUrl + "/img/hpms/nurse/" + fileName;
    }

    public void deleteExistingFile(String fileUrl) {
        try {
            if (fileUrl != null && fileUrl.contains("/img/hpms/nurse/")) {
                String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                Path filePath = Paths.get(uploadDirectory, fileName);

                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }
        } catch (IOException e) {
            System.err.println("파일 삭제 실패: " + e.getMessage());
        }
    }
}
