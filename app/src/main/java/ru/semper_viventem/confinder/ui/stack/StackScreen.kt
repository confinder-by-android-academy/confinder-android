package ru.semper_viventem.confinder.ui.stack

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.TextPaint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.setMargins
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayoutManager
import ru.semper_viventem.confinder.dp
import ru.semper_viventem.confinder.ui.*
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper
import java.util.*
import kotlin.math.max
import kotlin.math.min


class StackScreen : Fragment() {

    private companion object {
        private val colours = arrayOf(Color.BLACK, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.YELLOW, Color.GRAY)
        private val repository = Random()

        fun nextColour() = colours[repository.nextInt(colours.size)]
        fun nextName() = repository.nextLong().toString(36)
        fun nextInterests() = List(repository.nextInt(20)) {
            repository.nextLong().toString(36)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        RecyclerView(requireActivity()).also {
            val adapter = CardAdapter()
            it.adapter = adapter
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

    private class CardHolder(
        context: Context, chipsPool: RecyclerView.RecycledViewPool
    ) : RecyclerView.ViewHolder(CardView(context)) {
        private val cardView get() = itemView as CardView
        val photoView: ImageView
        val nameView: TextView
        val tagsView: RecyclerView
        init { with(cardView) {
            layoutParams = RecyclerLP(matchParent, matchParent).apply { setMargins(dp(48)) }
            cardElevation = 24f
            maxCardElevation = 24f
            radius = dp(16f)

            add(FrameLP(matchParent, matchParent), ::FuckLinearLayout) {
                orientation = LinearLayout.VERTICAL
                weightSum = 2f

                add(LinearLP(matchParent, 0, 1f), ::ImageView) {
                    photoView = this
                }

                add(LinearLP(matchParent, wrapContent), ::TextView) {
                    nameView = this
                    setPadding(dp(16))
                    textSize = 32f
                    setTextColor(Color.BLACK)
                }

                add(LinearLP(matchParent, wrapContent), ::RecyclerView) {
                    tagsView = this
                    setPadding(dp(12)) // 16dp - 4dp chip margin
                    layoutManager = FlexboxLayoutManager(context)
                    setRecycledViewPool(chipsPool)
                }

            }
        } }

        fun bind() {
            val name = nextName()
            photoView.setImageDrawable(object : Drawable() {
                private val col0 = nextColour()
                private val col1 = nextColour()
                val fillPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                    textSize = dp(36f)
                }
                val strokePaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
                    textSize = fillPaint.textSize
                    style = Paint.Style.STROKE
                    strokeWidth = dp(2f)
                    color = nextColour()
                }
                override fun draw(canvas: Canvas) {
                    canvas.drawColor(nextColour())
                    canvas.drawText("Йа $name", 10f, 100f, fillPaint)
                    canvas.drawText("Йа $name", 10f, 100f, strokePaint)
                }
                override fun setAlpha(alpha: Int) = Unit
                override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
                override fun setColorFilter(colorFilter: ColorFilter?) = Unit
                override fun onBoundsChange(bounds: Rect) {
                    fillPaint.shader = LinearGradient(0f, 0f, bounds.width().toFloat(), dp(36f), col0, col1, Shader.TileMode.CLAMP)
                }
            })
            nameView.text = name
            tagsView.swapAdapter(ChipsAdapter(nextInterests()), false)
        }
    }

    private class CardAdapter : RecyclerView.Adapter<CardHolder>() {

        private var items = 10
        val chipsPool = RecyclerView.RecycledViewPool()

        override fun getItemCount(): Int =
            items

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardHolder =
            CardHolder(parent.context, chipsPool)

        override fun onBindViewHolder(holder: CardHolder, position: Int) {
            holder.bind()
        }

        fun removeTopItem() {
            if (items > 0) {
                items--
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
