package ppc.cloud.plugin;

import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.boot.yaml.SpringProfileDocumentMatcher;
import org.springframework.core.CollectionFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import ppc.cloud.plugin.utils.Constants;
import ppc.cloud.plugin.utils.CustomProperties;
import ppc.cloud.plugin.utils.Env;
import ppc.cloud.plugin.utils.YamlConvertTool;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by qianjie on 6/29/17.
 */
public class DefaultYamlProcessor extends YamlProcessor {
    private static String APPENDIX = ".yml";
    private static String PREFIX = "/src/main/resources/config/";
    private boolean formatKey = false;

    public DefaultYamlProcessor(String basedir, Env env, boolean formatKey){
        this.formatKey = formatKey;
        Resource resource;
        if("basic".equals(env.name())){
            resource = new FileSystemResource(basedir + PREFIX + Constants.APPLICATION + APPENDIX);
        } else {
            resource = new FileSystemResource(basedir + PREFIX + Constants.APPLICATION + "-" + env.name() + APPENDIX);
        }

        super.setResources(resource);
        DocumentMatcher documentMatcher = new SpringProfileDocumentMatcher(env.name());
        super.setDocumentMatchers(documentMatcher);
    }

    public Properties createProperties() {
        final Properties result = new CustomProperties() {
            @Override
            public String getProperty(String key) {
                Object value = get(key);
                return (value != null ? value.toString() : null);
            }

            @Override
            public synchronized Enumeration<Object> keys() {
                return Collections.enumeration(new TreeSet<Object>(super.keySet()));
            }
        };
        final Map<String,String> stringMap = new HashMap();
        try{
            process(new MatchCallback() {
                public void process(Properties properties, Map<String, Object> map) {
                    Enumeration enumeration = properties.propertyNames();
                    while(enumeration.hasMoreElements()){
                        String key = (String)enumeration.nextElement();
                        String value = properties.get(key).toString();
                        stringMap.put(formatKey?YamlConvertTool.formatKey(key,"-"):key,value);
                    }
                    result.putAll(stringMap);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
