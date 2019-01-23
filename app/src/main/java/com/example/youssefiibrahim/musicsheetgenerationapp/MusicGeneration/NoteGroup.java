package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

import java.util.ArrayList;

public class NoteGroup {
    private ArrayList<Note> pitches;
    private Duration duration;

    NoteGroup() {
        this.pitches = new ArrayList<Note>();
        this.duration = null;
    }

    NoteGroup(Note note) {
        this.pitches = new ArrayList<Note>();
        this.pitches.add(note);
        this.duration = note.getDuration();
    }

    NoteGroup(ArrayList<Note> notes) {
        this.pitches = new ArrayList<Note>();
        for (Note note : notes)
            pitches.add(note);
        this.duration = notes.get(0).getDuration();
    }

    public void addNote(Note note) {
        if (this.duration == null)
            this.duration = note.getDuration();
        this.pitches.add(note);
    }

    public NoteGroup transpose(int pitches) {
        NoteGroup ng = new NoteGroup();
        for (Note note : this.pitches)
            ng.addNote(note.transpose(pitches));
        return ng;
    }

    public ArrayList<Note> getPitches() {
        return pitches;
    }

    public Duration getDuration() {
        return this.duration;
    }
}
