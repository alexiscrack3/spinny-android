package com.alexiscrack3.spinny.security

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module

var masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

var securityModule = module {
    single(named("securePrefs")) {
        EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            androidApplication(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
    factory {
        SecurePreferences(
            sharedPreferences = get(named("securePrefs"))
        )
    }
}
