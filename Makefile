## ## Makefile
##
##  usage:

help: # show help
	@echo ""
	@grep "^##" $(MAKEFILE_LIST) | grep -v grep
	@echo ""
	@grep "^[0-9a-zA-Z\-]*: #" $(MAKEFILE_LIST) | grep -v grep
	@echo ""

logand: # show log for android
	adb logcat | grep `adb shell ps | grep com.android.utils.androidmemtest | cut -c10-15`

logmem: # log android memory
	adb shell dumpsys meminfo com.android.utils.androidmemtest -d

andstart: # show log for android
	adb shell am start -n com.android.utils.androidmemtest/.MainActivity

build-dev: # build
	./gradlew installDebug

buildandrun: # build and run
	make build-dev && make andstart

unzip: # unzip the jar file
	uncomp SubProjects/AndroidMem/build/outputs/aar/AndroidMem-release.aar -d t -t .zip
	cp t/classes.jar .
	rm -frv t
