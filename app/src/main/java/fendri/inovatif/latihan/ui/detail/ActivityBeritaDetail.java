package fendri.inovatif.latihan.ui.detail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.zip.DataFormatException;

import butterknife.BindView;
import butterknife.ButterKnife;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.model.DataItem;

public class ActivityBeritaDetail extends AppCompatActivity {

    @BindView(R.id.iv_detalberita)
    ImageView ivDetalberita;
    @BindView(R.id.toolbarrr)
    Toolbar toolbarrr;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_tanggaldetail)
    TextView tvTanggaldetail;
    @BindView(R.id.wv_kontendetail)
    WebView wvKontendetail;

    // variable sbg penampung data dalam jumlah banyak
    public static final String EXTRA_DATA_KEY = "KEY";

    String idGue;

    String judul, images, tanggal, isi, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarrr);
        getDataReceievd();
        setViews();
    }

    private void getDataReceievd() {
        idGue = getIntent().getStringExtra(EXTRA_DATA_KEY);

//        DataItem data = getIntent().getParcelableExtra(EXTRA_DATA_KEY);
//        judul = data.getJudul();
//        images = data.getGambar();
//        tanggal = data.getTgl();
//        isi = data.getIsi();
//        id = data.getId();
    }

    private void setViews() {
        toolbarrr.setTitle(idGue);
        Picasso.with(this).load(images).into(ivDetalberita);
        tvTanggaldetail.setText(tanggal);

        // Set isi berita sebagai html ke WebView
        wvKontendetail.getSettings().setJavaScriptEnabled(true);
        wvKontendetail.loadData(isi, "text/html; charset=utf-8", "UTF-8");
    }
}
