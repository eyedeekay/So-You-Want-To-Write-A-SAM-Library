
index:
	pandoc INTRO.md | tee index.html
	pandoc SETUP.md| tee -a index.html
	pandoc SESSION.md | tee -a index.html
	pandoc OPTIONS.md | tee -a index.html
	cat INTRO.md SETUP.md SESSION.md OPTIONS.md > README.md

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
