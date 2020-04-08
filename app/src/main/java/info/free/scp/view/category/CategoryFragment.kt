package info.free.scp.view.category

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import info.free.scp.R
import info.free.scp.SCPConstants
import info.free.scp.SCPConstants.Category.SERIES
import info.free.scp.SCPConstants.Category.SERIES_CN
import info.free.scp.SCPConstants.Category.TALES
import info.free.scp.SCPConstants.Category.TALES_CN
import info.free.scp.util.PreferenceUtil
import info.free.scp.view.base.BaseAdapter
import info.free.scp.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_category.*

/**
 * 二级目录，点进去还有一层目录
 */
class CategoryFragment : BaseFragment() {
    private var categoryType = -1
    private var subPosition = -1
    private val categoryCount = PreferenceUtil.getCategoryCount()
    private val categoryList: MutableList<Any> = emptyList<Any>().toMutableList()
    private var categoryAdapter: CategoryAdapter? = null
    private var currentCategoryPosition = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 根据categoryType设置layoutManager

//        val lm = LinearLayoutManager(mContext, VERTICAL, false)
//        rv_category_list?.layoutManager = lm
        initData()
    }

    private fun initData() {
        categoryType = arguments?.getInt("category_type") ?: -1
        subPosition = arguments?.getInt("sub_position") ?: -1
        if (categoryType in arrayOf(SERIES, SERIES_CN, TALES, TALES_CN)) {
            val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            rv_category_list.layoutManager = layoutManager
        } else {
            val layoutManager = LinearLayoutManager(mContext, VERTICAL, false)
            rv_category_list.layoutManager = layoutManager
        }
        categoryList.clear()
        // 一级目录
        when (categoryType) {
            SERIES -> {
                // count = 100 0..10->001~900
                categoryList.addAll((0 until (1000 / categoryCount)).map { (it + subPosition * 10) * categoryCount })
            }
            SERIES_CN -> {
                categoryList.addAll((0 until (1000 / categoryCount)).map { (it + subPosition * 10) * categoryCount })
            }
            SCPConstants.Category.SCP_ARCHIVES -> {
                categoryList.addAll(arrayOf("已解明SCP", "已解明SCP-CN", "已归档SCP", "废弃SCP", "删除SCP"))
            }
            TALES -> {
                // 1021
                categoryList.addAll(arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0-9"))
            }
            TALES_CN -> {
                // 1021
                categoryList.addAll(arrayOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                        "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "0-9"))
            }
            SCPConstants.Category.EVENT -> {
                categoryList.addAll(arrayOf("实验记录", "探索报告", "事故/事件报告", "访谈记录", "独立补充材料"))
            }
            SCPConstants.Category.TALES_BY_TIME -> {
                categoryList.addAll(arrayOf("2014", "2015", "2016", "2017", "2018"))
            }
            SCPConstants.Category.SCP_INTERNATIONAL -> {
                categoryList.addAll(arrayOf("俄国分部", "韩国分部", "法国分部", "波兰分部", "西班牙分部",
                        "泰国分部", "日本分部", "德国分部", "意大利分部", "乌克兰分部", "葡萄牙语分部",
                        "捷克分部", "非官方分部"))
            }
        }

        if (categoryAdapter == null) {
            mContext?.let {
                categoryAdapter = CategoryAdapter(it, categoryType, categoryList)
                rv_category_list?.adapter = categoryAdapter
                categoryAdapter?.mOnItemClickListener = object : BaseAdapter.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        Log.i(tag, "onItemClick")
                        currentCategoryPosition = position
                        val subCateIntent = Intent(it, ScpListActivity::class.java)
                        subCateIntent.putExtra("category_type", categoryType)
                        subCateIntent.putExtra("click_position", (subPosition * 10) + position)
                        startActivity(subCateIntent)
                    }
                }
            }
        } else {
            categoryAdapter?.notifyDataSetChanged()
            rv_category_list?.adapter = categoryAdapter
            if (currentCategoryPosition > 0) {
                rv_category_list?.scrollToPosition(currentCategoryPosition)
            }
        }
    }

    fun reverseScpList() {
        categoryList.reverse()
        categoryAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        categoryAdapter?.notifyDataSetChanged()
    }

    companion object {

        fun newInstance(categoryType: Int, subPosition: Int): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putInt("category_type", categoryType)
            args.putInt("sub_position", subPosition)
//            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}