package Client;

import java.io.Serializable;
import java.util.Map;

/**
 *
 * @author landr
 */
public class Request implements Serializable {

    private String action;
    private Map<String, Object> payload;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }

    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
}
