# CasosCovidUS
--------------------------------------------------------------------------------
Respuestas del cuestionario

### Pregunta 1: ¿Qué es MVVM?

MVVM son las siglas de Model-View-ViewModel. Es una arquitectura para el código
en android, tal como MVC y MVP. La idea de esta arquitectura es separar la parte
visual (UI) de la lógica de negocio, donde la UI será manejada por la componente
Vista mientras que la lógica por el Modelo, y el ViewModel nos servirá como
enlace de comunicación entre ambos.

Un flujo de trabajo bajo esta arquitectura sería:
1. Usuario interactura con la UI
2. Vista notifica al ViewModel sobre las interacciones
3. ViewModel ejecuta alguna acción necesaria para actualizar la interfaz o
notifica al Modelo sobre los datos que seran necesarios para que ejecute la
lógica de negocio
4. Modelo informa al View Model sobre los datos procesados o cambios realizado
5. ViewModel notifica a la Vista sobre los cambios que sean necesarios para
mostrar o cambiar algo en la interfaz

Aparte de la separación de las resonsabilidades a cada componente, otro de sus
beneficios es que ya no se necesita instanciar a la Vista dentro del View Model,
esto facilita la tarea cuando se realizan pruebas unitarias.

### Pregunta 2: Actividad vs Fragmento

No son iguales, mientras la actividad tiene un ciclo de vida independiente. El
ciclo de vida de un fragmento está asociado a la actividad que está enlazada.
En la comparación en la parte visual, se puede destacar que una actividad
representa una pantalla de la aplicación, mientras que un fragmento no representa
una pantalla necesariamente. Los fragmentos pueden tener un tamaño menor a una
pantalla y combinarse con otros para formar la UI. Los fragmentos pueden ser
vistos como hojas de papel, que pueden superponerse o tener otra forma de arreglo
visual.

### Pregunta 3: Beneficios databinding y viewbinding

El view binding facilita el proceso de la referencia de los views gracias al
binding que se genera por cada layout. Esto evita usar el método findViewById
para obtener las referencias, además de evitar los errores al hacer búsqueda de
un id que no existe.

El data binding facilita el proceso de la referencia entre un modelo y la interfaz,
de forma que los cambios de una afectan a otra. Esto evita usar código para hacer
los cambios manualmente.

### Pregunta 4: Método que recibe @Body como parámetro

Los métodos que pueden recibir un parámetro con la anotación @Body son los métodos
POST, PUT y DELETE. Mientras que el método GET no puede hacerlo, y requiere de la
anotación @Path para enviar parámetros.
