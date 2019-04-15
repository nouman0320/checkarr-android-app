package work.checkarr.com.checkarr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.github.abdularis.civ.CircleImageView;

import java.io.IOException;

public class ProfilePictureSelection extends AppCompatActivity {

    ImageView noImage;
//    CircleImageView noImage;
    Button uploadPhoto;
    Button removePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_selection);

        noImage = findViewById(R.id.noImage);
        uploadPhoto = findViewById(R.id.uploadPhoto);
        removePhoto = findViewById(R.id.removePhoto);

        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });

        removePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noImage.setImageDrawable(getResources().getDrawable(R.drawable.neutral_image));
            }
        });

    }   // onCreate Function ends here

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Uri uri = data.getData();
            /*Intent intent = new Intent(ProfilePictureSelection.this,CroppingActivity.class);
            startActivity(intent);*/
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                noImage.setImageBitmap(bitmap);
                Intent intent = new Intent(ProfilePictureSelection.this,CroppingActivity.class);
                intent.putExtra("Uri",uri.toString());
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
