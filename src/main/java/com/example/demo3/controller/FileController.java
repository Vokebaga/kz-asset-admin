package com.example.demo3.controller;

import com.example.demo3.model.FileEntity;
import com.example.demo3.service.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        List<FileEntity> files = fileService.getAllFiles();
        model.addAttribute("files", files);
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("region") int region,
                                   @RequestParam("youtubeLink") String youtubeLink,
                                   @RequestParam("date") String date,
                                   @RequestParam("numAssignments") int numAssignments) throws IOException {
        fileService.uploadFile(file, region, youtubeLink, date, numAssignments);
        return "redirect:/upload";
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Long id) {
        fileService.deleteFile(id);
        return "redirect:/upload";
    }

    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        fileService.downloadFile(id, response);
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<FileEntity> fileEntityOptional = fileService.getFileById(id);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            model.addAttribute("fileEntity", fileEntity);
        }
        return "update";
    }

    @PostMapping("/update/{id}")
    public String handleUpdateFile(@PathVariable("id") Long id,
                                   @RequestParam("region") int region,
                                   @RequestParam("youtubeLink") String youtubeLink,
                                   @RequestParam("date") String date,
                                   @RequestParam("numAssignments") int numAssignments) {
        fileService.updateFile(id, region, youtubeLink, date, numAssignments);
        return "redirect:/upload";
    }


}
