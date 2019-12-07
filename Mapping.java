package server;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @ClassName Mapping
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/6 17:28]
 * @Version 1.0
 **/

public class Mapping {
    private String name;
    private Set<String> patterns;

    public Mapping() {
      patterns=new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }

    public void setPatterns(Set<String> patterns) {
        this.patterns = patterns;
    }

    public void addPatterns(String pattern){
        patterns.add(pattern);
    }
}
