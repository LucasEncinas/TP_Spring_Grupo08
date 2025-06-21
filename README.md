<h1>Sobre el Proyecto</h1>
<p>Trabajo Práctico Orientación a Objetos 2 Primera parte Spring Boot.</p>
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
<p>https://drive.google.com/file/d/1CX-Hr-j7I-ZoxCaizOEOn7_mQvrGdS4n/view?usp=drive_link</p>
<h2>Como levantar el Proyecto</h2>
<p>Clonar el repositorio de GitHub.</p>
<p>Crear una base de datos nueva con el siguiente nombre: <strong>tp_spring_grupo8</strong></p>
<p>En el archivo 'application.yml' reemplazar:</p>
<h3>En el bloque datasource:</h3>
<p>url: ${DB_URL} -> url: (URL de su base de datos local)</p>
<p>username: ${USERNAME} -> username: (nombre de usuario de su base de datos local)</p>
<p>password: ${PASSWORD} -> password: (contraseña del usuario ingresado anteriormente)</p>
<h3>En el bloque security:</h3>
<p>name: ${MAIL_USERNAME} -> name: clienteregistrado8@gmail.com</p>
<h3>En el bloque mail:</h3>
<p>username: ${MAIL_USERNAME} -> username: clienteregistrado8@gmail.com</p>
<p>password: ${MAIL_PASSWORD} -> password: mnkxeubvzlnzshvt</p>
<h2></h2>
<p>Iniciar el proyecto y dejar compilar para la creacion de las tablas. </p>
<p>Una vez creada la DB utilizar el script para cargar con datos de prueba <a href="https://drive.google.com/file/d/1sOx3dZqHdf0mpWoP2r6ZTcNgId8BYw70/view?usp=sharing">script para los datos</a></p>
<p>Al finalizar ya tendría la base de datos cargada con los datos de prueba.</p>
<p>El proyecto corre con la siguiente URL: localhost:8080</p>
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
<p>
  <ul a link href>https://github.com/camiroldan017</ul>
  <ul a link href>https://github.com/MicaelaInsfran02</ul>
  <ul a link href>https://github.com/LucaasCardozo</ul>
  <ul a link href>https://github.com/LucasEncinas</ul>
</p>
