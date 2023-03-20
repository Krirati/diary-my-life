FROM jenkins/jenkins:lts-jdk8
WORKDIR /var/jenkins_home/
RUN curl -o sdk.zip https://dl.google.com/android/repository/sdk-tools-linux-4333796.zip && \
unzip -d sdk sdk.zip && rm -rf sdk.zip
RUN mkdir -p ~/.android && touch ~/.android/repositories.cfg
RUN touch ~/.android/repositories.cfg
RUN ./sdk/tools/bin/sdkmanager --licenses
RUN yes | ./sdk/tools/bin/sdkmanager "platforms;android-33" && yes | ./sdk/tools/bin/sdkmanager "build-tools;30.0.3"
