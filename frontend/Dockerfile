FROM node:21 AS builder
WORKDIR /app
COPY . .
RUN npm install && npm run build

FROM nginx:latest
RUN mkdir -p /logs
COPY nginx.conf /etc/nginx/nginx.conf
COPY mime.types /etc/nginx/mime.types
COPY --from=builder /app/dist /usr/share/nginx/html
EXPOSE 3000
