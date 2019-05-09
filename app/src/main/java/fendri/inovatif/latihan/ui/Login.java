package fendri.inovatif.latihan.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fendri.inovatif.latihan.MainActivity;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.helper.SessionManager;
import fendri.inovatif.latihan.model.ResponseLogin;
import fendri.inovatif.latihan.model.ResponseRegister;
import fendri.inovatif.latihan.model.User;
import fendri.inovatif.latihan.networking.ApiService;
import fendri.inovatif.latihan.networking.ConfigRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    @BindView(R.id.edt_email_login)
    EditText edtEmailLogin;
    @BindView(R.id.edt_password_login)
    EditText edtPasswordLogin;
    @BindView(R.id.rb_admin_login)
    RadioButton rbAdminLogin;
    @BindView(R.id.rb_user_login)
    RadioButton rbUserLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;

    String nama, email, password, images, level;

    SessionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        manager = new SessionManager(this);

        if (rbAdminLogin.isChecked()) {
            level = "Admin";
        } else {
            level = "User";
        }


    }

    @OnClick({R.id.rb_admin_login, R.id.rb_user_login, R.id.btn_login, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_admin_login:
                level = "Admin";
                break;
            case R.id.rb_user_login:
                level = "User";
                break;
            case R.id.btn_login:
                validasilogin();
                break;
            case R.id.tv_register:
                startActivity(new Intent(Login.this, Register.class));
                break;
        }
    }

    private void validasilogin() {

        // ambil inputan user
        email = edtEmailLogin.getText().toString().trim();
        password = edtPasswordLogin.getText().toString().trim();

        // seleksi

       if (TextUtils.isEmpty(email))  {
            edtEmailLogin.setError("Tidak Boleh Kosong");
            edtEmailLogin.requestFocus();

        } else if (TextUtils.isEmpty(password)){
            edtPasswordLogin.setError("Tidak Boleh Kosong");
            edtPasswordLogin.requestFocus();

//        } else if (password.length()<5){
//            edtPasswordLogin.setError("Password minimum 5");
//            edtPasswordLogin.requestFocus();

        } else {
            sendRequestLogin();
        }
    }

    private void sendRequestLogin() {
        ConfigRetrofit.getInstance().login(
                 email, password, level).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {

                // juka berhasil
                if (response!= null && response.isSuccessful()) {
                    String result = response.body().getResult();
                    String msg = response.body().getMsg();
                    User data = response.body().getUser();

                    if (result.equals("1")) {
                        Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                        setUpSession(data);

                    } else {
                        Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("TAG", response.message());
            }

            // response dari server gagal
            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                Log.d("TAG", t.getMessage());
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpSession(User data) {
        manager.setLogin(true);
        manager.setID(data.getId());
        manager.setUSER_EMAIL(data.getEmail());
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }
}
