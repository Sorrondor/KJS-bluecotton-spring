package com.app.bluecotton.api;

import com.app.bluecotton.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FileApi {

    private final FileService fileService;

    // ✅ 게시글 이미지 업로드
    @PostMapping("/uploads")
    public ResponseEntity<?> uploadPostImage(@RequestParam("image") MultipartFile file) {
        String imageUrl = fileService.savePostImage(file);
        return ResponseEntity.ok().body("{\"imageUrl\": \"" + imageUrl + "\"}");
    }
}
