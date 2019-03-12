package jsam;

import java.lang.String;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
/* TODO: space-delimited strings with optional quoting. For now only the first
word of the error is passed on, which is not ideal.
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
*/
public class Reply {
    String topic = "";
    String type = "";
    REPLY_TYPES result = REPLY_TYPES.UNSET;
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
        TIMEOUT,
        UNSET;
        public static REPLY_TYPES set(String type) {
            String temp = type.trim();
            switch (temp) {
            case "RESULT=OK":
                return OK;
            case "RESULT=CANT_REACH_PEER":
                return CANT_REACH_PEER;
            case "RESULT=DUPLICATED_ID":
                return DUPLICATED_ID;
            case "RESULT=DUPLICATED_DEST":
                return DUPLICATED_DEST;
            case "RESULT=I2P_ERROR":
                return I2P_ERROR;
            case "RESULT=INVALID_KEY":
                return INVALID_KEY;
            case "RESULT=KEY_NOT_FOUND":
                return KEY_NOT_FOUND;
            case "RESULT=PEER_NOT_FOUND":
                return PEER_NOT_FOUND;
            case "RESULT=TIMEOUT":
                return TIMEOUT;
            }
            return UNSET;
        }
        public static String get(REPLY_TYPES type) {
            switch (type) {
            case OK:
                return "RESULT=OK";
            case CANT_REACH_PEER:
                return "RESULT=CANT_REACH_PEER";
            case DUPLICATED_ID:
                return "RESULT=DUPLICATED_ID";
            case DUPLICATED_DEST:
                return "RESULT=DUPLICATED_DEST";
            case I2P_ERROR:
                return "RESULT=I2P_ERROR";
            case INVALID_KEY:
                return "RESULT=INVALID_KEY";
            case KEY_NOT_FOUND:
                return "RESULT=KEY_NOT_FOUND";
            case PEER_NOT_FOUND:
                return "RESULT=PEER_NOT_FOUND";
            case TIMEOUT:
                return "RESULT=TIMEOUT";
            }
            return "RESULT=UNSET";
        }
    };
    public Reply(String reply) {
        String[] replyvalues = reply.trim().split(" ");
        if (replyvalues.length < 2) {
            System.out.println("Malformed reply: " + reply + replyvalues.length);
            return;
        }
        topic = replyvalues[0];
        type = replyvalues[1];
        result = REPLY_TYPES.set(replyvalues[2]);

        String[] replyLast = Arrays.copyOfRange(replyvalues, 2, replyvalues.length);
        for (int x = 0; x < replyLast.length; ++x) {
            String[] kv = replyLast[x].split("=", 2);
            if (kv.length == 2) {
                replyMap.put(kv[0], kv[1]);
            }
        }
    }
    public String String() {
        return topic + " " + type + " " + REPLY_TYPES.get(result) + " " + replyMap.toString();
    }
}
