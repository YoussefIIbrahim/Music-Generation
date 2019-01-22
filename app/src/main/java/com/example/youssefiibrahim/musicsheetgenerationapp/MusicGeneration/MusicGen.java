package com.example.youssefiibrahim.musicsheetgenerationapp.MusicGeneration;

import android.content.Context;

import com.example.youssefiibrahim.musicsheetgenerationapp.MainActivity;
import com.example.youssefiibrahim.musicsheetgenerationapp.MusicSheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class MusicGen {


	public void generateMusic(String category, Context context) {
        //Here goes the argument!
        Piece piece = new Piece(category);
        try {
            generateHTML(piece, context);
            //generateMIDI(piece);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    public static void generateMIDI(Piece piece, Context context) throws IOException {
        FileOutputStream file = new FileOutputStream("music.mid");
        byte header[] = {0x4d, 0x54, 0x68, 0x64, 0x00, 0x00, 0x00, 0x06, 0x00, 0x01, 0x00, 0x02, 0x01, (byte)0xe0};
        file.write(header);
        byte trackHeader[] = {0x4d, 0x54, 0x72, 0x6b};
        file.write(trackHeader);
        file.write(intToByteArray(piece.getTrebleLength()+25,4));
        byte time44[] = {0x00, (byte)0xff, 0x58, 0x04, 0x04, 0x02, 0x18, 0x08};
        file.write(time44);
        byte cMaj[] = {0x00, (byte)0xff, 0x59, 0x02, 0x00, 0x00};
        file.write(cMaj);
        byte tempo[] = {0x00, (byte)0xff, 0x51, 0x03};
        file.write(tempo);
        file.write(intToByteArray(1000000/piece.getTempo()*60,3));

        ArrayList<Byte> track1 = piece.trebleToByteArray();
        for(byte b : track1)
            file.write(b);

        byte endTrack[] = {0x01, (byte)0xff, 0x2f, 0x00};
        file.write(endTrack);

        file.write(trackHeader);
        file.write(intToByteArray(piece.getBassLength()+4, 4));

        ArrayList<Byte> track2 = piece.bassToByteArray();
        for(byte b : track2)
            file.write(b);

        file.write(endTrack);

        file.close();
    }

    public static byte[] intToByteArray(int num, int n) {
        byte arr[] = new byte[n];
        for(int i = 0; i < n; i++)
            arr[n-1-i] = (byte) (num >>> (i * 8));
        return arr;
    }
	
	public void generateHTML(Piece piece, Context context) throws IOException {


        String filePath = context.getExternalFilesDir(null).getPath().toString() + "/music.html";
        File f = new File(filePath);
        PrintWriter file = new PrintWriter(f);
        file.print("<HTML><HEAD><LINK HREF='https://firebasestorage.googleapis.com/v0/b/onlinequiz-5a0f2.appspot.com/o/abc_midi.css?alt=media&token=5de7705e-edb6-4aea-a937-3c69f970d410' REL='stylesheet'><STYLE>svg{display:block;margin:auto;}</STYLE></HEAD><BODY><BUTTON ID='btn' STYLE='BACKGROUND:#00A86B; WIDTH:100%; BORDER-RADIUS:10PX; FONT-SIZE:3EM; COLOR:WHITE;' ONCLICK='midi(this)'>PLAYBACK</BUTTON><DIV ID='m'></DIV>  <DIV ID='p'></DIV><PRE STYLE='display:none;'>");
        file.print(piece.getABCValue());
        file.print("</PRE><SCRIPT SRC='https://firebasestorage.googleapis.com/v0/b/onlinequiz-5a0f2.appspot.com/o/abc_midi.js?alt=media&token=fd1397dd-be18-4c5c-8f48-777f574cb660'></SCRIPT><SCRIPT>var abc=document.getElementsByTagName('pre')[0].innerHTML.trim();ABCJS.renderAbc('p', abc);function midi(div){div.innerHTML=ABCJS.renderMidi('m',abc);document.getElementById(\"btn\").style.display=\"none\";}</SCRIPT></BODY></HTML>");
        file.close();




//        PrintWriter file = new PrintWriter(f);
//        file.print("<HTML><HEAD><STYLE>svg{display:block;margin:auto;}</STYLE></HEAD><BODY><DIV ID='p'></DIV><PRE STYLE='display:none;'>");
//        file.print(piece.getABCValue());
//        file.print("</PRE><SCRIPT SRC='https://firebasestorage.googleapis.com/v0/b/onlinequiz-5a0f2.appspot.com/o/abc.js?alt=media&token=fdb29060-0e0e-40b7-bd80-e8a5b5eedf70'></SCRIPT><SCRIPT>var abc=document.getElementsByTagName('pre')[0].innerHTML.trim();ABCJS.renderAbc('p', abc);</SCRIPT></BODY></HTML>");
//        file.close();


	}
		
}
