.PHONY: build

build:
	sbt package

clean:
	rm -rf target

run:
	./submit.sh