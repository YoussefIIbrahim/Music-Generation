package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;
import java.util.ArrayList;
import java.util.Random;

public class Motif {
	private ArrayList<Measure> measures;
	private ArrayList<Chord> chords;
	private Duration harmonicRhythm;
	private Scale key;

	Motif(int measures, Duration harmonicRhythm, Scale key, double chance) {
		this.measures = new ArrayList<Measure>();
		this.chords = new ArrayList<Chord>();
		this.harmonicRhythm = harmonicRhythm;
		this.key = key;
		generate(measures, harmonicRhythm, key, chance);
	}

	public ArrayList<Measure> getMeasure() {
		return measures;
	}

	public ArrayList<Chord> getChords() {
		return chords;
	}

	public Duration getHarmonicRhythm() {
		return harmonicRhythm;
	}

	private void generate(int measures, Duration harmonicRhythm, Scale key, double chance) {
		ArrayList<Duration> rhythm = new ArrayList<Duration>();
		Measure t = new Measure(Pitch.values()[this.key.getScale()[0]]);
		for(int i = 0; i < measures * 2; i++)
			rhythm.add(Duration.HALF);
		Random r = new Random();
		for(int i = 0; i < rhythm.size(); i++) {
			if(r.nextFloat() <= chance) {
				if(rhythm.get(i) == Duration.HALF) {
					rhythm.add(i+1, Duration.QUARTER);
					rhythm.add(i+1, Duration.QUARTER);
					rhythm.remove(i);
				}
				else if(rhythm.get(i) == Duration.QUARTER) {
					rhythm.add(i+1, Duration.EIGHTH);
					rhythm.add(i+1, Duration.EIGHTH);
					rhythm.remove(i);
				}
				else if(rhythm.get(i) == Duration.EIGHTH) {
					rhythm.add(i+1, Duration.SIXTEENTH);
					rhythm.add(i+1, Duration.SIXTEENTH);
					rhythm.remove(i);
				}
				else if(rhythm.get(i) == Duration.SIXTEENTH)
					i++;
				i--;
			}
		}
		int[] scale = key.getScale();
		int pitch = 0;
		int octave = 6;
		for(Duration duration : rhythm) {
			t.addNote(new Note(Pitch.values()[scale[pitch]], duration, octave));
			int var = 2;
			if(measures == 6)
				var = 1;
			int change = r.nextInt(1 + 2*var) - var;
			if(chance == 1.0)
				while(change == 0)
					change = r.nextInt(1 + 2*var) - var;
			if(change < 0 && scale[pitch] < scale[realMod(pitch + change, 7)])
				octave--;
			if(change > 0 && scale[pitch] > scale[realMod(pitch + change, 7)])
				octave++;
			if(octave < 5 || octave > 7)
				octave = 6;
			pitch = realMod(pitch + change, 7);
		}
		this.measures.add(t);

		int sixteenths = 0;
		ArrayList<NoteGroup> trebleNotes = t.getNotes();
		for(NoteGroup note : trebleNotes) {
			sixteenths += note.getDuration().getSixteenths();
			while(sixteenths > 0) {
				this.chords.add(new Chord(findIndex(scale, note.getPitches().get(0).getPitch().getPitch()), key, harmonicRhythm, false, 0));
				sixteenths -= harmonicRhythm.getSixteenths();
			}
		}
	}

	private int realMod(int num, int mod) {
		num %= mod;
		return num < 0 ? num + mod : num;
	}

	private int findIndex(int[] arr, int val) {
		for(int i = 0; i < arr.length; i++)
			if(arr[i] == val)
				return i;
		return -1;
	}
}
