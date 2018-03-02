package com.kotlin.test.ui.fragment

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import com.kotlin.test.R
import com.kotlin.test.adapter.CommonAdapter
import com.kotlin.test.adapter.base.BaseViewHolder
import com.kotlin.test.base.BaseFragment
import com.kotlin.test.entity.Question
import com.kotlin.test.http.HttpClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @Title HomeFragment
 * @Description:
 * @Author: alvin
 * @Date: 2017/6/9.16:53
 * @E-mail: 49467306@qq.com
 */
class HomeFragment : BaseFragment() {
    private var mTitle: String = ""
    private val mList: ArrayList<Question> by lazy {
        ArrayList<Question>()
    }
    private var mAdapter: CommonAdapter<Question>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (this.arguments != null) {
            mTitle = arguments.getString("title")
        }
    }

    override fun getFragmentView(): View? {
        return null
    }

    override fun getFragmentViewByLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onClick(v: View?) {
    }

    override fun initData() {
        id_recyclerView.layoutManager = LinearLayoutManager(context)
        id_recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        if (mAdapter == null) {
            mAdapter = object : CommonAdapter<Question>(android.R.layout.simple_list_item_1, mList) {
                override fun convert(holder: BaseViewHolder, item: Question, position: Int) {
                    var view = holder.itemView as TextView
                    view.text = item.title
                }
            }
        }
        id_recyclerView.adapter = mAdapter

        HttpClient().service.getQuestionsList(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    mList.clear()
                    it.data.map { mList.add(it) }
                    mAdapter!!.notifyDataSetChanged()
                })
    }

    companion object {
        fun newInstance(str: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString("title", str)
            fragment.arguments = args
            return fragment
        }
    }
}