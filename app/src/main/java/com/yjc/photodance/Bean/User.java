package com.yjc.photodance.Bean;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/6/006.
 */

public class User {

    private String id;
    @SerializedName("updated_at")
    private Date updatedAt;
    private String username;
    private String name;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("twitter_username")
    private String twitterUsername;
    @SerializedName("portfolio_url")
    private String portfolioUrl;
    private String bio;
    private String location;
    private Links links;
    @SerializedName("total_likes")
    private int totalLikes;
    @SerializedName("total_photos")
    private int totalPhotos;
    @SerializedName("total_collections")
    private int totalCollections;
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setTwitterUsername(String twitterUsername) {
        this.twitterUsername = twitterUsername;
    }
    public String getTwitterUsername() {
        return twitterUsername;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }
    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getBio() {
        return bio;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    public String getLocation() {
        return location;
    }

    public void setLinks(Links links) {
        this.links = links;
    }
    public Links getLinks() {
        return links;
    }

    public void setTotalLikes(int totalLikes) {
        this.totalLikes = totalLikes;
    }
    public int getTotalLikes() {
        return totalLikes;
    }

    public void setTotalPhotos(int totalPhotos) {
        this.totalPhotos = totalPhotos;
    }
    public int getTotalPhotos() {
        return totalPhotos;
    }

    public void setTotalCollections(int totalCollections) {
        this.totalCollections = totalCollections;
    }
    public int getTotalCollections() {
        return totalCollections;
    }
}
