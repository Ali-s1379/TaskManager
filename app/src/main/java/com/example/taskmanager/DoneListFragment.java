package com.example.taskmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanager.models.Task;
import com.example.taskmanager.models.Repository;

import java.util.List;

public class DoneListFragment extends Fragment {
    private RecyclerView recyclerView;
    private Repository repository;
    private DoneTaskAdapter adapter;

    public static DoneListFragment newInstance() {

        Bundle args = new Bundle();

        DoneListFragment fragment = new DoneListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public DoneListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_done, container, false);
        recyclerView = view.findViewById(R.id.recycler_done);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        repository = Repository.getInstance();

        if (adapter == null)
            adapter = new DoneTaskAdapter(repository.getDoneTasks());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        updateUi();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    public void updateUi(){
        List<Task> tasks = Repository.getInstance().getDoneTasks();

        if (adapter == null) {
            adapter = new DoneTaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setDoneTasks(tasks);
            adapter.notifyDataSetChanged();
        }
    }



    class DoneTaskAdapter extends RecyclerView.Adapter<DoneTaskAdapter.DoneTaskViewHolder>{

        private List<Task> doneTaskList;


        public void setDoneTasks(List<Task> doneTaskList) {
            this.doneTaskList = doneTaskList;
        }

        public DoneTaskAdapter(List<Task> doneTaskList) {
            this.doneTaskList = doneTaskList;
        }


        @NonNull
        @Override
        public DoneTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DoneTaskViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.task_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull DoneTaskViewHolder holder, int position) {
            holder.bind(doneTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return doneTaskList.size();
        }

        class DoneTaskViewHolder extends RecyclerView.ViewHolder{
            TextView textView_title;
            TextView textView_description;
            TextView textView_date;
            TextView textView_icon;
            Task task;
            public DoneTaskViewHolder(@NonNull View itemView) {
                super(itemView);
                textView_title = itemView.findViewById(R.id.title);
                textView_description = itemView.findViewById(R.id.description);
                textView_date = itemView.findViewById(R.id.date);
                textView_icon = itemView.findViewById(R.id.textView2);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DetailDialogFragment fragment = DetailDialogFragment.newInstance(task.getId());
                        fragment.show(getFragmentManager(),"detailDialog");
                    }
                });
            }

            public void bind(Task task) {
                textView_title.setText(task.getTitle());
                textView_description.setText(task.getDescription());
                textView_date.setText(task.getDate().toString());
                if (task.getTitle().length() > 0){
                    textView_icon.setText(task.getTitle().substring(0,1));
                }
                this.task = task;
            }
        }
    }

}
