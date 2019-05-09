package fendri.inovatif.latihan.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import net.gotev.uploadservice.BinaryUploadTask;
import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fendri.inovatif.latihan.BuildConfig;
import fendri.inovatif.latihan.MainActivity;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.model.ResponseRegister;
import fendri.inovatif.latihan.networking.ConfigRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    @BindView(R.id.iv_profile)
    ImageView   ivRegister;
    @BindView(R.id.edt_nama)
    EditText edtNama;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_password)
    EditText edtPassword;
    @BindView(R.id.rb_admin)
    RadioButton rbAdmin;
    @BindView(R.id.rb_user)
    RadioButton rbUser;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    Bitmap bitmap;
    Uri filepath;

    private static final int REQ_FILE_CODE = 1;

    String nama, email, password, level, path;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if (rbAdmin.isChecked()) {
            level = "Admin";
        } else {
            level = "User";
        }
    }

    @OnClick({R.id.iv_profile, R.id.rb_admin, R.id.rb_user, R.id.btn_register, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_profile:
                showfilechooser(REQ_FILE_CODE);
                break;
            case R.id.rb_admin:
                level = "Admin";
                break;
            case R.id.rb_user:
                level = "User";
                break;
            case R.id.btn_register:
                validasiRegistrasi();
                break;
            case R.id.tv_login:
                startActivity(new Intent(Register.this, Login.class));
                break;
        }
    }

    private void validasiRegistrasi() {

        // ambil inputan user
        nama = edtNama.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        // seleksi
        if (TextUtils.isEmpty(nama)) {
            edtNama.setError("Tidak Boleh Kosong");
            edtNama.requestFocus();

        } else if (TextUtils.isEmpty(email)) {
            edtEmail.setError("Tidak Boleh Kosong");
            edtEmail.requestFocus();

        } else if (TextUtils.isEmpty(password)) {
            edtPassword.setError("Tidak Boleh Kosong");
            edtPassword.requestFocus();

        } else if (ivRegister.getDrawable() == null) {
            Toast.makeText(this, "Images Harus Ada", Toast.LENGTH_SHORT).show();

        }else {

            fetchRegister();
            startActivity(new Intent(this, Login.class));

        }
    }

    private void fetchRegister() {
        try {
            path = getPath(filepath);

        } catch (Exception e) {
            Toast.makeText(this, "gambar terlalu besar \n silahkan pilih gambar yang lebih kecil", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        /**
         * Sets the maximum time to wait in milliseconds between two upload attempts.
         * This is useful because every time an upload fails, the wait time gets multiplied by
         * {@link UploadService#BACKOFF_MULTIPLIER} and it's not convenient that the value grows
         * indefinitely.
         */
        try {
            new MultipartUploadRequest(this, BuildConfig.SERVER_URL + "/images/user/")
                    .addFileToUpload(path, "vsgambar")
                    .addParameter("vsnama", nama)
                    .addParameter("vsemail", email)
                    .addParameter("vspassword", password)
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void showfilechooser(int reqFileChoose) {
        Intent intentgalery = new Intent(Intent.ACTION_PICK);
        intentgalery.setType("image/*");
        intentgalery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intentgalery, "Select Pictures"), reqFileChoose);
    }

    private String getPath(Uri filepath) {
        Cursor cursor = getContentResolver().query(filepath, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_FILE_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                ivRegister.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/**
 * private void sendRequestRegister() {
 * <p>
 * ConfigRetrofit.getInstance().register(
 * nama, email, password, gambar, level).enqueue(new Callback<ResponseRegister>() {
 *
 * @Override public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
 * <p>
 * // juka berhasil
 * if (response != null && response.isSuccessful()) {
 * String result = response.body().getResult();
 * String msg = response.body().getMsg();
 * <p>
 * if (result.equals("1")) {
 * startActivity(new Intent(Register.this, MainActivity.class));
 * Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
 * <p>
 * } else {
 * Toast.makeText(Register.this, msg, Toast.LENGTH_SHORT).show();
 * }
 * }
 * Log.d("TAG", response.message());
 * }
 * <p>
 * // response dari server gagal
 * @Override public void onFailure(Call<ResponseRegister> call, Throwable t) {
 * Log.d("TAG", t.getMessage());
 * Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_SHORT).show();
 * }
 * });
 * }
 */
