package com.kotlin.test.entity

import java.util.ArrayList

/**
 * @Title Question
 * @Description:
 * @author: alvin
 * @Date: 2018/2/28.16:30
 * @E-mail: 49467306@qq.com
 */

data class Question(
        val id: Int,
        val userId: Int,
        var title: String,
        var content: String,
        val createTime: String,
        var answers: ArrayList<Answer>
)
