package com.yjc.photodance.BeanForJson;

import com.google.gson.annotations.SerializedName;

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
