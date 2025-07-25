<h1>Sobre el Proyecto</h1>
<p>Trabajo Práctico Orientación a Objetos 2 Spring Boot.</p>
<p>Consiste en la creación de un sistema genérico para la gestión de Turnos: en los cuales se le puede asignar empleados, clientes, sucursales y servicios donde se brindan los mismos. Donde la organización y el orden son
claves para la eficiencia.</p>
<h2>Tecnologías usadas</h2>
<p>
  <ul>Visual Studio Code</ul>
  <ul>MySQL</ul>
  <ul>Git/Github</ul>
  <ul>Java 21</ul>
  <ul>HTML, CSS, Bootstrap, JS</ul>
</p>
<h2>Video Explicativo</h2>
<span> Link video primera entrega</span>
<p>https://drive.google.com/file/d/1CX-Hr-j7I-ZoxCaizOEOn7_mQvrGdS4n/view?usp=drive_link</p>
<span>Link video segunda entrega</span>
<p>https://drive.google.com/drive/folders/1V3AxVnhU5V6A9YANPLzf4IH6dqTN-66j?usp=sharing</p>
<h2>Como levantar el Proyecto</h2>
<p>Clonar el repositorio de GitHub.</p>
<p>Crear una base de datos nueva con el siguiente nombre: <strong>tp_spring_grupo8</strong></p>
<p>En el archivo 'application.yml' reemplazar:</p>
<h3>En el bloque datasource:</h3>
<p>url: ${DB_URL} -> url: (URL de su base de datos local)</p>
<p>username: ${USERNAME} -> username: (nombre de usuario de su base de datos local)</p>
<p>password: ${PASSWORD} -> password: (contraseña del usuario ingresado anteriormente)</p>
<h3>En el bloque mail:</h3>
<p>username: ${MAIL_USERNAME} -> username: clienteregistrado8@gmail.com</p>
<p>password: ${MAIL_PASSWORD} -> password: mnkxeubvzlnzshvt</p>
<h2></h2>
<p>Iniciar el proyecto y dejar compilar para la creacion de las tablas. </p>
<p>Una vez creada la DB utilizar el script para cargar con datos de prueba <a href="https://drive.google.com/file/d/1YABHUYv8p4yJOs-9goAGTmlHEYpgPU6v/view?usp=sharing">script para los datos</a></p>
<p>Al finalizar ya tendría la base de datos cargada con los datos necesarios.</p>
<p>El proyecto corre con la siguiente URL: localhost:8080</p>
<h2>Para el primer ingreso:</h2>
<p>Credenciales</p>
<p>Email: admin@admin.com </p>
<p>Contraseña: admin123</p>
 <br>
  <strong>Las credenciales de los empleados y cliente para el login son: </strong> <br>
"Email: email" <br>
"Contraseña: dni"</p>
<p>Ya estaria en condiciones de utilizar todas las funciones. </p>
<p><strong>*Aclaración_1: Si se registra desde el login se le asignara rol: Cliente, y puede elegir su contraseña a gusto.</strong></p>
<p><strong>*Aclaración_2: El rol: Cliente esta limitado en funcionalidad.</strong></p>
<h3>Definición de los casos de uso / funcionalidades:</h3>
<h3>Empleado,Administrador:</h3>
<p>Alta, baja y modificación de Turno</p>
<p>Alta, baja y modificación de Cliente</p>
<p>Alta, baja y modificación de Empleado</p>
<p>Alta, baja y modificación de Sucursal</p>
<p>Alta, baja y modificación de Servicio</p>
<p>Alta de Contacto</p>
<p>Alta y baja de Día</p>
<p>Alta y modificación de Disponibilidad</p>
<h3>Autores:</h3>
