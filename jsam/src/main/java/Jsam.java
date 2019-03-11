package jsam;

import java.security.SecureRandom;
import java.lang.String;
import java.net.*;
import java.io.*;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class Jsam extends Socket {
    public String SAMHost = "127.0.0.1";
    public int SAMPort = 7656;
    private String ID = "";
    private PrintWriter writer;
    private BufferedReader reader;
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_";
    private static final SecureRandom RANDOM = new SecureRandom();
    enum SIGNATURE_TYPE {
        DSA_SHA1,
        ECDSA_SHA256_P256,
        ECDSA_SHA384_P384,
        ECDSA_SHA512_P521,
        EdDSA_SHA512_Ed25519;
    }
    public SIGNATURE_TYPE SigType = SIGNATURE_TYPE.EdDSA_SHA512_Ed25519;
    public Jsam() {
        startConnection();
    }
    public Jsam(String host, int port, SIGNATURE_TYPE sig) {
        SAMHost = host;
        SAMPort = port;
        SigType = sig;
        startConnection();
    }
    public void startConnection() {
        try {
            this.connect(new InetSocketAddress(SAMHost, SAMPort));
            writer = new PrintWriter(this.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(this.getInputStream()));
        } catch (Exception e) {

        }
    }
    public String SAMAddress() {
        return SAMHost + ":" + SAMPort;
    }
    public String SignatureType() {
        switch (SigType) {
        case DSA_SHA1:
            return "SIGNATURE_TYPE=DSA_SHA1";
        case ECDSA_SHA256_P256:
            return "SIGNATURE_TYPE=ECDSA_SHA256_P256";
        case ECDSA_SHA384_P384:
            return "SIGNATURE_TYPE=ECDSA_SHA384_P384";
        case ECDSA_SHA512_P521:
            return "SIGNATURE_TYPE=ECDSA_SHA512_P521";
        case EdDSA_SHA512_Ed25519:
            return "SIGNATURE_TYPE=EdDSA_SHA512_Ed25519";
        }
        return "";
    }
    public boolean HelloSAM() {
        Reply repl = CommandSAM("HELLO VERSION MIN=3.0 MAX=3.1\n");
        if (repl.result == Reply.REPLY_TYPES.OK) {
            System.out.println(repl.String());
            return true;
        }
        System.out.println(repl.String());
        return false;
    }
    public String CreateSession() {
        return CreateSession(generateID());
    }
    public String CreateSession(String id) {
        return CreateSession(id, "TRANSIENT");
    }
    public String CreateSession(String id, String destination ) {
        if (destination == "") {
            destination = "TRANSIENT";
        }
        if (id == "") {
            id = generateID();
        }
        ID = id;
        String cmd = "SESSION CREATE STYLE=STREAM ID=" + ID + " DESTINATION=" + destination + "\n";
        Reply repl = CommandSAM(cmd);
        if (repl.result == Reply.REPLY_TYPES.OK) {
            System.out.println(repl.String());
            return id;
        }
        System.out.println(repl.String());
        return "";
    }
    public String ConnectSession(String destination) {
        return ConnectSession(ID, destination);
    }
    public String ConnectSession(String id, String destination) {
        String cmd = "STREAM CONNECT ID=" + id + " DESTINATION=" + destination + "\n";
        Reply repl = CommandSAM(cmd);
        if (repl.result == Reply.REPLY_TYPES.OK) {
            System.out.println(repl.String());
            return id;
        }
        System.out.println(repl.String());
        return "";
    }
    public Reply CommandSAM(String args) {
        System.out.println(args);
        writer.println(args + "\n");
        try {
            String repl = reader.readLine();
            return new Reply(repl);
        } catch (Exception e) {
            return new Reply("");
        }
    }
    public static String generateID() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; ++i) {
            sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return sb.toString();
    }
    public void close() {

    }
}
