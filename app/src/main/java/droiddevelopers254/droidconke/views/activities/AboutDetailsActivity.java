package droiddevelopers254.droidconke.views.activities;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.AboutDetailsAdapter;
import droiddevelopers254.droidconke.models.AboutDetailsModel;
import droiddevelopers254.droidconke.viewmodels.AboutDetailsViewModel;
import droiddevelopers254.droidconke.views.BaseActivity;

public class AboutDetailsActivity extends BaseActivity {
    RecyclerView recyclerView;
    static RecyclerView.LayoutManager mLayoutManager;
    List<AboutDetailsModel> aboutDetailsList = new ArrayList<>();
    AboutDetailsAdapter aboutDetailsAdapter;
    AboutDetailsViewModel aboutDetailsViewModel;
    String aboutType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aboutDetailsViewModel= ViewModelProviders.of(this).get(AboutDetailsViewModel.class);

        //get intent extras
        Intent extraIntent=getIntent();
        aboutType=extraIntent.getStringExtra("aboutType");

        if (aboutType.equals("about_droidconKE")) {
            getSupportActionBar().setTitle("About droidconKE");
        }
        if (aboutType.equals("organizers")) {
            getSupportActionBar().setTitle("Organizers");
        }
        if (aboutType.equals("sponsors")) {
            getSupportActionBar().setTitle("Sponsors");
        }

        //fetch about details
        fetchAboutDetails(aboutType);


        recyclerView=findViewById(R.id.aboutDetailsRv);

        //observe live data emitted by view model
        aboutDetailsViewModel.getAboutDetails().observe(this,aboutDetailsState -> {
            if (aboutDetailsState.getDatabaseError() != null){
                handleDatabaseError(aboutDetailsState.getDatabaseError());
            }else {
                handleFetchAboutDetails(aboutDetailsState.getAboutDetailsModelList());
            }
        });
    }

    private void fetchAboutDetails(String aboutType) {
        aboutDetailsViewModel.fetchAboutDetails(aboutType);
    }

    private void handleFetchAboutDetails(List<AboutDetailsModel> aboutDetailsModelList) {
        if (aboutDetailsModelList != null){
            aboutDetailsList=aboutDetailsModelList;
            initView();
        }
    }

    private void handleDatabaseError(String databaseError) {
        Toast.makeText(getApplicationContext(),databaseError,Toast.LENGTH_SHORT).show();
    }
    private void initView() {
        aboutDetailsAdapter= new AboutDetailsAdapter(aboutDetailsList,getApplicationContext());
        mLayoutManager= new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(aboutDetailsAdapter);
    }

}
