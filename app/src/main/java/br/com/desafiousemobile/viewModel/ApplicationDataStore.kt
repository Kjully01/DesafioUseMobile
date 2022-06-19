package br.com.desafiousemobile.viewModel

import android.app.Application
import br.com.desafiousemobile.model.data_local.dataStore.DataStoreManager

class ApplicationDataStore: Application() {

    override fun onCreate() {
        super.onCreate()
        DataStoreManager.init(this)
    }
}