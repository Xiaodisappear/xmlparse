package com.xmlparse.controller;

import com.xmlparse.model.XmlLayoutParse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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
    @ResponseBody
    public String parseXml(@ModelAttribute("XmlLayoutParse") XmlLayoutParse xmlParseEntity) {

        if (xmlParseEntity != null) {
            xmlParseEntity.dealXmlLayout();
        }

        return xmlParseEntity.getResults();
    }


}
