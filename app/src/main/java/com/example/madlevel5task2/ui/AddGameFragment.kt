package com.example.madlevel5task2.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.viewmodel.GameViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_game.*
import java.time.LocalDate

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabSaveGame.setOnClickListener {
            addGame()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addGame() {

        val title = etTitle.text.toString()
        val platform = etPlatform.text.toString()
        val day = etDay.text.toString().toInt()
        val month = etMonth.text.toString().toInt()
        val year = etYear.text.toString().toInt()

        if (title.isEmpty()) {
            Snackbar.make(requireView(), "Enter a title", Snackbar.LENGTH_SHORT).show()
            return
        }

        if (platform.isEmpty()) {
            Snackbar.make(requireView(), "Enter a platform", Snackbar.LENGTH_SHORT).show()
            return
        }

        try {
            gameViewModel.addBacklogGame(
                    Game(
                            title,
                            platform,
                            LocalDate.of(year, month, day)
                    )
            )
            startActivity(Intent(activity, MainActivity::class.java))
        } catch (e: Exception) {
            Snackbar.make(requireView(), "Invalid date try again", Snackbar.LENGTH_SHORT).show()
        }


    }
}