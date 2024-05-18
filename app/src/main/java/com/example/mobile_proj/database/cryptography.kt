package com.example.mobile_proj.database

import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private const val HASH_BYTE_SIZE = 64 * 8 // 512 bits
private const val PBKDF2_ITERATIONS = 1000

private fun generateRandomSalt(): ByteArray {
    val random = SecureRandom()
    val salt = ByteArray(16)
    random.nextBytes(salt)
    return salt
}

fun generateHash(password: String): String {
    val spec = PBEKeySpec(password.toCharArray(), generateRandomSalt(), PBKDF2_ITERATIONS, HASH_BYTE_SIZE)
    val skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val hash = skf.generateSecret(spec).encoded
    val res = StringBuilder();
    for (x in hash) {
        res.append(x.toInt().toChar())
    }
    return res.toString()
}
