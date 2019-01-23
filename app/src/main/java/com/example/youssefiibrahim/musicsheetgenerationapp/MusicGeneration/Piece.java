package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

import java.util.ArrayList;
import java.util.Random;

public class Piece {
    private ArrayList<Measure> treble;
    private ArrayList<Measure> bass;
    private Pitch key;
    private int tempo;
    private String title;
    private int breakline;
    private boolean ornaments;

    Piece(String type) {
        this.treble = new ArrayList<Measure>();
        this.bass = new ArrayList<Measure>();
        this.key = Pitch.C;
        this.tempo = 160;
        this.breakline = 4;
        this.ornaments = false;
        generateMusic(type);
    }

    public void generateMusic(String type) {
        if (type.equals("SimpleUnison")) generateSimpleUnison();
        else if (type.equals("SimpleMelody")) generateSimpleMelody();
        else if (type.equals("Ternary")) generateTernary();
        else if (type.equals("OrnamentTernary")) generateOrnamentTernary();
        else if (type.equals("Rondo")) generateRondo();
        else if (type.equals("OrnamentRondo")) generateOrnamentRondo();
        else if (type.equals("Scales")) generateScales();
        else if (type.equals("PerpetualMotion")) generatePerpetualMotion();
        else if (type.equals("Random")) generateRandom();
    }

    private void generateTernary() {
        Random r = new Random();
        this.title = "Ternary";
        int scaleIndex = r.nextInt(12);
        key = Pitch.values()[scaleIndex];
        Motif motifA = new Motif(8, Duration.HALF, Scale.values()[scaleIndex], 0.5);
        Motif motifB = new Motif(8, Duration.QUARTER, Scale.values()[(scaleIndex + 7) % 12], 0.5);
        int[] scale = Scale.values()[scaleIndex].getScale();

        treble.addAll(motifA.getMeasure());
        treble.addAll(motifB.getMeasure());
        treble.addAll(motifA.getMeasure());

        ArrayList<Chord> chordsA = motifA.getChords();
        ArrayList<Chord> chordsB = motifB.getChords();

        for (Chord chord : chordsA)
            bass.add(chord.getChord());
        for (Chord chord : chordsB)
            bass.add(chord.getChord());
        for (Chord chord : chordsA)
            bass.add(chord.getChord());

        generateCadence(scale);
    }

    private void generateRondo() {
        Random r = new Random();
        this.title = "Rondo";
        int scaleIndex = r.nextInt(12);
        key = Pitch.values()[scaleIndex];
        Motif motifA = new Motif(4, Duration.HALF, Scale.values()[scaleIndex], 0.5);
        Motif motifB = new Motif(4, Duration.QUARTER, Scale.values()[scaleIndex], 0.5);
        Motif motifC = new Motif(8, Duration.HALF, Scale.values()[(scaleIndex + 5) % 12], 0.5);
        int[] scale = Scale.values()[scaleIndex].getScale();

        ArrayList<Measure> motifBMeasures = new ArrayList<Measure>();
        for (Measure m : motifB.getMeasure())
            motifBMeasures.add(m.transpose(-5));

        treble.addAll(motifA.getMeasure());
        treble.addAll(motifBMeasures);
        treble.addAll(motifA.getMeasure());
        treble.addAll(motifC.getMeasure());
        treble.addAll(motifA.getMeasure());
        treble.addAll(motifB.getMeasure());
        treble.addAll(motifA.getMeasure());

        ArrayList<Chord> chordsA = motifA.getChords();
        ArrayList<Chord> chordsB = motifB.getChords();
        ArrayList<Chord> chordsC = motifC.getChords();

        for (Chord chord : chordsA)
            bass.add(chord.getChord());
        for (Chord chord : chordsB) {
            chord.transpose(-5);
            bass.add(chord.getChord());
        }
        for (Chord chord : chordsA)
            bass.add(chord.getChord());
        for (Chord chord : chordsC)
            bass.add(chord.getChord());
        for (Chord chord : chordsA)
            bass.add(chord.getChord());
        for (Chord chord : chordsB) {
            chord.transpose(5);
            bass.add(chord.getChord());
        }
        for (Chord chord : chordsA)
            bass.add(chord.getChord());

        generateCadence(scale);
    }

    private void generateSimpleUnison() {
        Random r = new Random();
        this.title = "Simple Unison";
        this.breakline = 6;
        int scaleIndex = r.nextInt(12);
        key = Pitch.values()[scaleIndex];
        Motif motif = new Motif(12, Duration.HALF, Scale.values()[scaleIndex], 0.3);

        treble.addAll(motif.getMeasure());

        ArrayList<Measure> motifMeasures = new ArrayList<Measure>();
        for (Measure m : motif.getMeasure())
            motifMeasures.add(m.transpose(-24));

        bass.addAll(motifMeasures);
    }

