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
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.MainActivity.Companion.seaCreatureViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureAdapter
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureDictionary

class SeaCreaturesFragment : Fragment() {
    private lateinit var stillNeededSeaCreaturesRV: RecyclerView
    private lateinit var caughtSeaCreaturesRV: RecyclerView
    private lateinit var notCaughtSeaCreaturesRV: RecyclerView
    private lateinit var stillNeededSeaCreaturesAdapter: SeaCreatureAdapter
    private lateinit var caughtSeaCreaturesAdapter: SeaCreatureAdapter
    private lateinit var notCaughtSeaCreaturesAdapter: SeaCreatureAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sea_creatures_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stillNeededSeaCreaturesRV = view.findViewById(R.id.stillNeededSeaCreaturesRV)
        caughtSeaCreaturesRV = view.findViewById(R.id.caughtSeaCreaturesRV)
        notCaughtSeaCreaturesRV = view.findViewById(R.id.notCaughtSeaCreaturesRV)

        val stillNeededManager =
            GridLayoutManager(activity, 4)
        val caughtManager =
            GridLayoutManager(activity, 4)
        val notCaughtManager =
            GridLayoutManager(activity, 4)
        stillNeededSeaCreaturesRV.layoutManager = stillNeededManager
        caughtSeaCreaturesRV.layoutManager = caughtManager
        notCaughtSeaCreaturesRV.layoutManager = notCaughtManager

        val seaCreatureDictionary = SeaCreatureDictionary(activity)

        seaCreatureViewModel.allSeaCreatures.observe(viewLifecycleOwner, Observer { seaCreatures ->
            seaCreatures?.let {
                if (it.size == 40) {
                    seaCreatureDictionary.setSeaCreatures(it)

                    stillNeededSeaCreaturesAdapter =
                        SeaCreatureAdapter(this, seaCreatureDictionary.getStillNeededSeaCreaturesList())
                    stillNeededSeaCreaturesRV.adapter = stillNeededSeaCreaturesAdapter

                    caughtSeaCreaturesAdapter =
                        SeaCreatureAdapter(this, seaCreatureDictionary.getCaughtOrNotCaughtSeaCreatures("true"))
                    caughtSeaCreaturesRV.adapter = caughtSeaCreaturesAdapter

                    notCaughtSeaCreaturesAdapter =
                        SeaCreatureAdapter(this, seaCreatureDictionary.getCaughtOrNotCaughtSeaCreatures("false"))
                    notCaughtSeaCreaturesRV.adapter = notCaughtSeaCreaturesAdapter
                }
            }
        })
    }
}