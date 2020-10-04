package com.chops.app.activity;

import com.chaos.view.PinView;
import com.chops.app.R;
import com.chops.app.Utility;
import com.chops.app.model.User;
import com.chops.app.retrofit.APIClient;
import com.chops.app.retrofit.GetResult;
import com.chops.app.utils.GetService;
import com.chops.app.utils.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shuhart.stepview.StepView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;


public class AuthenticationLogin extends AppCompatActivity implements GetResult.MyListener {

    private static String uniqueIdentifier = null;
    private static final String UNIQUE_ID = "UNIQUE_ID";
    private static final long ONE_HOUR_MILLI = 60*60*1000;

    private int currentStep = 0;
    LinearLayout layout1,layout2,layout3;
    StepView stepView;


    private static final String TAG = "FirebasePhoneNumAuth";

    TextView tvTimer;
    Button btnResend;
    CountDownTimer timer;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            Log.e("PhoneAuth", "onVerificationCompleted");
            GetService.showPrograss(AuthenticationLogin.this);

            signInWithPhoneAuthCredential(phoneAuthCredential);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Log.e("PhoneAuth", "onVerificationFailed"+e);
            Toast.makeText(AuthenticationLogin.this, "The phone number does not exist", Toast.LENGTH_SHORT).show();
        }
        @Override
        public void onCodeSent(String verificationId,
                               PhoneAuthProvider.ForceResendingToken token) {

            Log.e("PhoneAuth", "onCodeSent");
            // Save verification ID and resending token so we can use them later
            mVerificationId = verificationId;
            mResendToken = token;

            timer = new CountDownTimer(1*60000, 1000) {
                @Override
                public void onTick(final long millSecondsLeftToFinish) {
                    String time = String.valueOf( new DecimalFormat("00").format(millSecondsLeftToFinish / 1000));
                    tvTimer.setText("00:"+time);
                }

                @Override
                public void onFinish() {
                    tvTimer.setText("00:00");
                    btnResend.setEnabled(true);
                    btnResend.setTextColor(getResources().getColor(R.color.colorAccentPink));
                }
            };
            timer.start();

            // ...
        }
    };
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            if (currentStep < stepView.getStepCount() - 1) {
                                currentStep++;
                                stepView.go(currentStep, true);
                            } else {
                                stepView.done(true);
                            }
                            //layout1.setVisibility(View.GONE);
                            //layout2.setVisibility(View.GONE);
                            //layout3.setVisibility(View.VISIBLE);
                            // ...
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    login();
                                }
                            },2000);
                        } else {

                            GetService.close();
                            Toast.makeText(AuthenticationLogin.this,"Try again after sometime...",Toast.LENGTH_SHORT).show();
                            finish();
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private FirebaseAuth firebaseAuth;

    private String phoneNumber;
    private Button sendCodeButton;
    private Button verifyCodeButton;
    private Button signOutButton;
    private Button button3;

    private EditText phoneNum;
    private PinView verifyCodeET;
    private TextView phonenumberText;

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    String mobile;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication_login);

        mAuth = FirebaseAuth.getInstance();
        sessionManager = new SessionManager(AuthenticationLogin.this);

        layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout3 = (LinearLayout) findViewById(R.id.layout3);
        sendCodeButton = (Button) findViewById(R.id.submit1);
        verifyCodeButton = (Button) findViewById(R.id.submit2);
        button3 = (Button) findViewById(R.id.submit3);
        firebaseAuth = FirebaseAuth.getInstance();
        phoneNum = (EditText) findViewById(R.id.phonenumber);
        verifyCodeET = (PinView) findViewById(R.id.pinView);
        phonenumberText = (TextView) findViewById(R.id.phonenumberText);
        tvTimer = (TextView) findViewById(R.id.tvTimer);
        btnResend = (Button) findViewById(R.id.btnResend);
        btnResend.setEnabled(false);
        btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSendCode();
            }
        });

        Bundle bundle = getIntent().getExtras();
        mobile = bundle.getString("mobile");

        stepView = findViewById(R.id.step_view);
        stepView.setStepsNumber(2);
        stepView.go(0, true);
        layout1.setVisibility(View.GONE);
        sendCodeButton();
        sendCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCodeButton();
            }
        });

        verifyCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verificationCode = verifyCodeET.getText().toString();
                if(verificationCode.isEmpty()){
                    Toast.makeText(AuthenticationLogin.this,"Enter verification code",Toast.LENGTH_SHORT).show();
                }else if(verificationCode.trim().length()== 6){
                    if(mVerificationId!=null){
                        LayoutInflater inflater = getLayoutInflater();
                        GetService.showPrograss(AuthenticationLogin.this);

                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
                        signInWithPhoneAuthCredential(credential);
                    }else {
                        Toast.makeText(AuthenticationLogin.this,"Enter a correct pin",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(AuthenticationLogin.this,"Enter a correct pin",Toast.LENGTH_SHORT).show();
                }
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentStep < stepView.getStepCount() - 1) {
                    currentStep++;
                    stepView.go(currentStep, true);
                } else {
                    stepView.done(true);
                }
                GetService.showPrograss(AuthenticationLogin.this);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetService.close();
                        Intent intent = new Intent(AuthenticationLogin.this, SignInActivity.class);
                        intent.putExtra("mobile", phoneNum.getText().toString());
                        startActivity(intent);
                        finish();
                    }
                },3000);
            }
        });

    }

    private void sendCodeButton() {

        phoneNumber = mobile;
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNum.setError("Enter a Phone Number");
            phoneNum.requestFocus();
        } else if (phoneNumber.length() < 10||phoneNumber.length() > 10) {
            phoneNum.setError("Please enter a valid phone");
            phoneNum.requestFocus();
        } else if (phoneNumber.trim().length()==10){
            if (currentStep < stepView.getStepCount() - 1) {
                currentStep++;
                stepView.go(currentStep, true);
            }else{
                stepView.done(true);
            }
            phoneNumber = "+91"+phoneNumber;
            phonenumberText.setText(phoneNumber);
            layout1.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            verifyCodeET.setVisibility(View.VISIBLE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    AuthenticationLogin.this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
        }
    }

    public void reSendCode() {
        btnResend.setEnabled(false);
        btnResend.setTextColor(getResources().getColor(R.color.gray));
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                AuthenticationLogin.this,               // Activity (for callback binding)
                mCallbacks,        // OnVerificationStateChangedCallbacks
                mResendToken);             // ForceResendingToken from callbacks
        Toast.makeText(AuthenticationLogin.this,"Once again the otp is sent to your mobile number.",Toast.LENGTH_SHORT).show();
    }

    private void login() {
        GetService.showPrograss(AuthenticationLogin.this);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);

            JsonParser jsonParser = new JsonParser();
            Call<JsonObject> call = APIClient.getInterface().getSignInPhone((JsonObject) jsonParser.parse(jsonObject.toString()));
            GetResult getResult = new GetResult();
            getResult.setMyListener(this);
            getResult.callForLogin(call, "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callback(JsonObject result, String callNo) {
        GetService.close();
        if (callNo.equalsIgnoreCase("1") || result.toString().length() != 0) {
            Gson gson = new Gson();

            Utility.Companion.log("Login Response: " + gson.toJson(result));

            User response = gson.fromJson(result.toString(), User.class);
            GetService.ToastMessege(AuthenticationLogin.this, response.getResponseMsg());
            if (response.getResult().equalsIgnoreCase("true")) {
                sessionManager.setUserDetails("", response.getResultData());
                sessionManager.setBooleanData(SessionManager.USERLOGIN, true);
                startActivity(new Intent(AuthenticationLogin.this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();
            }else {
                Intent intent = new Intent(AuthenticationLogin.this, SignUpActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GetService.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GetService.close();
    }
}