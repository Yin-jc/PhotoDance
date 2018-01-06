package com.yjc.photodance.Bean;

import com.google.gson.annotations.SerializedName;

import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/4/004.
 */

public class Photo {

//    public String _id;
//    public String createdAt;
//    public String desc;
//    public String publishedAt;
//    public String source;
//    public String type;
//    public String url;
//    public boolean used;
//    public String who;


//    {
//            "id": "G9JYncnA5O8",
//            "created_at": "2018-01-04T17:52:32-05:00",
//            "updated_at": "2018-01-05T03:49:40-05:00",
//            "width": 4393,
//            "height": 6589,
//            "color": "#15191D",
//            "description": null,
//            "categories": [],
//            "urls": {
//                "raw": "https://images.unsplash.com/photo-1515106242722-449ed91d1f2c",
//                "full": "https://images.unsplash.com/photo-1515106242722-449ed91d1f2c?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjE5MjY0fQ%3D%3D%0A&s=ef151fe5970c09c9f324c450766d850a",
//                "regular": "https://images.unsplash.com/photo-1515106242722-449ed91d1f2c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ%3D%3D%0A&s=7aa36b1ce3a3ae52fba199d20c3a1ed8",
//                "small": "https://images.unsplash.com/photo-1515106242722-449ed91d1f2c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ%3D%3D%0A&s=e1a1dc7ab3289f565527ec923c041d90",
//                "thumb": "https://images.unsplash.com/photo-1515106242722-449ed91d1f2c?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ%3D%3D%0A&s=e58ec04e8d6ae29dbfd39d26b9b19274"
//             },
//             "links": {
//                "self": "https://api.unsplash.com/photos/G9JYncnA5O8",
//                "html": "https://unsplash.com/photos/G9JYncnA5O8",
//                "download": "https://unsplash.com/photos/G9JYncnA5O8/download",
//                "download_location": "https://api.unsplash.com/photos/G9JYncnA5O8/download"
//             },
//             "liked_by_user": false,
//             "sponsored": false,
//             "likes": 160,
//             "user": {
//        "id": "YR_kUAmnr18",
//                "updated_at": "2018-01-06T02:52:13-05:00",
//                "username": "trapnation",
//                "name": "Andre Benz",
//                "first_name": "Andre",
//                "last_name": "Benz",
//                "twitter_username": "AllTrapNation",
//                "portfolio_url": "http://andrebenz.io",
//                "bio": "Founder of Trap Nation with a side passion for photos.",
//                "location": "New York, NY.",
//                "links": {
//            "self": "https://api.unsplash.com/users/trapnation",
//                    "html": "https://unsplash.com/@trapnation",
//                    "photos": "https://api.unsplash.com/users/trapnation/photos",
//                    "likes": "https://api.unsplash.com/users/trapnation/likes",
//                    "portfolio": "https://api.unsplash.com/users/trapnation/portfolio",
//                    "following": "https://api.unsplash.com/users/trapnation/following",
//                    "followers": "https://api.unsplash.com/users/trapnation/followers"
//        },
//        "profile_image": {
//            "small": "https://images.unsplash.com/profile-1513183198594-66e21a4cfe3d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=4915ba7e717369d35530e8b2535fb89d",
//                    "medium": "https://images.unsplash.com/profile-1513183198594-66e21a4cfe3d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=69f92f3bdec3fab96740c99f7cbeb493",
//                    "large": "https://images.unsplash.com/profile-1513183198594-66e21a4cfe3d?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=8f8320862d8b9dbe42a91f8d76a4741a"
//        },
//        "total_likes": 0,
//                "total_photos": 120,
//                "total_collections": 1
//    },
//        "current_user_collections": []
//    }
//    public String id;
//    public int width;
//    public int height;
//
//    public URLs urls;
//    public Links links;
//    public User user;

    private String id;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    private int width;
    private int height;
    private String color;
    private String description;
    private List<String> categories;
    private Urls urls;
    private Links links;
    @SerializedName("liked_by_user")
    private boolean likedByUser;
    private boolean sponsored;
    private int likes;
    private User user;
    @SerializedName("current_user_collections")
    private List<String> currentUserCollections;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    public int getHeight() {
        return height;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public String getColor() {
        return color;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public List<String> getCategories() {
        return categories;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }
    public Urls getUrls() {
        return urls;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
    public Links getLinks() {
        return links;
    }

    public void setLikedByUser(boolean likedByUser) {
        this.likedByUser = likedByUser;
    }
    public boolean getLikedByUser() {
        return likedByUser;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }
    public boolean getSponsored() {
        return sponsored;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
    public int getLikes() {
        return likes;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }

    public void setCurrentUserCollections(List<String> currentUserCollections) {
        this.currentUserCollections = currentUserCollections;
    }
    public List<String> getCurrentUserCollections() {
        return currentUserCollections;
    }
}
