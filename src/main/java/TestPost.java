import java.io.*;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class TestPost {


    public static void main(String[] args) throws Exception {

        TestPost http = new TestPost();

        System.out.println("\nTesting 2 - Send Http POST request");
        LogTest test = new LogTest();
        test.performSomeTask();
        http.sendPost();
    }


    public void sendPost() throws Exception {


        String url = "https://mattermost.oit.duke.edu/hooks/urwdfpf8xjbwuncj7dxe9uu4qw";
        JSONObject message = checkLogs();

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");
        StringEntity entity = new StringEntity(message.toString());
        post.setEntity(entity);

        HttpResponse response = client.execute(post);
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + post.getEntity());
        System.out.println("Response Code : " +
                response.getStatusLine().getStatusCode());

        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        System.out.println(result.toString());

    }

    public JSONObject checkLogs(){

        Properties props = new Properties();
        InputStream input = null;
        JSONObject message = new JSONObject();


        try{


            input = (TestPost.class.getClassLoader().getResourceAsStream("log.properties"));
            props.load(input);

            Set keys = props.keySet();
            for (Object k :keys){
                if(k.toString().equals("ERROR")){
                    message.put("text",k.toString() + " " + props.getProperty(k.toString()).toString());
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
          finally {
            if(input!=null){
                try{
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return message;
    }

}