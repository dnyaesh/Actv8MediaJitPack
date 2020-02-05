package com.actv8.k2annex.actv8mediaplayer.Model;

import java.util.ArrayList;

/**
 * Created by neoforce-01 on 2/28/2019.
 */

public class UserDetails
{
    int id;
    int application_id;
    int client_id;
    String username_hash;
    String login_method;
    String created_by;
    String validate_token;
    String support_pin;

    ArrayList<Category> categories;

    public int getId()
    {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getApplication_id() {
        return application_id;
    }

    public void setApplication_id(int application_id) {
        this.application_id = application_id;
    }

    public String getUsername_hash() {
        return username_hash;
    }

    public void setUsername_hash(String username_hash) {
        this.username_hash = username_hash;
    }

    public String getLogin_method() {
        return login_method;
    }

    public void setLogin_method(String login_method) {
        this.login_method = login_method;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getValidate_token() {
        return validate_token;
    }

    public void setValidate_token(String validate_token) {
        this.validate_token = validate_token;
    }

    public String getSupport_pin() {
        return support_pin;
    }

    public void setSupport_pin(String support_pin) {
        this.support_pin = support_pin;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
}
