package com.sparta.heartvera.domain.like.entity;

public enum LikeEnum {
    COMMENT(ContentType.COMMENT),
    POST(ContentType.POST),
    PUBPOST(ContentType.PUBPOST);

    private final String contentType;
    LikeEnum(String contentType) {this.contentType = contentType; }

    public static class ContentType{
        public static final String COMMENT = "COMMENT";
        public static final String POST = "POST";
        public static final String PUBPOST = "PUBPOST";
    }
}