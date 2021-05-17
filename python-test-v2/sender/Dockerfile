FROM python:3.8
RUN mkdir -p /app/myvol/storage
RUN mkdir -p /app/myvol/storage/models
RUN mkdir -p /app/myvol/current
RUN mkdir -p /app/myvol/models
COPY res.txt /app/myvol
COPY model_log.txt /app/myvol
VOLUME /app/myvol

WORKDIR /app

ENV VIRTUAL_ENV=/opt/venv
RUN python3 -m venv $VIRTUAL_ENV
ENV PATH="$VIRTUAL_ENV/bin:$PATH"

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY iofog /app/iofog
COPY index.py .

CMD ["python", "index.py", "--log", "DEBUG"]