package com.psych.demo.model;

import lombok.Data;

@Data
public class Item {
    private String _type;
    private String api_path;
    private String header_image_url;
    private int id;
    private String image_url;
    private String index_character;
    private boolean is_meme_verified;
    private boolean is_verified;
    private String name;
    private String slug;
    private String url;
    private int iq;

    public Item(){};
    public Item(String _type, String api_path, String header_image_url, int id, String image_url, String index_character, boolean is_meme_verified, boolean is_verified, String name, String slug, String url, int iq) {
        this._type = _type;
        this.api_path = api_path;
        this.header_image_url = header_image_url;
        this.id = id;
        this.image_url = image_url;
        this.index_character = index_character;
        this.is_meme_verified = is_meme_verified;
        this.is_verified = is_verified;
        this.name = name;
        this.slug = slug;
        this.url = url;
        this.iq = iq;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String getApi_path() {
        return api_path;
    }

    public void setApi_path(String api_path) {
        this.api_path = api_path;
    }

    public String getHeader_image_url() {
        return header_image_url;
    }

    public void setHeader_image_url(String header_image_url) {
        this.header_image_url = header_image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getIndex_character() {
        return index_character;
    }

    public void setIndex_character(String index_character) {
        this.index_character = index_character;
    }

    public boolean isIs_meme_verified() {
        return is_meme_verified;
    }

    public void setIs_meme_verified(boolean is_meme_verified) {
        this.is_meme_verified = is_meme_verified;
    }

    public boolean isIs_verified() {
        return is_verified;
    }

    public void setIs_verified(boolean is_verified) {
        this.is_verified = is_verified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIq() {
        return iq;
    }

    public void setIq(int iq) {
        this.iq = iq;
    }

    // Getters and setters for the fields

    // Constructors
}
