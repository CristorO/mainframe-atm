# Ejemplo de Cajero Automático como si fuera Mainframe.

## Funcionalidad

**Práctica de Línea de Comandos: Simulación de un Cajero Automático**

**Objetivo:**  
Desarrollar un programa interactivo en línea de comandos que simule las operaciones básicas de un cajero automático.

**Instrucciones:**

1. **Inicialización del Programa**:
    * Al iniciar el programa, se debe solicitar al usuario que ingrese un PIN (Número de Identificación Personal) de 4 dígitos.
    * Si el PIN es incorrecto después de 3 intentos, el programa debe cerrarse con un mensaje de error.
    * Si el PIN es correcto, el usuario puede acceder al menú principal.

2. **Menú Principal**:
    * El menú principal debe ofrecer las siguientes opciones:
        1. Consultar saldo.
        2. Realizar un depósito.
        3. Realizar un retiro.
        4. Cambiar PIN.
        5. Salir.

3. **Consultar Saldo**:
    * Mostrar el saldo actual del usuario.

4. **Realizar un Depósito**:
    * Solicitar al usuario que ingrese la cantidad que desea depositar.
    * Validar que la cantidad sea positiva.
    * Añadir la cantidad al saldo actual.
    * Mostrar un mensaje de confirmación.

5. **Realizar un Retiro**:
    * Solicitar al usuario que ingrese la cantidad que desea retirar.
    * Validar que la cantidad sea positiva y que no exceda el saldo actual.
    * Restar la cantidad del saldo actual.
    * Mostrar un mensaje de confirmación.

6. **Cambiar PIN**:
    * Solicitar al usuario que ingrese su PIN actual.
    * Si el PIN ingresado es correcto, pedir que ingrese el nuevo PIN.
    * Solicitar que confirme el nuevo PIN ingresándolo nuevamente.
    * Si ambos PINs coinciden, actualizar el PIN.

7. **Salir**:
    * Mostrar un mensaje de despedida y cerrar el programa.



## Requerimientos

Para correr este proyecto debe tener instalado:

 - Java 11
 - Maven 3.8.4

Se recomienda utilizar sdkman (Linux)

## Compilación

Para compilar el proyecto

```
mvn clean install
```

## Ejecución

```
mvn exec:java -Dexec.mainClass="bo.edu.ucb.sis213.App"
```

## Instalación de la Base de Datos

1. Hacer correr una instancia MySQL en docker

```
docker run --name mysql-atm -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:8
```

2. Me conecto a la BBDD (Le pedira password es 123456)

```
docker exec -it mysql-atm mysql -u root -p
```

3. Creamos la Base de Datos del ATM.

```
CREATE DATABASE atm;
```

4. Creamos la Base de Datos del ATM.

```
use atm;
```

5. Ejecutan el script init.sql de la carpeta database.

### Manual de Usuario

1. Inicialmente tenemos el inicio de sesion donde colocaremos el alias y password de un usuario
   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/fa358dc7-65c7-48b9-97ce-484e7988b7ad)

2. Luego tenemos el menu de opciones el cual cuenta con:
   
   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/139f9953-263f-40c0-8e22-dfc0a6cc4b62)

- Consultar saldo                                                     
   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/a5177b6f-fe1e-4c8f-af9e-b7eddcec2881)

- Realizar deposito: donde colocaremos un valor para aumentar el saldo (si el valor es alfabetico no realizara ninguna accion)
   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/9ca59fda-3bb5-4071-bcd1-205bbfc74d98)
   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/0cb4d7e5-6b75-4c2e-ad17-fbedcf51918d)

- Realizar retiro: donde colocaremos un valor para disminuir el saldo (si el valor es alfabetico o el monto del retiro es mayor al del saldo no se realizara ninguna accion)

   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/3d14bc5c-7c0d-46cb-bf9a-cb804ba6f1a5)
   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/ae4b585d-207d-4f42-8e69-f2f914f7237d)

- Cambiar password: Inicialmente ingresaremos el password que usemos (Si se ingresan valores alfabeticos no se realizara ninguna accion)

   ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/001e14b2-ece3-4de5-a4d3-dc9f8756bd3f)

   Si no nos equivocamos de password procederemos a colocar el nuevo password y confirmar el mismo
      ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/ec91ad7f-cc8b-42aa-b413-76643e04d060)
      ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/a14d9dd9-4b31-4ee6-9a70-8b23b3f5c53d)
      ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/5d668835-a14f-426f-a89f-f4cacfae17cb)
      
   Caso contrario un mensaje de alerta nos informara el error de password o de confirmacion del nuevo password
      ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/a31e0645-a63a-4092-bff4-5f460ff96ebe)
      ![imagen](https://github.com/CristorO/mainframe-atm/assets/125508127/f9c23be6-6f89-462a-a684-7d75253c049a)

- Salir
