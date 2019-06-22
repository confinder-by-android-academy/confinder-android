package ru.semper_viventem.confinder.ui.stack

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import com.squareup.picasso.Picasso
import retrofit2.Call
import ru.semper_viventem.confinder.data.Profile
import ru.semper_viventem.confinder.data.gateway.SwipeListGateway
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.*
import ru.semper_viventem.confinder.ui.chips.ChipsAdapter
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper
import kotlin.math.max
import kotlin.math.min


class StackScreen : Fragment() {

    private var profiles: List<Profile>? = null
    private var adapter: CardAdapter? = null
    private var currentRequest: Call<*>? = null

    init {
        retainInstance = true
    }

    override fun onStart() {
        super.onStart()

        if (profiles == null) {
            currentRequest = SwipeListGateway.getSwipeList({ profiles ->
                this.profiles = profiles
                adapter?.items = profiles
            }, { e ->
                e.printStackTrace()
                Toast.makeText(context, "Отстой.", Toast.LENGTH_SHORT).show()
            })
        }
    }

    override fun onStop() {
        currentRequest?.let {
            it.cancel()
            currentRequest = null
        }

        super.onStop()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        RecyclerView(requireActivity()).also {
            val adapter = CardAdapter(profiles ?: emptyList())
            this.adapter = adapter
            it.adapter = adapter
            it.layoutManager = SwipeableLayoutManager()
            ItemTouchHelper(object : SwipeableTouchHelperCallback(object : OnItemSwiped {
                override fun onItemSwiped() {
                    adapter.removeTopItem()
                }
                override fun onItemSwipedDown() = Unit
                override fun onItemSwipedUp() = Unit
                override fun onItemSwipedLeft() {
                    if (adapter.items.isNotEmpty()) {
                        // TODO: dislike adapter.items[0]
                    }
                }
                override fun onItemSwipedRight() {
                    if (adapter.items.isNotEmpty()) {
                        // TODO: like adapter.items[0]
                    }
                }
            }) {
                override fun getAllowedSwipeDirectionsMovementFlags(viewHolder: RecyclerView.ViewHolder): Int =
                    ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
            }).attachToRecyclerView(it)
        }

    override fun onDestroyView() {
        adapter = null
        super.onDestroyView()
    }

    private class CardHolder(
        context: Context, chipsPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(CardView(context)) {
        private val cardView get() = itemView as CardView
        val photoView: ImageView
        val nameView: TextView
        val interestsView: RecyclerView
        init { with(cardView) {
            layoutParams = RecyclerLP(matchParent, matchParent).apply { setMargins(dp(48)) }
            cardElevation = 24f
            maxCardElevation = 24f
            radius = dp(16f)

            add(FrameLP(matchParent, matchParent), ::FuckLinearLayout) {
                orientation = LinearLayout.VERTICAL
                weightSum = 2f

                photoView = add(LinearLP(matchParent, 0, 1f), ::ImageView)

                nameView = add(LinearLP(matchParent, wrapContent), ::TextView) {
                    setPadding(dp(16))
                    textSize = 32f
                    setTextColor(Color.BLACK)
                }

                interestsView = add(LinearLP(matchParent, wrapContent), ::RecyclerView) {
                    setPadding(dp(12)) // 16dp - 4dp chip margin
                    layoutManager = FlexboxLayoutManager(context)
                    setRecycledViewPool(chipsPool)
                    overScrollMode = View.OVER_SCROLL_NEVER
                }

            }
        } }

        fun bind(profile: Profile) {
            Picasso.get().load(profile.photo).into(photoView)
            nameView.text = "%s %s".format(profile.firstName, profile.lastName)
            interestsView.swapAdapter(ChipsAdapter(profile.tags, canRemove = false), true)
        }
    }

    private class CardAdapter(
        profiles: List<Profile>
    ) : RecyclerView.Adapter<CardHolder>() {

        var _items = ArrayList(profiles)

        var items: List<Profile>
            get() = _items
            set(value) {
                _items = ArrayList(value)
                notifyDataSetChanged()
            }

        val chipsPool = RecyclerView.RecycledViewPool()

        override fun getItemCount(): Int =
            _items.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
            CardHolder(parent.context, chipsPool)

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            holder.bind(_items[position])
        }

        fun removeTopItem() {
            if (_items.size > 0) {
                _items.removeAt(0)
                notifyItemRemoved(0)
            }
        }

    }

}

private class FuckLinearLayout(context: Context?) : LinearLayout(context) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthAvailable = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val heightAvailable = MeasureSpec.getSize(heightMeasureSpec)

        var heightLeft = heightAvailable
        forEachChild { child, lp ->
            if (lp.weight == 0f) {
                val hSpec = MeasureSpec.makeMeasureSpec(max(0, widthAvailable - lp.leftMargin - lp.rightMargin), widthMode)
                val vMargins = lp.topMargin + lp.bottomMargin
                val vSpec = MeasureSpec.makeMeasureSpec(heightLeft - vMargins, MeasureSpec.AT_MOST)
                child.measure(hSpec, vSpec)
                heightLeft -= child.measuredHeight + vMargins
            }
        }
        forEachChild { child, lp ->
            if (lp.weight != 0f) {
                val hSpec = MeasureSpec.makeMeasureSpec(max(0, widthAvailable - lp.leftMargin - lp.rightMargin), widthMode)
                val vMargins = lp.topMargin + lp.bottomMargin
                val vSpec = MeasureSpec.makeMeasureSpec(min(heightLeft, (heightAvailable * lp.weight / weightSum).toInt()) - vMargins, MeasureSpec.EXACTLY)
                child.measure(hSpec, vSpec)
                heightLeft -= child.measuredHeight + vMargins
            }
        }
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec))
    }

    private inline fun forEachChild(func: (View, LayoutParams) -> Unit): Unit = repeat(childCount) {
        val child = getChildAt(it)
        func(child, child.layoutParams as LayoutParams)
    }
}
