package fendri.inovatif.latihan.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.adapter.BeritaAdapter;
import fendri.inovatif.latihan.helper.SessionManager;
import fendri.inovatif.latihan.model.DataItem;
import fendri.inovatif.latihan.model.ResponseBerita;
import fendri.inovatif.latihan.networking.ConfigRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabKategoriSatu extends Fragment {

    SessionManager manager;
    String idLogin = "login";

    RecyclerView recyclerviewTabSatu;
    Unbinder unbinder;

    public TabKategoriSatu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_kategori_satu, container, false);
        recyclerviewTabSatu = view.findViewById(R.id.recyclerview_tab_satu);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        manager = new SessionManager(getActivity());
        sendRequestBerita();
    }

    private void sendRequestBerita() {
        idLogin = manager.getID();
        ConfigRetrofit.getInstance().getBerita(idLogin).enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if(response.isSuccessful()) {
                    boolean status = response.body().isIsSuccess();
                    List<DataItem> dataItems = response.body().getData();

                    if(status){
                        showListBerita(dataItems);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    private void showListBerita(List<DataItem> dataItems) {
        recyclerviewTabSatu.setHasFixedSize(true);
        recyclerviewTabSatu.setLayoutManager(new LinearLayoutManager(getActivity()));
        BeritaAdapter adapter = new BeritaAdapter(dataItems, getActivity());
        recyclerviewTabSatu.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
