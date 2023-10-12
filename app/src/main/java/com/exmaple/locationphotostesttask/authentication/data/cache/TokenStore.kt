package com.exmaple.locationphotostesttask.authentication.data.cache

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.exmaple.locationphotostesttask.core.SettingsDataStore
import javax.inject.Inject

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface TokenStore: SettingsDataStore<String> {

 class Base(
  tokenKey: Preferences.Key<String>,
  store: DataStore<Preferences>
 ): TokenStore, SettingsDataStore.Abstract<String>(tokenKey,store,"")

}