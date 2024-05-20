package com.example.mobile_proj.database

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private const val HASH_BYTE_SIZE = 64 * 8 // 512 bits
private const val PBKDF2_ITERATIONS = 1000

fun generateHash(password: String): String {
    val spec = PBEKeySpec(password.toCharArray(), ByteArray(16), PBKDF2_ITERATIONS, HASH_BYTE_SIZE)
    val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hash = skf.generateSecret(spec).encoded
    val res = StringBuilder()
    for (x in hash) {
        res.append(x.toInt().toChar())
    }
    return res.toString()
}
