package com.kotlin.test.util

import android.util.Base64
import java.security.*
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

/**
 * @Title RsaUtil
 * @Description:Rsa 算法公钥加密
 * @Author: alvin
 * @Date: 2017/8/31.17:08
 * @E-mail: 49467306@qq.com
 */
class RsaUtil {


    companion object {
        private val PUBLIC_KEY: String = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAn/72PhcXW5r5re+vFdsKqd1C3tzft4YhIdot7U7d+DE7niHsHJg2DgPbxr7o5Mghl0dlRQeR0kqmI/hwsTon0MsBjPMxPikl9nASOTzZVUpLiFOfFdh2n0eCPISkF+IHceBIimX9U85WmGpsHMzNeyp+EW60fQzfy8qq5ifhOJK8R88YjF4KMM0Q25I+DDU36siEhdcf2pRyXMQsuhWfrOxo5TikvTYqJ+PTI1NdPyoWNB8v625BRStYVznKzdm/sVaJGlI7fkSpyKrj9U1DlLckRh5hilhSC2TbxYxAiEak1xjf62Ivp867/nUKpsKwf7AnpFQKEaFM7S1pNzWH2QIDAQAB"


        fun encryptWithRSA(data: String): String? {
            return try {
                var buffer = Base64.decode(PUBLIC_KEY, Base64.DEFAULT)
                var keySpec = X509EncodedKeySpec(buffer)
                var keyFactory = KeyFactory.getInstance("RSA")
                var publicKey = keyFactory.generatePublic(keySpec) as RSAPublicKey
                Base64.encodeToString(rsaEncode(publicKey, data.toByteArray()), Base64.DEFAULT)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        }

        /**
         * 使用私钥进行解密
         */
        fun decryptByPrivateKey(encrypted: String?): String? {
            if (encrypted == null)
                return null
            // 得到私钥
            return try {
                var buffer = Base64.decode("", Base64.DEFAULT)
                val keySpec = PKCS8EncodedKeySpec(buffer)
                val kf = KeyFactory.getInstance("RSA")
                val keyPrivate = kf.generatePrivate(keySpec)

                rsaDecode(keyPrivate, Base64.decode(encrypted, Base64.DEFAULT))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        /**
         * 加密，三步走。
         *
         * @param key
         * @param plainText
         * @return
         */
        private fun rsaEncode(key: PublicKey, plainText: ByteArray): ByteArray? {
            try {
                val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
                cipher.init(Cipher.ENCRYPT_MODE, key)
                return cipher.doFinal(plainText)
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            }
            return null

        }

        /**
         * 解密，三步走。
         *
         * @param key
         * @param encodedText
         * @return
         */
        private fun rsaDecode(key: PrivateKey, encodedText: ByteArray): String? {

            try {
                val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding")
                cipher.init(Cipher.DECRYPT_MODE, key)
                return String(cipher.doFinal(encodedText))
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: NoSuchPaddingException) {
                e.printStackTrace()
            } catch (e: InvalidKeyException) {
                e.printStackTrace()
            } catch (e: IllegalBlockSizeException) {
                e.printStackTrace()
            } catch (e: BadPaddingException) {
                e.printStackTrace()
            }
            return null

        }
    }
}