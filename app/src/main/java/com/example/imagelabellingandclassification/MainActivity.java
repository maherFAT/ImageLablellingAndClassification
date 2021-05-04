package com.example.imagelabellingandclassification;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private ImageView imageView;
    private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load an Image to the ImageView
        imageView = (ImageView) findViewById(R.id.imageLabel);
        Bitmap bitmap = getBitmapFromAsset("image3.jfif");
        imageView.setImageBitmap(bitmap);

        // add BTN and Listener
        button = findViewById(R.id.btnPressed);

        // Text output
        text = findViewById(R.id.txtOutput);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                text.setText(null);
                    ImageLabeler label = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS);
                    // rotationDegrees is 0;

                    InputImage inputImage = InputImage.fromBitmap(bitmap, 0);
                    Task<List<ImageLabel>> listTask = label.process(inputImage);
                listTask.addOnSuccessListener(labels -> {
                    for (ImageLabel lab: labels) {
                        String textLab = lab.getText();
                        float confidence = lab.getConfidence();
                        System.out.println("maher" +textLab +":" + confidence +"\n");
                        text.append(textLab +":" + confidence +"\n");
                    }
                }).addOnFailureListener(e-> {
                    e.printStackTrace();
                });

                }
        });

    }


    private Bitmap getBitmapFromAsset(String strName)
    {
        AssetManager assetManager = getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(strName);

        } catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(istr);
        return bitmap;
    }
}