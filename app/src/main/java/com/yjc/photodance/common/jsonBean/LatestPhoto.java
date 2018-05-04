package com.yjc.photodance.common.jsonBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/4/004.
 */

public class LatestPhoto {


    /**
     * id : sW_10braKvE
     * created_at : 2018-03-27T23:00:58-04:00
     * updated_at : 2018-05-04T02:32:40-04:00
     * width : 6598
     * height : 4399
     * color : #D8A27A
     * description : null
     * urls : {"raw":"https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=7938f583923f62287aa901c4f949f07a","full":"https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=c45d2b30721c50604b1110a8f93ad4c1","regular":"https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=30278735a62c8fd84a6da603087c9524","small":"https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=efa3ecc5b732e53b862b6f08f92a062a","thumb":"https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=c7de416e1912f943619acc79efa91cb3"}
     * links : {"self":"https://api.unsplash.com/photos/sW_10braKvE","html":"https://unsplash.com/photos/sW_10braKvE","download":"https://unsplash.com/photos/sW_10braKvE/download","download_location":"https://api.unsplash.com/photos/sW_10braKvE/download"}
     * categories : []
     * sponsored : true
     * likes : 9
     * liked_by_user : false
     * current_user_collections : []
     * slug : null
     * user : {"id":"dG6lZyj-wvM","updated_at":"2018-05-03T23:02:41-04:00","username":"nate_dumlao","name":"Nathan Dumlao","first_name":"Nathan","last_name":"Dumlao","twitter_username":"Nate_Dumlao","portfolio_url":"http://www.nathandumlaophotos.com","bio":"Brand Consultant and Content Creator living in Los Angeles creating up the coast.","location":"Los Angeles","links":{"self":"https://api.unsplash.com/users/nate_dumlao","html":"https://unsplash.com/@nate_dumlao","photos":"https://api.unsplash.com/users/nate_dumlao/photos","likes":"https://api.unsplash.com/users/nate_dumlao/likes","portfolio":"https://api.unsplash.com/users/nate_dumlao/portfolio","following":"https://api.unsplash.com/users/nate_dumlao/following","followers":"https://api.unsplash.com/users/nate_dumlao/followers"},"profile_image":{"small":"https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=8e6405920894a45ce9204dd11d1465f3","medium":"https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=1978e1f9440ee4cc1da03c318068593f","large":"https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=8f75defe3b90fd37243d80d207c8c6d6"},"total_collections":1,"instagram_username":"nate_dumlao","total_likes":1170,"total_photos":635}
     */

    private String id;
    private String created_at;
    private String updated_at;
    private int width;
    private int height;
    private String color;
    private String description;
    private UrlsBean urls;
    private LinksBean links;
    private boolean sponsored;
    private int likes;
    private boolean liked_by_user;
    private Object slug;
    private UserBean user;
    private List<?> categories;
    private List<?> current_user_collections;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UrlsBean getUrls() {
        return urls;
    }

    public void setUrls(UrlsBean urls) {
        this.urls = urls;
    }

    public LinksBean getLinks() {
        return links;
    }

