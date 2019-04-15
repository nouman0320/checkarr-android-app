package work.checkarr.com.checkarr;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.takusemba.cropme.CropView;
import com.takusemba.cropme.OnCropListener;

public class CroppingActivity extends AppCompatActivity {

    CropView cropView;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropping);

        cropView = findViewById(R.id.cropView);

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            imageUri = extras.getString("Uri");
        }

        Toast.makeText(CroppingActivity.this,imageUri,Toast.LENGTH_LONG).show();
        /*Uri uri = Uri.parse(imageUri);
        cropView.setUri(uri);

        cropView.setMinimumHeight(100);
        cropView.setMinimumWidth(100);*/

        Uri uri = Uri.parse("android.resource://"+this.getPackageName()+"/drawable/neutral_image");
        cropView.setUri(uri);

        cropView.crop(new OnCropListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                // do something
            }

            @Override
            public void onFailure() {

            }
        });

    }   // onCreate Function ends here

}   // croppingActivity function ends here
