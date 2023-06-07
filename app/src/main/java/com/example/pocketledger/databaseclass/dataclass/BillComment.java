package com.example.pocketledger.databaseclass.dataclass;

public class BillComment {
    private byte[] photo; // 图片数据，使用 byte[] 类型存储
    private String comment; // 备注信息

    public BillComment(byte[] photo, String comment) {
        this.photo = photo;
        this.comment = comment;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
