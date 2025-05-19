FROM alpine:latest AS cert-builder
RUN apk add --no-cache openssl && \
    mkdir -p /etc/ssl/private /etc/ssl/cert && \
    openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
    -keyout /etc/ssl/private/private.key -out /etc/ssl/cert/private.crt \
    -subj "/C=RU/ST=Russia/L=Moscow/O=RUDN/CN=landing.rudn.ru"

FROM node:24 AS builder
WORKDIR /app
COPY . .
RUN npm ci && npm run build

FROM nginx:latest
RUN mkdir -p /logs
COPY --from=cert-builder /etc/ssl/private/private.key /etc/ssl/private/private.key
COPY --from=cert-builder /etc/ssl/cert/private.crt /etc/ssl/cert/private.crt
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/mime.types
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 80 443
