package ru.semper_viventem.confinder.ui.chips

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setMargins
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.chip.Chip
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.wrapContent


class ChipHolder(
    context: Context,
    private val removeListener: ((Int) -> Unit)?
) : RecyclerView.ViewHolder(Chip(context)), View.OnClickListener {

    private val chip get() = itemView as Chip
    init { with(chip) {
        layoutParams = FlexboxLayoutManager.LayoutParams(wrapContent, wrapContent).apply { setMargins(dp(4)) }
        if (removeListener != null) {
            isCloseIconVisible = true
            setOnCloseIconClickListener(this@ChipHolder)
        }
    } }

    fun bind(value: String) {
        chip.text = value
    }

    override fun onClick(v: View?) {
        removeListener!!(adapterPosition)
    }

}

class ChipsAdapter(
    items: List<String>,
    private val canRemove: Boolean
) : RecyclerView.Adapter<ChipHolder>(), (Int) -> Unit {

    private var _items: ArrayList<String> = ArrayList(items)

    var items: List<String>
        get() = _items
        set(value) {
            _items = ArrayList(value)
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int =
        _items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipHolder =
        ChipHolder(parent.context, if (canRemove) this else null)

    override fun onBindViewHolder(holder: ChipHolder, position: Int): Unit =
        holder.bind(_items[position])

    override fun invoke(index: Int) {
        _items.removeAt(index)
        notifyItemRemoved(index)
    }

    fun add(value: String) {
        val last = _items.size
        _items.add(value)
        notifyItemInserted(last)
    }

}
