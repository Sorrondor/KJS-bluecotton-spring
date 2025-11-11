package com.app.bluecotton.api.publicapi;

import com.app.bluecotton.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class ImageAPI {
    private final ImageService imageService;

    @PostMapping("/upload-image")
    public ResponseEntity<Map<String, String>> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder
    ) throws Exception {
        folder = java.net.URLDecoder.decode(folder, StandardCharsets.UTF_8);
        Map<String, String> result = imageService.uploadToImageServer(file, folder);

        return ResponseEntity.ok(result);
    }

}
