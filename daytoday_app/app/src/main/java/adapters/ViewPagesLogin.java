package adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.daytoday.fragment_createAccount;
import com.example.daytoday.fragment_login;

import java.util.ArrayList;

public class ViewPagesLogin extends FragmentStateAdapter {

    public ViewPagesLogin(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = new fragment_login();
                break;
            case 1:
                fragment = new fragment_createAccount();
                break;
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
