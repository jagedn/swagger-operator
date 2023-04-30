# Swagger-Operator

## Cluster local

`docker run -d --name registry.localhost --restart=always -v $HOME/registry:/var/lib/registry -p 5000:5000 registry`

./etc/docker/daemon.json
```
{
    "debug": true,
    "experimental": false,
    "insecure-registries": [
        "registry.localhost:5000"
    ],
    "registry-mirrors": [
        "https://registry-1.docker.io"
    ]
}
```

`k3d cluster create my-local --port 9999:80@loadbalancer -v $HOME/k3d-registries.yaml:/etc/rancher/k3s/registries.yaml`

`docker network connect k3d-my-local registry.localhost`

## Build

Desde service-example:

`./gradlew jib`

Desde swagger-operator:

`./gradlew jib`


## Deploy examples

`kubectl apply -f service-example/service-1.yml`

`kubectl apply -f service-example/service-2.yml`

## Deploy swagger (para validar)

`kubectl apply -f service-example/swagger-ui.yml  -f service-example/swagger-ui-service.yml`

Validar swagger en `http://localhost:9999/swagger-ui/` se ven service1 y service2

Una vez validado borrar swagger-ui

`kubectl delete -f service-example/swagger-ui.yml`

## Deploy operator

`kubectl apply -f swagger-operator/src/k8s/crd.yml`

`kubectl apply -f swagger-operator/src/k8s/operator-roles.yml`

`kubectl apply -f swagger-operator/src/k8s/deployment.yml`

`kubectl apply -f swagger-operator/src/k8s/operator-example.yml`

Validar swagger en `http://localhost:9999/swagger-ui/` se ven service1 y service2

Eliminar un servicio:

`kubectl delete -f service-example/service-2.yml`

Esperar 10 segundos y refrescar navegador, debería aparecer solo service1
