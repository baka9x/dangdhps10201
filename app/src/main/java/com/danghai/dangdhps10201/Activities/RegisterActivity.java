package com.danghai.dangdhps10201.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.danghai.dangdhps10201.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText fullname, email, password, rePassword;
    Button btnRegister;
    ProgressDialog loadingBar;
    FirebaseAuth auth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Đăng ký tài khoản");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Ánh xạ
        init();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerValidate();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void registerValidate() {
        //Khai báo các biến đăng ký tài khoản
        String txt_fullname = fullname.getText().toString().trim();
        String txt_email = email.getText().toString().trim();
        String txt_password = password.getText().toString().trim();
        String txt_rePassword = rePassword.getText().toString().trim();
        //Kiểm tra các điều kiện validate
        if (TextUtils.isEmpty(txt_fullname) || TextUtils.isEmpty(txt_email) ||
                TextUtils.isEmpty(txt_password) || TextUtils.isEmpty(txt_rePassword)) {
            Toast.makeText(this, "Bạn còn bỏ trống fields", Toast.LENGTH_SHORT).show();

        }
        //Check fullname lengh
        else if (txt_fullname.length() < 6 || txt_fullname.length() > 170) {
            Toast.makeText(this, "Họ và tên độ dài từ 6 đến 170 ký tự", Toast.LENGTH_SHORT).show();
        }
        //Check password length
        else if (txt_password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải lớn hơn 6 ký tự", Toast.LENGTH_SHORT).show();
        }
        //Check repeat password
        else if (!txt_rePassword.equals(txt_password)) {
            Toast.makeText(this, "Mật khẩu nhập lại không đúng", Toast.LENGTH_SHORT).show();
        } else {
            //Register account
            loadingBar.setTitle("Thông báo");
            loadingBar.setMessage("Chờ xíu, tài khoản của bạn đang được tạo...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            register(txt_fullname, txt_email, txt_password);

        }
    }

    //Check định dạng Email
    //Không cần thiết
    private boolean isEmail(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    //Connect to Firebase Auth
    private void register(final String fullname, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();


                            reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);
                            //Create table Users
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("fullname", fullname);


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        String msg = Objects.requireNonNull(task.getException()).getMessage();
                                        Toast.makeText(RegisterActivity.this, "LỖI: " + msg, Toast.LENGTH_SHORT).show();
                                    }
                                    loadingBar.dismiss();
                                }
                            });

                        } else {
                            Toast.makeText(RegisterActivity.this, "Bạn không thể đăng ký với tài khoản email này!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void init() {

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        rePassword = findViewById(R.id.rePassword);
        btnRegister = findViewById(R.id.btnRegister);
        loadingBar = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
    }
}
