package com.stc.launchersetting.com.stc.launchersetting.provider;

/**
 * Created by cninlaxu on 2015/4/7.
 */
public class Setting {
    private Integer _id;
    private String _key;
    private Integer _type;
    private Integer _keyValue;

    public Setting(String key, Integer type){
        _key = key;
        _type = type;
        _keyValue = 0;
    }

    public void set_id(Integer id){
        _id = id;
    }
    public Integer get_id(){
        return _id;
    }

    public void set_key(final String key){
        _key = key;
    }

    public String get_key(){
        return _key;
    }
    public Integer get_type() { return _type; }
    public void set_type(Integer type){_type = type;}
    public Integer get_keyValue(){
        return _keyValue;
    }
    public void set_keyValue(final Integer keyValue)
    {
        _keyValue = keyValue;
    }

}
