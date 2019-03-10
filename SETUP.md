Setting up the Library
----------------------

There are a few pieces of data that almost any SAM library should probably
manage. It will at least need to store the address of the SAM Bridge you intend
to use and the signature type you wish to use.

### Storing the SAM address

I prefer to store the SAM address as a String and an Integer, and re-combine
them in a function at runtime.


``` Java
    public String SAMHost = "127.0.0.1";
    public int SAMPort = "7656";
    public String SAMAddress(){
        return SAMHost + ":" + SAMPort;
    }
```

### Storing the Signature Type

The valid signature types for an I2P Tunnel are DSA\_SHA1, ECDSA\_SHA256\_P256,
ECDSA\_SHA384\_P384, ECDSA\_SHA512\_P521, EdDSA\_SHA512\_Ed25519, but it is
strongly recommended that you use EdDSA\_SHA512\_Ed25519 as a default if you
implement at least SAM 3.1. In Java, the 'enum' datastructure lends itself to
this task, as it is intended to contain a group of constants. Add the enum, and
an instance of the enum, to your Java class definition.

``` Java
    enum SIGNATURE_TYPE {
        DSA_SHA1,
        ECDSA_SHA256_P256,
        ECDSA_SHA384_P384,
        ECDSA_SHA512_P521,
        EdDSA_SHA512_Ed25519;
    }
    public SIGNATURE_TYPE SigType = SIGNATURE_TYPE.EdDSA_SHA512_Ed25519;
```

That takes care of reliably storing the signature type in use by the SAM
connection, but you've still got to retrieve it as a string to communicate it
to the bridge.

``` Java
    public String SignatureType() {
        switch (SigType) {
            case DSA_SHA1:
                return "DSA_SHA1";
            case ECDSA_SHA256_P256:
                return "ECDSA_SHA256_P256";
            case ECDSA_SHA384_P384:
                return "ECDSA_SHA384_P384";
            case ECDSA_SHA512_P521:
                return "ECDSA_SHA512_P521";
            case EdDSA_SHA512_Ed25519:
                return "EdDSA_SHA512_Ed25519";
        }
        return "EdDSA_SHA512_Ed25519";
    }
```

It's important to test things, so let's write some tests:

``` Java
    @Test public void testValidDefaultSAMAddress() {
        Jsam classUnderTest = new Jsam();
        assertEquals("127.0.0.1:7656", classUnderTest.SAMAddress());
    }
    @Test public void testValidDefaultSignatureType() {
        Jsam classUnderTest = new Jsam();
        assertEquals("EdDSA_SHA512_Ed25519", classUnderTest.SignatureType());
    }
```

Once that's done, begin creating your constructor. Note that we've given our
library defaults which will be useful in default situations on all existing I2P
routers so far.

``` Java
    public Jsam(String host, int port, SIGNATURE_TYPE sig) {
        SAMHost = host;
        SAMPort = port;
        SigType = sig;
    }
```

Establishing a SAM Connection
-----------------------------

Finally, the good part. Interaction with the SAM bridge is done by sending a
"command" to the address of the SAM bridge, and you can parse the result of the
command as a set of string-based key-value pairs.

``` Java
```
