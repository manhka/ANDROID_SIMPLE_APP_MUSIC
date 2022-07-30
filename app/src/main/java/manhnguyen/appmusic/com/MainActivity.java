package manhnguyen.appmusic.com;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView nameSong, timeStart, timeEnd;
    ImageView btnBack, btnOver, btnPlay, btnSop,cd_audio;
    SeekBar timeSong;
    int position = 0;
    ArrayList<Song> listSong;
    MediaPlayer mediaPlayer = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        AddSong();
        StartApp();
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
        //play song
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);

                } else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.play2);
                }
                SetTimeTotal();
                TimeSongRunning();
                cd_audio.startAnimation(animation);
            }
        });
        //stop song
        btnSop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                StartApp();
            }
        });
        // next song
        btnOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if (position == listSong.size()) {
                    position = 0;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                StartApp();
                mediaPlayer.start();
                SetTimeTotal();
                TimeSongRunning();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if (position < 0) {
                    position = listSong.size() - 1;
                }

                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                StartApp();
                mediaPlayer.start();
                SetTimeTotal();
            }
        });
        SetTimeChange();
        TimeSongRunning();
    }

    private void SetTimeChange() {
        timeSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(timeSong.getProgress());
                if (!mediaPlayer.isPlaying()){
                    btnPlay.setImageResource(R.drawable.play2);
                    mediaPlayer.start();
                }
            }
        });
    }

    private void TimeSongRunning() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                timeStart.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                timeSong.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;
                        if (position == listSong.size()) {
                            position = 0;
                        }

                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        StartApp();
                        mediaPlayer.start();
                        SetTimeTotal();
                        TimeSongRunning();
                    }
                });
                handler.postDelayed(this, 500);

            }
        }, 100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        timeEnd.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        // set maxSeekbar==mediaPlayer.getDuration()
        timeSong.setMax(mediaPlayer.getDuration());

    }

    private void StartApp() {
        mediaPlayer = MediaPlayer.create(MainActivity.this, listSong.get(position).getFile());
        nameSong.setText(listSong.get(position).getTitle());
    }

    private void AddSong() {
        listSong = new ArrayList<>();
        listSong.add(new Song("Bông hoa đẹp nhất", R.raw.bonghoadepnha));
        listSong.add(new Song("Hoa nở không màu", R.raw.hoanokhongmau));
        listSong.add(new Song("Mãi mãi không phải anh", R.raw.maimaikhongphaianh));
        listSong.add(new Song("Một phút", R.raw.motphut));
        listSong.add(new Song("Phía sau một cô gái", R.raw.phiasaumotcogai));
    }

    private void Anhxa() {
        cd_audio=(ImageView)findViewById(R.id.imageView3) ;
        nameSong = (TextView) findViewById(R.id.songName);
        timeStart = (TextView) findViewById(R.id.timeStart);
        timeEnd = (TextView) findViewById(R.id.timeEnd);
        timeSong = (SeekBar) findViewById(R.id.songTime);
        btnBack = (ImageView) findViewById(R.id.back);
        btnOver = (ImageView) findViewById(R.id.over);
        btnPlay = (ImageView) findViewById(R.id.play);
        btnSop = (ImageView) findViewById(R.id.stop);
    }
}