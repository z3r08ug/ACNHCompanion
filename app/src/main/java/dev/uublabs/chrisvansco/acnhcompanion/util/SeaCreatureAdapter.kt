package dev.uublabs.chrisvansco.acnhcompanion.util

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.model.SeaCreature
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.InformationActivity
import dev.uublabs.chrisvansco.acnhcompanion.util.SeaCreatureAdapter.SeaCreatureViewHolder
import java.util.*

class SeaCreatureAdapter(private val fragment: Fragment, private val seaCreatureList: List<SeaCreature>): RecyclerView.Adapter<SeaCreatureViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeaCreatureViewHolder {
        val v = LayoutInflater.from(fragment.context).inflate(R.layout.list_item, parent, false)

        return SeaCreatureViewHolder(v)
    }

    override fun getItemCount(): Int {
        return seaCreatureList.size
    }

    override fun onBindViewHolder(holder: SeaCreatureViewHolder, position: Int) {
        val context = holder.itemCIV.context
        val id: Int
        val seaCreature = seaCreatureList[position]

        id = context.resources.getIdentifier(
            seaCreature.name.replace(" ", "").replace("’", "").toLowerCase(locale = Locale.getDefault()),
            "drawable",
            context.packageName)

        holder.itemCIV.setOnClickListener {
            val intent = Intent(context, InformationActivity::class.java)
            intent.putExtra("seaCreature", seaCreature)
            context.startActivity(intent)
        }

        holder.itemCIV.setImageResource(id)
    }

    class SeaCreatureViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemCIV by lazy <CircleImageView> { view.findViewById(R.id.itemCIV) }
    }

    companion object {
        val TAG = SeaCreatureAdapter::class.java.simpleName
    }
}