package com.example.demo3.controller;

import com.example.demo3.model.FileEntity;
import com.example.demo3.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class FileController {

    private final FileRepository fileRepository;

    @Autowired
    public FileController(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        List<FileEntity> files = fileRepository.findAll();
        model.addAttribute("files", files);
        return "upload";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("region") int region,
                                   @RequestParam("youtubeLink") String youtubeLink,
                                   @RequestParam("date") String date,
                                   @RequestParam("numAssignments") int numAssignments) throws IOException {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(fileName);
            fileEntity.setData(bytes);
            fileEntity.setRegion(String.valueOf(region));
            fileEntity.setYoutubeLink(youtubeLink);
            fileEntity.setDate(LocalDate.parse(date));
            fileEntity.setNumAssignments(numAssignments);
            fileRepository.save(fileEntity);
        }
        return "redirect:/upload";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(id);
        if (optionalFileEntity.isPresent()) {
            FileEntity fileEntity = optionalFileEntity.get();
            model.addAttribute("file", fileEntity);
            return "update";
        } else {
            return "redirect:/upload";
        }
    }

    @PostMapping("/update/{id}")
    public String handleFileUpdate(@PathVariable("id") Long id,
                                   @RequestParam("file") MultipartFile file,
                                   @RequestParam("region") int region,
                                   @RequestParam("youtubeLink") String youtubeLink,
                                   @RequestParam("date") String date,
                                   @RequestParam("numAssignments") int numAssignments) throws IOException {
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(id);
        if (optionalFileEntity.isPresent() && !file.isEmpty()) {
            FileEntity fileEntity = optionalFileEntity.get();
            byte[] bytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            fileEntity.setFileName(fileName);
            fileEntity.setData(bytes);
            fileEntity.setRegion(String.valueOf(region));
            fileEntity.setYoutubeLink(youtubeLink);
            fileEntity.setDate(LocalDate.parse(date));
            fileEntity.setNumAssignments(numAssignments);
            fileRepository.save(fileEntity);
        }
        return "redirect:/upload";
    }

    @GetMapping("/delete/{id}")
    public String deleteFile(@PathVariable("id") Long id) {
        fileRepository.deleteById(id);
        return "redirect:/upload";
    }

    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(id);
        if (optionalFileEntity.isPresent()) {
            FileEntity fileEntity = optionalFileEntity.get();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileEntity.getFileName() + "\"");
            FileCopyUtils.copy(fileEntity.getData(), response.getOutputStream());
        }
    }
}
