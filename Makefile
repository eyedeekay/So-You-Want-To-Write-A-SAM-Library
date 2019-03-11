
index:
	pandoc -f gfm INTRO.md -o - | tee index.html
	pandoc -f gfm SETUP.md -o - | tee -a index.html
	pandoc -f gfm SESSION.md -o - | tee -a index.html
	pandoc -f gfm OPTIONS.md -o - | tee -a index.html
	cat INTRO.md SETUP.md SESSION.md OPTIONS.md > README.md
	pandoc --highlight-style=tango -f gfm README.md -t html5 -o README.pdf

build:
	cd jsam && \
		gradle build

clean:
	cd jsam && \
		gradle clean

test:
	cd jsam && \
		gradle test

fmt:
	find . -name '*.java' -exec astyle -A2 {} \;
	find . -name '*.java.orig' -exec rm -f {} \;
