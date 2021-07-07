package com.ydhnwb.simplecaching.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ydhnwb.simplecaching.databinding.ActivityMainBinding
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        observe()
        fetchTodos()
    }

    private fun observe(){
        observeState()
        observeTodos()
    }

    private fun observeState(){
        viewModel.state.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleState(it) }
            .launchIn(lifecycleScope)
    }

    private fun observeTodos(){
        viewModel.todos.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
            .onEach { handleTodos(it) }
            .launchIn(lifecycleScope)
    }

    private fun fetchTodos(){
        viewModel.fetchTodos()
    }

    private fun handleState(state : MainActivityState){
        when(state){
            is MainActivityState.ShowToast -> Toast.makeText(this@MainActivity, state.message, Toast.LENGTH_LONG).show()
            is MainActivityState.IsLoading -> handleLoading(state.isLoading)
            is MainActivityState.Init -> Unit
        }
    }

    private fun handleLoading(isLoading : Boolean){
        if(isLoading){
            binding.loadingBar.visibility = View.VISIBLE
        }else{
            binding.loadingBar.visibility = View.GONE
        }
    }

    private fun setupRecyclerView(){
        val a = TodoAdapter(mutableListOf())
        a.setOnTapListener(object: TodoAdapter.Listener{
            override fun onTap(todo: TodoEntity) {
                println(todo.title)
            }
        })

        binding.todoRecyclerView.apply {
            adapter = a
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun handleTodos(todos: List<TodoEntity>){
        binding.todoRecyclerView.adapter?.let { adapter ->
            if(adapter is TodoAdapter){
                adapter.updateList(todos)
            }
        }
    }
}