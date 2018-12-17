package droiddevelopers254.droidconke.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import droiddevelopers254.droidconke.R;
import droiddevelopers254.droidconke.adapters.AgendaAdapter;
import droiddevelopers254.droidconke.models.AgendaModel;
import droiddevelopers254.droidconke.viewmodels.AgendaViewModel;

public class AgendaFragment extends BaseFragment {
    RecyclerView recyclerView;
    AgendaAdapter agendaAdapter;
    List<AgendaModel> agendaModelList = new ArrayList<>();
    static RecyclerView.LayoutManager mLayoutManager;
    AgendaViewModel agendaViewModel;
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agenda, container, false);

        agendaViewModel= ViewModelProviders.of(this).get(AgendaViewModel.class);

        recyclerView=view.findViewById(R.id.agendaRv);

        //fetch agendas
        agendaViewModel.fetchAgendas();

        //observe live data emiited by view model
        agendaViewModel.getAgendas().observe(this,agendaState -> {
            if (agendaState.getDatabaseError() != null){
                handleDatabaseError(agendaState.getDatabaseError());
            }else {
                handleAgendaResponse(agendaState.getAgendaModel());
            }
        });

        return view;
    }

    private void handleAgendaResponse(List<AgendaModel> agendaList) {
        if (agendaList != null){
          agendaModelList=agendaList;
          initView();
        }
    }

    private void handleDatabaseError(String databaseError) {
        Toast.makeText(getActivity(),databaseError,Toast.LENGTH_SHORT).show();
    }

    private void initView() {
        agendaAdapter= new AgendaAdapter(agendaModelList,getActivity());
        mLayoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(agendaAdapter);
    }
}
