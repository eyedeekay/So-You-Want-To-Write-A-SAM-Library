### Creating a "Session" for your application

Now that you've negotiated your connection to SAM and agreed on a SAM version
you both speak, you can set up peer-to-peer connections for your application
to connect to other i2p applications. You do this by sending a "SESSION CREATE"
command to the SAM Bridge. To do that, we'll use a CreateSession function that
accepts a session ID and a destination type parameter.

``` Java
    public boolean CreateSession(String id, String destination ){
        Reply repl = CommandSAM("SESSION CREATE STYLE=STREAM ID=" + id + " DESTINATION=" + destination);
        if (repl.result == Reply.REPLY_TYPES.OK) {
            return true;
        }
        return false;
    }
```

That was easy, right? All we had to do was adapt the pattern we used in our
HelloSAM function to the ```SESSION CREATE``` command. A good reply from the
bridge will still return OK. Let's see if this function works by writing a test
for it:

``` Java
    @Test public void testCreateSession() {
        Jsam classUnderTest = new Jsam();
        assertTrue("HelloSAM should return 'true' in the presence of an alive SAM bridge", classUnderTest.HelloSAM());
        assertTrue("CreateSession should return 'true' in the presence of an alive SAM bridge", classUnderTest.CreateSession("test", ""));
    }
```

Note that in this test, we *must* call HelloSAM first to establish communication
with SAM before starting our session. If not, the bridge will reply with an
error and the test will fail.

### Sending and Recieving Information

