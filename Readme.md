<h2>Task condition</h2>

There is a Car entity, it has 
- an identifier; 
- model name;
- color;
- price

<p>The task is to create a project. Adding a table to the database must be done through liquibase,
make tests using H2.</p> 
<p>Cover functionality with tests and make a report using the jacoco plugin.</p>
<p>Connect checkstyle to the project.</p>
<p>For the Car entity, make a DAO over each field, write your own annotation MyColumn(name - the name of the column) with the name of the column, write the annotation MyTable(name - the name of the table) above the Car class. Implement CRUD operations on Car using jdbc</p>

- select
- update
- delete
- insert

<p>Moreover, these operations should make a request to the database using the annotations MyColumn and MyTable (through reflection). i.e. if the user of this API creates another entity, then</p>

- select
- update
- delete
- insert 
<p>should work without changing the internal logic</p>

<hr>

<h3>Условия задачи</h3>
Есть сущность Car, у нее есть 
- индификатор, 
- модель, 
- цвет,
- цена.
<p>Задание создать проект.</p>
<p>Добавление таблицы в базу необходимо сделать через liquibase,
сделать тесты используя H2. Покрыть функционал тестами
и сделать отчет используя плагин jacoco.
Подключить checkstyle к проекту.</p>
<p>Для сущности Car сделать DAO над каждым полем написать свою
аннотацию  MyColumn(name - название колонки) с названием колонки, над классом Car написать аннотацию MyTable(name - название таблицы). Реализовать CRUD операции с Car используя jdbc</p>

- select
- update
- delete
- insert
<p>Причем эти операции должны составлять запрос в базу используя аннотации MyColumn и MyTable (через рефлексию). T.е. если пользователь данного API создаст другую сущность то</p>

- select
- update
- delete
- insert 
<p>должны работать без изменения внутреней логики</p>