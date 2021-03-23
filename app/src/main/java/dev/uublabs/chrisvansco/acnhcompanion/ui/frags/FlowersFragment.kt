package dev.uublabs.chrisvansco.acnhcompanion.ui.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import dev.uublabs.chrisvansco.acnhcompanion.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [FlowersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FlowersFragment : Fragment() {

    private lateinit var cosmosIV: ImageView
    private lateinit var rosesIV: ImageView
    private lateinit var liliesIV: ImageView
    private lateinit var pansiesIV: ImageView
    private lateinit var hyacinthsIV: ImageView
    private lateinit var mumsIV: ImageView
    private lateinit var tulipsIV: ImageView
    private lateinit var windflowersIV: ImageView
    private lateinit var cosmosCombosIV: ImageView
    private lateinit var rosesCombosIV: ImageView
    private lateinit var liliesCombosIV: ImageView
    private lateinit var pansiesCombosIV: ImageView
    private lateinit var hyacinthsCombosIV: ImageView
    private lateinit var mumsCombosIV: ImageView
    private lateinit var tulipsCombosIV: ImageView
    private lateinit var windflowersCombosIV: ImageView

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
        return inflater.inflate(R.layout.fragment_flowers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews(view)
        setupListeners()
    }

    private fun setupViews(view: View) {
        cosmosIV = view.findViewById(R.id.cosmosIV)
        rosesIV = view.findViewById(R.id.rosesIV)
        liliesIV = view.findViewById(R.id.liliesIV)
        pansiesIV = view.findViewById(R.id.pansiesIV)
        hyacinthsIV = view.findViewById(R.id.hyancinthsIV)
        mumsIV = view.findViewById(R.id.mumsIV)
        tulipsIV = view.findViewById(R.id.tulipsIV)
        windflowersIV = view.findViewById(R.id.windflowersIV)
        cosmosCombosIV = view.findViewById(R.id.cosmosCombosIV)
        rosesCombosIV = view.findViewById(R.id.rosesCombosIV)
        liliesCombosIV = view.findViewById(R.id.liliesCombosIV)
        pansiesCombosIV = view.findViewById(R.id.pansiesCombosIV)
        hyacinthsCombosIV = view.findViewById(R.id.hyacinthsCombosIV)
        mumsCombosIV = view.findViewById(R.id.mumsCombosIV)
        tulipsCombosIV = view.findViewById(R.id.tulipsCombosIV)
        windflowersCombosIV = view.findViewById(R.id.windflowersCombosIV)
    }

    private fun setupListeners() {
        cosmosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(cosmosCombosIV)
        }
        rosesIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(rosesCombosIV)
        }
        liliesIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(liliesCombosIV)
        }
        pansiesIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(pansiesCombosIV)
        }
        hyacinthsIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(hyacinthsCombosIV)
        }
        mumsIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(mumsCombosIV)
        }
        tulipsIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(tulipsCombosIV)
        }
        windflowersIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(windflowersCombosIV)
        }

        cosmosCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(cosmosIV)
        }
        rosesCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(rosesIV)
        }
        liliesCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(liliesIV)
        }
        pansiesCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(pansiesIV)
        }
        hyacinthsCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(hyacinthsIV)
        }
        mumsCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(mumsIV)
        }
        tulipsCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(tulipsIV)
        }
        windflowersCombosIV.setOnClickListener { v ->
            toggleVisibility(v)
            toggleVisibility(windflowersIV)
        }
    }

    private fun toggleVisibility(view: View) {
        if (view.visibility == View.VISIBLE)
            view.visibility = View.GONE
        else
            view.visibility = View.VISIBLE
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FlowersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FlowersFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}