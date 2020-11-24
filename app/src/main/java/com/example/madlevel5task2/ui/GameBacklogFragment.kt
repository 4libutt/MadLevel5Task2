package com.example.madlevel5task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel5task2.R
import com.example.madlevel5task2.Ui.GameAdapter
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_game_backlog.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameBacklogFragment : Fragment() {

    private val gameViewmodel: GameViewModel by viewModels()
    private val games = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(games)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_backlog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<FloatingActionButton>(R.id.fabAdd).setOnClickListener {
            findNavController().navigate(
                R.id.action_GameBacklogFragment_to_AddGameFragment
            )
        }

        initRv()
        observeLiveData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btn_nav_icon_delete -> {
                gameViewmodel.deleteAllGames()
                gameAdapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeLiveData() {
        gameViewmodel.gameLiveData.observe(
                viewLifecycleOwner
        ) { liveBacklogGames: List<Game> ->
            games.clear()
            games.addAll(liveBacklogGames)
            games.sortBy { it.releaseDate }
            gameAdapter.notifyDataSetChanged()
        }
    }

    private fun initRv() {
        rvGames.apply {
            adapter = gameAdapter
            layoutManager =  LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            rvGames.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        createItemTouchHelper().attachToRecyclerView(rvGames)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val backlogGame = games[position]
                val undoSnackBar = Snackbar.make(
                        requireView(),
                        getString(R.string.removed_game),
                        Snackbar.LENGTH_SHORT
                ).setAction(getString(R.string.undo), fun(_: View) {
                    gameViewmodel.addBacklogGame(backlogGame)
                })

                gameViewmodel.deleteGame(backlogGame)

                undoSnackBar.show()
            }

        }

        return ItemTouchHelper(callback)
    }
}