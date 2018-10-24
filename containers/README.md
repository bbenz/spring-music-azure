# README / DEMO

# azure

```bash
# let's assume we've already deployed our:
# - aks cluster
# - azure container registry
# and given the cluster permission to pull from the registry
# otherwise see DEMO-SETUP.md

# list our aks clusters
az aks list | jq .[].id

# set some bash variables, re-used throughout
RESOURCE_GROUP='181000-aks'
LOCATION='eastus'
KUBERNETES_SERVICE='aksbbenz'
CONTAINER_REGISTRY='bbenzacr'
CONTAINER_IMAGE='spring-music:v1'

# connect to aks cluster
az aks get-credentials -g $RESOURCE_GROUP -n $KUBERNETES_SERVICE
cd opentracing/

```

# helm

```bash
# see: https://docs.bitnami.com/azure/get-started-aks/

# configure rbac
kubectl create -f helm-clusterrole.yaml
kubectl create serviceaccount -n kube-system tiller
kubectl create clusterrolebinding tiller-cluster-rule --clusterrole=cluster-admin --serviceaccount=kube-system:tiller

# helm reset --force
helm init --upgrade --service-account tiller

# check that tiller is running
kubectl --namespace kube-system get pods --watch
# ctrl + c

```

# jeager

```bash
# see: https://hub.kubeapps.com/charts/stable/jaeger-operator
# see also: https://github.com/jaegertracing/jaeger-operator

helm repo update

# in case of error:
# helm list
# helm delete ...

# deploy jaeger-operator
helm install stable/jaeger-operator --version 1.0.1

# 'helm install' returns a command similar to the below (with errors). use this one instead.
export POD=$(kubectl get pods -l app.kubernetes.io/name=jaeger-operator --namespace default --output name)
kubectl logs $POD --namespace=default

# deploy jaeger
kubectl apply -f jaeger-simplest.yaml

# list jeager instances
kubectl get jaeger

# get jaeger pods by label
kubectl get pods -l jaeger=simplest

# get logs by label
kubectl logs -l jaeger=simplest

# port-forward
kubectl port-forward service/simplest-query 9090:16686
# open localhost:9090
# ctrl + c

```

# spring-music

```bash
# if you haven't already, clone and build https://github.com/bbenz/spring-music-azure via: 
git clone https://github.com/bbenz/spring-music-azure.git
cd spring-music-azure/
az acr build --registry $CONTAINER_REGISTRY --image $CONTAINER_IMAGE .
cd ../

# create deployment
kubectl apply -f kubernetes-deployment.yaml

kubectl get deploy --watch
# ctrl + c

# port-forward
kubectl port-forward deploy/spring-music 9090:8080
# open localhost:9090
# ctrl + c

# debug / test
kubectl describe deploy spring-music

kubectl get pods

kubectl logs ...

# scale
kubectl scale deployment spring-music --replicas=1

# create service
kubectl apply -f kubernetes-service.yaml

# port-forward
kubectl port-forward service/spring-music 9090:80
# open localhost:9090
# ctrl + c

kubectl get service --watch
# ctrl + c

IP_ADDRESS=$(kubectl get service spring-music -o json | jq -r .status.loadBalancer.ingress[0].ip)

# curl / hey / browser
curl --connect-timeout 1 "http://${IP_ADDRESS}"

hey "http://${IP_ADDRESS}"

open "http://${IP_ADDRESS}"

# check jaeger
# port-forward
kubectl port-forward service/simplest-query 9090:16686
# open localhost:9090
# ctrl + c

```

# hotrod

```bash
kubectl apply -f jaeger-example.yaml
kubectl port-forward deploy/hotrod 9090:8080

```

# cleanup

```bash
# see: DEMO-CLEANUP.md
```
