package fendri.inovatif.latihan.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;



import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import fendri.inovatif.latihan.BuildConfig;
import fendri.inovatif.latihan.R;
import fendri.inovatif.latihan.adapter.TabPagerAdapter;
import fendri.inovatif.latihan.helper.SessionManager;
import fendri.inovatif.latihan.model.DataItem;
import fendri.inovatif.latihan.model.ResponseImageSlider;
import fendri.inovatif.latihan.networking.ConfigRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UtamaFragment extends Fragment {

    @BindView(R.id.slider)
    SliderLayout slider;
    Unbinder unbinder;

    SessionManager manager;
    String idLogin = "login";

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public UtamaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_utama, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViewPager(viewPager);
        tabs.setupWithViewPager(viewPager);
        manager = new SessionManager(getActivity());
        sendRequestImagesSlider();
    }

    private void initViewPager(ViewPager pager) {
        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager());
        adapter.fragment(new TabKategoriSatu(), "Berita");
        adapter.fragment(new TabKatergoriDua(), "Galeri Foto");
        pager.setAdapter(adapter);
    }

    private void sendRequestImagesSlider() {
        idLogin = manager.getID();
        ConfigRetrofit.getInstance().getImagesSlider(idLogin).enqueue(new Callback<ResponseImageSlider>() {
            @Override
            public void onResponse(Call<ResponseImageSlider> call, Response<ResponseImageSlider> response) {
                if (response.isSuccessful()) {
                    boolean status = response.body().isIsSuccess();
                    List<DataItem> dataItems = response.body().getData();

                    if (status) {
                        showSlider(dataItems);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseImageSlider> call, Throwable t) {
                Log.d("TAG", t.getMessage());
            }
        });
    }

    private void showSlider(List<DataItem> dataItems) {
        for (int i = 0; i < dataItems.size(); i++) {
            HashMap<String, String> url_images = new HashMap<String, String>();
            //url_images.put(String.valueOf(Html.fromHtml(dataItems.get(i).getJudul().toString() + "<br> tsting")), "http://192.168.100.8/appmobile/images/" + dataItems.get(i).getGambar());
            url_images.put(dataItems.get(i).getJudul().toString(), BuildConfig.SERVER_URL + "images/" + dataItems.get(i).getGambar());

            for (String name : url_images.keySet()) {
                TextSliderView textSliderView = new TextSliderView(getActivity());
                // initialize a SliderLayout
                textSliderView
                        .description(name)
                        .image(url_images.get(name))
                        .setScaleType(BaseSliderView.ScaleType.Fit);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", name);

                slider.addSlider(textSliderView);
            }
            slider.setPresetTransformer(SliderLayout.Transformer.Fade);
            slider.setPresetIndicator(SliderLayout.PresetIndicators.Left_Top);
            slider.setCustomAnimation(new DescriptionAnimation());
            slider.setDuration(4000);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        slider.stopAutoCycle();
        super.onStop();
    }
}
