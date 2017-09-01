package com.kotlin.test.util

import android.util.Base64
import java.security.KeyFactory
import java.security.KeyFactorySpi
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.Cipher

/**
 * @Title RsaUtil
 * @Description:Rsa 算法公钥加密
 * @Author: alvin
 * @Date: 2017/8/31.17:08
 * @E-mail: 49467306@qq.com
 */
class RsaUtil {


    companion object {
        private val PUBLIC_KEY: String = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8DBx61vl/L2+3ixl1KR6" +
                "eepdFl5gqOyL2Y+M8PmHkKWUXjhcAUaYmFZ//llIBYlrahh1LpDrObA9vRoEEtpx" +
                "PB65xE6oJgNM+kJOItRVpIwzZ76oLwWg0VY5/G2hTPoSclMAZF7UvKZebE0nH0V4" +
                "uFqve7JrKwBGBOtjMEnvuxNspkVD9nZbmthm41kwMyfjN2cOiqw0DAVKxkRtu3zw" +
                "yFYne0qwHFdnNp4vLA1Yd1XKl324TODEP0zSt3frSw559b2l8lHpSP0MxJx4Iobm" +
                "1AU2OyQ8REXGBKFMBe869XV0czVZFSAA7hHW2KywulDZQWmJYFsnSxV7U2TLgg0C" +
                "vwIDAQAB"


        fun encryptWithRSA(data: String): String {
            var buffer = Base64.decode(PUBLIC_KEY.toByteArray(Charsets.UTF_8), Base64.DEFAULT)
            var keySpec = X509EncodedKeySpec(buffer)
            var keyFactory = KeyFactory.getInstance("RSA")
            var publicKey = keyFactory.generatePublic(keySpec) as RSAPublicKey
            var cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
            cipher.init(Cipher.ENCRYPT_MODE, publicKey)
            var output = cipher.doFinal(Base64.decode(data.toByteArray(Charsets.UTF_8), Base64.DEFAULT))
            var encode = Base64.encode(output, Base64.DEFAULT)
            return Base64.encodeToString(encode, Base64.DEFAULT)

        }
    }
}