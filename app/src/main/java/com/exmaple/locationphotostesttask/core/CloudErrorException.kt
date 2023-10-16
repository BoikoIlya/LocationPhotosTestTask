package com.exmaple.locationphotostesttask.core

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
        private const val existed_login = "security.signup.login-already-use"
        private const val bad_image = "bad-image"
        private const val big_image = "big-image"
        private const val file_upload_error = "file-upload-error"
    }

    override fun map(data: Unit): Int {
       return when(message){
            wrong_credentials -> R.string.wrong_credentials
            existed_login->R.string.existed_login
            bad_image->R.string.bad_image
            big_image->R.string.big_image
            file_upload_error->R.string.file_upload_error
            else -> R.string.oops_something_went_wrong_data
        }
    }


}

class EnableGpsAndNetworkException: Exception()