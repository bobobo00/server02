import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Handler
 * @Description TODO
 * @Auther danni
 * @Date 2019/12/6 17:34]
 * @Version 1.0
 **/

public class Handler extends DefaultHandler {
    private List<Entity> entities;
    private  List<Mapping> mappings;
    private Entity entity;
    private Mapping mapping;
    private String tag;
    private boolean isMapping;

    public void startDocument()throws SAXException{
        entities=new ArrayList<>();
        mappings=new ArrayList<>();
    }


    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if(null!=qName){
            tag=qName;
            if(tag.equals("servlet")){
                entity=new Entity();
            }
            if(tag.equals("mapping")){
                mapping=new Mapping();
                isMapping=true;
            }
        }
    }


    public void characters(char[] ch, int start, int length) throws SAXException {
        String contents=new String(ch,start,length).trim();
        if(null!=tag){
            if(!isMapping){
                if(tag.equals("servlet-name")){
                    entity.setName(contents);
                }else if(tag.equals("servlet-class")){
                    entity.setClz(contents);
                }
            }else{
                if(tag.equals("name")){
                    mapping.setName(contents);
                }else if(tag.equals("pattern")){
                    mapping.addPatterns(contents);
                }
            }
        }
    }


    public void endElement(String uri, String localName, String qName) throws SAXException {
        if(null!=tag){
            if(qName.equals("servlet")){
                entities.add(entity);
            }else if(qName.equals("mapping")){
                mappings.add(mapping);
            }
        }
        tag=null;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public List<Mapping> getMappings() {
        return mappings;
    }
}
