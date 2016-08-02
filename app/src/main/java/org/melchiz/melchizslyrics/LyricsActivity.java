package org.melchiz.melchizslyrics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LyricsActivity extends AppCompatActivity {
    Lyrics lyrics;

    TextView tvLyrics, tvArtist, tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        tvLyrics = (TextView) findViewById(R.id.tvLyrics);
        tvArtist = (TextView) findViewById(R.id.tvArtist);
        tvTitle = (TextView) findViewById(R.id.tvSongTitle);

        lyrics = new Lyrics(0);
        displayLyrics(lyrics);

        Button prev = ((Button) findViewById(R.id.bPrev));
        prev.setOnClickListener(new LyricsEvent());

        Button next = ((Button) findViewById(R.id.bNext));
        next.setOnClickListener(new LyricsEvent());

    }

    public void displayLyrics(Lyrics lyrics) {
        tvLyrics.setText(lyrics.getText());
        tvArtist.setText(lyrics.getArtistName());
        tvTitle.setText(lyrics.getTitle());
    }

    class Lyrics {
        private int current;

        public Lyrics(int index) {
            setText(index);

        }

        public void setText(int index) {
            current = index;
        }

        public void increment() {
            current++;
        }

        public void decrement() {
            current--;
        }

        public String getText() {
            return getResources().getStringArray(R.array.song_lyrics)[getCurrent()];
        }

        public String getArtistName() {
            return getResources().getStringArray(R.array.artist_names)[getCurrent()];
        }

        public String getTitle() {
            return getResources().getStringArray(R.array.song_titles)[getCurrent()];
        }

        public int count(){
            return getResources().getStringArray(R.array.song_titles).length;
        }
        public void reset(){
            if(getCurrent() < 0  || getCurrent() >= count())
                setText(0);
        }
        public int getCurrent() {
            return current;
        }
    }

    class LyricsEvent implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            try {
                lyrics.reset();
                switch (view.getId()) {
                    case R.id.bPrev:
                        lyrics.decrement();
                       displayLyrics(lyrics);
                        break;
                    case R.id.bNext:
                        lyrics.increment();
                        displayLyrics(lyrics);
                        break;
                }
            } catch (Exception e) {
                lyrics.reset();
                displayLyrics(lyrics);
            }finally{
                if(lyrics.getCurrent() > 0 && lyrics.getCurrent() < lyrics.count())
                    ((Button) findViewById(R.id.bPrev)).setEnabled(true);
                else
                    ((Button) findViewById(R.id.bPrev)).setEnabled(false);
                if(lyrics.getCurrent() >= lyrics.count())
                    ((Button) findViewById(R.id.bNext)).setEnabled(false);
            }
        }
    }
}
