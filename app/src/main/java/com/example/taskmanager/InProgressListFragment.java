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

import com.example.taskmanager.models.Repository;
import com.example.taskmanager.models.Task;

import java.util.List;

public class InProgressListFragment extends Fragment {
    RecyclerView recyclerView;
    InProgressAdapter adapter;
    Repository repository;
    public static final String TAG = "ListAllFragment";

    public static InProgressListFragment newInstance() {

        Bundle args = new Bundle();

        InProgressListFragment fragment = new InProgressListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public InProgressListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_inprogress, container, false);
        recyclerView = view.findViewById(R.id.recycler_inProgress);
        repository = Repository.getInstance();
        if (adapter == null)
            adapter = new InProgressAdapter(repository.getInProgressTasks());
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

    public void updateUi() {
        List<Task> tasks = Repository.getInstance().getInProgressTasks();

        if (adapter == null) {
            adapter = new InProgressAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setInProgressTasks(tasks);
            adapter.notifyDataSetChanged();
        }
    }

    class InProgressAdapter extends RecyclerView.Adapter<InProgressAdapter.InProgressViewHolder> {
        private List<Task> inProgressTaskList;


        public void setInProgressTasks(List<Task> inProgressTaskList) {
            this.inProgressTaskList = inProgressTaskList;
        }

        public InProgressAdapter(List<Task> inProgressTaskList) {
            this.inProgressTaskList = inProgressTaskList;
        }

        @NonNull
        @Override
        public InProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new InProgressViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.task_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull InProgressViewHolder holder, int position) {
            holder.bind(inProgressTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return inProgressTaskList.size();
        }

        class InProgressViewHolder extends RecyclerView.ViewHolder {
            TextView textView_title;
            TextView textView_description;
            TextView textView_date;
            TextView textView_icon;
            Task task;
            public InProgressViewHolder(@NonNull View itemView) {
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


