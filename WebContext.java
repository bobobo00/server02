package server;

import server.Entity;
import server.Mapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName WebContext
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/6 17:48]
 * @Version 1.0
 **/

public class WebContext {
    private List<Entity> entities;
    private List<Mapping> mappings;
    //key-name  value-clz
    Map<String,String> entityMap=new HashMap<>();
    //key-pattern  value-name
    Map<String,String> mappingMap=new HashMap<>();

    public WebContext(List<Entity> entities, List<Mapping> mappings) {
        this.entities = entities;
        this.mappings = mappings;
        //将entitites 的List转成了对应map
        for(Entity entity:entities){
            String key=entity.getName();
            String val=entity.getClz();
            entityMap.put(key,val);
        }
        //将mapping的List转成了对应map
        for(Mapping mapping:mappings){
            String val=mapping.getName();
            Set<String> patterns=mapping.getPatterns();
            for(String key:patterns){
                mappingMap.put(key,val);
            }
        }
    }
    /**
     * 通过URL的路径找到了对应class
     * @param pattern
     * @return
     */
    public String getClz(String pattern){
        String name=mappingMap.get(pattern);
        return entityMap.get(name);
    }


}
