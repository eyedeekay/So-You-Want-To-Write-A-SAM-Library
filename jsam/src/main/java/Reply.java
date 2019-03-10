package jsam;

import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Reply {
    String topic;
    String type;
    Map<String, String> replyMap = new HashMap<String, String>();
    enum REPLY_TYPES {
        OK,
        CANT_REACH_PEER,
        DUPLICATED_ID,
        DUPLICATED_DEST,
        I2P_ERROR,
        INVALID_KEY,
        KEY_NOT_FOUND,
        PEER_NOT_FOUND,
        TIMEOUT;
    };
    public Reply(String reply) {
        String trimmed = reply.trim();
        String[] replyvalues = reply.split(" ");
        if (replyvalues.length < 3) {
            //handle malformed reply here
        }
        topic = replyvalues[0];
        topic = replyvalues[1];
        String[] replyLast = Arrays.copyOfRange(replyvalues, 2, replyvalues.length);
        for (int x = 0; x < replyLast.length; x++) {
            String[] kv = replyLast[x].split("=");
            if (kv.length != 2) {

            }
            replyMap.put(kv[0], kv[1]);
        }
    }
}
