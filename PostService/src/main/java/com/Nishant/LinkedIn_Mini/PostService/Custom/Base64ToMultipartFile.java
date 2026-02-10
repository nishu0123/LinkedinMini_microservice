package com.Nishant.LinkedIn_Mini.PostService.Custom;

import org.springframework.web.multipart.MultipartFile;
import java.io.*;

public class Base64ToMultipartFile implements MultipartFile {
    private final byte[] fileContent;
    private final String fileName;

    public Base64ToMultipartFile(byte[] fileContent, String fileName) {
        this.fileContent = fileContent;
        this.fileName = fileName;
    }

    @Override
    public String getName() { return "file"; }
    @Override
    public String getOriginalFilename() { return fileName; }
    @Override
    public String getContentType() { return "image/png"; } // Or detect dynamically
    @Override
    public boolean isEmpty() { return fileContent == null || fileContent.length == 0; }
    @Override
    public long getSize() { return fileContent.length; }
    @Override
    public byte[] getBytes() throws IOException { return fileContent; }
    @Override
    public InputStream getInputStream() throws IOException { return new ByteArrayInputStream(fileContent); }
    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        try (FileOutputStream fos = new FileOutputStream(dest)) { fos.write(fileContent); }
    }
}