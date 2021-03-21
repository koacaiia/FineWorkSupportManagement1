package fine.koaca.fineworksupportmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ScreenSlidePagerFragment extends Fragment {
    ArrayList<View> view=new ArrayList<View>();
    int position;

    public ScreenSlidePagerFragment(int position) {
        this.position=position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view1=(ViewGroup)inflater.inflate(R.layout.fragment_a4,container,false);
        View view2=(ViewGroup)inflater.inflate(R.layout.fragment_stretch_film_large,container,false);
        View view3=(ViewGroup)inflater.inflate(R.layout.fragment_stretch_film_small,container,false);
        view.add(view1);
        view.add(view2);
        view.add(view3);

        return view.get(position);
    }
}
