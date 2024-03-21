kubectl delete -n default deployment keycloak
kubectl delete -n default deployment fdifrison-configmaps
kubectl delete -n default deployment config-server-deployment
kubectl delete -n default deployment service-discovery-agent-deployment
kubectl delete -n default deployment accounts-deployment
kubectl delete -n default deployment loans-deployment
kubectl delete -n default deployment cards-deployment
kubectl delete -n default deployment gateway-server-deployment



kubectl apply -f keycloak.yml
kubectl apply -f configmaps.yml
kubectl apply -f configserver.yml
kubectl apply -f eurekaserver.yml
kubectl apply -f accounts.yml
kubectl apply -f loans.yml
kubectl apply -f cards.yml
kubectl apply -f gatewayForLast.yml