package one.empty3.feature.app;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;

import one.empty3.feature.app.replace.javax.imageio.ImageIO;

public class MyCameraActivity extends Activity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Button effectsButton;
    private File currentFile = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.imageView = (ImageView) this.findViewById(R.id.currentImageView);
        Button photoButton = (Button) this.findViewById(R.id.takePhotoButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        effectsButton = (Button) this.findViewById(R.id.effectsButton);
        effectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (currentFile != null) {
                        Intent intent = new Intent(Intent.ACTION_EDIT);
                        System.err.println("Cick on Effect button");
                        intent.setDataAndType(Uri.fromFile(currentFile),
                                "image/jpg");
                        intent.setClass(imageView.getContext(),/*EffectsFragment()
                                    .createPackageContext("com.android.example.cameraxbasic.fragments",*/
                                Class.forName("one.empty3.feature.app.ChooseEffects"));
                        intent.putExtra("data", currentFile.getAbsolutePath());
                        startActivity(intent);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fillGallery() {
        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/aaaa/");
        File[] allFiles = folder.listFiles();
        //Gallery gallery = findViewById(R.id.imageTakenPreviewGallery);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void writePhoto(Bitmap bitmap, String name) {

        Intent camera = new Intent(
                android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        int n = 1;
        //Folder is already created
        String dirName = "";
        boolean mkdirs = new File("./data").mkdirs();
        do {
            dirName = Environment.getExternalStorageDirectory().getPath()
                    + "/FeatureApp/data/" + name + "_" + n + ".png";
            n++;
        } while (new File(dirName).exists());

        Uri uriSavedImage = Uri.fromFile(new File(dirName));
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

        startActivityForResult(camera, 1);

        try {
            ImageIO.write(bitmap, "jpg", new File(dirName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
                writePhoto(photo, "MyImage");
            }
        }
    }
}