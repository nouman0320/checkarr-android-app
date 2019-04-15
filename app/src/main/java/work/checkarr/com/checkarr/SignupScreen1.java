package work.checkarr.com.checkarr;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SignupScreen1 extends AppCompatActivity {

    EditText fullNameEditText;
    EditText signUpEmailEditText;
    Button nextButton;
    TextView FooterLinkSignIn;
    TextView signUpEmailWarning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen1);

        fullNameEditText = findViewById(R.id.fullNameEditText);
        signUpEmailEditText = findViewById(R.id.signUpEmailEditText);
        nextButton = findViewById(R.id.nextButton);
        FooterLinkSignIn = findViewById(R.id.FooterLinkSignIn);
        signUpEmailWarning = findViewById(R.id.signUpEmailWarning);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextButtonClick();
            }
        });

        FooterLinkSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInScreen = new Intent(SignupScreen1.this, LoginScreen.class);
                signInScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInScreen);
            }
        });

        signUpEmailEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    nextButtonClick();
                    handled = true;
                }
                return handled;
            }
        });

        fullNameEditText.addTextChangedListener(watcher);
        signUpEmailEditText.addTextChangedListener(watcher);

    }   // onCreate Function ends here

    private final TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (fullNameEditText.getText().toString().length() == 0 || signUpEmailEditText.getText().toString().length() == 0) {
                nextButton.setEnabled(false);
                nextButton.setAlpha((float) 0.4);
                nextButton.setBackgroundResource(R.drawable.button);
                nextButton.setTextColor(getResources().getColor(R.color.redColor));
            } else {
                nextButton.setEnabled(true);
                nextButton.setAlpha(1);
                nextButton.setBackgroundResource(R.drawable.button);
                int TextColorInt = Color.parseColor("#FFFFFF");
                nextButton.setTextColor(TextColorInt);

            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }


        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void nextButtonClick() {
        String emailAddress = signUpEmailEditText.getText().toString();
        if (!isValidEmail(emailAddress)) {
            signUpEmailWarning.setText(R.string.please_enter_a_valid_email);
            signUpEmailWarning.setVisibility(View.VISIBLE);
            ConstraintLayout.LayoutParams np = (ConstraintLayout.LayoutParams) nextButton.getLayoutParams();
            np.setMargins(0, convertDpToPixels(40f), 0, 0);
            nextButton.setLayoutParams(np);
        } else if (isValidEmail(emailAddress)) {
            signUpEmailWarning.setVisibility(View.INVISIBLE);
            ConstraintLayout.LayoutParams np = (ConstraintLayout.LayoutParams) nextButton.getLayoutParams();
            np.setMargins(0, convertDpToPixels(24f), 0, 0);
            nextButton.setLayoutParams(np);
            Intent signUpScreen2 = new Intent(SignupScreen1.this, SignupScreen2.class);
            signUpScreen2.putExtra("Full Name", fullNameEditText.getText().toString());
            signUpScreen2.putExtra("Email", signUpEmailEditText.getText().toString());
            startActivityForResult(signUpScreen2,1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                String errorMessage = data.getStringExtra("Email already exists");
                signUpEmailWarning.setText(errorMessage);
                signUpEmailWarning.setVisibility(View.VISIBLE);
                ConstraintLayout.LayoutParams np = (ConstraintLayout.LayoutParams) nextButton.getLayoutParams();
                np.setMargins(0, convertDpToPixels(40f), 0, 0);
                nextButton.setLayoutParams(np);
            }
        }
    }

    public boolean isValidEmail(String email) {
        CharSequence target = email;
        if (target == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void ShowToast(String message) {
        Toast.makeText(SignupScreen1.this, message, Toast.LENGTH_LONG).show();
    }

    public int convertDpToPixels(float dip) {
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
        return (int) px;
    }
}   // SignupScreen1 activity ends here
