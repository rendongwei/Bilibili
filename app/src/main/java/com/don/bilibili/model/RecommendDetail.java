package com.don.bilibili.model;

import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

import java.util.ArrayList;
import java.util.List;

public class RecommendDetail extends Json {

    private int aid;
    private int videos;
    private int tid;
    @Name(name = "tname")
    private String name;
    private int copyright;
    private String pic;
    private String title;
    private long pubdate;
    private long ctime;
    private String desc;
    private int state;
    private int attribute;
    private String tag;
    private int duration;
    private Rights rights;
    private Owner owner;
    private Stat stat;
    @Name(name = "tag")
    private List<Tag> tags = new ArrayList<>();
    private List<Pages> pages = new ArrayList<>();
    private List<Relates> relates = new ArrayList<>();

    public static class Rights extends Json {
        private int bp;
        private int elec;
        private int download;
        private int movie;
        private int pay;
        private int hd5;
        @Name(name = "no_reprint")
        private int noReprint;

        @Override
        public Object getEntity() {
            return this;
        }

        public int getBp() {
            return bp;
        }

        public void setBp(int bp) {
            this.bp = bp;
        }

        public int getElec() {
            return elec;
        }

        public void setElec(int elec) {
            this.elec = elec;
        }

        public int getDownload() {
            return download;
        }

        public void setDownload(int download) {
            this.download = download;
        }

        public int getMovie() {
            return movie;
        }

        public void setMovie(int movie) {
            this.movie = movie;
        }

        public int getPay() {
            return pay;
        }

        public void setPay(int pay) {
            this.pay = pay;
        }

        public int getHd5() {
            return hd5;
        }

        public void setHd5(int hd5) {
            this.hd5 = hd5;
        }

        public int getNoReprint() {
            return noReprint;
        }

        public void setNoReprint(int noReprint) {
            this.noReprint = noReprint;
        }
    }

    public static class Owner extends Json {

        private int mid;
        private String name;
        private String face;

        @Override
        public Object getEntity() {
            return this;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }
    }

    public static class Stat extends Json {

        private int aid;
        private int view;
        private int danmaku;
        private int reply;
        private int favorite;
        private int coin;
        private int share;
        @Name(name = "now_rank")
        private int nowRank;
        @Name(name = "his_rank")
        private int hisRank;
        private int like;

        @Override
        public Object getEntity() {
            return this;
        }

        public int getAid() {
            return aid;
        }

        public void setAid(int aid) {
            this.aid = aid;
        }

        public int getView() {
            return view;
        }

        public void setView(int view) {
            this.view = view;
        }

        public int getDanmaku() {
            return danmaku;
        }

        public void setDanmaku(int danmaku) {
            this.danmaku = danmaku;
        }

        public int getReply() {
            return reply;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public int getFavorite() {
            return favorite;
        }

        public void setFavorite(int favorite) {
            this.favorite = favorite;
        }

        public int getCoin() {
            return coin;
        }

        public void setCoin(int coin) {
            this.coin = coin;
        }

        public int getShare() {
            return share;
        }

        public void setShare(int share) {
            this.share = share;
        }

        public int getNowRank() {
            return nowRank;
        }

        public void setNowRank(int nowRank) {
            this.nowRank = nowRank;
        }

        public int getHisRank() {
            return hisRank;
        }

        public void setHisRank(int hisRank) {
            this.hisRank = hisRank;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }
    }

    public static class Tag extends Json {
        @Name(name = "tag_id")
        private int id;
        @Name(name = "tag_name")
        private String name;
        private String cover;
        private int likes;
        private int hates;
        private int liked;
        private int hated;
        private int attribute;

        @Override
        public Object getEntity() {
            return this;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public int getHates() {
            return hates;
        }

        public void setHates(int hates) {
            this.hates = hates;
        }

        public int getLiked() {
            return liked;
        }

        public void setLiked(int liked) {
            this.liked = liked;
        }

        public int getHated() {
            return hated;
        }

        public void setHated(int hated) {
            this.hated = hated;
        }

        public int getAttribute() {
            return attribute;
        }

        public void setAttribute(int attribute) {
            this.attribute = attribute;
        }
    }

    public static class Pages extends Json {

