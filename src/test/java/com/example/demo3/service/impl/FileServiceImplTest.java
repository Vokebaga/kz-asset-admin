package com.example.demo3.service.impl;

import com.example.demo3.model.FileEntity;
import com.example.demo3.repository.FileRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class FileServiceImplTest {

    private FileServiceImpl fileService;

    @Mock
    private FileRepository fileRepository;

    @BeforeEach
    public void setup() {
        fileRepository = mock(FileRepository.class);
        fileService = new FileServiceImpl(fileRepository);
    }

    @Test
    public void testUploadFile_ValidPDFFile_Success() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile("file.pdf", "file.pdf", "application/pdf", "test data".getBytes());
        int region = 1;
        String youtubeLink = "https://www.youtube.com";
        String date = "2023-07-16";
        int numAssignments = 5;

        // Act
        fileService.uploadFile(file, region, youtubeLink, date, numAssignments);

        // Assert
        verify(fileRepository, times(1)).save(any(FileEntity.class));
    }

    @Test
    public void testUploadFile_InvalidTextFile_ExceptionThrown() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile("file.txt", "file.txt", "text/plain", "test data".getBytes());
        int region = 1;
        String youtubeLink = "https://www.youtube.com";
        String date = "2023-07-16";
        int numAssignments = 5;

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fileService.uploadFile(file, region, youtubeLink, date, numAssignments);
        });

        verify(fileRepository, never()).save(any(FileEntity.class));
    }

    @Test
    public void testGetAllFiles_ReturnsAllFiles() {
        // Arrange
        FileEntity file1 = new FileEntity();
        file1.setId(1L);
        file1.setFileName("file1.pdf");

        FileEntity file2 = new FileEntity();
        file2.setId(2L);
        file2.setFileName("file2.pdf");

        when(fileRepository.findAll()).thenReturn(List.of(file1, file2));

        // Act
        List<FileEntity> files = fileService.getAllFiles();

        // Assert
        Assertions.assertEquals(2, files.size());
        Assertions.assertEquals(file1.getId(), files.get(0).getId());
        Assertions.assertEquals(file2.getId(), files.get(1).getId());
        verify(fileRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteFile_ValidId_FileDeleted() {
        // Arrange
        Long fileId = 1L;

        // Act
        fileService.deleteFile(fileId);

        // Assert
        verify(fileRepository, times(1)).deleteById(fileId);
    }

    @Test
    public void testGetFileById_ExistingId_ReturnsFile() {
        // Arrange
        Long fileId = 1L;
        FileEntity file = new FileEntity();
        file.setId(fileId);
        when(fileRepository.findById(fileId)).thenReturn(Optional.of(file));

        // Act
        Optional<FileEntity> result = fileService.getFileById(fileId);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(fileId, result.get().getId());
        verify(fileRepository, times(1)).findById(fileId);
    }

    @Test
    public void testGetFileById_NonExistingId_ReturnsEmptyOptional() {
        // Arrange
        Long fileId = 1L;
        when(fileRepository.findById(fileId)).thenReturn(Optional.empty());

        // Act
        Optional<FileEntity> result = fileService.getFileById(fileId);

        // Assert
        Assertions.assertFalse(result.isPresent());
        verify(fileRepository, times(1)).findById(fileId);
    }

    @Test
    public void testUploadFile_InvalidFileFormat_ExceptionThrown() throws IOException {
        // Arrange
        MultipartFile file = new MockMultipartFile("file.docx", "file.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "test data".getBytes());
        int region = 1;
        String youtubeLink = "https://www.youtube.com";
        String date = "2023-07-16";
        int numAssignments = 5;

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            fileService.uploadFile(file, region, youtubeLink, date, numAssignments);
        });

        verify(fileRepository, never()).save(any(FileEntity.class));
    }

    // Test the updateFile method in a similar manner as the previous tests

    // Test the downloadFile method in a similar manner as the previous tests
}
