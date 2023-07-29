package io.github.xtvj.common.customView.addItemView

import android.annotation.SuppressLint
import android.content.Context
import android.os.Vibrator
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import io.github.xtvj.common.R
import io.github.xtvj.common.utils.LogUtils.logs

@SuppressLint("NotifyDataSetChanged")
open class AddItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    internal companion object {
        const val TAG = "AddItemView"
        const val VIEW_TYPE_IMAGE_ITEM = 1
        const val VIEW_TYPE_ADD_ITEM = 2
    }

    //数据要继承AddItemViewBean
    private val mItems = mutableListOf<AddItemViewBean>()
    private val mAdapter = InnerAdapter()

    private var mItemViewDelegate: AddItemView.ItemViewDelegate<*>? = InnerItemViewDelegate()
    private var mAddViewDelegate: AddItemView.AddViewDelegate<*>? = InnerAddItemViewDelegate()


    /**
     * 点击事件
     * @param type 点击事件的类型，可用于Item内的不同View的判断
     */
    var onItemViewClickListener: ((position: Int, bean: AddItemViewBean, type: Int) -> Unit)? = null
    var onAddViewClickListener: ((position: Int) -> Unit)? = null

    /** 拖拽排序 */
    private var mItemTouchHelper: ItemTouchHelper? = null

    private val mVibrator by lazy { context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator }

    /** 是否开启震动 */
    var enableVibrate: Boolean = true

    /** 震动时长 */
    var vibrateDuration: Long = 100L

    /** 动画的相关配置 */
    var enableAnimation: Boolean = true
    var animDuration: Long = 100
    var scaleValue: Float = 1.1f

    /** 总共的itemCount */
    var maxCount = Int.MAX_VALUE
        set(value) {
            adapter?.notifyDataSetChanged()
            field = value
        }

    /** 一排的itemCount */
    var spanCount = 3
        set(value) {
            layoutManager = GridLayoutManager(context, value)
            field = value
        }

    /** item之间的间距 */
    var itemGap: Int = 0
        set(value) {
            addItemDecoration(ItemGapDecoration(value), 0)
            field = value
        }

    /** 是否开启拖拽排序 */
    var enableDrag: Boolean = true
        set(value) {
            enableDrag(value)
            field = value
        }

    /**
     *
     */
    open fun dp2px(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            resources.displayMetrics
        )
    }

    init {
        this.layoutManager = GridLayoutManager(context, spanCount)
        this.adapter = mAdapter

        initAttrs(attrs)
    }

    private fun initAttrs(attrs: AttributeSet?) {
        if (attrs == null) return

        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.AddItemView)
        maxCount = typeArray.getInt(R.styleable.AddItemView_aiv_maxCount, Int.MAX_VALUE)
        spanCount = typeArray.getInt(R.styleable.AddItemView_aiv_spanCount, 3)
        itemGap = typeArray.getDimension(R.styleable.AddItemView_aiv_itemGap, dp2px(1f)).toInt()

        enableDrag = typeArray.getBoolean(R.styleable.AddItemView_aiv_enableDrag, true)
        enableVibrate = typeArray.getBoolean(R.styleable.AddItemView_aiv_enableVibrate, true)
        vibrateDuration = typeArray.getInt(R.styleable.AddItemView_aiv_vibrateDuration, 100).toLong()

        enableAnimation = typeArray.getBoolean(R.styleable.AddItemView_aiv_enableAnimation, true)
        animDuration = typeArray.getInt(R.styleable.AddItemView_aiv_animDuration, 100).toLong()
        scaleValue = typeArray.getFloat(R.styleable.AddItemView_aiv_scaleValue, 1.1f)
        typeArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (isInEditMode) {
            this.layoutManager = GridLayoutManager(context, spanCount)
            this.adapter = mAdapter
            registerAddViewDelegate(InnerAddItemViewDelegate())
        }
    }

    /**
     * 设置数据
     */
    fun setItems(paths: List<AddItemViewBean>) {
        mItems.clear()
        if (paths.size > maxCount) {
            val subList = paths.subList(0, maxCount)
            mItems.addAll(subList)
        } else {
            mItems.addAll(paths)
        }
        adapter?.notifyDataSetChanged()
    }

    /**
     * 添加一个item
     */
    fun addItem(path: AddItemViewBean) {
//        val temp = mItems.find {
//            it.order == path.order
//        }
//        if (temp != null) {
//            temp.select = path.select
//            temp.order = path.order
//            adapter?.notifyItemChanged(mItems.indexOf(temp))
//        } else {
            mItems.add(path)
            if (mItems.size == maxCount) {
                //最后一个由添加项目的布局改为项目信息的布局
                adapter?.notifyItemChanged(mItems.lastIndex)
            } else {
                adapter?.notifyItemInserted(mItems.lastIndex)
            }
//        }
    }

    /**
     * 添加一序列的item
     * 未确保唯一性
     */
    fun addItem(paths: List<AddItemViewBean>) {
        if (mItems.size + paths.size > maxCount) {
            val remainCount = maxCount - mItems.size
            val subList = paths.subList(0, remainCount)
            mItems.addAll(subList)
        } else {
            mItems.addAll(paths)
        }
        adapter?.notifyDataSetChanged()
    }

    /**
     * 删除一个item
     */
    fun removeItem(path: AddItemViewBean) {
        val index = mItems.indexOf(path)
        if (index == -1) return
        mItems.removeAt(index)
        adapter?.notifyItemRemoved(index)
    }

    /**
     * 删除一个item
     */
    fun removeItem(position: Int) {
        val file = mItems.removeAt(position)
        adapter?.notifyItemRemoved(position)
    }

    /**
     * 获取数据集
     */
    fun getItems() = mItems

    /**
     * 注册ItemView的代理
     */
    fun registerItemViewDelegate(delegate: ItemViewDelegate<*>) {
        this.mItemViewDelegate = delegate
        adapter?.notifyDataSetChanged()
    }

    /**
     * 注册AddView的代理
     */
    fun registerAddViewDelegate(delegate: AddViewDelegate<*>) {
        this.mAddViewDelegate = delegate
        adapter?.notifyDataSetChanged()
    }

    /**
     * Rv的Adapter
     */
    internal inner class InnerAdapter : Adapter<ViewHolder>() {

        override fun getItemCount() = if (mItems.size >= maxCount)
            maxCount
        else
            mItems.size + 1

        override fun getItemViewType(position: Int): Int {
            return if (position == itemCount - 1 && mItems.size < maxCount) {
                VIEW_TYPE_ADD_ITEM
            } else {
                VIEW_TYPE_IMAGE_ITEM
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            val viewDelegate = if (viewType == VIEW_TYPE_ADD_ITEM)
                mAddViewDelegate!!
            else
                mItemViewDelegate!!
            return viewDelegate.onCreateViewHolder(parent)
        }

        @Suppress("UNCHECKED_CAST")
        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            val viewType = getItemViewType(position)
            val viewDelegate = if (viewType == VIEW_TYPE_ADD_ITEM)
                mAddViewDelegate!!
            else
                mItemViewDelegate!!

            (viewDelegate as InnerViewDelegate<ViewHolder>).onBindViewHolder(
                holder,
                position,
                this@AddItemView
            )

            holder.itemView.setOnClickListener {
                val adapterPosition = holder.bindingAdapterPosition
                if (viewType == VIEW_TYPE_ADD_ITEM) {
                    onAddViewClickListener?.invoke(adapterPosition)
                } else {
                    onItemViewClickListener?.invoke(adapterPosition, mItems[adapterPosition], -1)
                }
            }
        }
    }

    /**
     * ViewHolder的基类代理
     */
    internal interface InnerViewDelegate<VH : ViewHolder> {
        fun onCreateViewHolder(parent: ViewGroup): VH
        fun onBindViewHolder(
            holder: VH,
            position: Int,
            addItemView: AddItemView
        )
    }

    /**
     * ItemView的代理
     */
    abstract class ItemViewDelegate<VH : ViewHolder> : InnerViewDelegate<VH> {
        override fun onBindViewHolder(
            holder: VH,
            position: Int,
            addItemView: AddItemView
        ) {
            val bean = addItemView.getItems()[holder.bindingAdapterPosition]
            onBindViewHolder(holder, bean, addItemView)
        }

        abstract fun onBindViewHolder(
            holder: VH,
            bean: AddItemViewBean,
            addItemView: AddItemView
        )
    }

    /**
     * AddView的代理
     */
    abstract class AddViewDelegate<VH : ViewHolder> : InnerViewDelegate<VH>

    /**
     * 拖拽的开关
     */
    private fun enableDrag(enable: Boolean) {
        logs("是否启用拖拽：$enable")
        if (!enable) {
            mItemTouchHelper?.attachToRecyclerView(null)
            return
        }
        mItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder
            ): Int {
                if (dragTargetIsAddView(viewHolder)) {
                    return 0
                }

                val dragFlags =
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END
                val swipeFlags = 0
                return makeMovementFlags(
                    dragFlags,
                    swipeFlags
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                if (dragTargetIsAddView(target)) {
                    return false
                }

                val fromPosition = viewHolder.bindingAdapterPosition
                val toPosition = target.bindingAdapterPosition
                if (fromPosition != toPosition) {
                    vibrate()
//                    java.util.Collections.swap(mItems, fromPosition, toPosition)
                    val movingItem = mItems[fromPosition]
                    mItems.removeAt(fromPosition)
                    mItems.add(toPosition, movingItem)
                    adapter?.notifyItemMoved(fromPosition, toPosition)
                    return true
                }

                return true
            }

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            }

            override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                    vibrate()
                    startScaleAnim(viewHolder)
                }
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                cancelScaleAnim(viewHolder)
            }
        })
        mItemTouchHelper?.attachToRecyclerView(this)
    }

    /**
     * 开始缩放动画
     */
    private fun startScaleAnim(viewHolder: ViewHolder?) {
        if (!enableAnimation) return

        val itemView = viewHolder?.itemView ?: return
        itemView.animate()
            .scaleX(scaleValue)
            .scaleY(scaleValue)
            .setDuration(animDuration)
            .start()
    }

    /**
     * 取消缩放动画
     */
    private fun cancelScaleAnim(viewHolder: ViewHolder?) {
        if (!enableAnimation) return

        val itemView = viewHolder?.itemView ?: return
        itemView.animate()
            .scaleX(1.0f)
            .scaleY(1.0f)
            .setDuration(animDuration)
            .start()
    }

    /**
     * 拖拽的是否是AddView
     */
    private fun dragTargetIsAddView(
        target: ViewHolder
    ): Boolean {
        val adapter = adapter ?: return false

        if (mItems.size < maxCount && target.bindingAdapterPosition == adapter.itemCount - 1) {
            return true
        }

        return false
    }

    /**
     * 震动的方法
     */
    private fun vibrate() {
        if (!enableVibrate) return

        mVibrator.vibrate(vibrateDuration)
    }

//    override fun onSaveInstanceState(): Parcelable? {
//        val superState = super.onSaveInstanceState() ?: return null
//        val savedState = SaveState(superState)
//
//        return savedState
//    }
//
//    override fun onRestoreInstanceState(state: Parcelable?) {
//        if (state is SaveState){
//
//        }
//        super.onRestoreInstanceState(state)
//    }
//
//    class SaveState : AbsSavedState {
//
//        constructor(superState: Parcelable) : super(superState)
//        constructor(source: Parcel, loader: ClassLoader?) : super(source, loader)
//
//    }
}