package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

import java.util.ArrayList;

public class Measure {
    private ArrayList<NoteGroup> notes;
    private Pitch key;

    Measure(Pitch key) {
        this.notes = new ArrayList<NoteGroup>();
        this.key = key;
    }

    public void addNote(Note note) {
        this.notes.add(new NoteGroup(note));
    }

    public void addNote(NoteGroup notes) {
        this.notes.add(notes);
    }

    public Measure transpose(int pitches) {
        int newPitch = this.key.getPitch() + pitches;
        while (newPitch > 11)
            newPitch -= 12;
        while (newPitch < 0)
            newPitch += 12;
        Measure m = new Measure(Pitch.values()[newPitch]);
        for (NoteGroup note : this.notes)
            m.addNote(note.transpose(pitches));
        return m;
    }

    public ArrayList<NoteGroup> getNotes() {
        return notes;
    }

    public int getLength() {
        int length = 0;
        for (NoteGroup note : this.notes) {
            length += note.getPitches().size() * 8;
            if (note.getDuration() != Duration.SIXTEENTH)
                length += 1;
        }
        return length;
    }

    public ArrayList<Byte> toByteArray() {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for (NoteGroup note : this.notes) {
            ArrayList<Note> pitches = note.getPitches();
            for (Note pitch : pitches) {
                bytes.add((byte) 0x00);
                bytes.add((byte) 0x90);
                bytes.add((byte) pitch.getValue());
                bytes.add((byte) 0x50);
            }
            byte deltaTime[] = intToByteArray(note.getDuration());
            for (byte b : deltaTime)
                bytes.add(b);
            for (Note pitch : pitches) {
                if (deltaTime == null)
                    bytes.add((byte) 0x00);
                else
                    deltaTime = null;
                bytes.add((byte) 0x80);
                bytes.add((byte) pitch.getValue());
                bytes.add((byte) 0x00);
            }
        }
        return bytes;
    }

    public String getABCValue(int mainKey) {
        String str = "";
        int dur = 0;
        int measures = 0;
        for (NoteGroup ng : this.notes) {
            str += "[";
            ArrayList<Note> pitches = ng.getPitches();
            for (Note pitch : pitches)
                //str += pitch.getABCValue(mainKey, key.getPitch());
                str += "]";
            dur += ng.getDuration().getSixteenths();
            if (dur >= 16) {
                dur -= 16;
                str += "|";
                measures++;
                //bw.bool = true;
                if (measures == 3) {
                    measures = 0;
                    str += "\n";
                }
            }
        }
        return str;
    }

    public byte[] intToByteArray(Duration d) {
        switch (d) {
            case SIXTEENTH:
                return new byte[]{0x78};
            case EIGHTH:
                return new byte[]{(byte) 0x81, 0x70};
            case DOTTED_EIGHTH:
                return new byte[]{(byte) 0x82, 0x68};
            case QUARTER:
                return new byte[]{(byte) 0x83, 0x60};
            case DOTTED_QUARTER:
                return new byte[]{(byte) 0x85, 0x50};
            case HALF:
                return new byte[]{(byte) 0x87, 0x40};
            case DOTTED_HALF:
                return new byte[]{(byte) 0x8B, 0x20};
            case WHOLE:
                return new byte[]{(byte) 0x8F, 0x00};
            default:
                return new byte[]{0x00};
        }
    }

    public Pitch getKey() {
        return this.key;
    }
}
