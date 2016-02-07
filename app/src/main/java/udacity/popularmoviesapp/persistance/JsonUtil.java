package udacity.popularmoviesapp.persistance;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import java.io.IOException;

/**
 * Created by kartik on 16/7/15.
 */
public class JsonUtil {

    public Object writeJsonToJavaObject(String json, Class<?> tClass) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(json, tClass);
        } catch (JsonParseException e) {
        } catch (JsonMappingException e) {
        } catch (IOException e) {
        }
        throw new RuntimeException("This is a stupid idea.");
    }

    public String writeJavaObjectToString(Object object){

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;
        try {
            json = ow.writeValueAsString(object);
            return json;

        } catch (JsonGenerationException e) {
        } catch (JsonMappingException e) {
        } catch (IOException e) {
        }
        throw new RuntimeException("THis is a stupid idea.");
    }
}