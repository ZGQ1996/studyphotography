package com.example.studyphotography.util;

import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ Author     ：Zgq
 * @ Date       ：Created in 10:28 2019/3/27
 * @ Description：
 * @ Modified By：
 * @Version: $
 */
public class HttpUtil {

    private static Logger log = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 处理get请求
     *
     * @param url
     *            请求地址 如
     *            http://localhost:9090/base_rpc/basicData/getInvoice?t=1507513445960&invoiceId=039D906D07C74306B635DD89F87584CD&token=bd302857fbd4a01af7401fe229d43918
     * @return
     */
    public static String getHttp(String url) {
        HttpClient httpClient = null;
        GetMethod get = null;
        SimpleHttpConnectionManager simpleHttpConnectionManager = null;
        String info = null;

        try {
            httpClient = new HttpClient();
            get = new GetMethod(url);
            // 执行
            httpClient.executeMethod(get);
            // 接口返回信息
            info = new String(get.getResponseBody(), "UTF-8");
            log.info("接口【" + url + "】 返回:" + info);
        } catch (Exception e) {
            log.info("调用接口【" + url + "】  出错:" + e);
            return null;
        } finally {
            if (get != null) {
                get.releaseConnection();
            }
            if (httpClient != null) {
                simpleHttpConnectionManager = (SimpleHttpConnectionManager) httpClient.getHttpConnectionManager();
                if (simpleHttpConnectionManager != null) {
                    simpleHttpConnectionManager.shutdown();
                }
            }
        }
        return info;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 发起https请求并获取结果
     *
     * @param request
     *            请求地址
     * @param RequestMethod
     *            请求方式（GET、POST）
     * @param output
     *            提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     *
     * example:  HttpUtil.httpRequest(url, "POST", jsonMenu);
     */
    public static JSONObject httpRequest(String request, String RequestMethod, String output) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 建立连接
            URL url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod(RequestMethod);
            if (output != null) {
                OutputStream out = connection.getOutputStream();
                out.write(output.getBytes("UTF-8"));
                out.close();
            }
            // 流处理
            InputStream input = connection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(input, "UTF-8");
            BufferedReader reader = new BufferedReader(inputReader);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            // 关闭连接、释放资源
            reader.close();
            inputReader.close();
            input.close();
            input = null;
            connection.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (Exception e) {
        }
        return jsonObject;
    }
}
