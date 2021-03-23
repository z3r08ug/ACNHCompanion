package dev.uublabs.chrisvansco.acnhcompanion.ui.frags

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.MainActivity.Companion.bugViewModel
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.MainActivity.Companion.fishViewModel
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.MainActivity.Companion.seaCreatureViewModel
import dev.uublabs.chrisvansco.acnhcompanion.util.BugDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.FishDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureDictionary
import dev.uublabs.chrisvansco.acnhcompanion.util.TimeUtil


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var monthlyFishTV: TextView
    private lateinit var monthlyBugsTV: TextView
    private lateinit var monthlySeaCreaturesTV: TextView
    private lateinit var fishCaughtTV: TextView
    private lateinit var bugsCaughtTV: TextView
    private lateinit var seaCreaturesCaughtTV: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        monthlyFishTV = view.findViewById(R.id.monthlyFishTV)
        monthlyBugsTV = view.findViewById(R.id.monthlyBugsTV)
        monthlySeaCreaturesTV = view.findViewById(R.id.monthlySeaCreaturesTV)
        fishCaughtTV = view.findViewById(R.id.fishCaughtTV)
        bugsCaughtTV = view.findViewById(R.id.bugsCaughtTV)
        seaCreaturesCaughtTV = view.findViewById(R.id.seaCreaturesCaughtTV)

        val fishDictionary = FishDictionary(activity)
        val bugDictionary = BugDictionary(activity)
        val seaCreatureDictionary = SeaCreatureDictionary(activity)

        fishViewModel.allFish.observe(viewLifecycleOwner, Observer { fish ->
            fish?.let {
                Log.d(TAG, "onCreate: loaded fish ${it.size}")
                if (it.size == 80) {
                    monthlyFishTV.movementMethod = LinkMovementMethod.getInstance()
                    monthlyFishTV.text = fishDictionary.getCurrentlyCatchableFish(it, activity)
                    fishCaughtTV.text =
                        "${fishDictionary.getFishCaught(it)}/${fishDictionary.getAllFish().size}"
                }
            }
        })

        bugViewModel.allBugs.observe(viewLifecycleOwner, Observer { bug ->
            bug?.let {
                Log.d(TAG, "onCreate: loaded bugs ${it.size}")
                if (it.size == 80) {
                    monthlyBugsTV.movementMethod = LinkMovementMethod.getInstance()
                    monthlyBugsTV.text = bugDictionary.getCurrentCatchableBugs(it, activity)
                    bugsCaughtTV.text = "${bugDictionary.getBugsCaught(it)}/${bugDictionary.getAllBugs().size}"
                    
                    val timeUtil = TimeUtil()
                    Log.d(TAG, "onViewCreated: Time conversion: ${it[22].time} ${timeUtil.convertTimeToList(it[22].time)}")
                }
            }
        })

        seaCreatureViewModel.allSeaCreatures.observe(viewLifecycleOwner, Observer { seaCreature ->
            seaCreature?.let {
                Log.d(TAG, "onCreate: loaded seaCreature ${it.size}")
                if (it.size == 40) {
                    monthlySeaCreaturesTV.movementMethod = LinkMovementMethod.getInstance()
                    monthlySeaCreaturesTV.text = seaCreatureDictionary.getCurrentCatchableSeaCreatures(it, activity)
                    seaCreaturesCaughtTV.text = "${seaCreatureDictionary.getSeaCreaturesCaught(it)}/${seaCreatureDictionary.getAllSeaCreatures().size}"
                }
            }
        })
    }

    companion object {
        private val TAG = HomeFragment::class.simpleName

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}