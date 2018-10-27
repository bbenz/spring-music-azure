# DEMO CLEANUP

```bash
# helm
kubectl delete -f helm-clusterrole.yaml
kubectl delete serviceaccount -n kube-system tiller
kubectl delete clusterrolebinding tiller-cluster-rule

# jeager
kubectl delete -f jaeger-simplest.yaml

# spring-music
kubectl delete -f kubernetes-deployment.yaml
kubectl delete -f kubernetes-service.yaml

# hotrod
kubectl delete -f jaeger-example.yaml

```