    public void setLinks(LinksBean links) {
        this.links = links;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored(boolean sponsored) {
        this.sponsored = sponsored;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLiked_by_user() {
        return liked_by_user;
    }

    public void setLiked_by_user(boolean liked_by_user) {
        this.liked_by_user = liked_by_user;
    }

    public Object getSlug() {
        return slug;
    }

    public void setSlug(Object slug) {
        this.slug = slug;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<?> getCategories() {
        return categories;
    }

    public void setCategories(List<?> categories) {
        this.categories = categories;
    }

    public List<?> getCurrent_user_collections() {
        return current_user_collections;
    }

    public void setCurrent_user_collections(List<?> current_user_collections) {
        this.current_user_collections = current_user_collections;
    }

    public static class UrlsBean {
        /**
         * raw : https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=7938f583923f62287aa901c4f949f07a
         * full : https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=c45d2b30721c50604b1110a8f93ad4c1
         * regular : https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=30278735a62c8fd84a6da603087c9524
         * small : https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=efa3ecc5b732e53b862b6f08f92a062a
         * thumb : https://images.unsplash.com/photo-1522206052224-9c5ad951dd74?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&ixid=eyJhcHBfaWQiOjE5MjY0fQ&s=c7de416e1912f943619acc79efa91cb3
         */

        private String raw;
        private String full;
        private String regular;
        private String small;
        private String thumb;

        public String getRaw() {
            return raw;
        }

        public void setRaw(String raw) {
            this.raw = raw;
        }

        public String getFull() {
            return full;
        }

        public void setFull(String full) {
            this.full = full;
        }

        public String getRegular() {
            return regular;
        }

        public void setRegular(String regular) {
            this.regular = regular;
        }

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }

    public static class LinksBean {
        /**
         * self : https://api.unsplash.com/photos/sW_10braKvE
         * html : https://unsplash.com/photos/sW_10braKvE
         * download : https://unsplash.com/photos/sW_10braKvE/download
         * download_location : https://api.unsplash.com/photos/sW_10braKvE/download
         */

        private String self;
        private String html;
        private String download;
        private String download_location;

        public String getSelf() {
            return self;
        }

        public void setSelf(String self) {
            this.self = self;
        }

        public String getHtml() {
            return html;
        }

        public void setHtml(String html) {
            this.html = html;
        }

        public String getDownload() {
            return download;
        }

        public void setDownload(String download) {
            this.download = download;
        }

        public String getDownload_location() {
            return download_location;
        }

        public void setDownload_location(String download_location) {
            this.download_location = download_location;
        }
    }

    public static class UserBean {
        /**
         * id : dG6lZyj-wvM
         * updated_at : 2018-05-03T23:02:41-04:00
         * username : nate_dumlao
         * name : Nathan Dumlao
         * first_name : Nathan
         * last_name : Dumlao
         * twitter_username : Nate_Dumlao
         * portfolio_url : http://www.nathandumlaophotos.com
         * bio : Brand Consultant and Content Creator living in Los Angeles creating up the coast.
         * location : Los Angeles
         * links : {"self":"https://api.unsplash.com/users/nate_dumlao","html":"https://unsplash.com/@nate_dumlao","photos":"https://api.unsplash.com/users/nate_dumlao/photos","likes":"https://api.unsplash.com/users/nate_dumlao/likes","portfolio":"https://api.unsplash.com/users/nate_dumlao/portfolio","following":"https://api.unsplash.com/users/nate_dumlao/following","followers":"https://api.unsplash.com/users/nate_dumlao/followers"}
         * profile_image : {"small":"https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=8e6405920894a45ce9204dd11d1465f3","medium":"https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=1978e1f9440ee4cc1da03c318068593f","large":"https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=8f75defe3b90fd37243d80d207c8c6d6"}
         * total_collections : 1
         * instagram_username : nate_dumlao
         * total_likes : 1170
         * total_photos : 635
         */

        private String id;
        private String updated_at;
        private String username;
        private String name;
        private String first_name;
        private String last_name;
        private String twitter_username;
        private String portfolio_url;
        private String bio;
        private String location;
        private LinksBeanX links;
        private ProfileImageBean profile_image;
        private int total_collections;
        private String instagram_username;
        private int total_likes;
        private int total_photos;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getTwitter_username() {
            return twitter_username;
        }

        public void setTwitter_username(String twitter_username) {
            this.twitter_username = twitter_username;
        }

        public String getPortfolio_url() {
            return portfolio_url;
        }

        public void setPortfolio_url(String portfolio_url) {
            this.portfolio_url = portfolio_url;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public LinksBeanX getLinks() {
            return links;
        }

        public void setLinks(LinksBeanX links) {
            this.links = links;
        }

        public ProfileImageBean getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(ProfileImageBean profile_image) {
            this.profile_image = profile_image;
        }

        public int getTotal_collections() {
            return total_collections;
        }

        public void setTotal_collections(int total_collections) {
            this.total_collections = total_collections;
        }

        public String getInstagram_username() {
            return instagram_username;
        }

        public void setInstagram_username(String instagram_username) {
            this.instagram_username = instagram_username;
        }

        public int getTotal_likes() {
            return total_likes;
        }

        public void setTotal_likes(int total_likes) {
            this.total_likes = total_likes;
        }

        public int getTotal_photos() {
            return total_photos;
        }

        public void setTotal_photos(int total_photos) {
            this.total_photos = total_photos;
        }

        public static class LinksBeanX {
            /**
             * self : https://api.unsplash.com/users/nate_dumlao
             * html : https://unsplash.com/@nate_dumlao
             * photos : https://api.unsplash.com/users/nate_dumlao/photos
             * likes : https://api.unsplash.com/users/nate_dumlao/likes
             * portfolio : https://api.unsplash.com/users/nate_dumlao/portfolio
             * following : https://api.unsplash.com/users/nate_dumlao/following
             * followers : https://api.unsplash.com/users/nate_dumlao/followers
             */

            private String self;
            private String html;
            private String photos;
            private String likes;
            private String portfolio;
            private String following;
            private String followers;

            public String getSelf() {
                return self;
            }

            public void setSelf(String self) {
                this.self = self;
            }

            public String getHtml() {
                return html;
            }

            public void setHtml(String html) {
                this.html = html;
            }

            public String getPhotos() {
                return photos;
            }

            public void setPhotos(String photos) {
                this.photos = photos;
            }

            public String getLikes() {
                return likes;
            }

            public void setLikes(String likes) {
                this.likes = likes;
            }

            public String getPortfolio() {
                return portfolio;
            }

            public void setPortfolio(String portfolio) {
                this.portfolio = portfolio;
            }

            public String getFollowing() {
                return following;
            }

            public void setFollowing(String following) {
                this.following = following;
            }

            public String getFollowers() {
                return followers;
            }

            public void setFollowers(String followers) {
                this.followers = followers;
            }
        }

        public static class ProfileImageBean {
            /**
             * small : https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=8e6405920894a45ce9204dd11d1465f3
             * medium : https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=1978e1f9440ee4cc1da03c318068593f
             * large : https://images.unsplash.com/profile-1495427732560-fe5248ad6638?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=8f75defe3b90fd37243d80d207c8c6d6
             */

            private String small;
            private String medium;
            private String large;

            public String getSmall() {
                return small;
            }

            public void setSmall(String small) {
                this.small = small;
            }

            public String getMedium() {
                return medium;
            }

            public void setMedium(String medium) {
                this.medium = medium;
            }

            public String getLarge() {
                return large;
            }

            public void setLarge(String large) {
                this.large = large;
            }
        }
    }
}
