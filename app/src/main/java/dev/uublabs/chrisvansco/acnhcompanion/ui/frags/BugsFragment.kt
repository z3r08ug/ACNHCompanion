package dev.uublabs.chrisvansco.acnhcompanion.ui.frags

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.MainActivity.Companion.bugViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.BugDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.FishBugAdapter

class BugsFragment : Fragment() {
    private lateinit var stillNeededBugsRV: RecyclerView
    private lateinit var caughtBugsRV: RecyclerView
    private lateinit var notCaughtBugsRV: RecyclerView
    private lateinit var stillNeededBugsAdapter: FishBugAdapter
    private lateinit var caughtBugsAdapter: FishBugAdapter
    private lateinit var notCaughtBugsAdapter: FishBugAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bugs, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stillNeededBugsRV = view.findViewById(R.id.stillNeededBugsRV)
        caughtBugsRV = view.findViewById(R.id.caughtBugsRV)
        notCaughtBugsRV = view.findViewById(R.id.notCaughtBugsRV)

        val stillNeededManager =
            GridLayoutManager(activity, 4)
        val caughtManager =
            GridLayoutManager(activity, 4)
        val notCaughtManager =
            GridLayoutManager(activity, 4)
        stillNeededBugsRV.layoutManager = stillNeededManager
        caughtBugsRV.layoutManager = caughtManager
        notCaughtBugsRV.layoutManager = notCaughtManager
        val bugDictionary = BugDictionary(activity)

        bugViewModel.allBugs.observe(viewLifecycleOwner, Observer { bug ->
            bug?.let {
                if (it.size == 80) {
                    Log.d(TAG, "onViewCreated: loaded still needed bugs ${it.size}")
                    bugDictionary.setBugs(it)

                    stillNeededBugsAdapter =
                        FishBugAdapter(this, null, bugDictionary.getStillNeededBugsList())
                    stillNeededBugsRV.adapter = stillNeededBugsAdapter

                    caughtBugsAdapter =
                        FishBugAdapter(this, null, bugDictionary.getCaughtOrNotCaughtBugs("true"))
                    caughtBugsRV.adapter = caughtBugsAdapter

                    notCaughtBugsAdapter =
                        FishBugAdapter(this, null, bugDictionary.getCaughtOrNotCaughtBugs("false"))
                    notCaughtBugsRV.adapter = notCaughtBugsAdapter
                }
            }
        })
    }

    companion object {
        private val TAG = BugsFragment::class.simpleName
    }
}