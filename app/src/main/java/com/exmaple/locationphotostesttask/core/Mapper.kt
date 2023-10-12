package com.exmaple.locationphotostesttask.core

/**
 * Created by Ilya Boiko @camancho
on 12.10.2023.
 **/
interface Mapper<T,R> {

 fun map(data: T): R


}