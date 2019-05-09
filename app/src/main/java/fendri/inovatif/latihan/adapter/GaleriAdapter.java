package fendri.inovatif.latihan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.model.DataItem;

public class GaleriAdapter extends RecyclerView.Adapter<GaleriAdapter.ViewHolder> {

    private List<DataItem> dataItemList;
    private Context context;

    public GaleriAdapter(List<DataItem> dataItemList, Context context) {
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvJudul.setText(dataItemList.get(position).getJudul());
        holder.tvTanggal.setText(dataItemList.get(position).getTgl());
    }

    @Override
    public int getItemCount() {
        if (dataItemList == null) return 0;
        return dataItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.images_berita)
        ImageView imagesBerita;
        @BindView(R.id.tv_judul)
        TextView tvJudul;
        @BindView(R.id.tv_tanggal)
        TextView tvTanggal;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
