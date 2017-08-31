package com.kotlin.test.util

import android.util.Base64
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * @Title RsaUtil
 * @Description:
 * @Author: alvin
 * @Date: 2017/8/31.17:08
 * @E-mail: 49467306@qq.com
 */
class RsaUtil {


    companion object {
        private var mRSAPublicKey: RSAPublicKey? = null
        fun loadPublicKey(pubkey: String) {

            try {
                var buffer = Base64.decode(pubkey, Base64.DEFAULT)
                var keyFactory = KeyFactory.getInstance("RSA")
                var keySpec = X509EncodedKeySpec(buffer)
                mRSAPublicKey = keyFactory.generatePublic(keySpec) as RSAPublicKey
            } catch(e: Exception) {
                e.printStackTrace()
            }

        }

        fun encryptWithRSA(data: String): String {
            if (mRSAPublicKey == null)
                throw NullPointerException("encrypt PublicKey is null !")
            var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, mRSAPublicKey)
            var output = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
            var encode = Base64.encode(output, Base64.DEFAULT)
            return Base64.encodeToString(encode, Base64.DEFAULT)

        }
    }
}