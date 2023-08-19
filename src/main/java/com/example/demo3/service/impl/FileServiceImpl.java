package com.example.demo3.service.impl;

import com.example.demo3.model.FileEntity;
import com.example.demo3.repository.FileRepository;
import com.example.demo3.service.interfaces.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;

    @Autowired
    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    @Override
    public void uploadFile(MultipartFile file, int region, String youtubeLink, String date, int numAssignments) throws IOException {
        if (!file.isEmpty()) {
            // Check the file type
            if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
                throw new IllegalArgumentException("Only PDF files are allowed.");
            }

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
        } else {
            throw new IllegalArgumentException("No file selected.");
        }
    }


    @Override
    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public Optional<FileEntity> getFileById(Long id) {
        return fileRepository.findById(id);
    }

    @Override
    public void downloadFile(Long id, HttpServletResponse response) throws IOException {
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(id);
        if (optionalFileEntity.isPresent()) {
            FileEntity fileEntity = optionalFileEntity.get();
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileEntity.getFileName() + "\"");
            FileCopyUtils.copy(fileEntity.getData(), response.getOutputStream());
        }
    }
    @Override
    public void updateFile(Long id, int region, String youtubeLink, String date, int numAssignments) {
        Optional<FileEntity> optionalFileEntity = fileRepository.findById(id);
        if (optionalFileEntity.isPresent()) {
            FileEntity existingFileEntity = optionalFileEntity.get();

            // Update the file entity properties if the fields are not empty
            if (region != 0) {
                existingFileEntity.setRegion(String.valueOf(region));
            }
            if (!youtubeLink.isEmpty()) {
                existingFileEntity.setYoutubeLink(youtubeLink);
            }
            if (!date.isEmpty()) {
                existingFileEntity.setDate(LocalDate.parse(date));
            }
            if (numAssignments != 0) {
                existingFileEntity.setNumAssignments(numAssignments);
            }

            // Save the updated file entity
            fileRepository.save(existingFileEntity);
        }
    }


}
