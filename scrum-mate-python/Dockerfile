FROM tesseractshadow/tesseract4re

RUN apt-get update \
  && apt-get install -y python3-pip python3-dev \
  && cd /usr/local/bin \
  && ln -s /usr/bin/python3 python \
  && pip3 install --upgrade pip

RUN mkdir service

COPY requirements.txt ./
COPY httpServer.py ./
COPY mainQr.py ./
COPY AgileBoardQr.jpg ./
COPY service/* ./service/

RUN pip3 install -r requirements.txt
RUN apt-get update && \
    apt-get install -y libsm6 libxext6 && \
    apt-get install -y build-essential libzbar-dev

EXPOSE 8081
ENTRYPOINT exec python httpServer.py