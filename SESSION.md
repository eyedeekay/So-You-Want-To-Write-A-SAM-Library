### Creating a "Session" for your application

Now that you've negotiated your connection to SAM and agreed on a SAM version
you both speak, you can set up peer-to-peer connections for your application
to connect to other i2p applications. You do this by sending a "SESSION CREATE"
command to the SAM Bridge. To do that, we'll use a CreateSession function that
accepts a session ID and a destination type parameter.

``` Java
    public String CreateSession(String id, String destination ) {
        if (destination == "") {
            destination = "TRANSIENT";
        }
        Reply repl = CommandSAM("SESSION CREATE STYLE=STREAM ID=" + ID + " DESTINATION=" + destination);
        if (repl.result == Reply.REPLY_TYPES.OK) {
            return id;
        }
        return "";
    }
```

That was easy, right? All we had to do was adapt the pattern we used in our
HelloSAM function to the ```SESSION CREATE``` command. A good reply from the
bridge will still return OK, and in that case we return the ID of the newly
created SAM connection. Otherwise, we return an empty string because that's an
invalid ID anyway and it failed, so it's easy to check. Let's see if this
function works by writing a test for it:

``` Java
    @Test public void testCreateSession() {
        Jsam classUnderTest = new Jsam();
        assertTrue("HelloSAM should return 'true' in the presence of an alive SAM bridge", classUnderTest.HelloSAM());
        assertEquals("test", classUnderTest.CreateSession("test", ""));
    }
```

Note that in this test, we *must* call HelloSAM first to establish communication
with SAM before starting our session. If not, the bridge will reply with an
error and the test will fail.

### Looking up Hosts by name or .b32

Now you have your session established and your local destination, and need to
decide what you want to do with them. Your session can now be commanded to
connect to a remote service over I2P, or to wait for incoming connections to
respond to. However, before you can connect to a remote destination, you may
need to obtain the base64 of the destination, which is what the API expects. In
order to do this, we'll create a LookupName function, which will return the
base64 in a usable form.

``` Java
    public String LookupName(String name) {
        String cmd = "NAMING LOOKUP NAME=" + name + "\n";
        Reply repl = CommandSAM(cmd);
        if (repl.result == Reply.REPLY_TYPES.OK) {
            System.out.println(repl.replyMap.get("VALUE"));
            return repl.replyMap.get("VALUE");
        }
        return "";
    }
```

Again, this is almost the same as our HelloSAM and CreateSession functions,
with one difference. Since we're looking for the VALUE specifically and the NAME
field will be the same as the ```name``` argument, it simply returns the base64
string of the destination requested.

Now that we have our LookupName function, let's test it:

``` Java
    @Test public void testLookupName() {
        Jsam classUnderTest = new Jsam();
        assertTrue("HelloSAM should return 'true' in the presence of an alive SAM bridge", classUnderTest.HelloSAM());
        assertEquals("8ZAW~KzGFMUEj0pdchy6GQOOZbuzbqpWtiApEj8LHy2~O~58XKxRrA43cA23a9oDpNZDqWhRWEtehSnX5NoCwJcXWWdO1ksKEUim6cQLP-VpQyuZTIIqwSADwgoe6ikxZG0NGvy5FijgxF4EW9zg39nhUNKRejYNHhOBZKIX38qYyXoB8XCVJybKg89aMMPsCT884F0CLBKbHeYhpYGmhE4YW~aV21c5pebivvxeJPWuTBAOmYxAIgJE3fFU-fucQn9YyGUFa8F3t-0Vco-9qVNSEWfgrdXOdKT6orr3sfssiKo3ybRWdTpxycZ6wB4qHWgTSU5A-gOA3ACTCMZBsASN3W5cz6GRZCspQ0HNu~R~nJ8V06Mmw~iVYOu5lDvipmG6-dJky6XRxCedczxMM1GWFoieQ8Ysfuxq-j8keEtaYmyUQme6TcviCEvQsxyVirr~dTC-F8aZ~y2AlG5IJz5KD02nO6TRkI2fgjHhv9OZ9nskh-I2jxAzFP6Is1kyAAAA", classUnderTest.LookupName("i2p-projekt.i2p"));
    }
```


### Sending and Recieving Information

