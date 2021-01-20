package com.udacity.cloudstorage.service;

import java.util.List;
import com.udacity.cloudstorage.model.Note;
import org.springframework.stereotype.Service;
import com.udacity.cloudstorage.infrastructure.mapper.NoteMapper;

@Service
public class NoteService {

    private final NoteMapper notes;

    public NoteService(NoteMapper mapper) {
        this.notes = mapper;
    }

    public List<Note> allBy(String UID) {
        return notes.allFrom(UID);
    }

    public void remove(Note note) {
        notes.delete(note);
    }

    public void add(Note note) {
        if (note.getId() == null) {
            notes.insert(note);
            return;
        }

        notes.update(note);
    }

}