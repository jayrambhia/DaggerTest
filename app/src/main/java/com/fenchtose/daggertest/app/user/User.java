package com.fenchtose.daggertest.app.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Jay Rambhia on 15/03/15.
 */

@DatabaseTable(tableName = "user")
public class User {

    @DatabaseField(useGetSet = true, canBeNull = false)
    private String login;

    @DatabaseField(useGetSet = true, id = true)
    private int id;

    @DatabaseField(useGetSet = true)
    private String avatar_url;

    @DatabaseField(useGetSet = true)
    private String url;

    @DatabaseField(useGetSet = true)
    private String html_url;

    @DatabaseField(useGetSet = true)
    private float score;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
