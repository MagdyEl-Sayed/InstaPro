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
    public String timeSpam;

    public PostModel(){}

    public PostModel(String description, String mainImageUrl, String imag2Url, String imag3Url, String timeSpam) {
        this.description = description;
        this.mainImageUrl = mainImageUrl;
        this.imag2Url = imag2Url;
        this.imag3Url = imag3Url;
        this.timeSpam = timeSpam;
    }

    public Map<String,Object> insertPost(){
        HashMap<String,Object> params = new HashMap<>();
        params.put("description",description);
        params.put("mainImageUrl",mainImageUrl);
        params.put("imag2Url",imag2Url);
        params.put("imag3Url",imag3Url);
        params.put("timeSpam",timeSpam);
        return params;
    }
}
