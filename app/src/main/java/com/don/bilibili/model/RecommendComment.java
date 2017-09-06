package com.don.bilibili.model;

import com.don.bilibili.Json.Json;
import com.don.bilibili.Json.annotation.Name;

import java.util.ArrayList;
import java.util.List;

public class RecommendComment extends Json {

    private List<Comment> hots = new ArrayList<>();
    private List<Comment> top = new ArrayList<>();
    private List<Comment> replies = new ArrayList<>();
    private Upper upper;
    private Page page;

    public static class Page extends Json {

        private int acount;
        private int count;
        private int num;
        private int size;

        @Override
        public Object getEntity() {
            return this;
        }

        public int getAcount() {
            return acount;
        }

        public void setAcount(int acount) {
            this.acount = acount;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }

    public static class Upper extends Json {

        private int mid;
        private List<Comment> top = new ArrayList<>();

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

        public List<Comment> getTop() {
            return top;
        }

        public void setTop(List<Comment> top) {
            this.top = top;
        }
    }

    public static class Comment extends Json {

        private int rpid;
        private int oid;
        private int type;
        private int mid;
        private int root;
        private int parent;
        private int count;
        private int rcount;
        private int floor;
        private int state;
        @Name(name = "fansgrade")
        private int fansGrade;
        private int attr;
        private long ctime;
        private int like;
        private int action;
        private int assist;
        private List<Comment> replies = new ArrayList<>();
        private Member member;
        private Content content;

        public static class Member extends Json {

            private int mid;
            private String uname;
            private String sex;
            private String sign;
            private String avatar;
            private String rank;
            @Name(name = "DisplayRank")
            private String displayRank;
            @Name(name = "reg_year")
            private String regYear;
            @Name(name = "fans_detail")
            private String fansDetail;
            @Name(name = "level_info")
            private LevelInfo levelInfo;

            public static class LevelInfo extends Json {

                @Name(name = "current_level")
                private int currentLevel;
                @Name(name = "current_min")
                private int currentMin;
                @Name(name = "current_exp")
                private int currentExp;
                @Name(name = "next_exp")
                private int nextExp;

                @Override
                public Object getEntity() {
                    return this;
                }

                public int getCurrentLevel() {
                    return currentLevel;
                }

                public void setCurrentLevel(int currentLevel) {
                    this.currentLevel = currentLevel;
                }

                public int getCurrentMin() {
                    return currentMin;
                }

                public void setCurrentMin(int currentMin) {
                    this.currentMin = currentMin;
                }

                public int getCurrentExp() {
                    return currentExp;
                }

                public void setCurrentExp(int currentExp) {
                    this.currentExp = currentExp;
                }

                public int getNextExp() {
                    return nextExp;
                }

                public void setNextExp(int nextExp) {
                    this.nextExp = nextExp;
                }
            }

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

            public String getUname() {
                return uname;
            }

            public void setUname(String uname) {
                this.uname = uname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getRank() {
                return rank;
            }

            public void setRank(String rank) {
                this.rank = rank;
            }

            public String getDisplayRank() {
                return displayRank;
            }

            public void setDisplayRank(String displayRank) {
                this.displayRank = displayRank;
            }

            public String getRegYear() {
                return regYear;
            }

            public void setRegYear(String regYear) {
                this.regYear = regYear;
            }

            public String getFansDetail() {
                return fansDetail;
            }

            public void setFansDetail(String fansDetail) {
                this.fansDetail = fansDetail;
            }

            public LevelInfo getLevelInfo() {
                return levelInfo;
            }

            public void setLevelInfo(LevelInfo levelInfo) {
                this.levelInfo = levelInfo;
            }
        }

        public static class Content extends Json {

            private String message;
            private int plat;
            private String device;
            private List<Member> members = new ArrayList<>();

            @Override
            public Object getEntity() {
                return this;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getPlat() {
                return plat;
            }

            public void setPlat(int plat) {
                this.plat = plat;
            }

            public String getDevice() {
                return device;
            }

            public void setDevice(String device) {
                this.device = device;
            }

            public List<Member> getMembers() {
                return members;
            }

            public void setMembers(List<Member> members) {
                this.members = members;
            }
        }

        @Override
        public Object getEntity() {
            return this;
        }

        public int getRpid() {
            return rpid;
        }

        public void setRpid(int rpid) {
            this.rpid = rpid;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public int getRoot() {
            return root;
        }

        public void setRoot(int root) {
            this.root = root;
        }

        public int getParent() {
            return parent;
        }

        public void setParent(int parent) {
            this.parent = parent;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getRcount() {
            return rcount;
        }

        public void setRcount(int rcount) {
            this.rcount = rcount;
        }

        public int getFloor() {
            return floor;
        }

        public void setFloor(int floor) {
            this.floor = floor;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getFansGrade() {
            return fansGrade;
        }

        public void setFansGrade(int fansGrade) {
            this.fansGrade = fansGrade;
        }

        public int getAttr() {
            return attr;
        }

        public void setAttr(int attr) {
            this.attr = attr;
        }

        public long getCtime() {
            return ctime;
        }

        public void setCtime(long ctime) {
            this.ctime = ctime;
        }

        public int getLike() {
            return like;
        }

        public void setLike(int like) {
            this.like = like;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        public int getAssist() {
            return assist;
        }

        public void setAssist(int assist) {
            this.assist = assist;
        }

        public List<Comment> getReplies() {
            return replies;
        }

        public void setReplies(List<Comment> replies) {
            this.replies = replies;
        }

        public Member getMember() {
            return member;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }
    }

    @Override
    public Object getEntity() {
        return this;
    }

    public List<Comment> getHots() {
        return hots;
    }

    public void setHots(List<Comment> hots) {
        this.hots = hots;
    }

    public List<Comment> getTop() {
        return top;
    }

    public void setTop(List<Comment> top) {
        this.top = top;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    public Upper getUpper() {
        return upper;
    }

    public void setUpper(Upper upper) {
        this.upper = upper;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
