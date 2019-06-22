package ru.semper_viventem.confinder.ui.profile;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import org.jetbrains.annotations.NotNull;
import ru.semper_viventem.confinder.R;
import ru.semper_viventem.confinder.ui.Screen;

public class ProfileScreen extends Screen {

    @Override
    public int getLayoutId() {
        return R.layout.profile_screen;
    }

    @Override
    public void initView(@NotNull View view) {
        view.findViewById(R.id.accountImage);
        view.findViewById(R.id.accountName);
        view.findViewById(R.id.accountDescription);
        view.findViewById(R.id.accountTags);
    }



}
