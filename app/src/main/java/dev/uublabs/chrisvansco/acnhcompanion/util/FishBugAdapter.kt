package dev.uublabs.chrisvansco.acnhcompanion.util

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import dev.uublabs.chrisvansco.acnhcompanion.R
import dev.uublabs.chrisvansco.acnhcompanion.model.Bug
import dev.uublabs.chrisvansco.acnhcompanion.model.Fish
import dev.uublabs.chrisvansco.acnhcompanion.ui.activities.InformationActivity
import dev.uublabs.chrisvansco.acnhcompanion.util.FishBugAdapter.BugFishViewHolder
import java.util.*

class FishBugAdapter(private val fragment: Fragment, private val fishList: List<Fish>?, private val bugList: List<Bug>?): RecyclerView.Adapter<BugFishViewHolder>() {
    private var isFish: Boolean = true
    
    init {
        isFish = !fishList.isNullOrEmpty()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BugFishViewHolder {
        val  v = LayoutInflater.from(fragment.context).inflate(R.layout.list_item, parent, false)

        return BugFishViewHolder(v)
    }

    override fun getItemCount(): Int {
        if (isFish) {
            if (fishList != null) {
                Log.d(TAG, "getItemCount: ${fishList.size}")
                return fishList.size
            }
        } else {
            if (bugList != null) {
                return bugList.size
            }
        }
        return 0
    }

    override fun onBindViewHolder(holder: BugFishViewHolder, position: Int) {
        val context: Context = holder.itemCIV.context
        var id = 0
        
        if (isFish) {
            val fish = fishList?.get(position)

            if (fish != null) {
                id = if (fish.name != "Char") {
                    context.resources
                        .getIdentifier(
                            fish.name.replace(" ", "").replace("-", "").toLowerCase(Locale.getDefault()),
                            "drawable",
                            context.packageName
                        )
                } else {
                    context.resources
                        .getIdentifier("charfish", "drawable", context.packageName)
                }

                val fishDictionary = FishDictionary(fragment.context)

                holder.itemCIV.setOnClickListener {
                    val intent = Intent(context, InformationActivity::class.java)
                    intent.putExtra("fish", fish)
                    context.startActivity(intent)
                }
            }
        } else {
            val bug = bugList?.get(position)
            try {
                if (bug != null) {
                    id = context.resources
                        .getIdentifier(
                            bug.name.toLowerCase(Locale.getDefault())
                                .replace(" ", "")
                                .replace("'", "")
                                .replace("-", ""),
                            "drawable",
                            context.packageName)

                    holder.itemCIV.setOnClickListener {
                        val intent = Intent(context, InformationActivity::class.java)
                        intent.putExtra("bug", bug)
                        context.startActivity(intent)
                    }
                }
            } catch (npe: NullPointerException) {
                Log.d(TAG, "onBindViewHolder: ")
            }

        }
        holder.itemCIV.setImageResource(id)
    }

    class BugFishViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemCIV by lazy <CircleImageView>{ view.findViewById(R.id.itemCIV) }
    }

    companion object {
        val TAG = FishBugAdapter::class.java.simpleName
    }

}