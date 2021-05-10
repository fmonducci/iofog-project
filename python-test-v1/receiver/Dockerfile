FROM python:3.8
RUN mkdir -p /app/myvol
COPY res.txt /app/myvol
VOLUME /app/myvol

WORKDIR /app

COPY requirements.txt requirements.txt
RUN pip3 install -r requirements.txt

COPY iofog /app/iofog
COPY index.py .

CMD ["python", "index.py", "--log", "DEBUG"]