# FEM.PRC2.AlejandroPueblaHolguin
# Asignatura: *Front-end para Móviles*
#### [Máster en Ingeniería Web por la U.P.M.](http://miw.etsisi.upm.es)
### Alejandro Puebla Holguín
Se pide realizar una aplicación móvil para una empresa de repartos (p.ej. deliveroo, la casa del libro, amazon, reparto de helados, etc…) que gestione la trazabilidad de sus productos, entregas y que cubra la siguiente funcionalidad:

#### Autentificación de usuarios
* Es decir, autenticar los repartidores de la empresa. Para ello se empleará el servicio Firebase Authentication.
####  Acceso a un servicio externo
* Api restful Web service para la obtención de información relevante para la aplicación escogida (por ejemplo: lista de productos, lista de ciudades de reparto, etc…). Se sugiere utilizar ANY-API (https://any-api.com/) o Mashape (https://market.mashape.com/explore) para identificar apis sobre cualquier temática. Se recomienda utilizar las librerías Volley o Retrofit.
#### Gestión de repartos y trazabilidad de entregas (Firebase Realtime Database)
* Registrar fecha/hora de inicio del reparto, registrar las localizaciones intermedias del producto, fecha/hora de recogida/entrega, y registrar incidencias en la ruta. Para alojar esta información se utilizará Firebase Realtime Database. Registrar fecha/hora de inicio del reparto, registrar las localizaciones intermedias del producto, fecha/hora de recogida/entrega, y registrar incidencias en la ruta. Para alojar esta información se utilizará Firebase Realtime Database. Adicionalmente se valorará positivamente el registro de imágenes en Firebase Storage: por ejemplo el código de barras (id paquete), fotografías para reportar sobre un paquete defectuoso, un accidente, etc.