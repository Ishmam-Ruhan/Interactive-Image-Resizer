FROM nginx:alpine

COPY nginx.conf /etc/nginx/nginx.conf

COPY /dist/image-resizer-client /usr/share/nginx/html

EXPOSE 4200

CMD ["/bin/sh",  "-c",  "envsubst < /usr/share/nginx/html/assets/env.sample.js > /usr/share/nginx/html/assets/env.js && exec nginx -g 'daemon off;'"]