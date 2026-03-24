package com.lcu.controller;

import com.lcu.bean.JsonResponse;
import com.lcu.bean.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/upload")
public class UploadController {
    @PostMapping("/uploadFile")
    public JsonResponse upload(@RequestParam("file") MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String end = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        String pre = UUID.randomUUID().toString().replaceAll("-", "");
        String newName = pre + "." + end;
        String path = "";
        String responsePath = "";
        if (file.getContentType().contains("image")) {
            path = "C:/upload/share/img";
            responsePath =  "/pics/" + newName;
        }else {
            path = "C:/upload/share/video";
            responsePath = "/videos/" + newName;
        }
        File f = new File(path, newName);
        try {
            file.transferTo(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(responsePath);
    }
}
