

<h2 id="setting-up-the-library">Setting up the Library</h2>
<p>There are a few pieces of data that almost any SAM library should probably manage. It will at least need to store the address of the SAM Bridge you intend to use and the signature type you wish to use.</p>
<h3 id="storing-the-sam-address">Storing the SAM address</h3>
<p>I prefer to store the SAM address as a String and an Integer, and re-combine them in a function at runtime.</p>
<div class="sourceCode" id="cb1"><pre class="sourceCode java"><code class="sourceCode java"><a class="sourceLine" id="cb1-1" title="1">    <span class="kw">public</span> <span class="bu">String</span> SAMHost = <span class="st">&quot;127.0.0.1&quot;</span>;</a>
<a class="sourceLine" id="cb1-2" title="2">    <span class="kw">public</span> <span class="dt">int</span> SAMPort = <span class="st">&quot;7656&quot;</span>;</a>
<a class="sourceLine" id="cb1-3" title="3">    <span class="kw">public</span> <span class="bu">String</span> <span class="fu">SAMAddress</span>(){</a>
<a class="sourceLine" id="cb1-4" title="4">        <span class="kw">return</span> SAMHost + <span class="st">&quot;:&quot;</span> + SAMPort;</a>
<a class="sourceLine" id="cb1-5" title="5">    }</a></code></pre></div>
<h3 id="storing-the-signature-type">Storing the Signature Type</h3>
<p>The valid signature types for an I2P Tunnel are DSA_SHA1, ECDSA_SHA256_P256, ECDSA_SHA384_P384, ECDSA_SHA512_P521, EdDSA_SHA512_Ed25519, but it is strongly recommended that you use EdDSA_SHA512_Ed25519 as a default if you implement at least SAM 3.1. In Java, the ‘enum’ datastructure lends itself to this task, as it is intended to contain a group of constants. Add the enum, and an instance of the enum, to your Java class definition.</p>
<div class="sourceCode" id="cb2"><pre class="sourceCode java"><code class="sourceCode java"><a class="sourceLine" id="cb2-1" title="1">    <span class="kw">enum</span> SIGNATURE_TYPE {</a>
<a class="sourceLine" id="cb2-2" title="2">        DSA_SHA1,</a>
<a class="sourceLine" id="cb2-3" title="3">        ECDSA_SHA256_P256,</a>
<a class="sourceLine" id="cb2-4" title="4">        ECDSA_SHA384_P384,</a>
<a class="sourceLine" id="cb2-5" title="5">        ECDSA_SHA512_P521,</a>
<a class="sourceLine" id="cb2-6" title="6">        EdDSA_SHA512_Ed25519;</a>
<a class="sourceLine" id="cb2-7" title="7">    }</a>
<a class="sourceLine" id="cb2-8" title="8">    <span class="kw">public</span> SIGNATURE_TYPE SigType = SIGNATURE_TYPE.<span class="fu">EdDSA_SHA512_Ed25519</span>;</a></code></pre></div>
<p>That takes care of reliably storing the signature type in use by the SAM connection, but you’ve still got to retrieve it as a string to communicate it to the bridge.</p>
<div class="sourceCode" id="cb3"><pre class="sourceCode java"><code class="sourceCode java"><a class="sourceLine" id="cb3-1" title="1">    <span class="kw">public</span> <span class="bu">String</span> <span class="fu">SignatureType</span>() {</a>
<a class="sourceLine" id="cb3-2" title="2">        <span class="kw">switch</span> (SigType) {</a>
<a class="sourceLine" id="cb3-3" title="3">            <span class="kw">case</span> DSA_SHA1:</a>
<a class="sourceLine" id="cb3-4" title="4">                <span class="kw">return</span> <span class="st">&quot;DSA_SHA1&quot;</span>;</a>
<a class="sourceLine" id="cb3-5" title="5">            <span class="kw">case</span> ECDSA_SHA256_P256:</a>
<a class="sourceLine" id="cb3-6" title="6">                <span class="kw">return</span> <span class="st">&quot;ECDSA_SHA256_P256&quot;</span>;</a>
<a class="sourceLine" id="cb3-7" title="7">            <span class="kw">case</span> ECDSA_SHA384_P384:</a>
<a class="sourceLine" id="cb3-8" title="8">                <span class="kw">return</span> <span class="st">&quot;ECDSA_SHA384_P384&quot;</span>;</a>
<a class="sourceLine" id="cb3-9" title="9">            <span class="kw">case</span> ECDSA_SHA512_P521:</a>
<a class="sourceLine" id="cb3-10" title="10">                <span class="kw">return</span> <span class="st">&quot;ECDSA_SHA512_P521&quot;</span>;</a>
<a class="sourceLine" id="cb3-11" title="11">            <span class="kw">case</span> EdDSA_SHA512_Ed25519:</a>
<a class="sourceLine" id="cb3-12" title="12">                <span class="kw">return</span> <span class="st">&quot;EdDSA_SHA512_Ed25519&quot;</span>;</a>
<a class="sourceLine" id="cb3-13" title="13">        }</a>
<a class="sourceLine" id="cb3-14" title="14">        <span class="kw">return</span> <span class="st">&quot;EdDSA_SHA512_Ed25519&quot;</span>;</a>
<a class="sourceLine" id="cb3-15" title="15">    }</a></code></pre></div>
<p>It’s important to test things, so let’s write some tests:</p>
<div class="sourceCode" id="cb4"><pre class="sourceCode java"><code class="sourceCode java"><a class="sourceLine" id="cb4-1" title="1">    <span class="at">@Test</span> <span class="kw">public</span> <span class="dt">void</span> <span class="fu">testValidDefaultSAMAddress</span>() {</a>
<a class="sourceLine" id="cb4-2" title="2">        Jsam classUnderTest = <span class="kw">new</span> <span class="fu">Jsam</span>();</a>
<a class="sourceLine" id="cb4-3" title="3">        <span class="fu">assertEquals</span>(<span class="st">&quot;127.0.0.1:7656&quot;</span>, classUnderTest.<span class="fu">SAMAddress</span>());</a>
<a class="sourceLine" id="cb4-4" title="4">    }</a>
<a class="sourceLine" id="cb4-5" title="5">    <span class="at">@Test</span> <span class="kw">public</span> <span class="dt">void</span> <span class="fu">testValidDefaultSignatureType</span>() {</a>
<a class="sourceLine" id="cb4-6" title="6">        Jsam classUnderTest = <span class="kw">new</span> <span class="fu">Jsam</span>();</a>
<a class="sourceLine" id="cb4-7" title="7">        <span class="fu">assertEquals</span>(<span class="st">&quot;EdDSA_SHA512_Ed25519&quot;</span>, classUnderTest.<span class="fu">SignatureType</span>());</a>
<a class="sourceLine" id="cb4-8" title="8">    }</a></code></pre></div>
<p>Once that’s done, begin creating your constructor. Note that we’ve given our library defaults which will be useful in default situations on all existing I2P routers so far.</p>
<div class="sourceCode" id="cb5"><pre class="sourceCode java"><code class="sourceCode java"><a class="sourceLine" id="cb5-1" title="1">    <span class="kw">public</span> <span class="fu">Jsam</span>(<span class="bu">String</span> host, <span class="dt">int</span> port, SIGNATURE_TYPE sig) {</a>
<a class="sourceLine" id="cb5-2" title="2">        SAMHost = host;</a>
<a class="sourceLine" id="cb5-3" title="3">        SAMPort = port;</a>
<a class="sourceLine" id="cb5-4" title="4">        SigType = sig;</a>
<a class="sourceLine" id="cb5-5" title="5">    }</a></code></pre></div>
<h2 id="establishing-a-sam-connection">Establishing a SAM Connection</h2>
<p>Finally, the good part. Interaction with the SAM bridge is done by sending a “command” to the address of the SAM bridge, and you can parse the result of the command as a set of string-based key-value pairs.</p>
<div class="sourceCode" id="cb6"><pre class="sourceCode java"><code class="sourceCode java"></code></pre></div>


