package com.example.photo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.dsphotoeditor.sdk.activity.DsPhotoEditorActivity;
import com.dsphotoeditor.sdk.utils.DsPhotoEditorConstants;

public class MainActivity extends AppCompatActivity {
    private Button gallery;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Binding
        gallery = findViewById(R.id.gallery_button);
        image = findViewById(R.id.image);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            pickImage();
//        } else {
//            Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            switch (requestCode) {
                case 100:
                    Intent intent = new Intent(MainActivity.this, DsPhotoEditorActivity.class);
                    intent.setData(uri);
                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_OUTPUT_DIRECTORY,
                            "Images");
                    intent.putExtra(DsPhotoEditorConstants.DS_TOOL_BAR_BACKGROUND_COLOR,
                            Color.parseColor("#75668A"));
                    intent.putExtra(DsPhotoEditorConstants.DS_MAIN_BACKGROUND_COLOR,
                            Color.parseColor("#242227"));
                    intent.putExtra(DsPhotoEditorConstants.DS_PHOTO_EDITOR_TOOLS_TO_HIDE, new int[]{
                            DsPhotoEditorActivity.TOOL_WARMTH, DsPhotoEditorActivity.TOOL_PIXELATE,
                            DsPhotoEditorActivity.TOOL_CONTRAST, DsPhotoEditorActivity.TOOL_EXPOSURE,
                            DsPhotoEditorActivity.TOOL_SATURATION, DsPhotoEditorActivity.TOOL_SHARPNESS,
                            DsPhotoEditorActivity.TOOL_ORIENTATION,
                            DsPhotoEditorActivity.TOOL_CONTRAST, DsPhotoEditorActivity.TOOL_ROUND,
                            DsPhotoEditorActivity.TOOL_FRAME
                    });
                    startActivityForResult(intent, 101);
                    break;
                case 101:
                    image.setImageURI(uri);
                    Toast.makeText(getApplicationContext(), "Photo saved", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}