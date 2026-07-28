package me.loongly.mods.sclp.language;

import java.util.HashMap;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

import org.apache.commons.io.input.ReaderInputStream;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Formatting;

import me.loongly.mods.sclp.client.SCLPClientMod;
public class LangFile 
{
    private String langCode_;
    private Map<String, String> data_ = new HashMap<String,String>();
    public LangFile(String langCode)
    {
        langCode_ = langCode;
        initMap();
    }

    public Map<String, String> toMap()
    {
        return Collections.unmodifiableMap(data_);
    }

    public void initMap()
    {
        CompletableFuture.runAsync
        (
            ()->
            {
                String langUrl = LangFile.getLangUrl(langCode_);
                String langStr = doGet(langUrl);
                if(langStr != null)
                {
                    SCLPClientMod.LOGGER.info("[SCLP]" + langCode_ +" have internet update.");
                    Map<String, String> tmp = convertJsonToMap(langStr);
                    for(Map.Entry<String, String> entry : tmp.entrySet())
                    {
                        data_.put(entry.getKey(), entry.getValue());
                    }
                }
                SCLPClientMod.LOGGER.info("[SCLP]" + langCode_ + " loaded.");
            }
        );
    }

    public static String getLangUrl(String langCode)
    {
        String url = "https://gitee.com/zixuan_long/Json/raw/master/sclp/1.20/lang/%s.json";
        return String.format(url, langCode);
    }

    static public Map<String, String> convertJsonToMap(String jsonString)
    {
        Gson gson = new Gson();
        TypeToken<Map<String, String>> typeToken = new TypeToken<Map<String, String>>() {};
        return gson.fromJson(jsonString, typeToken.getType());
    }

    static private String doGet(String httpurl)
    {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
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
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null)
                {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
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
            // 关闭资源
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

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
