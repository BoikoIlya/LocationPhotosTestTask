package com.exmaple.locationphotostesttask.core

import android.os.Message
import com.exmaple.locationphotostesttask.R

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
class CloudErrorException(override val message: String): Exception()

class CloudErrorWithoutFullDescriptionException(override val message: String)
    : Exception(),Mapper<Unit,Int>{

    companion object{
        private const val wrong_credentials = "security.signin.incorrect"
    }

    override fun map(data: Unit): Int {
       return when(message){
            wrong_credentials -> R.string.wrong_credentials
            else -> R.string.oops_something_went_wrong_data
        }
    }


}