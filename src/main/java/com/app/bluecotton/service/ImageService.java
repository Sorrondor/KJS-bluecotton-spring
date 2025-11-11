package com.app.bluecotton.service;

import com.app.bluecotton.domain.vo.som.SomImageVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final SomImageService somImageService;

    public Map<String, String> uploadToImageServer(MultipartFile file, String folder) throws Exception {
        String boundary = "----BlueCottonBoundary" + System.currentTimeMillis();
        String uploadUrl = "https://image-server.ideaflow.co.kr/upload/"
                + (folder.startsWith("/") ? folder : "/" + folder);

        URL url = new URL(uploadUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("Connection", "keep-alive");
//        conn.setFixedLengthStreamingMode(file.getSize());

        try (OutputStream os = conn.getOutputStream()) {
            // 파일 헤더 작성
            os.write(("--" + boundary + "\r\n").getBytes());
            os.write(("Content-Disposition: form-data; name=\"file\"; filename=\"" +
                    file.getOriginalFilename() + "\"\r\n").getBytes());
            os.write(("Content-Type: " + file.getContentType() + "\r\n\r\n").getBytes());
            os.write(file.getBytes());
            os.write("\r\n".getBytes());

            // 폴더명 필드 추가
            os.write(("--" + boundary + "\r\n").getBytes());
            os.write(("Content-Disposition: form-data; name=\"folder\"\r\n\r\n").getBytes());
            os.write(folder.getBytes());
            os.write("\r\n".getBytes());

            os.write(("--" + boundary + "--\r\n").getBytes());
            os.flush();
        }

        int responseCode = conn.getResponseCode();
        InputStream responseStream = responseCode >= 400 ? conn.getErrorStream() : conn.getInputStream();
        String responseBody = new String(responseStream.readAllBytes());
        System.out.println("ImageServer 응답: " + responseBody);

        // JSON 수동 파싱
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(responseBody, Map.class);
    }
}
