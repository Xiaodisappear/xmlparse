package com.xmlparse.model;

import com.xmlparse.utils.TextUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xinggen.guo on 10/11/16.
 */
public class XmlLayoutParse {

    private static final String xmlIdName = "id";
    private static final String xmlIdAdd = "+id";
    private static final String variableReplace = "_";

    /**
     * 使用java或注解
     * <p>
     * java or aa
     */
    private String javaOrAA;

    /**
     * 修饰符
     * <p>
     * 使用 public 或者 private
     */
    private String publicPrivate;

    /**
     * 是否使用静态
     */
    private String staticModify;

    /**
     * 是否使用final修饰
     */
    private String finalModify;

    /**
     * 生成变量名的前缀
     */
    private String prefix;


    private String verbose;
    private String suppress;

    /**
     * 需要解析的xml
     */
    private String xml;

    /**
     * 返回结果
     */
    private String results;

    /**
     * 所有节点名称
     */
    private Map<String, String> xmlMaps;

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }


    public String getJavaOrAA() {
        return javaOrAA;
    }

    public void setJavaOrAA(String javaOrAA) {
        this.javaOrAA = javaOrAA;
    }

    public String getPublicPrivate() {
        return publicPrivate;
    }

    public void setPublicPrivate(String publicPrivate) {
        this.publicPrivate = publicPrivate;
    }

    public String getStaticModify() {
        return staticModify;
    }

    public void setStaticModify(String staticModify) {
        this.staticModify = staticModify;
    }

    public String getFinalModify() {
        return finalModify;
    }

    public void setFinalModify(String finalModify) {
        this.finalModify = finalModify;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getVerbose() {
        return verbose;
    }

    public void setVerbose(String verbose) {
        this.verbose = verbose;
    }

    public String getSuppress() {
        return suppress;
    }

    public void setSuppress(String suppress) {
        this.suppress = suppress;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void dealXmlLayout() {

        try {
            if (!TextUtils.isEmpty(xml)) {

                if (xmlMaps == null) {
                    xmlMaps = new HashMap<>();
                }

                SAXReader reader = new SAXReader();
                Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));

                Element el = doc.getRootElement();
                listNodes(el);
                assembleData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void assembleData() {
        if (!xmlMaps.isEmpty()) {

            String endLine = System.getProperty("line.separator", "\n");

            boolean isJava = isJavaOrAa();
            String templateVariable = getVariableTemplate();
            String templateFind = getFindTemolate();

            StringBuffer variableDataSb = new StringBuffer();
            StringBuffer findDataSb = new StringBuffer();

            variableDataSb.append(" // Content View Elements").append(endLine).append(endLine);
            findDataSb.append(" private void bindViews() {").append(endLine).append(endLine);

            for (Map.Entry<String, String> entry : xmlMaps.entrySet()) {

                String key = entry.getKey().replace("@+id/", "");
                String variableKey = getVariableByKey(key);
                String value = entry.getValue();
                if (isJava) {
                    variableDataSb.append(String.format(templateVariable, variableKey)).append(endLine);
                    findDataSb.append(String.format(templateFind, variableKey, value, key)).append(endLine);
                }

            }

            variableDataSb.append(endLine).append(" // End Of Content View Elements").append(endLine).append(endLine);
            findDataSb.append(endLine).append("  }");
            results = variableDataSb.toString() + findDataSb.toString();
        }
    }

    /**
     * 获取变量
     *
     * @param key
     * @return
     */
    private String getVariableByKey(String key) {

        if (TextUtils.isEmpty(key)) {
            return null;
        }

        if(!TextUtils.isEmpty(prefix)){
            if(key.length() > 0) {
                String upperCase = String.valueOf(key.charAt(0)).toUpperCase();
                key = new StringBuffer(key).replace(0, 1, upperCase).toString();
                key = new StringBuffer(prefix).append(key).toString();
            }
        }

        while (key.contains(variableReplace)) {
            int index = key.indexOf(variableReplace);
            if (index + 1 < key.length()) {
                String upperCase = String.valueOf(key.charAt(index + 1)).toUpperCase();
                key = new StringBuffer(key).replace(index + 1, index + 2, upperCase).toString();
            }

            key = key.replaceFirst(variableReplace, "").trim();
        }

        return key;
    }

    /**
     * 判断是java还是注解
     *
     * @return
     */
    private boolean isJavaOrAa() {

        if (!TextUtils.isEmpty(javaOrAA) && javaOrAA.equals("aa")) {
            return false;
        }

        return true;

    }

    private String getFindTemolate() {
        return "    %s = (%s) findViewById(R.id.%s);";
    }

    /**
     * 组织数据变量模版
     *
     * @return
     */
    private String getVariableTemplate() {

        StringBuffer sb = new StringBuffer();
        sb.append(" ");
        sb.append(publicPrivate).append(" ");

        if (!TextUtils.isEmpty(staticModify)) {
            sb.append(staticModify).append(" ");
        }

        if (!TextUtils.isEmpty(finalModify)) {
            sb.append(finalModify).append(" ");
        }

        sb.append("String").append(" ");
        sb.append("%s").append(";");

        return sb.toString();

    }

    /**
     * Loop reads all need id node.
     */
    public void listNodes(Element node) {

        List<Attribute> list = node.attributes();
        for (Attribute attribute : list) {
            if (attribute.getName().trim().equals(xmlIdName) && attribute.getValue().trim().contains(xmlIdAdd)) {
                xmlMaps.put(attribute.getValue(), node.getName());
            }
        }

        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext()) {
            Element e = iterator.next();
            listNodes(e);
        }
    }

    @Override
    public String toString() {
        return "XmlLayoutParse{" +
                "javaOrAA='" + javaOrAA + '\'' +
                ", publicPrivate='" + publicPrivate + '\'' +
                ", staticModify='" + staticModify + '\'' +
                ", finalModify='" + finalModify + '\'' +
                ", prefix='" + prefix + '\'' +
                ", verbose='" + verbose + '\'' +
                ", suppress='" + suppress + '\'' +
                ", xml='" + xml + '\'' +
                ", results='" + results + '\'' +
                ", xmlMaps=" + xmlMaps +
                '}';
    }
}
