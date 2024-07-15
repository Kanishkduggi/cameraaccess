package com.example.androidiapplication;

import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button recordVideoBtn;
    private VideoView videoView;
    private File outputFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordVideoBtn = findViewById(R.id.idBtnRecordVideo);
        videoView = findViewById(R.id.videoView);

        recordVideoBtn.setOnClickListener(v -> {
            startRecording();
        });
    }

    private void startRecording() {
        try {
            // Create MediaRecorder instance
            MediaRecorder mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

            // Create output file
            outputFile = createOutputFile();
            mediaRecorder.setOutputFile(outputFile.getAbsolutePath());

            // Set other configurations (e.g., video size, frame rate)
            mediaRecorder.setVideoSize(640, 480);
            mediaRecorder.setVideoFrameRate(30);

            mediaRecorder.prepare();
            mediaRecorder.start();

            // ... (recording logic)

            mediaRecorder.stop();
            mediaRecorder.release();

            // Play recorded video
            playVideo(outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createOutputFile() {
        // Create a directory to store videos
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "MyVideos");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("App", "failed to create directory");
            return null;
        }

        // Create a file name with timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile = new File(mediaStorageDir, "VID_" + timeStamp + ".mp4");
        return mediaFile;
    }

    private void playVideo(String filePath) {
        Uri videoUri = Uri.parse(filePath);
        videoView.setVideoURI(videoUri);
        videoView.start();
    }
}
