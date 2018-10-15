FROM ubuntu:16.04
ADD . /openbox
WORKDIR /openbox

RUN /bin/bash -c 'pushd moonlight && ./install.sh'
RUN /bin/bash -c 'pushd MoonlightSnort && ./make-jar'
RUN /bin/bash -c 'pushd MoonlightFirewall && ./make-jar'
CMD /bin/bash -c 'pushd moonlight && ./start_moonlight'
