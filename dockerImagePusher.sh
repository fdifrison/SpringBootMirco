cd accounts || exit

docker push  docker.io/fdifrison/accounts:0.0.10

cd ..

cd cards || exit

docker push docker.io/fdifrison/cards:0.0.10

cd ..

cd loans || exit

docker push docker.io/fdifrison/loans:0.0.10

cd ..

cd config-server || exit

docker push docker.io/fdifrison/config-server:0.0.10

cd ..

cd gateway-server || exit

docker push docker.io/fdifrison/gateway-server:0.0.10

cd ..

cd service-discovery-agent || exit

docker push docker.io/fdifrison/service-discovery-agent:0.0.10

cd ..

cd message || exit

docker push docker.io/fdifrison/message:0.0.10