    private void generateSimpleMelody() {
        Random r = new Random();
        this.title = "Simple Melody";
        this.breakline = 6;
        int scaleIndex = r.nextInt(12);
        key = Pitch.values()[scaleIndex];
        Motif motif = new Motif(12, Duration.WHOLE, Scale.values()[scaleIndex], 0.3);

        treble.addAll(motif.getMeasure());

        ArrayList<Chord> chords = motif.getChords();

        for (Chord chord : chords)
            bass.add(chord.getChord());

        generateCadence(Scale.values()[scaleIndex].getScale());
    }

    private void generatePerpetualMotion() {
        Random r = new Random();
        this.title = "Perpetual Motion";
        this.breakline = 3;
        int scaleIndex = r.nextInt(12);
        key = Pitch.values()[scaleIndex];
        Motif motifA = new Motif(8, Duration.QUARTER, Scale.values()[scaleIndex], 1);
        Motif motifB = new Motif(8, Duration.QUARTER, Scale.values()[(scaleIndex + 2) % 12], 1);
        int[] scale = Scale.values()[scaleIndex].getScale();

        treble.addAll(motifA.getMeasure());
        treble.addAll(motifB.getMeasure());
        treble.addAll(motifA.getMeasure());

        ArrayList<Chord> chordsB = motifB.getChords();

        ArrayList<Measure> motifAMeasures = new ArrayList<Measure>();
        for (Measure m : motifA.getMeasure())
            motifAMeasures.add(m.transpose(-24));
        bass.addAll(motifAMeasures);
        for (Chord chord : chordsB)
            bass.add(chord.getPerpetualChord());
        bass.addAll(motifAMeasures);
    }

    private void generateScales() {
        Random r = new Random();
        this.title = "Scales";
        this.breakline = 3;
        int scaleIndex = r.nextInt(12);
        key = Pitch.values()[scaleIndex];
        Motif motifA = new Motif(6, Duration.HALF, Scale.values()[scaleIndex], 1);
        Motif motifB = new Motif(6, Duration.HALF, Scale.values()[(scaleIndex + 5) % 12], 1);
        Motif motifC = new Motif(6, Duration.HALF, Scale.values()[(scaleIndex + 7) % 12], 1);
        Motif motifD = new Motif(6, Duration.HALF, Scale.values()[(scaleIndex + 7) % 12], 1);
        int[] scale = Scale.values()[scaleIndex].getScale();

        ArrayList<Chord> chordsA = motifA.getChords();
        ArrayList<Chord> chordsB = motifB.getChords();

        treble.addAll(motifA.getMeasure());
        for (Chord chord : chordsB) {
            Measure m = chord.getChord();
            m = m.transpose(24);
            treble.add(m);
        }
        treble.addAll(motifC.getMeasure());

        ArrayList<Measure> motifBMeasures = new ArrayList<Measure>();
        for (Measure m : motifB.getMeasure())
            motifBMeasures.add(m.transpose(-24));
        ArrayList<Measure> motifDMeasures = new ArrayList<Measure>();
        for (Measure m : motifD.getMeasure())
            motifDMeasures.add(m.transpose(-24));

        for (Chord chord : chordsA)
            bass.add(chord.getChord());
        bass.addAll(motifBMeasures);
        bass.addAll(motifDMeasures);
    }

    private void generateRandom() {
        Random r = new Random();
        this.title = "Random";
        this.breakline = 6;
        this.ornaments = true;
        int scaleIndex = 0;
        key = Pitch.values()[scaleIndex];

        Motif[] motifs = new Motif[36];
        for (int i = 0; i < 36; i++) {
            scaleIndex = r.nextInt(12);
            motifs[i] = new Motif(1, Duration.HALF, Scale.values()[scaleIndex], 0.2);
            if (i < 18)
                treble.addAll(motifs[i].getMeasure());
            else {
                ArrayList<Measure> motifMeasures = new ArrayList<Measure>();
                for (Measure m : motifs[i].getMeasure())
                    motifMeasures.add(m.transpose(-24));
                bass.addAll(motifMeasures);
            }
        }
    }

    private void generateOrnamentTernary() {
        generateTernary();
        this.ornaments = true;
        this.title = "Ornament Ternary";
    }

