package com.ssa.taskapp.ui.fragment.home;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ssa.taskapp.App;
import com.ssa.taskapp.R;
import com.ssa.taskapp.databinding.FragmentHomeBinding;
import com.ssa.taskapp.ui.fragment.detail.DetailFragment;
import com.ssa.taskapp.ui.fragment.home.adapter.TaskAdapter;
import com.ssa.taskapp.data.model.TaskModel;


public class HomeFragment extends Fragment implements Click {

    private FragmentHomeBinding binding;
    private NavController controller;
    private TaskAdapter adapter;
    public static final String ADAPTER_KEY = "adapter_key";
    public static final String ADAPTER_KEY1 = "adapter_key1";
    public static final String ADAPTER_KEY2 = "adapter_key2";
    public static final String ADAPTER_KEY3 = "adapter_key3";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        initAdapter();
        initNavController();
        initListener();
        initResultListener();
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.add(0,1,0,"Sort Alphabetically");
        menu.add(0,2,1,"sort by date");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == 1){
           adapter.sortA();
        }else {
            adapter.sortD();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {
        adapter = new TaskAdapter(this);
        adapter.addList(App.dateBase.taskDao().getAllTasks());
        binding.itemTask.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.addList(App.dateBase.taskDao().getAllTasks());
    }

    private void initResultListener() {
        getParentFragmentManager().setFragmentResultListener(
                DetailFragment.RESULT_HOME_KEY,
                this,
                ((requestKey, result) -> {
                    String text = result.getString(DetailFragment.HOME_KEY).trim();
                    String date = result.getString(DetailFragment.HOME_KEY1);
                    App.dateBase.taskDao().addTask(new TaskModel(text, date));
                })
        );
        getParentFragmentManager().setFragmentResultListener(
                DetailFragment.RESULT_HOME_KEY_EDIT,
                this,
                ((requestKey, result) -> {
                    String text = result.getString(DetailFragment.HOME_KEY).trim();
                    String date = result.getString(DetailFragment.HOME_KEY1);
                    int id = result.getInt(DetailFragment.HOME_KEY3);
                    App.dateBase.taskDao().update(new TaskModel(id, text, date));
                })
        );
    }



    private void initNavController() {
        NavHostFragment navHostController = (NavHostFragment)
                requireActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostController != null;
        controller = navHostController.getNavController();
    }

    private void initListener() {
        binding.addTaskBtn.setOnClickListener(view -> controller.navigate(R.id.detailFragment));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void click(TaskModel taskModel) {
    Bundle bundle = new Bundle();
    bundle.putString(ADAPTER_KEY,taskModel.getTitle());
    bundle.putString(ADAPTER_KEY1, taskModel.getCreated());
    bundle.putInt(ADAPTER_KEY3, taskModel.getId());
    getParentFragmentManager().setFragmentResult(ADAPTER_KEY2, bundle);
    controller.navigate(R.id.detailFragment);
    }

    @Override
    public void delete(TaskModel taskModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        String quiz = "Вы точно хотите удалить?";
        builder.setTitle(quiz);
        String delete = " Удалить";
        builder.setPositiveButton(delete, (dialog, which) -> {
                    App.dateBase.taskDao().delete(taskModel);
                    initAdapter();
                    Toast.makeText(requireContext(), "Успешно удалено",
                            Toast.LENGTH_SHORT).show();
                }
        );
        String no = "Оставить";
        builder.setNegativeButton(no, (dialog, which) ->
                Toast.makeText(requireContext(), "Изменение не призошло",
                Toast.LENGTH_SHORT).show()).show();
    }
}