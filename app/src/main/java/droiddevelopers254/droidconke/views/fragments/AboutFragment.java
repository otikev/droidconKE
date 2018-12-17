package droiddevelopers254.droidconke.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.views.activities.AboutDetailsActivity;

public class AboutFragment extends BaseFragment{
    TextView aboutDroidconText,organizersText,sponsorsText;
    String aboutType;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        aboutDroidconText=view.findViewById(R.id.aboutDroidconText);
        organizersText=view.findViewById(R.id.organizersText);
        sponsorsText=view.findViewById(R.id.sponsorsText);

        //load about details
        //about type is used to fetch for the specific clicked one

        aboutDroidconText.setOnClickListener(view1 -> {
            aboutType="about_droidconKE";
            Intent aboutDetailsIntent=new Intent(getActivity(), AboutDetailsActivity.class);
            aboutDetailsIntent.putExtra("aboutType",aboutType);
            startActivity(aboutDetailsIntent);
        });
        organizersText.setOnClickListener(view1 -> {
            aboutType="organizers";
            Intent aboutDetailsIntent=new Intent(getActivity(), AboutDetailsActivity.class);
            aboutDetailsIntent.putExtra("aboutType",aboutType);
            startActivity(aboutDetailsIntent);
        });
        sponsorsText.setOnClickListener(view1 -> {
            aboutType="sponsors";
            Intent aboutDetailsIntent=new Intent(getActivity(), AboutDetailsActivity.class);
            aboutDetailsIntent.putExtra("aboutType",aboutType);
            startActivity(aboutDetailsIntent);
        });

        return view;
    }

}
