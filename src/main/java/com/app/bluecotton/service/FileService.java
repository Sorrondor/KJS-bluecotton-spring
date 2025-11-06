package com.app.bluecotton.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@Slf4j
public class FileService {

    // ✅ OS별 사용자 홈 디렉토리 안전 경로
    private static final String UPLOAD_DIR =
            System.getProperty("user.home") + "/bluecotton/upload/post/";

    public String savePostImage(MultipartFile file) {
        try {
            log.info("📸 업로드 시도: {}", file.getOriginalFilename());
            log.info("📏 파일 크기: {}", file.getSize());

            // 🔥 mkdirs()가 실패하지 않도록 경로 유효성 체크
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                boolean made = dir.mkdirs();
                log.info("📁 폴더 생성 시도: {} → {}", UPLOAD_DIR, made);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            String imageUrl = "/upload/post/" + fileName;
            log.info("✅ 이미지 업로드 완료: {}", imageUrl);
            return imageUrl;
        } catch (IOException e) {
            log.error("❌ 이미지 업로드 실패: {}", e.getMessage());
            throw new RuntimeException("이미지 업로드 실패", e);
        }
    }

    public String getDefaultImagePath() {
        return "/upload/default/default_post.jpg";
    }
}
