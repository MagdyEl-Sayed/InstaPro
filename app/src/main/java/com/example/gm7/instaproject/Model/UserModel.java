package com.example.gm7.instaproject.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emad on 12/20/17.
 *
 */

public class UserModel implements Serializable {

    public String _userName;
    public String _email;
    public String _coverUrl;
    public String _profileUrl;

    public UserModel(){}

    public UserModel(String _userName, String _email, String _coverUrl, String _profileUrl) {
        this._userName = _userName;
        this._email = _email;
        this._coverUrl = _coverUrl;
        this._profileUrl = _profileUrl;
    }

    public Map<String,Object> insertUser() {
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",_userName);
        params.put("email",_email);
        params.put("coverUrl",_coverUrl);
        params.put("profileUrl",_profileUrl);
        return params;
    }


    public Map<String,Object> insertUser(String userId,String email) {
        HashMap<String,Object> params = new HashMap<>();
        params.put("uid",userId);
        params.put("email",email);
        return params;
    }
}
