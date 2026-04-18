package loongly.sclp.language;

import java.util.HashMap;

import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
public class LangFile 
{
     /**
     * 解析 .lang 文件流为 Map
     * @param inputStream lang 文件的输入流
     * @return 包含键值对的 Map
     */
    public static HashMap<String, String> parseLangFile(InputStream inputStream) 
    {
        HashMap<String, String> map = new HashMap<>();
        if (inputStream == null) 
        {
            return map;
        }

        try (java.io.BufferedReader reader = new java.io.BufferedReader(
                new java.io.InputStreamReader(inputStream, java.nio.charset.StandardCharsets.UTF_8))) 
        {
            
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) 
            {
                lineNumber++;
                // 去除首尾空白
                line = line.trim();
                
                // 跳过空行和注释行
                if (line.isEmpty() || line.startsWith("#")) 
                {
                    continue;
                }

                // 查找第一个 '=' 的位置
                int separatorIndex = line.indexOf('=');
                if (separatorIndex == -1) 
                {
                    System.out.println("Invalid line: " + line + " in lang file, line number: " + lineNumber);
                    // 如果没有 '='，则该行格式不正确，跳过
                    continue;
                }

                // 提取 Key 和 Value
                String key = line.substring(0, separatorIndex).trim();
                String value = line.substring(separatorIndex + 1);
                value = value.trim();

                if (!key.isEmpty()) 
                {
                    map.put(key, value);
                }
            }
        }
        catch (Exception e) 
        {
            System.err.println("Error parsing lang file stream: " + e.getMessage());
            e.printStackTrace();
        }

        return map;
    }
    
    public static String getLangFilePath(String langCode)
    {
        String path = "/assets/sclp/lang/%s.lang";
        return String.format(path, langCode);
    }
}
