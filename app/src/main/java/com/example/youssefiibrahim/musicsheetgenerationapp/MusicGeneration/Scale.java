package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;


public enum Scale {
    C(new int[]{0, 2, 4, 5, 7, 9, 11}),
    C_SHARP(new int[]{1, 3, 5, 6, 8, 10, 0}),
    D(new int[]{2, 4, 6, 7, 9, 11, 1}),
    D_SHARP(new int[]{3, 5, 7, 8, 10, 0, 2}),
    E(new int[]{4, 6, 8, 9, 11, 1, 3}),
    F(new int[]{5, 7, 9, 10, 0, 2, 4}),
    F_SHARP(new int[]{6, 8, 10, 11, 1, 3, 5}),
    G(new int[]{7, 9, 11, 0, 2, 4, 6}),
    G_SHARP(new int[]{8, 10, 0, 1, 3, 5, 7}),
    A(new int[]{9, 11, 1, 2, 4, 6, 8}),
    A_SHARP(new int[]{10, 0, 2, 3, 5, 7, 9}),
    B(new int[]{11, 1, 3, 4, 6, 8, 10});

    public final int[] scale;

    Scale(int[] _scale) {
        this.scale = _scale;
    }

    public int[] getScale() {
        return this.scale;
    }

    public String getABCValue() {
        String[] values = {"C", "C#", "D", "D#", "E", "E#", "F", "F#", "G", "G#", "A", "A#", "B"};
        return values[scale[0]];
    }
}
