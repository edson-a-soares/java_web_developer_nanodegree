package com.udacity.cloudstorage.service;

import java.util.List;
import com.udacity.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.infrastructure.mapper.FileMapper;

@Service
public class FileService {

    private final FileMapper files;

    public FileService(FileMapper mapper) {
        files = mapper;
    }

    public File get(File file) {
        return files.get(file);
    }

    public List<File> allBy(String UID) {
        return files.allFrom(UID);
    }

    public void remove(File file) {
        files.delete(file);
    }

    public boolean exists(File file) {
        return files.find(file) != null;
    }

    public void store(File file) {
        files.insert(file);
    }

}
