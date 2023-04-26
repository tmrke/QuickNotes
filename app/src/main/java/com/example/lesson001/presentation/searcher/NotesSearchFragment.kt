package com.example.lesson001.presentation.searcher

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson001.R
import com.example.lesson001.databinding.FragmentNotesListBinding
import com.example.lesson001.databinding.FragmentSearchBinding
import com.example.lesson001.presentation.list.NotesListAdapter
import com.example.lesson001.presentation.list.NotesListViewModel
import com.example.lesson001.presentation.list.SwipeToDeleteCallback
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotesSearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel by viewModels<NotesListViewModel>()

    @Inject
    lateinit var listAdapter: NotesListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = Navigation.findNavController(view)

        viewModel.getNotes()

        val recyclerView = binding.recyclerView

        recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

            adapter = listAdapter.apply {
                setCallbackSwipeToDelete { note ->
                    viewModel.deleteNote(note.id)
                }
            }
        }

        val swipeToDeleteCallback =
            SwipeToDeleteCallback(binding.recyclerView.adapter as NotesListAdapter)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}