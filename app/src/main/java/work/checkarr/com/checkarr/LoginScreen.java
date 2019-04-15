package work.checkarr.com.checkarr;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.sql.Array;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import work.checkarr.com.checkarr.API.RetroService;
import work.checkarr.com.checkarr.API.RetrofitClient;
import work.checkarr.com.checkarr.Classes.Authentication;

public class LoginScreen extends AppCompatActivity {

    ConstraintLayout parentLayout;
    AutoCompleteTextView emailEditText;
//    EditText emailEditText;
    EditText passwordEditText;
    Button signInButton;
    TextView helpText2;
    TextView FooterLinkSignUp;
    TextView emailWarning;
    Authentication auth;
    final RetroService service = RetrofitClient.getRetrofitInstance().create(RetroService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        parentLayout = findViewById(R.id.parentLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signInButton = findViewById(R.id.signInButton);
        helpText2 = findViewById(R.id.helpText2);
        FooterLinkSignUp = findViewById(R.id.FooterLinkSignUp);
        emailWarning = findViewById(R.id.emailWarning);



        FooterLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpScreen = new Intent(LoginScreen.this, SignupScreen1.class);
                startActivity(signUpScreen);
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInButtonClick();
            }
        });

        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    signInButtonClick();
                    handled = true;
                }
                return handled;
            }
        });

        emailEditText.addTextChangedListener(watcher);
        passwordEditText.addTextChangedListener(watcher);
    }   // onCreate Function ends here

    private final TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (emailEditText.getText().toString().length() == 0 || (passwordEditText.getText().toString().length() < 6 || passwordEditText.getText().toString().length() > 25)) {
                signInButton.setEnabled(false);
                signInButton.setAlpha((float) 0.4);
                signInButton.setBackgroundResource(R.drawable.button);
                signInButton.setTextColor(getResources().getColor(R.color.redColor));
            } else {
                signInButton.setEnabled(true);
                signInButton.setAlpha(1);
                signInButton.setBackgroundResource(R.drawable.button);
                int TextColorInt = Color.parseColor("#FFFFFF");
                signInButton.setTextColor(TextColorInt);

            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }


        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public boolean isValidEmail(String email) {
        CharSequence target = email;
        if (target == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }


    /*public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{3,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }*/

    /*public static int convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int dp = (int) (px / (metrics.densityDpi / 160f));
        return dp;
    }*/

    public int convertDpToPixels(float dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }

    public void signInButtonClick() {

        String emailAddress = emailEditText.getText().toString();
        if (!isValidEmail(emailAddress)) {
            emailWarning.setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams np = (ConstraintLayout.LayoutParams) passwordEditText.getLayoutParams();
            np.setMargins(0, convertDpToPixels(24f), 0, 0);
            passwordEditText.setLayoutParams(np);
        } else if (isValidEmail(emailAddress)) {
            emailWarning.setVisibility(View.INVISIBLE);
            ConstraintLayout.LayoutParams np = (ConstraintLayout.LayoutParams) passwordEditText.getLayoutParams();
            np.setMargins(0, convertDpToPixels(8f), 0, 0);
            passwordEditText.setLayoutParams(np);
            Intent profilePictureSelection = new Intent(LoginScreen.this, ProfilePictureSelection.class);
            startActivity(profilePictureSelection);
//            AuthenticateUser();
        }

    }

    public void AuthenticateUser() {
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("Email", emailEditText.getText().toString());
        jsonObj.addProperty("Password", passwordEditText.getText().toString());
        Call<Authentication> postCall = service.getVectors(jsonObj);
        postCall.enqueue(new Callback<Authentication>() {
            @Override
            public void onResponse(@NonNull Call<Authentication> call, @NonNull Response<Authentication> response) {
                if (response.body() != null) {
                    Boolean issued;
                    String token, refreshToken, activationStatus, userEmail, dpUrl;
                    Integer userID;
                    issued = response.body().getIssued();
                    token = response.body().getToken();
                    refreshToken = response.body().getRefreshToken();
                    activationStatus = response.body().getActivationStatus();
                    userID = response.body().getUserID();
                    userEmail = response.body().getUserEmail();
                    dpUrl = response.body().getDpUrl();
                    auth = new Authentication(issued, token, refreshToken, activationStatus, userID, userEmail, dpUrl);
//                    showToast("User found : " + auth.getUserID().toString());
                    Intent profilePictureSelection = new Intent(LoginScreen.this, ProfilePictureSelection.class);
                    startActivity(profilePictureSelection);
                } else if (response.raw().code()==401){
                    showToast(String.valueOf(response.raw().code()) + "User not found!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Authentication> call, @NonNull Throwable t) {
                showToast(t.getMessage());
            }
        });
    }

    public void showToast(String message) {
        Toast.makeText(LoginScreen.this, message, Toast.LENGTH_LONG).show();
    }

}