        private int cid;
        private int page;
        private String from;
        private String part;
        private int duration;
        private String vid;
        private String weblink;
        private List<Metas> metas = new ArrayList<>();
        private String dmlink;

        @Override
        public Object getEntity() {
            return this;
        }

        public static class Metas extends Json {

            private int quality;
            private String format;
            private int size;

            @Override
            public Object getEntity() {
                return this;
            }

            public int getQuality() {
                return quality;
            }

            public void setQuality(int quality) {
                this.quality = quality;
            }

            public String getFormat() {
                return format;
            }

            public void setFormat(String format) {
                this.format = format;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }
        }

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getPart() {
            return part;
        }

        public void setPart(String part) {
            this.part = part;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getVid() {
            return vid;
        }

        public void setVid(String vid) {
            this.vid = vid;
        }

        public String getWeblink() {
            return weblink;
        }

        public void setWeblink(String weblink) {
            this.weblink = weblink;
        }

        public List<Metas> getMetas() {
            return metas;
        }

        public void setMetas(List<Metas> metas) {
            this.metas = metas;
        }

        public String getDmlink() {
            return dmlink;
        }

        public void setDmlink(String dmlink) {
            this.dmlink = dmlink;
        }
    }

    public static class Relates extends Json {

        private String aid;
        private String pic;
        private String title;
        private Owner owner;
        private Stat stat;


        public static class Owner extends Json {
            private int mid;
            private String name;
            private String face;

            @Override
            public Object getEntity() {
                return this;
            }

            public int getMid() {
                return mid;
            }

            public void setMid(int mid) {
                this.mid = mid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getFace() {
                return face;
            }

            public void setFace(String face) {
                this.face = face;
            }
        }

        public static class Stat extends Json {

            private int aid;
            private int view;
            private int danmaku;
            private int reply;
            private int favorite;
            private int coin;
            private int share;
            @Name(name = "now_rank")
            private int nowRank;
            @Name(name = "his_rank")
            private int hisRank;
            private int like;

            @Override
            public Object getEntity() {
                return this;
            }

            public int getAid() {
                return aid;
            }

            public void setAid(int aid) {
                this.aid = aid;
            }

            public int getView() {
                return view;
            }

            public void setView(int view) {
                this.view = view;
            }

            public int getDanmaku() {
                return danmaku;
            }

            public void setDanmaku(int danmaku) {
                this.danmaku = danmaku;
            }

            public int getReply() {
                return reply;
            }

            public void setReply(int reply) {
                this.reply = reply;
            }

            public int getFavorite() {
                return favorite;
            }

            public void setFavorite(int favorite) {
                this.favorite = favorite;
            }

            public int getCoin() {
                return coin;
            }

            public void setCoin(int coin) {
                this.coin = coin;
            }

            public int getShare() {
                return share;
            }

            public void setShare(int share) {
                this.share = share;
            }

            public int getNowRank() {
                return nowRank;
            }

            public void setNowRank(int nowRank) {
                this.nowRank = nowRank;
            }

            public int getHisRank() {
                return hisRank;
            }

            public void setHisRank(int hisRank) {
                this.hisRank = hisRank;
            }

            public int getLike() {
                return like;
            }

            public void setLike(int like) {
                this.like = like;
            }
        }

        @Override
        public Object getEntity() {
            return this;
        }

        public String getAid() {
            return aid;
        }

        public void setAid(String aid) {
            this.aid = aid;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Stat getStat() {
            return stat;
        }

        public void setStat(Stat stat) {
            this.stat = stat;
        }
    }

    @Override
    public Object getEntity() {
        return this;
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public int getVideos() {
        return videos;
    }

    public void setVideos(int videos) {
        this.videos = videos;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPubdate() {
        return pubdate;
    }

    public void setPubdate(long pubdate) {
        this.pubdate = pubdate;
    }

    public long getCtime() {
        return ctime;
    }

    public void setCtime(long ctime) {
        this.ctime = ctime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Rights getRights() {
        return rights;
    }

    public void setRights(Rights rights) {
        this.rights = rights;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Pages> getPages() {
        return pages;
    }

    public void setPages(List<Pages> pages) {
        this.pages = pages;
    }

    public List<Relates> getRelates() {
        return relates;
    }

    public void setRelates(List<Relates> relates) {
        this.relates = relates;
    }
}
