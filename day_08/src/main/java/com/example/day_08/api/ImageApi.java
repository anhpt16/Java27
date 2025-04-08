package com.example.day_08.api;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageApi {

    private static final String IMAGE_THUMBNAIL_DIR = "G:/movie_app_images/thumbnails";
    private static final String DEFAULT_IMAGE = "assets/default_movie_thumbnail.webp";

    @GetMapping("/thumbnail/{name}")
    public ResponseEntity<Resource> getImage(@PathVariable String name) throws IOException {
        try {
            // Tạo đường dẫn đến ảnh trong thư mục lưu ảnh
            Path path = Paths.get(IMAGE_THUMBNAIL_DIR).resolve(name).normalize();
            System.out.println("🔹 Kiểm tra ảnh: " + path);

            Resource resource = new UrlResource(path.toUri());

            // Nếu ảnh tồn tại và có thể đọc
            if (resource.exists() && resource.isReadable()) {
                System.out.println("✅ Ảnh tồn tại: " + path);
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
            }

            // Nếu ảnh không tồn tại, trả về ảnh mặc định từ thư mục static
            System.out.println("⚠ Ảnh không tồn tại! Dùng ảnh mặc định.");

            Resource defaultResource = new ClassPathResource("static/" + DEFAULT_IMAGE);
            System.out.println("🔍 Đường dẫn ảnh mặc định: " + defaultResource.getURL());

            if (defaultResource.exists() && defaultResource.isReadable()) {
                System.out.println("✅ Ảnh mặc định tồn tại, gửi về client.");
                return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"default_movie_thumbnail.webp\"")
                    .body(defaultResource);
            }

            System.out.println("❌ Không tìm thấy ảnh mặc định!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (MalformedURLException e) {
            System.out.println("❌ Lỗi đường dẫn ảnh: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
