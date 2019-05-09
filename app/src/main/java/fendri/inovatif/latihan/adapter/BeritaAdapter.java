package fendri.inovatif.latihan.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fendri.inovatif.latihan.BuildConfig;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.model.DataItem;
import fendri.inovatif.latihan.ui.detail.ActivityBeritaDetail;

public class BeritaAdapter extends RecyclerView.Adapter<BeritaAdapter.ViewHolder> {

    private List<DataItem> dataItemList;
    private Context context;

    private String judul, tanggal, images, isi, id;

    public BeritaAdapter(List<DataItem> dataItemList, Context context) {
        this.dataItemList = dataItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_berita, parent, false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        judul = dataItemList.get(position).getJudul();
        tanggal = dataItemList.get(position).getTgl();
        images = BuildConfig.SERVER_URL + "images/berita/" + dataItemList.get(position).getGambar();
        isi = dataItemList.get(position).getIsi();

        Picasso.with(context).load(images).into(holder.imagesBerita);
        holder.tvJudul.setText(judul);
        holder.tvTanggal.setText(tanggal);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String judul = dataItemList.get(position).getJudul();

                DataItem dataItem = new DataItem();
                dataItem.setJudul(dataItemList.get(position).getJudul());
                dataItem.setTgl(tanggal);
                dataItem.setGambar(images);
                dataItem.setIsi(isi);

                Intent send = new Intent(context, ActivityBeritaDetail.class);
                send.putExtra(ActivityBeritaDetail.EXTRA_DATA_KEY, judul);
                Toast.makeText(context, judul, Toast.LENGTH_SHORT).show();
//                context.startActivity(send);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (dataItemList == null) return 0;
        return dataItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.images_berita)
        ImageView imagesBerita;
        @BindView(R.id.tv_judul)
        TextView tvJudul;
        @BindView(R.id.tv_tanggal)
        TextView tvTanggal;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
