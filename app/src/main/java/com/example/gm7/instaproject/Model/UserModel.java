package com.example.gm7.instaproject.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emad on 12/20/17.
 *
 */

public class UserModel implements Serializable {

    public String username;
    public String email;
    public String coverUrl;
    public String profileUrl;
    public String uid;

     UserModel(){}

    public UserModel(String _userName, String _email, String _coverUrl, String _profileUrl, String uid) {
        this.username = _userName;
        this.email = _email;
        this.coverUrl = _coverUrl;
        this.profileUrl = _profileUrl;
        this.uid = uid;
    }

    public Map<String,Object> insertUser() {
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",username);
        params.put("email",email);
        params.put("coverUrl",coverUrl);
        params.put("profileUrl",profileUrl);
        params.put("uid",uid);
        return params;
    }

}
