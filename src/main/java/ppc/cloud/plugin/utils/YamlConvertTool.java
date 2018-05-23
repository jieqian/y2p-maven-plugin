package ppc.cloud.plugin.utils;

/**
 * Created by qianjie on 10/26/17.
 */

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class YamlConvertTool {

    public static void main(String[] args) {
        String str = "spring.datasource.hikari.maximum-pool-size";
        System.out.println(formatKey(str,"-"));
    }

    /**
     * 去掉特殊字符，如果含有特殊字符，把key的特殊字符所在位置处理成驼峰形式
     *
     * @param key
     * @param sep
     * @return
     */
    public static String formatKey(String key, String sep) {
        if (key == null) return null;

        if (!key.contains(sep)) return key;

        String[] items = key.split(sep);
        StringBuffer sb = new StringBuffer();
        for(String item : items){
            sb.append(capitalize(item));
        }
        return uncapitalize(sb.toString());
    }


    /**
     * 摘自commons-lang3的StringUtils
     *
     * @param str
     * @return
     */
    private static String capitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toTitleCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int newCodePoints[] = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint; // copy the remaining ones
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }

    /**
     * 摘自commons-lang3的StringUtils
     *
     * @param str
     * @return
     */
    private static String uncapitalize(final String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }

        final int firstCodepoint = str.codePointAt(0);
        final int newCodePoint = Character.toLowerCase(firstCodepoint);
        if (firstCodepoint == newCodePoint) {
            // already capitalized
            return str;
        }

        final int newCodePoints[] = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        newCodePoints[outOffset++] = newCodePoint; // copy the first codepoint
        for (int inOffset = Character.charCount(firstCodepoint); inOffset < strLen; ) {
            final int codepoint = str.codePointAt(inOffset);
            newCodePoints[outOffset++] = codepoint; // copy the remaining ones
            inOffset += Character.charCount(codepoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }
}
