package com.jrangel.fingerreaderexample

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.CancellationSignal
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

@RequiresApi(Build.VERSION_CODES.M)
class FingerprintHandler(
    val context: Context,
    val view: OnUpdateListener
) : FingerprintManager.AuthenticationCallback() {

    fun startAuth(manager: FingerprintManager, cryptoObject: FingerprintManager.CryptoObject?) {
        val cancellationSignal = CancellationSignal()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.USE_FINGERPRINT
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null)
    }

    override fun onAuthenticationError(errMsgId: Int, errString: CharSequence) {
        view.update("Error de Autenticación de huellas dactilares\n$errString", false)
    }

    override fun onAuthenticationHelp(helpMsgId: Int, helpString: CharSequence) {
        view.update("Ayuda de Autenticación de huellas dactilares\n$helpString", false)
    }

    override fun onAuthenticationFailed() {
        view.update("Fallo al autenticar con la huella dactilar.", false)
    }

    override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
        view.update("Éxito al autenticar con la huella dactilar.", true)
    }
}