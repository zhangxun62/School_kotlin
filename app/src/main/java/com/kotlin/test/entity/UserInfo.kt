package com.kotlin.test.entity

/**
 * @Title UserInfo
 * @Description:
 * @Author: alvin
 * @Date: 2017/9/21.10:00
 * @E-mail: 49467306@qq.com
 */
data class UserInfo(
        var userId: Int,
        var nickName: String,
        var age: Int,
        var gender: Int,
        var phoneNumber: String,
        var uuid: String
)
