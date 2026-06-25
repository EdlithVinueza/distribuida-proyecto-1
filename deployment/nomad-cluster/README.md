# Instalar packer
```bash

packer init xx.pkr.hcl
packer build xx.pkr.hcl
```

# Editar la red
```bash
nano /etc/network/interfaces
```
* Cambiar la IP y Gateway

## Reiniciar la red
```
rc-service networking restart
```
# Configuración Nomad
```
nano /etc/nomad.d/nomad.hcl
```
* Cambiar la IP del servidor
 

## Reiniciar nomad

```bash
/etc/init.d/nomad restart
```
