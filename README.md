# vgnh

[More about the name](http://www.rot13.com)

## Plano de Trabalho
O plano de trabalho (item 1) pode ser encontrado no arquivo [docs/PLAN.md](docs/PLAN.md)

## Relatório Técnico
O relatório técnico (item 8) pode ser encontrado no arquivo [docs/REPORT.md](docs/PLAN.md)

## Para construir a imagem docker

*Funciona para Ubuntu ou qualquer sistema que utilize o mecanismos de `sudo`*

```sh
git clone https://github.com/acasimiro/vgnh.git
cd vgnh/docker
./build-docker.sh
```

## Para testar a solução
```sh
docker run -it -p 8080:8080 -p 3000:3000 acasimiro/vgnh

# ... espere 1 minuto até todas as aplicações subirem ...

./item4.sh
./item7.sh
```
