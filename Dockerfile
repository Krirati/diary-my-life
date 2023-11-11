FROM jenkins/jenkins:jdk11
USER root
RUN cd ~/
RUN mkdir -p /opt/android-sdk/cmdline-tools/
RUN curl -o sdk.zip https://dl.google.com/android/repository/commandlinetools-linux-9477386_latest.zip && \
unzip -d ./opt/android-sdk/cmdline-tools sdk.zip && rm -rf sdk.zip
RUN mkdir -p ~/.android && touch ~/.android/repositories.cfg
RUN touch ~/.android/repositories.cfg
RUN mv ./opt/android-sdk/cmdline-tools/cmdline-tools/ ./opt/android-sdk/cmdline-tools/latest/
RUN ./opt/android-sdk/cmdline-tools/latest/bin/sdkmanager --version
RUN ./opt/android-sdk/cmdline-tools/latest/bin/sdkmanager --licenses
RUN yes | ./opt/android-sdk/cmdline-tools/latest/bin/sdkmanager "build-tools;30.0.1"
RUN yes | ./opt/android-sdk/cmdline-tools/latest/bin/sdkmanager "platforms;android-30"
