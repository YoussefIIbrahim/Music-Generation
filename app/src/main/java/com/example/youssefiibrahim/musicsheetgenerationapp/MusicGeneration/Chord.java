package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

import java.util.Random;

public class Chord {
	private int degree;
	private Scale key;
	private Duration duration;
	private boolean seventh;
	private int inversion;

	Chord(int _degree, Scale _key, Duration _duration, boolean _seventh, int _inversion) {
		this.degree = _degree;
		this.key = _key;
		this.duration = _duration;
		this.seventh = _seventh;
		this.inversion = this.seventh ? _inversion % 4 : _inversion % 3;
	}

	public Measure getChord() {
		Measure measure = new Measure(Pitch.values()[this.key.getScale()[0]]);
		int[] scale = this.key.getScale();
		Random r = new Random();
		int type = r.nextInt(3);

		if(type == 0) {
			NoteGroup ng = new NoteGroup();
			ng.addNote(new Note(Pitch.values()[scale[this.degree]], this.duration, 4));
			ng.addNote(new Note(Pitch.values()[scale[(this.degree + 2) % 7]], this.duration, 4));
			ng.addNote(new Note(Pitch.values()[scale[(this.degree + 4) % 7]], this.duration, 4));
			measure.addNote(ng);
		}

		else if(type == 1) {
			measure.addNote(new Note(Pitch.values()[scale[this.degree]], this.duration.twice(), 4));
			NoteGroup ng = new NoteGroup();
			ng.addNote(new Note(Pitch.values()[scale[(this.degree + 2) % 7]], this.duration.twice(), 4));
			ng.addNote(new Note(Pitch.values()[scale[(this.degree + 4) % 7]], this.duration.twice(), 4));
			measure.addNote(ng);
		}

		else if(type == 2) {
			measure.addNote(new Note(Pitch.values()[scale[(this.degree) % 7]], this.duration.twice().twice(), 4));
			measure.addNote(new Note(Pitch.values()[scale[(this.degree + 2) % 7]], this.duration.twice().twice(), 4));
			measure.addNote(new Note(Pitch.values()[scale[(this.degree + 4) % 7]], this.duration.twice().twice(), 4));
			measure.addNote(new Note(Pitch.values()[scale[(this.degree + 2) % 7]], this.duration.twice().twice(), 4));
		}

		return measure;
	}

	public Measure getPerpetualChord() {
		Measure measure = new Measure(Pitch.values()[this.key.getScale()[0]]);
		int[] scale = this.key.getScale();

		measure.addNote(new Note(Pitch.values()[scale[(this.degree) % 7]], this.duration.twice().twice(), 4));
		measure.addNote(new Note(Pitch.values()[scale[(this.degree + 2) % 7]], this.duration.twice().twice(), 4));
		measure.addNote(new Note(Pitch.values()[scale[(this.degree + 4) % 7]], this.duration.twice().twice(), 4));
		measure.addNote(new Note(Pitch.values()[scale[(this.degree + 2) % 7]], this.duration.twice().twice(), 4));

		return measure;
	}

	public void transpose(int pitches) {
		int scaleIndex = 0;
		Scale[] scales = Scale.values();
		for(int i = 0; i < scales.length; i++)
			if(this.key == scales[i])
				scaleIndex = i;
		scaleIndex = realMod(scaleIndex + pitches, 12);
		this.key = scales[scaleIndex];
	}

	private int realMod(int num, int mod) {
		num %= mod;
		return num < 0 ? num + mod : num;
	}
}
