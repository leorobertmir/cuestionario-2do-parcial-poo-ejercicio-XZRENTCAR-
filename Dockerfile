# Dockerfile para crear una imagen de MySQL 5.7
# Utiliza la imagen base oficial de MySQL 5.7
FROM mysql:5.7
# Establece la variable de entorno para la contraseña del root
ENV MYSQL_ROOT_PASSWORD=root   
# Establece la variable de entorno para el nombre de la base de datos
ENV MYSQL_DATABASE=xzrentcar
# Establece las variables de entorno para el usuario y la contraseña
ENV MYSQL_USER=renta
ENV MYSQL_PASSWORD=Rentas2025.

# Exponer el puerto 3306 para conexiones externas
EXPOSE 3306
# Comando por defecto para iniciar el servidor MySQL
CMD ["mysqld"]
# Fin del Dockerfile