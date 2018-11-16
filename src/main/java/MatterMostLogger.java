import java.io.*;
import java.util.Properties;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class MatterMostLogger {

    private String url;
    private String errorType = null;

    public MatterMostLogger() throws Exception {

         url = "https://mattermost.oit.duke.edu/hooks/urwdfpf8xjbwuncj7dxe9uu4qw";


        System.out.println("\nTesting 2 - Send Http POST request");

    }


    public void sendPost() throws Exception {


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

        if(errorType == null){
            errorType = "ERROR";
        }

        System.out.println(errorType);
        try{


            input = (MatterMostLogger.class.getClassLoader().getResourceAsStream("log.properties"));
            props.load(input);

            Set keys = props.keySet();
            for (Object k :keys){
                if(k.toString().equals(errorType.toUpperCase())){
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }


}