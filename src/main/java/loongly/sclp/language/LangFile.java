package loongly.sclp.language;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;
public class LangFile 
{
    private String langCode_;
    private Map<String, String> data_ = null;
    public LangFile(String langCode)
    {
        langCode_ = langCode;
        initMap();
    }

    public Map<String, String> toMap()
    {
        if(data_ == null)
        {
            initMap();
        }
        return Collections.unmodifiableMap(data_);
    }

    public void initMap()
    {
        String langFilePath = LangFile.getLangFilePath(langCode_);
        try (InputStream inputStream = I18NLanguage.class.getResourceAsStream(langFilePath)) 
        {
            if (inputStream == null) 
            {
                
                System.out.println(langCode_ + ".lang file not found");
                data_ = new HashMap<String,String>();
            }
            else
            {
                data_ = LangFile.parseLangFile(inputStream);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        CompletableFuture.runAsync
        (
            ()->
            {
                String langUrl = LangFile.getLangUrl(langCode_);
                data_ = parseLangFile(doGet(langUrl));
            }
        );
    }

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

    public static String getLangUrl(String langCode)
    {
        String url = "https://gitee.com/zixuan_long/Json/raw/master/sclp/lang/%s";
        return String.format(url, langCode);
    }

    static private InputStream doGet(String httpurl)
    {
        HttpURLConnection connection = null;
        InputStream is = null;
        InputStream result = null;// 返回结果字符串
        try
        {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(3000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(6000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200)
            {
                is = connection.getInputStream();
                result = is;
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != is)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }
}
