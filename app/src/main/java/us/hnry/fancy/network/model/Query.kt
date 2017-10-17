package us.hnry.fancy.network.model

/**
 * @author Henry
 * 10/16/2017
 */
data class Query<out T>(val count: Int, val created: String, val lang: String, val results: Results<T>?)