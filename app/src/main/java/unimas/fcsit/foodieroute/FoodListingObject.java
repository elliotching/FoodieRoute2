package unimas.fcsit.foodieroute;

/**
 * Created by Elliot on 19-Aug-16.
 */
class FoodListingObject {
    final String foodName;
//    public final int color;
//    final int photoRes;
    final String photoName;
    final String foodPrice;

    FoodListingObject(String name, String photoName, String price) {
        foodName = name;
        this.photoName = photoName;
        foodPrice = price;
    }
}