package com.yjc.photodance.bean.searchBean;

import java.util.List;

/**
 * Created by Administrator on 2018/5/3/003.
 */

public class SearchPhoto {
    /**
     * total : 133
     * total_pages : 7
     * results : [{"id":"eOLpJytrbsQ","created_at":"2014-11-18T14:35:36-05:00","width":4000,"height":3000,"color":"#A7A2A1","likes":286,"liked_by_user":false,"description":"A man drinking a coffee.","user":{"id":"Ul0QVz12Goo","username":"ugmonk","name":"Jeff Sheldon","first_name":"Jeff","last_name":"Sheldon","instagram_username":"instantgrammer","twitter_username":"ugmonk","portfolio_url":"http://ugmonk.com/","profile_image":{"small":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41","medium":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f","large":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202"},"links":{"self":"https://api.unsplash.com/users/ugmonk","html":"http://unsplash.com/@ugmonk","photos":"https://api.unsplash.com/users/ugmonk/photos","likes":"https://api.unsplash.com/users/ugmonk/likes"}},"current_user_collections":[],"urls":{"raw":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f","full":"https://hd.unsplash.com/photo-1416339306562-f3d12fefd36f","regular":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=92f3e02f63678acc8416d044e189f515","small":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=263af33585f9d32af39d165b000845eb","thumb":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=8aae34cf35df31a592f0bef16e6342ef"},"links":{"self":"https://api.unsplash.com/photos/eOLpJytrbsQ","html":"http://unsplash.com/photos/eOLpJytrbsQ","download":"http://unsplash.com/photos/eOLpJytrbsQ/download"}}]
     */

    private int total;
    private int total_pages;
    private List<ResultsBean> results;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * id : eOLpJytrbsQ
         * created_at : 2014-11-18T14:35:36-05:00
         * width : 4000
         * height : 3000
         * color : #A7A2A1
         * likes : 286
         * liked_by_user : false
         * description : A man drinking a coffee.
         * user : {"id":"Ul0QVz12Goo","username":"ugmonk","name":"Jeff Sheldon","first_name":"Jeff","last_name":"Sheldon","instagram_username":"instantgrammer","twitter_username":"ugmonk","portfolio_url":"http://ugmonk.com/","profile_image":{"small":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41","medium":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f","large":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202"},"links":{"self":"https://api.unsplash.com/users/ugmonk","html":"http://unsplash.com/@ugmonk","photos":"https://api.unsplash.com/users/ugmonk/photos","likes":"https://api.unsplash.com/users/ugmonk/likes"}}
         * current_user_collections : []
         * urls : {"raw":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f","full":"https://hd.unsplash.com/photo-1416339306562-f3d12fefd36f","regular":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=92f3e02f63678acc8416d044e189f515","small":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=263af33585f9d32af39d165b000845eb","thumb":"https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=8aae34cf35df31a592f0bef16e6342ef"}
         * links : {"self":"https://api.unsplash.com/photos/eOLpJytrbsQ","html":"http://unsplash.com/photos/eOLpJytrbsQ","download":"http://unsplash.com/photos/eOLpJytrbsQ/download"}
         */

        private String id;
        private String created_at;
        private int width;
        private int height;
        private String color;
        private int likes;
        private boolean liked_by_user;
        private String description;
        private UserBean user;
        private UrlsBean urls;
        private LinksBeanX links;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public UrlsBean getUrls() {
            return urls;
        }

        public void setUrls(UrlsBean urls) {
            this.urls = urls;
        }

        public LinksBeanX getLinks() {
            return links;
        }

        public void setLinks(LinksBeanX links) {
            this.links = links;
        }

        public List<?> getCurrent_user_collections() {
            return current_user_collections;
        }

        public void setCurrent_user_collections(List<?> current_user_collections) {
            this.current_user_collections = current_user_collections;
        }

        public static class UserBean {
            /**
             * id : Ul0QVz12Goo
             * username : ugmonk
             * name : Jeff Sheldon
             * first_name : Jeff
             * last_name : Sheldon
             * instagram_username : instantgrammer
             * twitter_username : ugmonk
             * portfolio_url : http://ugmonk.com/
             * profile_image : {"small":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41","medium":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f","large":"https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202"}
             * links : {"self":"https://api.unsplash.com/users/ugmonk","html":"http://unsplash.com/@ugmonk","photos":"https://api.unsplash.com/users/ugmonk/photos","likes":"https://api.unsplash.com/users/ugmonk/likes"}
             */

            private String id;
            private String username;
            private String name;
            private String first_name;
            private String last_name;
            private String instagram_username;
            private String twitter_username;
            private String portfolio_url;
            private ProfileImageBean profile_image;
            private LinksBean links;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public String getInstagram_username() {
                return instagram_username;
            }

            public void setInstagram_username(String instagram_username) {
                this.instagram_username = instagram_username;
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

            public ProfileImageBean getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(ProfileImageBean profile_image) {
                this.profile_image = profile_image;
            }

            public LinksBean getLinks() {
                return links;
            }

            public void setLinks(LinksBean links) {
                this.links = links;
            }

            public static class ProfileImageBean {
                /**
                 * small : https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=7cfe3b93750cb0c93e2f7caec08b5a41
                 * medium : https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=5a9dc749c43ce5bd60870b129a40902f
                 * large : https://images.unsplash.com/profile-1441298803695-accd94000cac?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=32085a077889586df88bfbe406692202
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

            public static class LinksBean {
                /**
                 * self : https://api.unsplash.com/users/ugmonk
                 * html : http://unsplash.com/@ugmonk
                 * photos : https://api.unsplash.com/users/ugmonk/photos
                 * likes : https://api.unsplash.com/users/ugmonk/likes
                 */

                private String self;
                private String html;
                private String photos;
                private String likes;

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
            }
        }

        public static class UrlsBean {
            /**
             * raw : https://images.unsplash.com/photo-1416339306562-f3d12fefd36f
             * full : https://hd.unsplash.com/photo-1416339306562-f3d12fefd36f
             * regular : https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=92f3e02f63678acc8416d044e189f515
             * small : https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=263af33585f9d32af39d165b000845eb
             * thumb : https://images.unsplash.com/photo-1416339306562-f3d12fefd36f?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=8aae34cf35df31a592f0bef16e6342ef
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

        public static class LinksBeanX {
            /**
             * self : https://api.unsplash.com/photos/eOLpJytrbsQ
             * html : http://unsplash.com/photos/eOLpJytrbsQ
             * download : http://unsplash.com/photos/eOLpJytrbsQ/download
             */

            private String self;
            private String html;
            private String download;

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
        }
    }
}
