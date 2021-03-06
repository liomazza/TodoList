package com.example.lio.todolist.ui.tasks

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lio.todolist.R
import com.example.lio.todolist.databinding.FragmentTasksBinding
import com.example.lio.todolist.util.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TaskFragment: Fragment(R.layout.fragment_tasks) {

    private val viewModel: TasksViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentTasksBinding.bind(view)
        val taskAdapter = TaskAdapter()

        binding.apply {
            recyclerViewTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            taskAdapter.submitList(it)
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_tasks, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.onQueryTextChanged {
            // update search query
            viewModel.searchQuery.value = it
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return when(item.itemId) {
           R.id.action_sort_by_name -> {
              true
           }
           R.id.action_sort_by_date_created -> {
               true
           }
           R.id.action_hide_completed_tasks -> {
               item.isChecked = !item.isChecked

               true
           }
           R.id.action_delete_all_completed_tasks -> {
               true
           }
           else -> super.onOptionsItemSelected(item)
       }
    }
}