package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

public enum Duration {
	WHOLE (16),
	DOTTED_HALF (12),
	HALF (8),
	DOTTED_QUARTER (6),
	QUARTER (4),
	DOTTED_EIGHTH (3),
	EIGHTH (2),
	SIXTEENTH (1);

	private final int duration;
	Duration(int _duration) {
		this.duration = _duration;
	}

	public int getDuration() {
		return (int)(480 * (double)this.duration/4);
	}

	public int getSixteenths() {
		return this.duration;
	}

	public Duration twice() {
		if(this.duration == 16)
			return Duration.HALF;
		else if(this.duration == 8)
			return Duration.QUARTER;
		else if(this.duration == 4)
			return Duration.EIGHTH;
		else if(this.duration == 2)
			return Duration.SIXTEENTH;
		else
			return this;
	}

	public String getABCValue() {
		String str = "";
		for(int i = this.duration; i < 16; i *= 2)
			str += "/";
		return str;
	}
}
