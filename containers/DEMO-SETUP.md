# DEMO SETUP

# azure

```bash
# 1. define bash variables
RESOURCE_GROUP='180900-test'
LOCATION='eastus'
if [ -z "$RANDOM_STR" ]; then RANDOM_STR=$(openssl rand -hex 3); else echo $RANDOM_STR; fi
# RANDOM_STR='9f889c'
CONTAINER_REGISTRY=acr${RANDOM_STR}
CONTAINER_IMAGE='spring-music:v1'
KUBERNETES_SERVICE=aks${RANDOM_STR}

# 2. deploy and configure aks, acr and kubectl, as neccessary
az group create --name $RESOURCE_GROUP --location $LOCATION
az acr create -g $RESOURCE_GROUP -n $CONTAINER_REGISTRY --sku Basic
az aks create -g $RESOURCE_GROUP -n $KUBERNETES_SERVICE --node-count 3 --generate-ssh-keys

# get the id of the service principal configured for AKS, get the ACR registry resource id, and create role assignment
CLIENT_ID=$(az aks show -g $RESOURCE_GROUP -n $KUBERNETES_SERVICE --query "servicePrincipalProfile.clientId" --output tsv)
CONTAINER_REGISTRY_ID=$(az acr show -g $RESOURCE_GROUP -n $CONTAINER_REGISTRY --query "id" --output tsv)
az role assignment create --assignee $CLIENT_ID --scope $CONTAINER_REGISTRY_ID --role Reader

# install cli and connect to aks cluster
az aks install-cli
az aks get-credentials -g $RESOURCE_GROUP -n $KUBERNETES_SERVICE
```

# spring-music

```bash
# git clone
git clone https://github.com/bbenz/spring-music-azure.git
cd spring-music-azure/

# build using acr
az acr build --registry $CONTAINER_REGISTRY --image $CONTAINER_IMAGE .
cd ../

# or build locally and push (as a backup)
cd spring-music-azure/
az acr login --name $CONTAINER_REGISTRY
docker pull "${CONTAINER_REGISTRY}.azurecr.io/${CONTAINER_IMAGE}"
docker build -t "${CONTAINER_REGISTRY}.azurecr.io/${CONTAINER_IMAGE}" . 
docker push "${CONTAINER_REGISTRY}.azurecr.io/${CONTAINER_IMAGE}"
cd ../

```
