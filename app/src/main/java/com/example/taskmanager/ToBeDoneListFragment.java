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


public class ToBeDoneListFragment extends Fragment {
    private RecyclerView recyclerView;
    private Repository repository;
    private ToBeDoneTaskAdapter adapter;

    public static ToBeDoneListFragment newInstance() {

        Bundle args = new Bundle();

        ToBeDoneListFragment fragment = new ToBeDoneListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ToBeDoneListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_tobedone, container, false);
        recyclerView = view.findViewById(R.id.recycler_toBeDone);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        repository = Repository.getInstance();

        if (adapter == null) {
            adapter = new ToBeDoneListFragment.ToBeDoneTaskAdapter(repository.getToBeDoneTasks());
        }
        recyclerView.setAdapter(adapter);
//        updateUi();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().finish();
//        Intent intent = new Intent(getContext(), MainActivity.class);
//        startActivity(intent);
//        MainActivity.updateAdaptor();
        updateUi();
    }

    public void updateUi(){
//        adapter.setToBeDoneTasks(repository.getToBeDoneTasks());
//        adapter.notifyDataSetChanged();

        List<Task> tasks = Repository.getInstance().getToBeDoneTasks();

        if (adapter == null) {
            adapter = new ToBeDoneTaskAdapter(tasks);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setToBeDoneTasks(tasks);
            adapter.notifyDataSetChanged();
        }
    }

    class ToBeDoneTaskAdapter extends RecyclerView.Adapter<ToBeDoneListFragment.ToBeDoneTaskAdapter.ToBeDoneTaskViewHolder>{

        private List<Task> toBeDoneTaskList;

        public void setToBeDoneTasks(List<Task> toBeDoneTaskList) {
            this.toBeDoneTaskList = toBeDoneTaskList;
        }

        public ToBeDoneTaskAdapter(List<Task> toBeDoneTaskList) {
            this.toBeDoneTaskList = toBeDoneTaskList;
        }

        @NonNull
        @Override
        public ToBeDoneListFragment.ToBeDoneTaskAdapter.ToBeDoneTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ToBeDoneListFragment.ToBeDoneTaskAdapter.ToBeDoneTaskViewHolder(LayoutInflater.from(getActivity()).inflate(R.layout.task_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ToBeDoneListFragment.ToBeDoneTaskAdapter.ToBeDoneTaskViewHolder holder, int position) {
            holder.bind(toBeDoneTaskList.get(position));
        }

        @Override
        public int getItemCount() {
            return toBeDoneTaskList.size();
        }

        class ToBeDoneTaskViewHolder extends RecyclerView.ViewHolder{
            TextView textView_title;
            TextView textView_description;
            TextView textView_date;
            TextView textView_icon;
            Task task;
            public ToBeDoneTaskViewHolder(@NonNull View itemView) {
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
