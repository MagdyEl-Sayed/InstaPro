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

    UserModel(){}

    public UserModel(String _userName, String _email) {
        this._userName = _userName;
        this._email = _email;
    }

    public Map<String,Object> insertUser() {
        HashMap<String,Object> params = new HashMap<>();
        params.put("username",_userName);
        params.put("email",_email);
        return params;
    }
}
