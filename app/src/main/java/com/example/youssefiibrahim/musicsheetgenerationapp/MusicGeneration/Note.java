package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

public class Note {
	private Pitch pitch;
	private Duration duration;
	private int octave;

	Note(Pitch _pitch, Duration _duration, int _octave) {
		this.pitch = _pitch;
		this.duration = _duration;
		this.octave = _octave;
	}

	public Note transpose(int pitches) {
		int newPitch = this.pitch.getPitch() + pitches;
		int newOctave = this.octave;
		while(newPitch > 11) {
			newPitch -= 12;
			newOctave++;
		}
		while(newPitch < 0) {
			newPitch += 12;
			newOctave--;
		}
		return new Note(Pitch.values()[newPitch], this.duration, newOctave);
	}

	public Pitch getPitch() {
		return this.pitch;
	}

	public Duration getDuration() {
		return this.duration;
	}

	public int getOctave() {
		return this.octave;
	}

	public int getValue() {
		return this.octave * 12 + this.pitch.getPitch();
	}

	public String getABCValue(int mainKey, int key) {
		String str = pitch.getABCValue(mainKey, key);
		for(int i = getValue(); i < 60 || i > 71;) {
			if(i < 60) {
				str += ",";
				i += 12;
			}
			else if(i > 71) {
				str += "'";
				i -= 12;
			}
		}
		str += duration.getABCValue();
		return str;
	}
}
