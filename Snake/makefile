default:
	javac -sourcepath src src/*.java -d out/src
	jar cmf src/manifest.mf out/Snake.jar -C out/src EndScreen.class -C out/src Apple.class -C out/src SnakeBody.class -C out/src Gamepanel.class -C out/src SnakeGame.class 

run: default
	java -jar out/Snake.jar

clean:	
	-@rm out/src/*.class
	-@rm out/*.jar

