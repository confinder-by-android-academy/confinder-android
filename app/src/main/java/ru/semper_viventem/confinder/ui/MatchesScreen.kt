package ru.semper_viventem.confinder.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.setMargins
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.chips.ChipsAdapter


class MatchesScreen : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        RecyclerView(requireContext()).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MatchesAdapter()
        }

    private class MatchHolder(
        context: Context, chipsPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(RelativeLayout(context)) {
        private val photoView: ImageView
        private val nameView: TextView
        private val descriptionView: TextView
        private val interestsView: RecyclerView
        init { with(itemView as RelativeLayout) {
            layoutParams = RecyclerLP(matchParent, wrapContent)

            photoView = add(RelativeLP(wrapContent, wrapContent).apply {
                setMargins(dp(8))
            }, ::ImageView) {
                id = 1
            }

            nameView = add(RelativeLP(matchParent, wrapContent).apply {
                addRule(RelativeLayout.END_OF, 1)
                addRule(RelativeLayout.ALIGN_TOP, 1)
                setMargins(0, 0, 0, dp(8))
                marginEnd = dp(8)
            }, ::TextView) {
                id = 2
                textSize = 24f
                setTextColor(Color.BLACK)
            }

            descriptionView = add(RelativeLP(matchParent, wrapContent).apply {
                addRule(RelativeLayout.END_OF, 1)
                addRule(RelativeLayout.BELOW, 2)
                addRule(RelativeLayout.ALIGN_BOTTOM, 1)
                marginEnd = dp(8)
            }, ::TextView) {
                id = 3
                textSize = 18f
                setTextColor(Color.BLACK)
            }

            interestsView = add(RelativeLP(matchParent, wrapContent).apply {
                addRule(RelativeLayout.BELOW, 3)
                setMargins(dp(4))  // 8dp - 4dp chip margin
            }, ::RecyclerView) {
                layoutManager = FlexboxLayoutManager(context)
                setRecycledViewPool(chipsPool)
                overScrollMode = View.OVER_SCROLL_NEVER
            }

        } }

        fun bind() {
            photoView.setImageResource(ru.semper_viventem.confinder.R.mipmap.ic_launcher_round)
            nameView.text = "A very cool person"
            descriptionView.text = "Interested in some cool things"
            // fixme: flexbox is ultimately slow
            interestsView.swapAdapter(ChipsAdapter(listOf("interest", "zzz", "sss", "xxx")), true)
        }
    }

    private class MatchesAdapter() : RecyclerView.Adapter<MatchHolder>() {

        private val chipsPool = RecyclerView.RecycledViewPool()

        override fun getItemCount(): Int =
            10

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchHolder =
            MatchHolder(parent.context, chipsPool)

        override fun onBindViewHolder(holder: MatchHolder, position: Int) {
            holder.bind()
        }

    }

}
