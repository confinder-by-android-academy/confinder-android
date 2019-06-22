package ru.semper_viventem.confinder.ui.chips

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.chip.Chip
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.wrapContent


class ChipHolder(context: Context) : RecyclerView.ViewHolder(Chip(context)) {

    private val chip get() = itemView as Chip
    init {
        chip.layoutParams = FlexboxLayoutManager.LayoutParams(wrapContent, wrapContent).apply {
            setMargins(dp(4))
        }
    }

    fun bind(value: String) {
        chip.text = value
    }

}

class ChipsAdapter(
    private val items: List<String>
) : RecyclerView.Adapter<ChipHolder>() {

    override fun getItemCount(): Int =
        items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipHolder =
        ChipHolder(parent.context)

    override fun onBindViewHolder(holder: ChipHolder, position: Int): Unit =
        holder.bind(items[position])

}
