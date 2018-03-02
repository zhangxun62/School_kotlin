package com.kotlin.test.entity

/**
 * @Title Answer
 * @Description:
 * @author: alvin
 * @Date: 2018/3/1.10:17
 * @E-mail: 49467306@qq.com
 */

data class Answer(
        val id: Int,
        val userId: Int,
        val questionId: Int,
        var content: String,
        val createTime: String
)