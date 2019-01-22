package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;


public enum Pitch {
	C (0),
	C_SHARP (1),
	D (2),
	D_SHARP (3),
	E (4),
	F (5),
	F_SHARP (6),
	G (7),
	G_SHARP (8),
	A (9),
	A_SHARP (10),
	B (11);

	private final int pitch;
	Pitch(int _pitch) {
		this.pitch = _pitch;
	}

	public int getPitch() {
		return this.pitch;
	}

	public String getABCValue(int key, int realKey) {
		if(key != realKey) {
			int[] fifths = {1, 8, 3, 10, 5, 0, 7, 2, 9, 4, 11, 6};
			String[] names = {"C", "D", "E", "F", "G", "A", "B"};
			int[] division1 = {0, 2, 4, 0, 1, 3, 5};
			int[] division2 = {6, 8, 10, 5, 7, 9, 11};
			int keyIndex = findIndex(fifths, key);
			int realKeyIndex = findIndex(fifths, realKey);
			int pitchIndex = 0;
			String str = "";
			if(pitch == 0) pitchIndex = 0;
			else if(pitch == 1) pitchIndex = (realKeyIndex >= 7) ? 0 : 1;
			else if(pitch == 2) pitchIndex = 1;
			else if(pitch == 3) pitchIndex = (realKeyIndex >= 9) ? 1 : 2;
			else if(pitch == 4) pitchIndex = 2;
			else if(pitch == 5) pitchIndex = (realKeyIndex >= 11) ? 2 : 3;
			else if(pitch == 6) pitchIndex = (realKeyIndex >= 6) ? 3 : 4;
			else if(pitch == 7) pitchIndex = 4;
			else if(pitch == 8) pitchIndex = (realKeyIndex >= 8) ? 4 : 5;
			else if(pitch == 9) pitchIndex = 5;
			else if(pitch == 10) pitchIndex = (realKeyIndex >= 10) ? 5 : 6;
			else if(pitch == 11) pitchIndex = 6;

			if(realKeyIndex < division1[pitchIndex] && keyIndex >= division1[pitchIndex]) str = "_";
			else if(realKeyIndex > division2[pitchIndex] && keyIndex <= division2[pitchIndex]) str = "^";
			else if((realKeyIndex >= division1[pitchIndex] && realKeyIndex <= division2[pitchIndex]) &&
					(keyIndex < division1[pitchIndex] || keyIndex > division2[pitchIndex])) str = "=";
			str += names[pitchIndex];
			return str;
		}
		String[] valuesSharp = {"C", "C", "D", "D", "E", "F", "F", "G", "G", "A", "A", "B"};
		String[] valuesSharp6 = {"C", "C", "D", "D", "E", "E", "F", "G", "G", "A", "A", "B"};
		String[] valuesFlat = {"C", "D", "D", "E", "E", "F", "G", "G", "A", "A", "B", "B"};
		if(key == 0 || key == 2 || key == 4 || key == 7 || key == 9 || key == 11) return valuesSharp[pitch];
		if(key == 6) return valuesSharp6[pitch];
		return valuesFlat[pitch];
	}

	public String getKeyABCValue() {
		String[] values = {"C", "Db", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
		return values[pitch];
	}

	private int findIndex(int[] arr, int val) {
		for(int i = 0; i < arr.length; i++)
			if(arr[i] == val)
				return i;
		return -1;
	}
}
