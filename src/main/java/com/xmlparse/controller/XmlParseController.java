package com.xmlparse.controller;

import com.xmlparse.model.XmlLayoutParse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xinggen.guo on 10/9/16.
 */

@Controller
public class XmlParseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "/layout_parse";
    }

    @RequestMapping(value = "/dealXml", method = RequestMethod.POST)
    public String parseXmk(@ModelAttribute("XmlLayoutParse") XmlLayoutParse xmlParseEntity) {

        if(xmlParseEntity != null){
            xmlParseEntity.dealXmlLayout();
        }
        return "/index";
    }

}
