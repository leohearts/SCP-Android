package info.free.scp.view.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import info.free.scp.R
import info.free.scp.bean.ScpModel
import info.free.scp.view.base.BaseAdapter

/**
 * Created by zhufree on 2018/10/25.
 *
 */

class SearchResultAdapter(mContext: Context, dataList: MutableList<ScpModel?>)
    : BaseAdapter<SearchHolder, ScpModel?>(mContext, dataList) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_search, parent, false)
        //        val newHolder = CategoryHolder(view)
        view?.setOnLongClickListener(this)
        view?.setOnClickListener(this)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.itemView.tag = position
        holder.setData(dataList[position]?.title)
    }
}