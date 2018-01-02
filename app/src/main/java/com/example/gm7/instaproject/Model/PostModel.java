package com.example.gm7.instaproject.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emad on 12/29/17.
 *
 */

public class PostModel implements Serializable {

    public String description;
    public String mainImageUrl;
    public String imag2Url;
    public String imag3Url;
    public String image4Url;
    public String timeSpam;
    public String uid;

    public PostModel(){}

    public PostModel(String description, String mainImageUrl, String imag2Url, String imag3Url, String image4Url,
                     String timeSpam, String uid) {
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.imag2Url = imag2Url;
        this.imag3Url = imag3Url;
        this.image4Url = image4Url;
        this.timeSpam = timeSpam;
        this.uid = uid;
    }

    public Map<String,Object> insertPost(){
        HashMap<String,Object> params = new HashMap<>();
        params.put("description",description);
        params.put("mainImageUrl",mainImageUrl);
        params.put("imag2Url",imag2Url);
        params.put("imag3Url",imag3Url);
        params.put("timeSpam",timeSpam);
        params.put("uid",uid);
        return params;
    }
}
