default:
	javac -sourcepath src src/*.java -d out/src
	jar cmf src/manifest.mf out/Fotag.jar -C out/src AlignLayout.class -C out/src StarMouse.class -C out/src FilterStar.class -C out/src FilterStarMouse.class -C out/src FilterStarRating.class -C out/src MyWindowA.class -C out/src Fotag.class -C out/src MainFrame.class -C out/src Toolbar.class -C out/src MainPanel.class -C out/src PicPanel.class -C out/src PicButton.class -C out/src PicDescripPanel.class -C out/src StarRating.class -C out/src Star.class -C out/src Grid_button_Listener.class -C out/src List_button_Listener.class -C out/src Pic_Frame.class -C out/src Pic_MouseListener.class -C out/src Load_button_Listener.class -C out/src Clear_button_Listener.class

run: default
	java -jar out/Fotag.jar

clean:	
	-@rm out/src/*.class
	-@rm out/*.jar

