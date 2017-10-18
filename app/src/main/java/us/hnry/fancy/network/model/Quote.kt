package us.hnry.fancy.network.model

/**
 * @author Henry
 * 10/16/2017
 */
data class Quote<out T>(val query: Query<T>?)