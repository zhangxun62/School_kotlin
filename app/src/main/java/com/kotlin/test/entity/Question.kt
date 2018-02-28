package com.kotlin.test.entity

/**
 * @Title Question
 * @Description:
 * @author: alvin
 * @Date: 2018/2/28.16:30
 * @E-mail: 49467306@qq.com
 */
data class Question(
        var userId: Int,
        var title: String,
        var content: String,
        var createTime: String
)