    private void generateOrnamentRondo() {
        generateRondo();
        this.ornaments = true;
        this.title = "Ornament Rondo";
    }

    private void generateCadence(int[] scale) {
        Measure cadence = new Measure(this.key);
        NoteGroup cadanceNG1 = new NoteGroup();
        NoteGroup cadanceNG2 = new NoteGroup();
        int[] cad1 = {3, 2, 4, 0};
        int[] cad2 = {4, 3, 5, 0};
        int pitch1 = scale[1];
        int pitch2 = scale[0];
        int octave1 = 5;
        int octave2 = scale[0] >= 10 ? 4 : 5;
        for (int i = 0; i < 4; i++) {
            cadanceNG1.addNote(new Note(Pitch.values()[pitch1], Duration.HALF, octave1));
            cadanceNG2.addNote(new Note(Pitch.values()[pitch2], Duration.HALF, octave2));
            pitch1 += cad1[i];
            pitch2 += cad2[i];
            if (pitch1 > 11) {
                pitch1 -= 12;
                octave1++;
            }
            if (pitch2 > 11) {
                pitch2 -= 12;
                octave2++;
            }
        }

        cadence.addNote(cadanceNG1);
        cadence.addNote(cadanceNG2);

        treble.add(cadence);

        NoteGroup cadanceNG3 = new NoteGroup();
        cadanceNG3.addNote(new Note(Pitch.values()[scale[4]], Duration.HALF, 3));
        cadanceNG3.addNote(new Note(Pitch.values()[scale[4]], Duration.HALF, 4));

        NoteGroup cadanceNG4 = new NoteGroup();
        cadanceNG4.addNote(new Note(Pitch.values()[scale[0]], Duration.HALF, 3));
        cadanceNG4.addNote(new Note(Pitch.values()[scale[0]], Duration.HALF, 4));

        Measure b = new Measure(this.key);

        b.addNote(cadanceNG3);
        b.addNote(cadanceNG4);

        bass.add(b);
    }

    public ArrayList<Measure> getTreble() {
        return this.treble;
    }

    public ArrayList<Measure> getBass() {
        return this.bass;
    }

    public int getTrebleLength() {
        int length = 0;
        for (Measure measure : this.treble)
            length += measure.getLength();
        return length;
    }

    public int getBassLength() {
        int length = 0;
        for (Measure measure : this.bass)
            length += measure.getLength();
        return length;
    }

    public ArrayList<Byte> trebleToByteArray() {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for (Measure measure : treble)
            bytes.addAll(measure.toByteArray());
        return bytes;
    }

    public ArrayList<Byte> bassToByteArray() {
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        for (Measure measure : bass)
            bytes.addAll(measure.toByteArray());
        return bytes;
    }

    public int getTempo() {
        return this.tempo;
    }

    public String getABCValue(ArrayList<Measure> line) {
        String str = "";
        int dur = 0;
        int measures = 0;
        for (int i = 0; i < line.size(); i++) {
            Measure m = line.get(i);
            for (int j = 0; j < m.getNotes().size(); j++) {
                NoteGroup ng = m.getNotes().get(j);
                str += "[";
                ArrayList<Note> pitches = ng.getPitches();
                for (Note pitch : pitches) {
                    if (this.ornaments == true) {
                        String[] orn = {".", "!turn!", "!lowermordent!", "!uppermordent!", "!pp!", "!p!", "!mp!", "!mf!", "!f!", "!ff!",
                                "!trill!", "!accent!", "!crescendo)!", "!crescendo(!"};
                        Random r = new Random();
                        if (r.nextDouble() < 0.05) {
                            str += orn[r.nextInt(14)];
                        }
                    }
                    str += pitch.getABCValue(this.key.getPitch(), m.getKey().getPitch());
                }
                str += "]";
                dur += ng.getDuration().getSixteenths();
                if (dur >= 16) {
                    dur -= 16;
                    str += "|";
                    measures++;
                    if (measures == this.breakline) {
                        measures = 0;
                        if (i != line.size() - 1 || j != m.getNotes().size() - 1)
                            str += "\n";
                    }
                }
            }
        }
        return str;
    }

    public String getABCValue() {
        String str = "X:1\nT:" + this.title + "\nM:C\nL:1\n%%staves {1 2}\nV:1\n[K:" + key.getKeyABCValue() + " clef=treble]";
        str += getABCValue(this.treble) + "]";
        str += "\nV:2\n[K:" + key.getKeyABCValue() + " clef=bass]";
        str += getABCValue(this.bass) + "]";
        return str;
    }
}
