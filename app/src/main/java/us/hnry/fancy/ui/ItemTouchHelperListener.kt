package us.hnry.fancy.ui

/**
 * @author Henry
 * 10/21/2017
 */
interface ItemTouchHelperListener {

    /**
     * Handle the Card-being-swiped event.
     * @param adapterPosition index of the item being swiped.
     */
    fun onItemSwiped(adapterPosition: Int?)

    /**
     * Handle the Card-being-dragged event.
     */
    fun onItemDragged()
}