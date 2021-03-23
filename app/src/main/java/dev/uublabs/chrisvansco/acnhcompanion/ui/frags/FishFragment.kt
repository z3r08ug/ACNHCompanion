package dev.uublabs.chrisvansco.acnhcompanion.ui.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.MainActivity.Companion.fishViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.FishBugAdapter
import dev.uublabs.chrisvansco.acnhcompanion.util.FishDictionary

class FishFragment : Fragment() {
    private lateinit var stillNeededFishRV: RecyclerView
    private lateinit var caughtFishRV: RecyclerView
    private lateinit var notCaughtFishRV: RecyclerView
    private lateinit var stillNeededFishAdapter: FishBugAdapter
    private lateinit var caughtFishAdapter: FishBugAdapter
    private lateinit var notCaughtFishAdapter: FishBugAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fish, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stillNeededFishRV = view.findViewById(R.id.stillNeededFishRV)
        caughtFishRV = view.findViewById(R.id.caughtFishRV)
        notCaughtFishRV = view.findViewById(R.id.notCaughtFishRV)

        val stillNeededManager =
            GridLayoutManager(activity, 4)
        val caughtManager =
            GridLayoutManager(activity, 4)
        val notCaughtManager =
            GridLayoutManager(activity, 4)
        stillNeededFishRV.layoutManager = stillNeededManager
        caughtFishRV.layoutManager = caughtManager
        notCaughtFishRV.layoutManager = notCaughtManager

        val fishDictionary = FishDictionary(activity)

        fishViewModel.allFish.observe(viewLifecycleOwner, Observer { fish ->
            fish?.let {
                if (it.size == 80) {
                    fishDictionary.setFish(it)

                    stillNeededFishAdapter =
                        FishBugAdapter(this, fishDictionary.getStillNeededFishList(), null)
                    stillNeededFishRV.adapter = stillNeededFishAdapter

                    caughtFishAdapter =
                        FishBugAdapter(this, fishDictionary.getCaughtOrNotCaughtFish("true"), null)
                    caughtFishRV.adapter = caughtFishAdapter

                    notCaughtFishAdapter =
                        FishBugAdapter(this, fishDictionary.getCaughtOrNotCaughtFish("false"), null)
                    notCaughtFishRV.adapter = notCaughtFishAdapter
                }
            }
        })
    }
}