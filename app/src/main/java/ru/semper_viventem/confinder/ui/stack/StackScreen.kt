package ru.semper_viventem.confinder.ui.stack

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.Screen
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper
import java.util.*


class StackScreen : Screen() {

    override val layoutId: Int
        get() = throw UnsupportedOperationException("no_face.jpg")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        RecyclerView(requireActivity()).also {
            val adapter = CardAdapter()
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
            it.layoutManager = SwipeableLayoutManager()
            ItemTouchHelper(SwipeableTouchHelperCallback(object : OnItemSwiped {
                override fun onItemSwiped() {
                    adapter.removeTopItem()
                }
                override fun onItemSwipedRight() = Unit
                override fun onItemSwipedDown() = Unit
                override fun onItemSwipedUp() = Unit
                override fun onItemSwipedLeft() = Unit
            })).attachToRecyclerView(it)
        }

    override fun initView(view: View) {
        // Fuck. I don't want to cast `view` to `RecyclerView`, let init be in `onCreateView`.
    }

    private class CardHolder(cardView: CardView) : RecyclerView.ViewHolder(cardView) {
        init {
            cardView.layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            ).also {
                it.setMargins(dp(48))
            }
            cardView.cardElevation = 24f
            cardView.maxCardElevation = 24f
            cardView.radius = dp(16f)
        }
    }

    private class CardAdapter : RecyclerView.Adapter<CardHolder>() {

        private var items = 10

        override fun getItemCount(): Int =
            items

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
            CardHolder(CardView(parent.context))

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            (holder.itemView as CardView).setCardBackgroundColor(
                arrayOf(Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GRAY).let {
                    it[Random().nextInt(it.size)]
                }
            )
        }

        fun removeTopItem() {
            if (items > 0) {
                items--
                notifyItemRemoved(0)
            }
        }

    }

}
