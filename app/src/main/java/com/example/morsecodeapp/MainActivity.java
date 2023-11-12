package com.example.morsecodeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    EditText et_input;

    Button b_submit, b_sound;

    TextView tv_result_title, tv_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_input = findViewById(R.id.et_input);

        b_submit = findViewById(R.id.b_submit);
        b_sound = findViewById(R.id.b_sound);

        tv_result_title = findViewById(R.id.tv_result_title);
        tv_result = findViewById(R.id.tv_result);

        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                calculate(et_input.getText().toString());

            }
        });

        b_sound.setVisibility(View.GONE);

    }

    public void calculate(String input) {

        String result;

        if (isMorse(input)) {
            result = morseToEnglish(input);
            tv_result_title.setText("Morse To English:");
            tv_result.setText(result);
        }
        else {
            result = englishToMorse(input);
            tv_result.setText(result);
            tv_result_title.setText("English To Morse:");

            b_sound.setVisibility(View.VISIBLE);
            b_sound.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playMorse(result);
                }
            });

        }

    }

    public boolean isMorse(String input) {

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isAlphabetic(ch))
                return false;
        }
        return true;
    }


    public static String morseToEnglish(String morseCode) {

        // store the all the alphabet in an array
        char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '1', '2', '3', '4',
                '5', '6', '7', '8', '9', '0' };
        // Morse code by indexing
        String[] code
                = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..", "|" };

        String[] array = morseCode.split(" ");
        StringBuilder english = new StringBuilder();
        // Morse code to English
        for (String s : array) {
            for (int j = 0; j < code.length; j++) {
                if (s.compareTo(code[j]) == 0) {
                    english.append((char) (j + 'a')).append(" ");
                    break;
                }
            }
        }

        return english.toString();
    }

    public static String englishToMorse(String englishLang) {

        // store the all the alphabet in an array
        char[] letter = { 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l',
                'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x',
                'y', 'z', '1', '2', '3', '4',
                '5', '6', '7', '8', '9', '0' };
        // Morse code by indexing
        String[] code
                = { ".-",   "-...", "-.-.", "-..",  ".",
                "..-.", "--.",  "....", "..",   ".---",
                "-.-",  ".-..", "--",   "-.",   "---",
                ".--.", "--.-", ".-.",  "...",  "-",
                "..-",  "...-", ".--",  "-..-", "-.--",
                "--..", "|" };


        StringBuilder final_morse = new StringBuilder();
        String[] words = englishLang.split(" ");

        for (int i = 0; i < words.length; i++) { // Each Word
            String word = words[i];
            StringBuilder morse = new StringBuilder();

            for (int j = 0; j < word.length(); j++) { // Each Character
                for (int k = 0; k < letter.length; k++) {
                    if (Character.toLowerCase(word.charAt(j)) == letter[k]) {
                        morse.append(code[k]).append(" ");
                        break;
                    }
                }
            }

            final_morse.append(morse);
            if (i != words.length-1)
                final_morse.append("/ ");
        }

        return final_morse.toString();
    }

    public void playMorse(String morse) {

        for (int i = 0; i < morse.length(); i++) {

            playSound(morse.charAt(i));
            for (int j = 0; j < 100000000; j++){
                ;
            }

        }

    }

    public void playSound(char ch) {

        MediaPlayer player, pause_player;
        pause_player = MediaPlayer.create(this,R.raw.none);
        switch (ch) {

            case '.':
                player = MediaPlayer.create(this,R.raw.dot);
                break;

            case '-':
                player = MediaPlayer.create(this,R.raw.dash);
                break;

            case '/':
                player = MediaPlayer.create(this,R.raw.pause);
                break;

            default:
                player = MediaPlayer.create(this,R.raw.none);
                break;

        }

        player.start();

        pause_player.start();

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                player.release();
            }
        });

        pause_player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pause_player.release();
            }
        });

    }


}