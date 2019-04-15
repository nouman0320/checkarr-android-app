package work.checkarr.com.checkarr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import work.checkarr.com.checkarr.API.RetroService;
import work.checkarr.com.checkarr.API.RetrofitClient;
import work.checkarr.com.checkarr.Classes.Authentication;

public class SignupScreen2 extends AppCompatActivity {

    EditText signUpPasswordEditText;
    Spinner gender;
    Button signUpButton;
    TextView FooterLinkSignIn;
    final RetroService service = RetrofitClient.getRetrofitInstance().create(RetroService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen2);

        gender = findViewById(R.id.gender);
        signUpPasswordEditText = findViewById(R.id.signUpPasswordEditText);
        signUpButton = findViewById(R.id.signUpButton);
        FooterLinkSignIn = findViewById(R.id.FooterLinkSignIn);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genderArray, R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedOption = (String) parent.getSelectedItem();
                if (parent.getSelectedView() != null) {
                    ((TextView) parent.getSelectedView()).setTextColor(getResources().getColor(R.color.redColor));
                }
                if (selectedOption.equals("Select gender") && parent.getSelectedView() != null) {
                    ((TextView) parent.getSelectedView()).setTextColor(getResources().getColor(R.color.redColor));
                    signUpButton.setEnabled(false);
                    signUpButton.setAlpha((float) 0.4);
                    signUpButton.setBackgroundResource(R.drawable.button);
                    signUpButton.setTextColor(getResources().getColor(R.color.redColor));
                    /*int TextColorInt = Color.parseColor("#2196F3");
                    signUpButton.setTextColor(TextColorInt);*/
                } else if (!selectedOption.equals("Select gender") && parent.getSelectedView() != null) {
                    ((TextView) parent.getSelectedView()).setTextColor(getResources().getColor(R.color.blackColor));
                    if (signUpPasswordEditText.getText().toString().length() >= 6 && signUpPasswordEditText.getText().toString().length() <= 25) {
                        signUpButton.setEnabled(true);
                        signUpButton.setAlpha(1);
                        signUpButton.setBackgroundResource(R.drawable.button);
                        int TextColorInt = Color.parseColor("#FFFFFF");
                        signUpButton.setTextColor(TextColorInt);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FooterLinkSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInScreen = new Intent(SignupScreen2.this, LoginScreen.class);
                signInScreen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(signInScreen);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButtonClick();
            }
        });

        signUpPasswordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    hideKeyboard();
                    signUpPasswordEditText.clearFocus();
                    gender.requestFocus();
                    gender.performClick();
                    handled = true;
                }
                return handled;
            }
        });

        signUpPasswordEditText.addTextChangedListener(watcher);

    }   // onCreate Function ends here

    private void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private final TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String item = (String) gender.getSelectedItem();
            if ((signUpPasswordEditText.getText().toString().length() < 6 || signUpPasswordEditText.getText().toString().length() > 25)) {
                signUpButton.setEnabled(false);
                signUpButton.setAlpha((float) 0.4);
                signUpButton.setBackgroundResource(R.drawable.button);
                signUpButton.setTextColor(getResources().getColor(R.color.redColor));
            } else {
                if (!item.equals("Select gender")) {
                    signUpButton.setEnabled(true);
                    signUpButton.setAlpha(1);
                    signUpButton.setBackgroundResource(R.drawable.button);
                    int TextColorInt = Color.parseColor("#FFFFFF");
                    signUpButton.setTextColor(TextColorInt);
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }


        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void signUpButtonClick() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fullName = extras.getString("Full Name");
            String email = extras.getString("Email");
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("Fullname", fullName);
            jsonObj.addProperty("Email", email);
            jsonObj.addProperty("Sex", gender.getSelectedItem().toString());
            jsonObj.addProperty("Password", signUpPasswordEditText.getText().toString());
            Call<ResponseBody> registerCall = service.createUser(jsonObj);
            registerCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.body() != null) {
                        ResponseBody responseBody = response.body();
                        String result = null;
                        try {
                            assert responseBody != null;
                            result = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        showToast(result);
                    } else if (response.errorBody() != null) {
                        ResponseBody errorBody = response.errorBody();
                        String errorMessage = null;
                        try {
                            errorMessage = errorBody.string();
                            Intent signUpScreen1 = new Intent();
                            signUpScreen1.putExtra("Email already exists",errorMessage);
                            setResult(RESULT_OK,signUpScreen1);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showToast("Null");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    showToast(t.getMessage());
                }
            });
        }

        /*Intent profilePictureSelection = new Intent(SignupScreen2.this, ProfilePictureSelection.class);
        startActivity(profilePictureSelection);*/
    }

    public void showToast(String message) {
        Toast.makeText(SignupScreen2.this, message, Toast.LENGTH_LONG).show();
    }

}   // SignupScreen2 activity ends here
