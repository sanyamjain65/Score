package mypocketvakil.example.com.score.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mypocketvakil.example.com.score.R;


public class post extends Fragment {
    //TextView location;
    TextView next;
    TextView tv_post_title, tv_post_summary, tv_post_description;
    String title, summary, description;
    View v_title, v_summary, v_desc;
    TextInputLayout rl_title, rl_summary, rl_desc;
    RelativeLayout rl_post;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ost, container, false);


    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        next = (TextView) view.findViewById(R.id.tv_post_button);

        v_title = (View) view.findViewById(R.id.v_title);
        v_summary = (View) view.findViewById(R.id.v_summary);
        v_desc = (View) view.findViewById(R.id.v_desc);
        rl_post = (RelativeLayout) view.findViewById(R.id.rl_post);
        rl_desc = (TextInputLayout) view.findViewById(R.id.rl_post_desc);
        rl_summary = (TextInputLayout) view.findViewById(R.id.rl_post_summary);
        rl_title = (TextInputLayout) view.findViewById(R.id.rl_post_title);

        tv_post_title = (TextView) view.findViewById(R.id.et_post_title);
        tv_post_summary = (TextView) view.findViewById(R.id.et_post_summary);
        tv_post_description = (TextView) view.findViewById(R.id.et_post_desc);

        tv_post_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_desc.setBackgroundColor(getResources().getColor(R.color.AppColor));
                v_title.setBackgroundColor(getResources().getColor(R.color.login_gray_color));
                v_summary.setBackgroundColor(getResources().getColor(R.color.login_gray_color));
            }
        });
        tv_post_summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_title.setBackgroundColor(getResources().getColor(R.color.login_gray_color));
                v_desc.setBackgroundColor(getResources().getColor(R.color.login_gray_color));
                v_summary.setBackgroundColor(getResources().getColor(R.color.AppColor));
            }
        });
        tv_post_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v_summary.setBackgroundColor(getResources().getColor(R.color.login_gray_color));
                v_desc.setBackgroundColor(getResources().getColor(R.color.login_gray_color));
                v_title.setBackgroundColor(getResources().getColor(R.color.AppColor));
            }
        });
//
//
//        p.setTitle(title);
//        p.setSummary(summary);
//        p.setDescription(description);
//
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = tv_post_title.getText().toString();
                summary = tv_post_summary.getText().toString();
                description = tv_post_description.getText().toString();
                String a = "";
                if (title.equals(a)) {
                    Snackbar snack = Snackbar.make(rl_post, "Title cannot be left blank", Snackbar.LENGTH_LONG);
                    snack.show();
                }
                if (summary.equals(a)) {
                    Snackbar snack = Snackbar.make(rl_post, "Summary cannot be left blank", Snackbar.LENGTH_LONG);
                    snack.show();
                }
                if (description.equals(a)) {
                    Snackbar snack = Snackbar.make(rl_post, "Description cannot be left blank", Snackbar.LENGTH_LONG);
                    snack.show();
                } else {
                    post2 newfrag = new post2();
                    Bundle args = new Bundle();
                    args.putString("title", title);
                    args.putString("summary", summary);
                    args.putString("description", description);

                    FragmentManager fm = getFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    newfrag.setArguments(args);
                    ft.replace(R.id.content_frame, newfrag).addToBackStack(null);
                    ft.commit();
                }

            }
        });
    }


}



