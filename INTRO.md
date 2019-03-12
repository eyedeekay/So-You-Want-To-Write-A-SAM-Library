So You Want To Write A SAM Library
==================================

*Or, talking to i2p for people who aren't really used to reading specs*

One of the best features of I2P, in my opinion, is it's SAM API, which can be
used to build a bridge between I2P and your application or language of choice.
Currently, dozens of SAM libraries exist for a variety of languages, including:

  * [i2psam, for c++](https://github.com/i2p/i2psam)
  * [libsam3, for C](https://github.com/i2p/libsam3)
  * [txi2p for Python](https://github.com/str4d/txi2p)
  * [i2plib for Python](https://github.com/l-n-s/i2plib)
  * [i2p.socket for Python](https://github.com/majestrate/i2p.socket)
  * [leaflet for Python](https://github.com/MuxZeroNet/leaflet)
  * [gosam, for Go](https://github.com/eyedeekay/gosam)
  * [sam3 for Go](https://github.com/eyedeekay/sam3)
  * [node-i2p for nodejs](https://github.com/redhog/node-i2p)
  * [haskell-network-anonymous-i2p](https://github.com/solatis/haskell-network-anonymous-i2p)
  * [i2pdotnet for .Net languages](https://github.com/SamuelFisher/i2pdotnet)
  * [rust-i2p](https://github.com/stallmanifold/rust-i2p)
  * [and i2p.rb for ruby](https://github.com/dryruby/i2p.rb)

If you're using any of these languages, you may be able to port your application
to I2P already, using an existing library. That's not what this tutorial is
about, though. This tutorial is about what to do if you want to create a SAM
library in a new language. In this tutorial, I will implement a new SAM library
in Java. I chose Java because there isn't a Java library that connects you to
SAM yet, because of Java's use in Android, and because it's a language almost
everybody has at least a *little* experience with, so hopefully you can
translate it into a language of your choice.

Creating your library
---------------------

How you set up your own library will vary depending on the language you wish
to use. For this example library, we'll be using java so we can create a library
like this:

``` sh
mkdir jsam
cd jsam
gradle init --type java-library
```

Or, if you are using gradle 5 or greater:

``` sh
gradle init --type java-library --project-name jsam
```
